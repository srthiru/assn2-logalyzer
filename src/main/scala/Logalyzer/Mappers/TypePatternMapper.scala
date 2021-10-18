package Logalyzer.Mappers

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import java.util.StringTokenizer
import scala.util.matching.Regex

class TypePatternMapper extends Mapper[Object, Text, Text, IntWritable]{
  val log = LogFactory.getLog(this.getClass)

  val one = new IntWritable(1)
  val word = new Text()

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {
    val itr = new StringTokenizer(value.toString)
    while (itr.hasMoreTokens()) {
      val msg = itr.nextToken()

      val msgPattern = new Regex("([0-9]{2}):([0-9]{2}):([0-9]{2}).[0-9]{3} \\[(.*)\\] (WARN|INFO|ERROR|DEBUG) (.*)\\$ - (.*)") // (WARN|INFO|ERROR|DEBUG) (.*)$ - (.*)

      val typePattern = new Regex("WARN|INFO|ERROR|DEBUG")
      val foundType = typePattern.findFirstIn(msg)

      val injectedPattern = new Regex("([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}")
      val foundPattern = injectedPattern.findFirstIn(msg)

      val timePattern = new Regex("([0-9]{2}):([0-9]{2}):([0-9]{2}).")
      val foundTime = timePattern.findAllIn(msg).map {
        case timePattern(hour, min, sec) => hour + ":" + min + ":" + ((sec.toInt/30)*30).toString
        case _ =>
      }

      val interval = if(foundTime.hasNext) foundTime.next() else "Unknown"

      log.info("this is the matched value" + typePattern)

      val result = foundPattern match {
        case Some(s) => word.set(interval + "," + foundType.toString + ",has pattern")
        case None => word.set(interval + "," + foundType.toString + ",no pattern")
      }

      context.write(word, one)
    }
  }

  def getInterval(hour: Int, min: Int, sec: Int, interval: String): String = {
    return ""
  }

}