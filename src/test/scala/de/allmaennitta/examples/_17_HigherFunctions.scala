package de.allmaennitta.examples

import java.io._
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{Files, Paths}

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class _17_HigherFunctions extends FunSpec with Matchers {

  describe("The difference between map and flatMap") {
    def twoTimes(x: Int): Int = {
      x * 2
    }

    def intToIntList(x: Int): List[Int] = {
      List(x)
    }

    it("is, that map takes a function producing single values") {
      List(1, 2, 3).map(twoTimes).sum should be(12)
    }
    it("is, that flatMap takes a function producing lists, reducing them to single values afterwards") {
      List(1, 2, 3).map(twoTimes).flatMap(intToIntList).sum should be(12)
    }
  }
  describe("A combination of higher functions") {
    it("can be transformed into a for statement") {
      List(1,2,3) map (i => (1,i)) should be (List((1,1),(1,2),(1,3)))

    }
  }
}

