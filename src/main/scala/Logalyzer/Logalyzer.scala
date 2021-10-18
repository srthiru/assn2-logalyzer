package Logalyzer

import Mappers.{LogTypeMapper, TypePatternMapper}
import Reducers.LogStatAggregator
import org.apache.commons.logging.LogFactory
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

object Loganalyzer {

  val log = LogFactory.getLog(this.getClass)

  def runMapReduce(hadoopConf: Configuration, jobName: String, inPath: String, outPath: String, mapClass: String, reduceClass: Class[_]): Int = {

    val job = Job.getInstance(hadoopConf, jobName)
    job.setJarByClass(this.getClass)
    job.setMapperClass(classOf[TypePatternMapper])
    job.setCombinerClass(classOf[LogStatAggregator])
    job.setReducerClass(classOf[LogStatAggregator])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputKeyClass(classOf[Text]);
    job.setOutputValueClass(classOf[IntWritable]);
    FileInputFormat.addInputPath(job, new Path(inPath))
    FileOutputFormat.setOutputPath(job, new Path(outPath))
    if (job.waitForCompletion(true)) 0 else 1
  }

  def main(args: Array[String]): Unit = {
    val hadoopConf = new Configuration

    val jobCompleted = runMapReduce(hadoopConf, "type_pattern_interval", args(0), args(1), "TypePatternMapper", classOf[LogStatAggregator])

    if (jobCompleted == 0) System.exit(1);
  }
}
