package Logalyzer.CompositeKey

import Logalyzer.CompositeKey.IntervalCountPair
import org.apache.hadoop.io.{WritableComparable, WritableComparator}

// Grouping comparator class to compare keys when grouping after mapping task
// Currently not used for implementation
class IntervalCountGroupingComparator extends WritableComparator(classOf[IntervalCountPair], true){

  override def compare(a: WritableComparable[_], b: WritableComparable[_]): Int = {
    val pair = a.asInstanceOf[IntervalCountPair]
    val pair2 = b.asInstanceOf[IntervalCountPair]

    return pair.getInterval().compareTo(pair2.getInterval())
  }
}
