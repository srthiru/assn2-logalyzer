package Logalyzer

import org.apache.hadoop.io.{IntWritable, Text, Writable, WritableComparable}

import java.io.{DataInput, DataOutput}

class IntervalCountPair extends Writable, WritableComparable[IntervalCountPair]{
  val interval = new Text();
  val count = new IntWritable();

  def getCount(): Int = count.get()
  def getInterval(): String = interval.toString

  def setCount(countVal: Int): Unit = count.set(countVal)
  def setInterval(intervalVal: String): Unit = interval.set(intervalVal)

  override def compareTo(o: IntervalCountPair): Int = {
    val compareValue = count.get().compareTo(o.getCount())

    return -1*compareValue
  }

  override def readFields(dataInput: DataInput): Unit = {
    count.set(dataInput.readInt())
//    interval.set(dataInput.readChar())
  }

  override def write(dataOutput: DataOutput): Unit = {
    dataOutput.writeInt(count.get())
//    dataOutput.writeChars(interval.toString)
  }
}
