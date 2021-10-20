package Logalyzer.Mappers

import Logalyzer.IntervalCountPair
import jdk.nashorn.internal.runtime.regexp.joni.Regex
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class ErrorMapper  extends Mapper[Object, Text, Text, IntWritable]{

  val one = new IntWritable(2)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    val msg = value.toString

    if(msg.contains("ERROR") && msg.contains("has_pattern")) {
//      word.set(List(logCount, interval).mkString(","))
      val msgSplit = msg.split(",")
      val interval = msgSplit(0)
      val logCount = msgSplit(3).toInt
      val reducerKey = new IntervalCountPair
      reducerKey.setCount(logCount)
      reducerKey.setInterval(interval)

      word.set(interval)
      one.set(logCount)

      context.write(word, one)
    }
  }

}
