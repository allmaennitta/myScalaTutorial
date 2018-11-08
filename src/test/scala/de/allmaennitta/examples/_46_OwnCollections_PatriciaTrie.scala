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

    def get(s: String): Option[T] = {
      println("s:"+s)
      if (s.isEmpty) {
        println("GET: s empty => :"+value.toString)
        value
      }
      else {
//        suffixes get s(0) flatMap (_.get(s substring 1))
        val s0 = suffixes get s(0)
        println("GET: s0:"+s0)
        val fmap = s0 flatMap (_.get(s substring 1))
        println("GET: fmap:"+fmap)
        fmap
      }
    }

    def withPrefix(s: String): PrefixMap[T] =
      if (s.isEmpty) this
      else {
        val leading = s(0)
        suffixes get leading match {
          case None =>
            suffixes = suffixes + (leading -> empty)
            println("WITH_P: case None: "+suffixes)
          case _ =>
            println("WITH_P: case Hit: "+suffixes)
        }
        val suf = suffixes(leading) withPrefix (s substring 1)
        println("WITH_P: result: "+suf)
        suf
      }

    override def update(s: String, elem: T) = {
      println("UPDATE: Der Wert von "+withPrefix(s)+ " wird auf "+elem.toString+" gesetzt.")
      withPrefix(s).value = Some(elem)
    }

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
    it("can be constructed") {
      var m1 = PrefixMap.empty[Int]
      m1.size should be(0) // empty
      val retVal = m1 += ("abc" -> 5) // +=
      retVal should be(PrefixMap("abc" -> 5)) // apply
      m1 should be(PrefixMap("abc" -> 5))
      m1 map { case (k, v) => (k + "!", v + 1) } should be // canBuildFrom
      (PrefixMap("abc!" -> 6))
      val concatMap = m1 ++= PrefixMap("xyz" -> 11)
      concatMap should be(PrefixMap("abc" -> 5, "xyz" -> 11))
    }
     it("has items removed and updated") {
       val capitals = PrefixMap("Andorra" -> "Andorra la Vella", "Angola" -> "Luanda", "Antigua und " +
         "Barbuda" -> "St. John’s", "Äquatorialguinea" -> "Malago",
         "Argentinien" -> "Buenos Aires", "Armenien" -> "Jerewan", "TestCountry" -> "TestCapital")
       capitals.get("TestCountry") should be(Some("TestCapital"))
       capitals.remove("TestCountry") //remove
       capitals.get("TestCountry") should be(None) //get
       capitals.update("Äquatorialguinea", "Malabo") //update
       capitals.get("Äquatorialguinea") should be(Some("Malabo"))
     }
    it("has items retrieved by 'get'") {
      println("INIT_MAP_START")
      val capitals = PrefixMap("Andorra" -> "Andorra la Vella", "Angola" -> "Luanda", "Antigua und " +
        "Barbuda" -> "St. John’s", "Äquatorialguinea" -> "Malabo", "Argentinien" ->
        "Buenos Aires", "Armenien" -> "Jerewan")
      println("INIT_MAP_END")
      println("GET_START")
      val aequ = capitals.get("Äquatorialguinea")
      println("GET_END")
      println("EVALUATION_START")
      aequ should be(Some("Malabo")) //get
      println("EVALUATION_END")
    }
    it("has items retrieved by 'withPrefix'") {
      println("INIT_MAP_START")
      val capitals = PrefixMap("Andorra" -> "Andorra la Vella", "Angola" -> "Luanda", "Antigua und " +
        "Barbuda" -> "St. John’s", "Äquatorialguinea" -> "Malabo", "Argentinien" ->
        "Buenos Aires", "Armenien" -> "Jerewan")
      println("INIT_MAP_END")
      println("WITH_PREFIX_START")
      val an = capitals.withPrefix("An")
      println("WITH_PREFIX_END")
      println("EVALUATION_START")
      an should be (PrefixMap("dorra" -> "Andorra la Vella", "gola" -> "Luanda",  //withPrefix
        "tigua und Barbuda" -> "St. John’s"))
      println("EVALUATION_END")
    }

    it("is built up incrementally") {
      println("EMPTY")
      var p = PrefixMap.empty[Int]
      println("+AB")
      p = p+=("AB"->4)
      println("Map: "+p)
      println("Suffixes: "+p.suffixes)
      println("+ABA")
      p = p+=("ABA"->5)
      println("Map: "+p)
      println("Suffixes: "+p.suffixes)
      println("+ABI")
      p = p+=("ABI"->6)
      println("Map: "+p)
      println("Suffixes: "+p.suffixes)
    }
  }

}


