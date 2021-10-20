package Logalyzer.Reducers

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import java.lang
import scala.collection.JavaConverters.iterableAsScalaIterableConverter

class MaxCharLengthReducer  extends Reducer[Text,IntWritable,Text,IntWritable] {

  override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    val max = values.asScala.foldLeft(0)(_ max _.get)
    context.write(key, new IntWritable(max))
  }

}