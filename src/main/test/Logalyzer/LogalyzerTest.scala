package Logalyzer

import com.typesafe.config.ConfigFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class LogalyzerTest extends AnyFlatSpec with Matchers {

  val config = ConfigFactory.load()

  it should "interval should be atleast 10 seconds" in {
    val interval = config.getInt("interval")
    assert(interval >= 10)
  }

  it should "tasks should not have the same output path" in {
    val task1output = config.getString("task1output")
    val task2output = config.getString("task2output")

    assert(task1output != task2output)
  }

}