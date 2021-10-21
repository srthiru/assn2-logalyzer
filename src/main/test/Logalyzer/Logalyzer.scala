import com.typesafe.config.ConfigFactory


class RandomStringGeneratorTest extends AnyFlatSpec with Matchers {

  val config = ConfigFactory.load()

  it should "interval should be greater than 10 seconds" in {
    val interval = config.getInt("interval")
    interval shoudBe > 10
  }

  it should "tasks should not have the same output path" in {
    val task1output = config.getInt("task1output")
    val task2output = config.getInt("task2output")

    task1output should not be task2output
  }

}