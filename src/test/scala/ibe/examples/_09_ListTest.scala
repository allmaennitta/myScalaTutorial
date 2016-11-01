package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _09_ListTest extends FunSpec with Matchers {

  describe("A list") {
    it("is immutable") {
      val nums = List(1, 2, 3, 4)
      List() should be(List.empty)
      List().isEmpty should be (true)
    }

    it("can be multidimensional") {
      val matrixLists: List[List[Int]] =
        List(
          List(1, 0, 0),
          List(0, 1, 0),
          List(0, 0, 1)
        )

      val matrixArrays: Array[Array[Int]] =
        Array(
          Array(1, 0, 0),
          Array(0, 1, 0),
          Array(0, 0, 1)
        )

      matrixLists should be(matrixArrays)
    }

    it("has some interesting options to operate with.") {
      Nil should be(List())
      (1 :: (2 :: (3 :: Nil))) should be(List(1, 2, 3))

      val fruit = List("apple", "cherry", "pear")
      fruit.head should be("apple")
      fruit.tail should be(List("cherry", "pear"))
    }

    it("")

  }
}