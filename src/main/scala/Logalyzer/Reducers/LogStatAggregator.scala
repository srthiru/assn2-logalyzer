package Logalyzer.Reducers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

// Reducer class to sum the log counts based on the key
import scala.collection.JavaConverters.iterableAsScalaIterableConverter

class LogStatAggregator extends Reducer[Text,IntWritable,Text,IntWritable] {
  override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    val sum = values.asScala.foldLeft(0)(_ + _.get)
    context.write(key, new IntWritable(sum))
  }
}
