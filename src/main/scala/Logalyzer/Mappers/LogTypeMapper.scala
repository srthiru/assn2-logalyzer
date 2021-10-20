package Logalyzer.Mappers

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path

import java.util.StringTokenizer
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

import scala.util.matching.Regex

class LogTypeMapper extends Mapper[Object, Text, Text, IntWritable] {

  val log = LogFactory.getLog(this.getClass)

  val count = new IntWritable(0)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    val distribution = value.toString
    val split = distribution.split(",")

    val logType = split(1)
    val logCount = split(3)

    word.set(logType)
    count.set(logCount.toInt)

    context.write(word, count)
  }
}
