package Logalyzer

import org.apache.hadoop.io.WritableComparator
import org.apache.hadoop.io.WritableComparable

class IntervalCountGroupingComparator extends WritableComparator(classOf[IntervalCountPair], true){

  override def compare(a: WritableComparable[_], b: WritableComparable[_]): Int = {
    val pair = a.asInstanceOf[IntervalCountPair]
    val pair2 = b.asInstanceOf[IntervalCountPair]

    return pair.getInterval().compareTo(pair2.getInterval())
  }
}
