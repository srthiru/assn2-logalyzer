package Logalyzer.Mappers

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import scala.util.matching.Regex

// Mapper class to map input to length of log message
class CharLengthMapper extends Mapper[Object, Text, Text, IntWritable]{
  val log = LogFactory.getLog(this.getClass)

  override def map(key: Object, value: Text, context: Mapper[Object, Text, Text, IntWritable]#Context): Unit = {

    val msg = value.toString

    // Regex patterns
    val msgPattern = new Regex(".*(WARN|INFO|ERROR|DEBUG).* - (.*)")
    val injectedPattern = new Regex(".*(([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}).*")

    msg match {
      case msgPattern(logType, msg) => {
        // If injected pattern is in the message, the write the lenght to output
        if (injectedPattern.matches(msg)) context.write(new Text(logType), new IntWritable(msg.length))
      }
      case _ => {
        // If log pattern doesn't match, log a warning
        log.warn("Log pattern mismatch - " + value.toString)
      }
    }

  }

}