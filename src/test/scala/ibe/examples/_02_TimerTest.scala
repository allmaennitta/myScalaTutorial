package ibe.examples

import java.util.Date
import scala.math.Numeric.LongIsIntegral.abs

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class _02_TimerTest extends FunSpec with Matchers {
  var dateArr = Array.empty[Long]

  describe("Timer") {
    it("should execute 3 times with an interval of 500ms") {
      val repetitions = 3
      val pauseInterval = 500 //ms
      Timer.oncePerInterval(repetitions, pauseInterval, measureTime)

      dateArr.size should be (3)
      absoluteValueOfDifference(dateArr(2), dateArr(1)) should be > 490L
      absoluteValueOfDifference(dateArr(2), dateArr(1)) should be < 510L
      absoluteValueOfDifference(dateArr(1), dateArr(0)) should be > 490L
      absoluteValueOfDifference(dateArr(1), dateArr(0)) should be < 510L
    }
  }
  def measureTime() : Unit = {dateArr = dateArr :+ new Date().getTime()}
  def absoluteValueOfDifference(minuend : Long, subtrahend : Long) : Long = {
    abs((minuend-subtrahend))
  }

}
