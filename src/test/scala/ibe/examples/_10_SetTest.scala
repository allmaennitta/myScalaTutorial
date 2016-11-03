package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable

class _10_SetTest extends FunSpec with Matchers {

  describe("A set") {
    it("has each element only once") {
      Set(1,3,3,3,7) should be (Set(1,3,7))
      Set(1,3,3,3,7) should not be (Array(1,3,7))
    }
    it("can be concatenated with others") {
      val _123 = Set(1, 2, 3)
      val _345 = Set(3, 4, 5)
      _123 ++ _345 should be(Set(1, 2, 3, 4, 5))
      (_123.toList ++ _345).toList should be(List(1, 2, 3, 3, 4, 5))
      _123.toList ::: _345.toList should be(List(1, 2, 3, 3, 4, 5))
    }

    it("'union' and 'intersection' can be shortened by symbols here"){
      Set(1,3).union(Set(2,3)) should be ((Set(1,3,2,3)))
      Set(1,3).|(Set(2,3)) should be ((Set(1,3,2,3)))

      Set(1,2,3).intersect(Set(2,2)) should be (Set(2,2))
      Set(1,2,3).&(Set(2,2)) should be (Set(2,2))
    }
  }

  describe("A Map") {
    it("is per default immutable") {
      Map("A" -> "a")("A") should be("a")
      // Map("A" -> "a")("B") = "b" // geht nicht, da immutable
    }

    it("can be created / noted in various ways"){
      val myMap = mutable.Map("A" -> "a", "B" -> "b")
      myMap("C") = "c"
      myMap("C") should be("c")

      var newMap: Map[Char, Char] = Map() //Typing is important here
      newMap += ('D' -> 'd')
      newMap += ('E' -> 'e')
      newMap += ('F' -> 'f')

      newMap -= ('A')
      newMap -= ('B')
      newMap -= ('C')

      newMap.keys should be (Set('D', 'E', 'F'))
      newMap.values.toList should be (List('d', 'e', 'f'))
      newMap.toList should be (List(('D','d'),('E','e'),('F','f')))

      Map('d'->'e') ++ Map('e'->'d') should be (Map('d'->'e', 'e'->'d'))
      Map('d'->'e', 'e'->'d') - ('e') should be (Map('d' -> 'e')) // Map without 'e'-keyed tuple
      Map('d'->'e', 'e'->'d') - ('d','e') should be (Map.empty) // Map without second tuple

    }
  }
}