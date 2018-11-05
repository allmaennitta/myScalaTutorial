package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom
// https://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html
class _45_OwnCollections_RNA extends FunSpec with Matchers {

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

  import collection.mutable.{ArrayBuffer, Builder}

  final class RNA1 private (val groups: Array[Int],
                            val length: Int) extends IndexedSeq[Base] {
    import RNA1._

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
      RNA1(U, A, A, C, G).length should be (5)
      val rna1 = RNA1 fromSeq Seq(U, U, U, G)
      rna1.length should be (4)
      rna1.groups should be (Array(149))
      rna1.mkString("[",", ","]") should be ("[U, U, U, G]")
      rna1.take(3) should be(Vector(U, U, U)) //=> Should be RNA1(U,U,U)
      // => Representation is deviating from original repr. This has to be optimized => RNA2withNewBuilder

      rna1 map { case U => A case b => b } should be (Vector(A, A, A, G))// Should be
                                                                            // (RNA1(A, A, A, G))
                                                                            // => RNA3
      rna1 ++ rna1 should be (Vector(U, U, U, G, U, U, U, G)) // Should be
                                                              // (RNA1(U, U, U, G, U, U, U, G))
                                                              // => RNA3
    }
  }


  final class RNA2withNewBuilder private(val groups: Array[Int],
                                         val length: Int) extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA2withNewBuilder]{
    import RNA2withNewBuilder ._

    override def newBuilder: Builder[Base, RNA2withNewBuilder] =
      new ArrayBuffer[Base] mapResult fromSeq

    def apply(idx: Int): Base = {
      if (idx < 0 || length <= idx)
        throw new IndexOutOfBoundsException
      Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
    }
  }

  object RNA2withNewBuilder {
    // Number of bits necessary to represent group
    private val S = 2

    // Number of groups that fit in an Int
    private val N = 32 / S

    // Bitmask to isolate a group
    private val M = (1 << S) - 1

    def fromSeq(buf: Seq[Base]): RNA2withNewBuilder = {
      val groups = new Array[Int]((buf.length + N - 1) / N)
      for (i <- 0 until buf.length)
        groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
      new RNA2withNewBuilder(groups, buf.length)
    }

    def apply(bases: Base*) = fromSeq(bases)
  }

  describe("A RNA-group (advanced") {
    it("can be created") {
      RNA2withNewBuilder(U, A, A, C, G).length should be (5)
      val rna2 = RNA2withNewBuilder fromSeq Seq(C, U, A, A)
      rna2.length should be (4)
      rna2.groups should be (Array(7))
      rna2.mkString("[",", ","]") should be ("[C, U, A, A]")
      rna2.take(3) should be (RNA2withNewBuilder(C, U, A)) // NOW CORRECT!

      rna2 map { case U => A case b => b } should be (Vector(C, A, A, A))// Should be
                                                                // (RNA2withNewBuilder(C, A, A, A))
                                                                // => RNA3
      rna2 ++ rna2 should be (Vector(C, U, A, A, C, U, A, A)) // Should be
                                                    // (RNA2withNewBuilder(C, U, A, A, C, U, A, A))
                                                    // => RNA3
    }
  }

  final class RNA3ForEachCanBuildFrom private(val groups: Array[Int], val length: Int)
    extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA3ForEachCanBuildFrom] {

    import RNA3ForEachCanBuildFrom._

    // Mandatory re-implementation of `newBuilder` in `IndexedSeq`
    override protected[this] def newBuilder: Builder[Base, RNA3ForEachCanBuildFrom] =
      RNA3ForEachCanBuildFrom.newBuilder

    // Mandatory implementation of `apply` in `IndexedSeq`
    def apply(idx: Int): Base = {
      if (idx < 0 || length <= idx)
        throw new IndexOutOfBoundsException
      Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
    }

    // Optional re-implementation of foreach,
    // to make it more efficient.
    override def foreach[U](f: Base => U): Unit = {
      var i = 0
      var b = 0
      while (i < length) {
        b = if (i % N == 0) groups(i / N) else b >>> S
        f(Base.fromInt(b & M))
        i += 1
      }
    }
  }

  object RNA3ForEachCanBuildFrom {

    private val S = 2            // number of bits in group
    private val M = (1 << S) - 1 // bitmask to isolate a group
    private val N = 32 / S       // number of groups in an Int

    def fromSeq(buf: Seq[Base]): RNA3ForEachCanBuildFrom = {
      val groups = new Array[Int]((buf.length + N - 1) / N)
      for (i <- 0 until buf.length)
        groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
      new RNA3ForEachCanBuildFrom(groups, buf.length)
    }

    def apply(bases: Base*) = fromSeq(bases)

    def newBuilder: Builder[Base, RNA3ForEachCanBuildFrom] =
      new ArrayBuffer mapResult fromSeq

    implicit def canBuildFrom: CanBuildFrom[RNA3ForEachCanBuildFrom, Base, RNA3ForEachCanBuildFrom] =
      new CanBuildFrom[RNA3ForEachCanBuildFrom, Base, RNA3ForEachCanBuildFrom] {
        def apply(): Builder[Base, RNA3ForEachCanBuildFrom] = newBuilder
        def apply(from: RNA3ForEachCanBuildFrom): Builder[Base, RNA3ForEachCanBuildFrom] = newBuilder
      }
  }

  describe("A RNA-group (ultimately advanced") {
    it("can be created") {
      RNA3ForEachCanBuildFrom(U, A, A, C, G).length should be (5)
      val rna3 = RNA3ForEachCanBuildFrom fromSeq Seq(C, U, A, A)
      rna3.length should be (4)
      rna3.groups should be (Array(7))
      rna3.mkString("[",", ","]") should be ("[C, U, A, A]")
      rna3.take(3) should be (RNA3ForEachCanBuildFrom(C, U, A)) // NOW CORRECT!

      rna3 map { case U => A case b => b } should be (RNA3ForEachCanBuildFrom(C, A, A, A))
      rna3 ++ rna3 should be (RNA3ForEachCanBuildFrom(C, U, A, A, C, U, A, A))
    }
  }











}
