package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}
// https://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html
class _45_ImplementYourOwnCollection extends FunSpec with Matchers {

  abstract class Base
  case object A extends Base
  case object U extends Base
  case object G extends Base
  case object C extends Base

  object Base {
    val fromInt: Int => Base = Array(A, U, G, C)
    val toInt: Map[Base, Int] = Map(A -> 0, U -> 1, G -> 2, C -> 3)
  }

  describe("A Base") {
    it("can be created") {
      A shouldBe a [Base]
      Base.fromInt(1) should be (U)
      Base.toInt(U) should be (1)
    }
  }

  import collection.IndexedSeqLike
  import collection.mutable.{Builder, ArrayBuffer}
  import collection.generic.CanBuildFrom

  final class RNA1 private (val groups: Array[Int],
                            val length: Int) extends IndexedSeq[Base] {
    import RNA1._

    override def newBuilder: Builder[Base, RNA1] =
      new ArrayBuffer[Base] mapResult fromSeq

    def apply(idx: Int): Base = {
      if (idx < 0 || length <= idx)
        throw new IndexOutOfBoundsException
      Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
    }
  }

  object RNA1 {
    // Number of bits necessary to represent group
    private val S = 2

    // Number of groups that fit in an Int
    private val N = 32 / S

    // Bitmask to isolate a group
    private val M = (1 << S) - 1

    def fromSeq(buf: Seq[Base]): RNA1 = {
      val groups = new Array[Int]((buf.length + N - 1) / N)
      for (i <- 0 until buf.length)
        groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
      new RNA1(groups, buf.length)
    }

    def apply(bases: Base*) = fromSeq(bases)
  }

  describe("A RNA-group") {
    it("can be created") {
      import _45_ImplementYourOwnCollection.RNA1
      RNA1(U, A, A, C, G).length should be (5)
      val rna = RNA1 fromSeq Seq(U, U, U, A, A, A, G)
      rna.length should be (7)
      rna.groups should be (Array(8213))
      rna.mkString("[",", ","]") should be ("[U, U, U, A, A, A, G]")
//      rna.take(3) should be(Vector(U, U, U)) // Representation is deviating from original repr.
      rna.take(3) should be (U, U, U) // Representation is deviating from original repr.

    }
  }
}
