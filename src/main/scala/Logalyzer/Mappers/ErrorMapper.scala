package Logalyzer.Mappers

import Logalyzer.CompositeKey.IntervalCountPair
import jdk.nashorn.internal.runtime.regexp.joni.Regex
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

// Mapper class to map input keys with intervals and values as log counts from output of Task 1
class ErrorMapper  extends Mapper[Object, Text, Text, IntWritable]{

  val one = new IntWritable(2)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    val msg = value.toString

    // If Log type is error and it contains the pattern
    if(msg.contains("ERROR") && msg.contains("has_pattern")) {
      val msgSplit = msg.split(",")
      val interval = msgSplit(0)
      val logCount = msgSplit(3).toInt
      val reducerKey = new IntervalCountPair
      reducerKey.setCount(logCount)
      reducerKey.setInterval(interval)

      // write out the interval and count from that interval
      word.set(interval)
      one.set(logCount)

      context.write(word, one)
    }
  }

}
