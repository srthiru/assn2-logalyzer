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

  val one = new IntWritable(1)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val itr = new StringTokenizer(value.toString)
    while (itr.hasMoreTokens()) {
      val msg = itr.nextToken()
      val typePattern = new Regex("WARN|INFO|ERROR|DEBUG")
      log.info("this is the pattern" + typePattern)
      val foundType = typePattern.findFirstIn(msg)

      log.info("this is the matched value" + typePattern)

      val assifoundType = foundType match {
        case Some(s) => word.set(s)
        case None => word.set("")
      }

      context.write(word, one)
    }
  }
}
