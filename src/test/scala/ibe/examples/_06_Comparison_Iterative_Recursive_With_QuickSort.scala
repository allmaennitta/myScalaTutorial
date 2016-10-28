package ibe.examples

import org.scalatest.{FunSpec, Ignore, Matchers}

class _06_Comparison_Iterative_Recursive_With_QuickSort extends FunSpec with Matchers {

  describe("A QuickSortTest") {


    it("should quicksort in an imperative way") {
      println("------------------------------------")
      val array = Array(1,5,3,7,2,6,4)
      QuickSort.quicksortImperative(array)
      println(s"Result: ${array.mkString}")
      array should equal (Array(1,2,3,4,5,6,7))
    }

    it ("should quicksort in a functional way") {
      println("------------------------------------")
      val array = Array(1,5,3,7,2,6,4)
      val result = QuickSort.quicksortFunctional(array)
      println(s"Result: ${result.mkString}")
      result should equal (Array(1,2,3,4,5,6,7))
    }
  }
}
