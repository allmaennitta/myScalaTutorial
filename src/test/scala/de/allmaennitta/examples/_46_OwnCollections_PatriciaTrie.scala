package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom

// https://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html
class _46_OwnCollections_PatriciaTrie extends FunSpec with Matchers {

  import collection._

  final class PrefixMap[T]
    extends mutable.Map[String, T]
      with mutable.MapLike[String, T, PrefixMap[T]] {

    var suffixes: immutable.Map[Char, PrefixMap[T]] = Map.empty
    var value: Option[T] = None

    def get(s: String): Option[T] =
      if (s.isEmpty) value
      else suffixes get (s(0)) flatMap (_.get(s substring 1))

    def withPrefix(s: String): PrefixMap[T] =
      if (s.isEmpty) this
      else {
        val leading = s(0)
        suffixes get leading match {
          case None =>
            suffixes = suffixes + (leading -> empty)
          case _ =>
        }
        suffixes(leading) withPrefix (s substring 1)
      }

    override def update(s: String, elem: T) =
      withPrefix(s).value = Some(elem)

    override def remove(s: String): Option[T] =
      if (s.isEmpty) {
        val prev = value; value = None; prev
      }
      else suffixes get (s(0)) flatMap (_.remove(s substring 1))

    def iterator: Iterator[(String, T)] =
      (for (v <- value.iterator) yield ("", v)) ++
        (for ((chr, m) <- suffixes.iterator;
              (s, v) <- m.iterator) yield (chr +: s, v))

    def +=(kv: (String, T)): this.type = {
      update(kv._1, kv._2); this
    }

    def -=(s: String): this.type = {
      remove(s); this
    }

    override def empty = new PrefixMap[T]
  }

  import scala.collection.mutable.{Builder, MapBuilder}
  import scala.collection.generic.CanBuildFrom

  object PrefixMap extends {
    def empty[T] = new PrefixMap[T]

    def apply[T](kvs: (String, T)*): PrefixMap[T] = {
      val m: PrefixMap[T] = empty
      for (kv <- kvs) m += kv
      m
    }

    def newBuilder[T]: Builder[(String, T), PrefixMap[T]] =
      new MapBuilder[String, T, PrefixMap[T]](empty)

    implicit def canBuildFrom[T]
    : CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] =
      new CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] {
        def apply(from: PrefixMap[_]) = newBuilder[T]
        def apply() = newBuilder[T]
      }
  }

  describe("A PrefixMap") {
    it("can be created") {
      PrefixMap.empty[String].size should be (0)                                // => empty
      var m = PrefixMap("hello" -> 5, "hi" -> 2)
      m.get("hi").getOrElse(-99) should be (2)                                  // => apply
      m.get("h").getOrElse(-99) should be (-99)                                  // => apply
      m map { case (k, v) => (k + "!", "x" * v) } should be
        (PrefixMap("hello!" -> "xxxxx", "hi!" -> "xx" ))                        // => canBuildFrom
      val m2 = PrefixMap("abc" -> 0, "abd" -> 1, "al" -> 2,
        "all" -> 3, "xy" -> 4)
      m2.get("xy").getOrElse(-99) should be (4)
      val m3 = m2 withPrefix("x")
      m3 should be (PrefixMap("y" -> 4))
    }
  }

}


