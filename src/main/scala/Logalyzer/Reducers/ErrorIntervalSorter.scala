package Logalyzer.Reducers

import Logalyzer.CompositeKey.IntervalCountPair
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import scala.collection.JavaConverters.iterableAsScalaIterableConverter
import scala.collection.immutable.{ListMap, TreeMap}
import scala.collection.mutable
import java.util
import java.util.Collections
import java.util.Comparator
import java.util.Map.Entry
import scala.jdk.CollectionConverters.*

class ErrorIntervalSorter extends Reducer[Text,IntWritable,Text,IntWritable] {

  val result = mutable.Map[String, Int]()

  override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {

    values.forEach(value => result.put(key.toString, value.get()))
  }

  override def cleanup(context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {

    val sorted = ListMap(result.toSeq.sortWith(_._2 > _._2):_*)

    sorted.foreach((key, value) => {
      context.write(new Text(key), new IntWritable(value))
    })
  }
}