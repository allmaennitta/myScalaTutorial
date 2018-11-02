package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable
import scala.util.control.Breaks
import scala.util.control.Breaks._

class _04_Loop extends FunSpec with Matchers {

  describe("A for loop") {
    it("is breakable... in a slightly strange way") {
      breakable {
        for (i <- 1 to 10) {
          println(i)
          if (i > 4) break  // break out of the for loop
        }
      }

      class Thingy(myval: Int = 0) {
        def getValue: Int = myval

        override def toString: String = {
          "Thingy: " + myval
        }
      }

      val indexes = List(1, 2, 3, 4, 5)
      var result = mutable.ListBuffer[Thingy]()

      val loop = new Breaks
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

    it("is built like that") {
      var x = 0
      for (a <- 1 to 3) {
        x += a
      }
      x should be(6)
    }

    it("can have more than one iteration") {
      val results = mutable.Stack(2, 3, 3, 4)
      var sum = 0
      for (a <- 1 until 3; b <- 1 to 2) {
        println(s"a: $a and b: $b")
        (a + b) should be(results.pop())
        sum += (a + b)
      }
      sum should be(12)
    }

    it("can have a collection as source") {
      val myList = List("hund", "katze", "maus")

      for (txt <- myList) {
        println(txt)
        Array(txt) should contain oneOf("hund", "katze", "maus")
      }
    }

    it("can be limited by filters") {
      val results = mutable.Stack(6, 12)
      val myList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
      for (a <- myList
        if a % 2 == 0
        if a % 3 == 0) {
        println(a)
        a should be(results.pop())
      }
    }

    it("can be enhanced with zipWithIndex to process value und index at the same time") {
      val a = List("a", "b", "c")
      val result = List("0 is a", "1 is b", "2 is c")

      for ((e, count) <- a.zipWithIndex) {
        s"$count is $e" should be(result(count))
      }
    }
  }

  describe("So called 'for comprehensions'") {
    it("create a collection by a for statement") {
      val expected = mutable.Stack(12, 24)
      val myList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
      val results =
        for (a <- myList
          if a % 2 == 0
          if a % 3 == 0) yield a * 2
      results should be(expected)
    }
  }


  describe("While loops") {
    it(" are boring. Well no, they just behave like expected") {
      val iter = Iterator("a", "b", "c")

      iter.hasNext should be(true)

      while (iter.hasNext) {
        println(iter.next())
      }

      iter.hasNext should be(false)
    }

    it("do while loops are... we had that") {
      val iter = Iterator("a", "b", "c")

      iter.hasNext should be(true)

      var x = false
      do {
        x = true
      } while (false)

      x should be(true)
    }

  }
}
