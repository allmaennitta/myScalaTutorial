package ibe.examples

import org.scalatest.{FunSpec, Matchers}
import Array._

class _08_ArrayTest extends FunSpec with Matchers {

  describe("An Array"){
    it("can append new elements"){
      var myArr = Array(1,2,3)
      myArr = 44 +: myArr :+ 55
      myArr should be(Array(44,1,2,3,55))

      //consider using ArrayBuffer for appending new elements
    }


    it ("can be created and processed in various ways") {
      val withTypeAndSize = new Array[Int](3)
      withTypeAndSize.toList.toString should be ("List(0, 0, 0)")
      withTypeAndSize.update(0,55)
      withTypeAndSize.update(1,44)
      withTypeAndSize.update(2,33)
      withTypeAndSize.toList.toString should be ("List(55, 44, 33)")
      withTypeAndSize(2) should be (33)
      withTypeAndSize(10/5) should be (33)

      val expectedArray = Array(55,44,33)

      withTypeAndSize should be (expectedArray)

      for (i <- withTypeAndSize.indices){
        withTypeAndSize(i) should be (expectedArray(i))
      }
    }
    it("can be multidimensional") {
      val myMatrix = ofDim[Int](3, 3)

      for (i <- 0 to 2) {
        for (j <- 0 to 2) {
          myMatrix(i)(j) = i - j
        }
      }

      for (i <- 0 to 2) {
        for (j <- 0 to 2) {
          if (i == j) myMatrix(i)(j) should be(0) //Diagonale
        }
      }
    }

    it("can be processed in many ways"){

      concat(Array(1,2,3), Array(4,5,6)) should be (Array(1,2,3,4,5,6))

      val myArray = Array(1,2,3)
      myArray(2) should be (3)
      myArray.apply(2) should be (3) //long version of myArray(2)

      val mySecondArray = Array(4,5,6)
      Array.copy(myArray, 0, mySecondArray, 0, 2) // replace index 0,length 2-positions of the second
                                                  // array by 0 to length 2 positions of the first
      mySecondArray should be (Array(1,2,6))
    }

    it("can be created using ranges"){
      val myStep3Arr = range(0, 30, 3)

      for (i <- myStep3Arr) {
        i % 3 should be (0)
      }
    }
  }
}