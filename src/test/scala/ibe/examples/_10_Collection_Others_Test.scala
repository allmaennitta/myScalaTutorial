package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable

class _10_Collection_Others_Test extends FunSpec with Matchers {

  describe("A set") {
    it("has each element only once") {
      Set(1, 3, 3, 3, 7) should be(Set(1, 3, 7))
      Set(1, 3, 3, 3, 7) should not be (Array(1, 3, 7))
    }
    it("can be concatenated with others") {
      val _123 = Set(1, 2, 3)
      val _345 = Set(3, 4, 5)
      _123 ++ _345 should be(Set(1, 2, 3, 4, 5))
      (_123.toList ++ _345).toList should be(List(1, 2, 3, 3, 4, 5))
      _123.toList ::: _345.toList should be(List(1, 2, 3, 3, 4, 5))
    }

    it("'union' and 'intersection' can be shortened by symbols here") {
      Set(1, 3).union(Set(2, 3)) should be((Set(1, 3, 2, 3)))
      Set(1, 3).|(Set(2, 3)) should be((Set(1, 3, 2, 3)))

      Set(1, 2, 3).intersect(Set(2, 2)) should be(Set(2, 2))
      Set(1, 2, 3).&(Set(2, 2)) should be(Set(2, 2))
    }
  }

  describe("A Map") {
    it("is per default immutable") {
      Map("A" -> "a")("A") should be("a")
      // Map("A" -> "a")("B") = "b" // geht nicht, da immutable
    }

    it("can be created / noted in various ways") {
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


      Map('d' -> 'e') ++ Map('e' -> 'd') should be(Map('d' -> 'e', 'e' -> 'd'))
      Map('d' -> 'e', 'e' -> 'd') - ('e') should be(Map('d' -> 'e')) // Map without 'e'-keyed tuple
      Map('d' -> 'e', 'e' -> 'd') - ('d', 'e') should be(Map.empty) // Map without second tuple
    }

    it("can be processed in various ways") {
      val myMap = Map('D' -> 'd', 'E' -> 'e', 'F' -> 'f').withDefaultValue('x') //Default as value
      myMap('Z') should be('x')

      myMap.keys should be(Set('D', 'E', 'F'))
      myMap.values.toList should be(List('d', 'e', 'f'))
      myMap.toList should be(List(('D', 'd'), ('E', 'e'), ('F', 'f')))

      myMap.contains('D') should be(true)
      myMap.contains('G') should be(false)

      myMap('Z') should be('x')

      val nameAndAge = Map("hans" -> 30, "anna" -> 23, "ute" -> 50)
        .withDefault({ case (name: String) => name.length * 10 }) //Default as function

      nameAndAge("hans") should be(30)
      nameAndAge("johann") should be(60) //default

      nameAndAge.filterKeys((name: String) => name.length < 4) should be(Map("ute" -> 50))

      nameAndAge.count({ case (n, a) => a < 30 }) should be(1)
    }

    it("can be looped over") {
      val names = Map("a" -> 1, "b" -> 2, "c"->3)
      val result = Iterator("a is 1", "b is 2", "c is 3")

      for ((k, v) <- names) {
        s"$k is $v" should be(result.next())
      }
    }
  }
  describe("A tuple") {
    it("is an immutable set of values of different types") {
      val ingo = (41, true, "developer", 0x1A) // a Tuple
      var horst: Tuple4[Int, Boolean, String, Int] = (46, false, "developer", 0x1F)
      horst = ingo
      horst should be(41, true, "developer", 0x1A)
      horst._2 should be(true)
      horst._4 should be(0x1A)

      (1, 2).swap should be(2, 1)
    }
  }
  describe("An Option") {
    it("is an object or none") {
      val capital = Map("France" -> "Paris")
      capital.get("France") should be(Some("Paris")) //get returns Option type
      capital.get("Germany") should be(None) //get returns Option type

      capital.get("France").getOrElse("???") should be("Paris")
      capital.get("Germany").getOrElse("???") should be("???")

      capital.get("Germany").isEmpty should be(true)
    }

    it("can be pattern-matched") {
      def evalQuestion(o: Option[String]) = o match {
        case Some(s) => s
        case None => "???"
      }
      val capital = Map("France" -> "Paris")
      evalQuestion(capital.get("France")) should be("Paris")
      evalQuestion(capital.get("Germany")) should be("???")
    }
  }

  describe("An Iterator") {
    it("iterates") {
      val loriterator = Iterator("ein", "ohne", "ist", "aber")
      val iteriot = Iterator("leben", "mops", "möglich", "sinnlos")
      var citation = new mutable.StringBuilder()

      while (loriterator.hasNext && iteriot.hasNext) {
        citation ++= loriterator.next()
        citation ++= " "
        citation ++= iteriot.next()
        if (loriterator.hasNext) citation ++= " "
      }
      citation.result() should be("ein leben ohne mops ist möglich aber sinnlos")
    }
    it("can be combined") {
      val loriterator = Iterator("ein", "ohne", "ist", "aber")
      val iteriot = Iterator("leben", "mops", "möglich", "sinnlos")
      var citation = new mutable.StringBuilder()

      val lorit_iterator = loriterator.zip(iteriot)
      while (lorit_iterator.hasNext) {
        val item = lorit_iterator.next()
        citation ++= item._1 ++= " " ++= item._2
        if (loriterator.hasNext) citation ++= " "
      }
      citation.result() should be("ein leben ohne mops ist möglich aber sinnlos")

    }
  }
}