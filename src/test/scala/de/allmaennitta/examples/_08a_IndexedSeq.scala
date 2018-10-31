package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.IndexedSeq._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class _08a_IndexedSeq extends FunSpec with Matchers {


  describe("An IndexedSeq which is implicitly converted to the concrete type 'Vector'") {
    it("is per default immutable") {
      val seq_immutable = mutable.IndexedSeq(1, 2, 3)
      //seq_immutable(0) = 33 // not possible because immutable
      val seq_mutable = mutable.IndexedSeq(1, 2, 3)
      seq_mutable(0) = 33
      seq_mutable(0) should be(33)
    }

    it("is the best general purpose approach for immutable indexed collections") {
      // use IndexedSeq instead of Seq if you want to be prepared for the case, that an other concrete class
      // might be chosen as the best general purpose implementation

      var seq = IndexedSeq(1, 2, 3)
      (44 +: seq :+ 55) should be(IndexedSeq(44, 1, 2, 3, 55))
      seq ++ Vector(4, 5, 6) should be(Vector(1, 2, 3, 4, 5, 6))

      IndexedSeq(1, 2, 3).apply(2) should be(3) //long version
      IndexedSeq(1, 2, 3)(2) should be(3) //short version
      seq.head should be(1)


      concat(IndexedSeq(1, 2, 3), IndexedSeq(4, 5, 6)) should be(IndexedSeq(1, 2, 3, 4, 5, 6))
    }

    it("can be (like other collections) created using ranges") {
      val myStep3Arr = range(0, 30, 3)

      for (i <- myStep3Arr) {
        i % 3 should be(0)
      }
    }

    it("can be updated") {
      val a = Vector(1, 2, 3)
      a.updated(0, "x") should be (Vector("x", 2, 3))
      a.updated(0, "x") should be (Vector("x", 2, 3))
      a.updated(0, "x").apply(0).getClass.getCanonicalName should be ("java.lang.String")
      a.updated(0, "x").apply(1).getClass.getCanonicalName should be ("java.lang.Integer")
    }
  }

  describe("An Array Buffer") {
    it("is the best general purpose approach for mutable indexed collections") {
      //empty ArrayBuffer
      ArrayBuffer[String]() should be (empty)
      ArrayBuffer(1,2,3) should not be (empty)
    }
    it("is ideal for adding and removing stuff"){
      (ArrayBuffer(1,2) += 4 += (5,6) ++= List(7,8)).toArray should be (Array(1,2,4,5,6,7,8))
      (ArrayBuffer(11,12,13,14,15,16,17,18) -= 3 -= (5,6) --= Array(5,6)).toArray should be (Array(11, 12, 13, 14, 15, 16, 17, 18))
      (ArrayBuffer(11,12,13,14,15,16,17,18) -= 14 -= (15,16) --= Array(12,18)).toArray should be (Array(11, 13,  17))
      var x = ArrayBuffer(1,2)
      x.append(3)
      x.insert(2,4)
      x.prepend(12)
      x.toArray should be (Array(12,1,2,4,3))
      x.remove(1)
      x.toArray should be (Array(12,2,4,3))
      x.remove(1,3)
      x.toArray should be (Array(12))
      x.clear()
      x.toArray should be (Array.empty)

    }
  }

  describe("An Array") {
    it("can be created and processed in various ways") {
      Array[Int](1, 2, 3) should be(Array(1, 2, 3)) //most simple ways of defining it

      val withTypeAndSize = new Array[Int](3) //most formal way
      withTypeAndSize.toList.toString should be("List(0, 0, 0)")
      withTypeAndSize.update(0, 55)
      withTypeAndSize(1) = 44
      withTypeAndSize.mkString(",") should be("55,44,0")
      withTypeAndSize(2) should be(0)
      withTypeAndSize(10 / 5) should be(0)

      val expected = Array(55, 44, 0)
      withTypeAndSize should be(expected)

      for (i <- withTypeAndSize.indices) {
        withTypeAndSize(i) should be(expected(i))
      }
    }

    it("can be converted into a list"){
      val arr1 = Array(1, 2, 3)
      arr1.toList should be (List(1,2,3))
    }

    it("can be copied as a whole or in parts") {
      val arr1 = Array(1, 2, 3)
      val arr2 = Array(4, 5, 6)
      Array.copy(arr1, 0, arr2, 0, 2) // replace index 0,length 2-positions of the second
      // array by 0 to length 2 positions of the first
      arr2 should be(IndexedSeq(1, 2, 6))

    }

    it("can be multidimensional") {
      val myMatrix = Array.ofDim[Int](3, 3)

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
  }
}

