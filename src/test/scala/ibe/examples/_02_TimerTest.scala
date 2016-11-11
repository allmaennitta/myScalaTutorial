package ibe.examples

import java.util.Date
import scala.math.Numeric.LongIsIntegral.abs

import org.scalatest.{FunSpec, Matchers}

class _02_TimerTest extends FunSpec with Matchers {
  var dateArr = Array.empty[Long]

  describe("A Timer") {

    object Timer {
      def oncePerInterval(repetions: Int, pauseInterval: Int, callback: () => Unit) {
        for( i <- 1 to repetions){
          callback();
          Thread sleep pauseInterval
        }
      }
    }

    def measureTime() : Unit = {dateArr = dateArr :+ new Date().getTime()}
    def absoluteValueOfDifference(minuend : Long, subtrahend : Long) : Long = {
      abs((minuend-subtrahend))
    }

    it("should execute 3 times with an interval of 500ms") {
      val repetitions = 3
      val pauseInterval = 500 //ms
      Timer.oncePerInterval(repetitions, pauseInterval, measureTime)

      dateArr.size should be (3)
      absoluteValueOfDifference(dateArr(2), dateArr(1)) should be > 490L
      absoluteValueOfDifference(dateArr(2), dateArr(1)) should be < 510L
      absoluteValueOfDifference(dateArr(1), dateArr(0)) should be > 490L
      absoluteValueOfDifference(dateArr(1), dateArr(0)) should be < 600L
    }
  }
}
