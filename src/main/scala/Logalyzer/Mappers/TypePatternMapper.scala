package Logalyzer.Mappers

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import java.util.StringTokenizer
import scala.util.matching.Regex

// Mapper class to map input to distribution key - interval, log type and presence of pattern
class TypePatternMapper extends Mapper[Object, Text, Text, IntWritable]{
  val log = LogFactory.getLog(this.getClass)

  val one = new IntWritable(1)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    val msg = value.toString

    val msgPattern = new Regex("([0-9]{2}):([0-9]{2}):([0-9]{2})(\\.[0-9]{3})\\s+\\[(.*)\\]\\s+(WARN|INFO|ERROR|DEBUG)\\s+(.*) - (.*)")
    val injectedPattern = new Regex("([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}")

    val result = msg match {
      // If pattern matches, extract key from the message
      case msgPattern(hour, min, sec, milsec, context, logType, className, logMessage) => {
        val interval = List(hour, min, ((sec.toInt/30)*30)).mkString(":")
        val hasPattern = if (injectedPattern.findAllIn(logMessage).length > 0) "has_pattern" else "no_pattern"

        List(interval, logType, hasPattern).mkString(",")
      }
      case _ => "No match"
    }
    word.set(result)
    context.write(word, one)
  }

  // Can implement a function to extract interval based on a parameterized input
  def getInterval(hour: Int, min: Int, sec: Int, interval: String): String = {
    return ""
  }

}