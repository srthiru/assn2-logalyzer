package Logalyzer

import scala.io.Source
import scala.util.matching.Regex

object RegexTest {

  def RegexTest: Unit = {
    val msgPattern = new Regex(".*(([a-c][e-g][0-3]|[A-Z][5-9][f-w]){5,15}).*")
    val filename = "E:/MS/Fall 2021/CS 441/Assignments/2/LogFileGenerator/log/LogFileGenerator.2021-10-20_13-17.log"

    val result = Source.fromFile(filename).getLines().map(msg => {
      val result = msg match {
        case msgPattern(pattern) => {
          println(pattern)
          pattern
        }
        case _ => ""
      }
    })

    print(result.toList)


  }
}