package Logalyzer.CompositeKey

import Logalyzer.CompositeKey.IntervalCountPair
import org.apache.hadoop.mapreduce.Partitioner

class IntervalCountPartitioner extends Partitioner[IntervalCountPair, Int]{

  override def getPartition(key: IntervalCountPair, value: Int, i: Int): Int = {
    return Math.abs(key.getInterval().hashCode % i)
  }
}
