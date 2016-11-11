package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable
import scala.util.control.Breaks

/**
  * Created by ingo on 26.09.2016.
  */
class _04_LoopTest extends FunSpec with Matchers {

  describe("In Scala a loop") {
    it("is breakable... in a slightly strange way") {
      class Thingy(myval: Int = 0) {
        def getValue = myval

        override def toString(): String = {
          "Thingy: " + myval
        }
      }

      val indexes = List(1, 2, 3, 4, 5)
      var result = mutable.ListBuffer[Thingy]()

      val loop = new Breaks;
      loop.breakable {
        for (a <- indexes) {
          println("Value of a: " + a)
          result += new Thingy(a)
          if (a == 3) {
            loop.break
          }
        }
      }
      val resultList = result.toList
      println("resultList: " + resultList)
      resultList.last.getValue should be(3)
    }

    it("with for is fun") {
      var x = 0
      for (a <- 1 to 3) {
        x += a
      }
      x should be(6)
    }

    it("with for can have more than one iteration") {
      val results = mutable.Stack(2, 3, 3, 4)
      var sum = 0
      for (a <- 1 until 3; b <- 1 to 2) {
        println(s"a: $a and b: $b")
        (a + b) should be(results.pop())
        sum += (a + b)
      }
      sum should be(12)
    }

    it("with for can can have a collection as source") {
      val myList = List("hund", "katze", "maus")

      for (txt <- myList) {
        println(txt)
        Array(txt) should contain oneOf("hund", "katze", "maus")
      }
    }

    it("with for can be limited by filters") {
      val a: Int = 0
      val results = mutable.Stack(6, 12)
      val myList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
      for (a <- myList
           if a % 2 == 0;
           if a % 3 == 0) {
        println(a)
        a should be(results.pop())
      }
    }

    it("with for can be used like filter- and map statements") {
      val a: Int = 0
      val expected = mutable.Stack(12, 24)
      val myList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
      val results =
        for (a <- myList
             if a % 2 == 0;
             if a % 3 == 0) yield a * 2
      results should be(expected)
    }

    it("with for and zipWithIndex value und index can be processed at the same time"){
      val a = List("a", "b", "c")
      val result = List("0 is a", "1 is b","2 is c")

      for ((e, count) <- a.zipWithIndex) {
        s"$count is $e" should be (result(count))
      }
    }


  }
}
