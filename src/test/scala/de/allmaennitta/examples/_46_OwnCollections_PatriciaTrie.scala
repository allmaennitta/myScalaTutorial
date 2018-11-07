package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom

// https://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html
class _46_OwnCollections_PatriciaTrie extends FunSpec with Matchers {

  import collection._
  import scala.collection.mutable.{Builder, MapBuilder}
  import scala.collection.generic.CanBuildFrom

  final class PrefixMap[T]
    extends mutable.Map[String, T]
      with mutable.MapLike[String, T, PrefixMap[T]] {

    var suffixes: immutable.Map[Char, PrefixMap[T]] = Map.empty
    var value: Option[T] = None

    def get(s: String): Option[T] =
      if (s.isEmpty) value
      else suffixes get s(0) flatMap (_.get(s substring 1))

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

  object PrefixMap extends {
    def empty[T] = new PrefixMap[T]

    def apply[T](kvs: (String, T)*): PrefixMap[T] = {
      val m: PrefixMap[T] = empty
      for (kv <- kvs) m += kv
      m
    }

    def newBuilder[T]: Builder[(String, T), PrefixMap[T]] =
      new MapBuilder[String, T, PrefixMap[T]](empty)

    implicit def canBuildFrom[T]: CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] =
      new CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] {
        def apply(from: PrefixMap[_]) = newBuilder[T]
        def apply() = newBuilder[T]
      }
  }

  describe("A PrefixMap") {
    it("can be created") {
      var m1 = PrefixMap.empty[Int]
      m1.size should be (0)                                // empty
      val retVal = m1 += ("abc" -> 5)                      // +=
      retVal should be (PrefixMap("abc" -> 5))             // apply
      m1 should be (PrefixMap("abc" -> 5))
      m1 map { case (k,v) => (k+"!", v+1)} should be       // canBuildFrom
        (PrefixMap("abc!" -> 6))
      val concatMap = m1 ++= PrefixMap("xyz" -> 11)
      concatMap  should be (PrefixMap("abc" -> 5, "xyz" -> 11))
      val capitals = PrefixMap("Andorra"->"Andorra la Vella", "Angola"->"Luanda", "Antigua und " +
        "Barbuda"->"St. John’s", "Äquatorialguinea"->"Malago", "Argentinien" ->"	Buenos Aires",
        "Armenien"->"Jerewan", "TestCountry"->"TestCapital")
      capitals.get("TestCountry") should be (Some("TestCapital"))
      capitals.remove("TestCountry")                                              //remove
      capitals.get("TestCountry") should be (None)                                //get
      capitals.update("Äquatorialguinea","Malabo")                                //update
      capitals.withPrefix("An") should be (PrefixMap(                             //withPrefix
        "dorra" -> "Andorra la Vella", "gola" -> "Luanda", "tigua und Barbuda" -> "St. John’s"))

//      m.get("hi").getOrElse(-99) should be (2)                                  // => apply
//      m.get("h").getOrElse(-99) should be (-99)                                  // => apply
//      m map { case (k, v) => (k + "!", "x" * v) } should be
//        (PrefixMap("hello!" -> "xxxxx", "hi!" -> "xx" ))                        // => canBuildFrom

//      m2.get("xy").getOrElse(-99) should be (4)
//      val m3 = m2 withPrefix("x")
//      m3 should be (PrefixMap("y" -> 4))
    }
  }

}


