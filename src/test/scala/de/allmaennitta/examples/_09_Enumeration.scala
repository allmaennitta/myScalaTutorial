package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

//noinspection SimplifiableFoldOrReduce,SimplifiableFoldOrReduce
class _09_Enumeration extends FunSpec with Matchers {
  describe("An enumeration") {

    object Day extends Enumeration {
      type Day = Value
      val MONDAY, TUESDAY, WEDNESDAY = Value
    }

    it("are helpful constant groups") {
      import Day._
      val today = MONDAY
    }
  }
  describe("A number of case classes grouped by a common trait") {

    trait Day
    case object MONDAY extends Day
    case object TUESDAY extends Day
    case object WEDNESDAY extends Day

    it("might be the better heavier approach for the Java pattern of 'enums with attributes and methods'") {
      val today = MONDAY
    }
  }
}