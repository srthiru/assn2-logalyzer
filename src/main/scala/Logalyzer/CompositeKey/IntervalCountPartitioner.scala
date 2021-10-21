package Logalyzer.CompositeKey

import Logalyzer.CompositeKey.IntervalCountPair
import org.apache.hadoop.mapreduce.Partitioner

// Custom Partitioner class to partition input to reducers
// Currently not used in implemetation
class IntervalCountPartitioner extends Partitioner[IntervalCountPair, Int]{

  override def getPartition(key: IntervalCountPair, value: Int, i: Int): Int = {
    return Math.abs(key.getInterval().hashCode % i)
  }
}
