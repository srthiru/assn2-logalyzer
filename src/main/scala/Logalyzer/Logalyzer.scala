package Logalyzer

import Mappers.{CharLengthMapper, ErrorMapper, LogTypeMapper, TypePatternMapper}
import Reducers.{ErrorIntervalSorter, LogStatAggregator, MaxCharLengthReducer}
import com.typesafe.config.ConfigFactory
import org.apache.commons.logging.LogFactory
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.fs
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}

import java.lang.Class.forName

object Loganalyzer {

  val log = LogFactory.getLog(this.getClass)
  val appConfig = ConfigFactory.load()

  def main(args: Array[String]): Unit = {

    log.info("Starting Logalyzer")

    //Get hadoop configuration
    val hadoopConf = new Configuration
    //Set output format to be comma-separated values
    hadoopConf.set("mapred.textoutputformat.separator", ",")

    //Get a reference to Hadoop file system
    val fs = FileSystem.get(hadoopConf)

    val task1Input = new Path(appConfig.getString("task1input"))
    val task1Output = new Path(appConfig.getString("task1output"))
    if fs.exists(task1Output) then fs.delete(task1Output, true)

    // Task 1: Compute the distribution of log messages by log type, interval and presence of injected pattern
    val job1 = Job.getInstance(hadoopConf, "distribution")
    job1.setJarByClass(this.getClass)
    job1.setMapperClass(classOf[TypePatternMapper])
    job1.setCombinerClass(classOf[LogStatAggregator])
    job1.setReducerClass(classOf[LogStatAggregator])
    job1.setOutputKeyClass(classOf[Text])
    job1.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job1, task1Input)
    FileOutputFormat.setOutputPath(job1, task1Output)
    val task1Completed = job1.waitForCompletion(true)

    if (!task1Completed) log.error("Failed task 1")

    if(task1Completed){
      // Task 2: Sort the intervals by number of log messages in descending order
      // for the log message type ERROR that has the injected pattern
      val task2Output = new Path(appConfig.getString("task2output"))
      if fs.exists(task2Output) then fs.delete(task2Output, true)

      val job2 = Job.getInstance(hadoopConf, "error_sorter")
      job2.setJarByClass(this.getClass)
      job2.setMapperClass(classOf[ErrorMapper])
      //    job2.setPartitionerClass(classOf[IntervalCountPartitioner])
      //    job2.setGroupingComparatorClass(classOf[IntervalCountGroupingComparator])
      job2.setCombinerClass(classOf[ErrorIntervalSorter])
      job2.setReducerClass(classOf[ErrorIntervalSorter])
      job2.setNumReduceTasks(1)
      //    job2.setMapOutputKeyClass(classOf[IntervalCountPair])
      job2.setOutputKeyClass(classOf[Text])
      job2.setOutputValueClass(classOf[IntWritable])
      FileInputFormat.addInputPath(job2, task1Output)
      FileOutputFormat.setOutputPath(job2, task2Output)

      if (!job2.waitForCompletion(true))
        log.error("Failed task 2")

      // Task 3: Compute the number of log messages by log type
      val task3Output = new Path(appConfig.getString("task3output"))
      if fs.exists(task3Output) then fs.delete(task3Output, true)

      val job3 = Job.getInstance(hadoopConf, "log_type_total")
      job3.setJarByClass(this.getClass)
      job3.setMapperClass(classOf[LogTypeMapper])
      //    job3.setPartitionerClass(classOf[IntervalCountPartitioner])
      //    job3.setGroupingComparatorClass(classOf[IntervalCountGroupingComparator])
      job3.setCombinerClass(classOf[LogStatAggregator])
      job3.setReducerClass(classOf[LogStatAggregator])
      //    job3.setMapOutputKeyClass(classOf[IntervalCountPair])
      job3.setOutputKeyClass(classOf[Text])
      job3.setOutputValueClass(classOf[IntWritable])
      FileInputFormat.addInputPath(job3, task1Output)
      FileOutputFormat.setOutputPath(job3, task3Output)

      if (!job3.waitForCompletion(true))
        log.error("Failed task 3")

    }

    // Task 4: Compute the maximum number of characters in messages with the injected pattern for each log type
    val task4Output = new Path(appConfig.getString("task4output"))
    if fs.exists(task4Output) then fs.delete(task4Output, true)

    val job4 = Job.getInstance(hadoopConf, "maxlengthlog")
    job4.setJarByClass(this.getClass)
    job4.setMapperClass(classOf[CharLengthMapper])
    //    job4.setPartitionerClass(classOf[IntervalCountPartitioner])
    //    job4.setGroupingComparatorClass(classOf[IntervalCountGroupingComparator])
    job4.setCombinerClass(classOf[MaxCharLengthReducer])
    job4.setReducerClass(classOf[MaxCharLengthReducer])
    //    job4.setMapOutputKeyClass(classOf[IntervalCountPair])
    job4.setOutputKeyClass(classOf[Text])
    job4.setOutputValueClass(classOf[IntWritable])
    FileInputFormat.addInputPath(job4, task1Input)
    FileOutputFormat.setOutputPath(job4, task4Output)

    if (!job4.waitForCompletion(true)) log.error("Failed task 4")
  }
}
