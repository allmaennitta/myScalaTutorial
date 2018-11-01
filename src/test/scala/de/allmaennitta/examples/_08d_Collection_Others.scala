package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.immutable.Queue
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

//noinspection FunctionTupleSyntacticSugar,FunctionTupleSyntacticSugar,
// FunctionTupleSyntacticSugar,ScalaUnusedSymbol
class _08d_Collection_Others extends FunSpec with Matchers {

  describe("A set") {
    it("has each element only once") {
      Set(1, 3, 3, 3, 7) should be(Set(1, 3, 7))
      Set(1, 3, 3, 3, 7) should not be Array(1, 3, 7)
    }
    it("can be concatenated with others") {
      val _123 = Set(1, 2, 3)
      val _345 = Set(3, 4, 5)
      _123 ++ _345 should be(Set(1, 2, 3, 4, 5))
      (_123.toList ++ _345) should be(List(1, 2, 3, 3, 4, 5))
      _123.toList ::: _345.toList should be(List(1, 2, 3, 3, 4, 5))
    }

    it("'union' and 'intersection' can be shortened by symbols here") {
      Set(1, 3).union(Set(2, 3)) should be(Set(1, 3, 2, 3))
      Set(1, 3).|(Set(2, 3)) should be(Set(1, 3, 2, 3))

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

      newMap -= 'A'
      newMap -= 'B'
      newMap -= 'C'


      Map('d' -> 'e') ++ Map('e' -> 'd') should be(Map('d' -> 'e', 'e' -> 'd'))
      Map('d' -> 'e', 'e' -> 'd') - 'e' should be(Map('d' -> 'e')) // Map without 'e'-keyed tuple
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

      nameAndAge.count({ case (_, myAge) => myAge < 30 }) should be(1)

      var buffer = new ArrayBuffer[String]()
      nameAndAge.foreach {
        case (name, age) => buffer.append(name(0).toString, age.toString)
      }
      buffer.mkString should be("h30a23u50")
    }
  }

  it("can be looped over") {
    val names = Map("a" -> 1, "b" -> 2, "c" -> 3)
    val result = Iterator("a is 1", "b is 2", "c is 3")

    for ((k, v) <- names) {
      s"$k is $v" should be(result.next())
    }
  }


  describe("A tuple") {
    it("is an immutable set of values of different types") {
      val ingo = (41, true, "developer", 0x1A)
      //noinspection FunctionTupleSyntacticSugar
      // a Tuple
      var horst = (46, false, "developer", 0x1F)
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

      capital.getOrElse("France", "???") should be("Paris")
      capital.getOrElse("Germany", "???") should be("???")

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
    it("can be combined - no need to generate an extra index anymore or not to use the " +
      "for-each-approach") {
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


  describe("A Queue") {
    it("works FIFO") {
      val queue = mutable.Queue[Int]()
      queue.enqueue(1)
      queue.enqueue(3, 5, 7)
      queue should be(Queue(1, 3, 5, 7))
      queue.head should be(1)
      queue.head should be(1) //head is not popped
      queue.last should be(7)
      queue.dequeue() should be(1)
      queue.head should be(3)
    }
  }

  /**
    * A Tree (here not the built-in version) is an easily composable data type due to matching, etc.
    */
  describe("A Tree") {

    abstract class Tree

    case class Sum(l: Tree, r: Tree) extends Tree

    case class Var(n: String) extends Tree

    case class Const(v: Int) extends Tree

    type Environment = String => Int

    object Tree {
      def eval(t: Tree, env: Environment): Int = t match {
        case Sum(l, r) => eval(l, env) + eval(r, env)
        case Var(n) => env(n)
        case Const(v) => v
      }
    }

    it("should eval") {
      val exp: Tree = Sum(Sum(Var("x"), Var("x")), Sum(Const(7), Var("y")))
      val env: Environment = {
        case "x" => 1
        case "y" => 2
      }

      println("Expression: " + exp)
      Tree.eval(exp, env) should be(11)
    }
  }
  describe("A List and an Option") {
    it("can play together interestingly ") {
      //If a stacktrace is as resource consuming in Scala as it is in Java this seems to me no
      // good implementation for me

      def toInt(in: String): Option[Int] = {
        try {
          Some(Integer.parseInt(in.trim))
        } catch {
          case e: Exception => None
        }
      }

      val bag = List("1", "2", "three", "4", "one hundred seventy five")
      bag.flatMap(toInt).sum should be(7)
      //flatMap, which is actually mapFlat turns
      //List(Some(1), Some(2), None, Some(4), None)
      //into List(1, 2, 4)

    }
  }

  describe("A view") {
    it("turns a collection into lazy mode") {
      // => (SeqView(2, 4, 6, 8)) elements are only generated as demanded
      Range(1, 5).view.map(_ * 2) should be(List(2, 4, 6, 8))
    }
    it("enables stacking of operations") {
      for (x <- 1 to 10000) {
        (Range(1, 100).view map (_ + 1) map (_ + 2) map (_ * 33)).force.reverse.head should be(3366)
      }
    }
    it("should be quicker than without using the view") {
      //but for pretty high amount of repetitions it isn't... better analysis needed
      for (x <- 1 to 10000) {
        (Range(1, 100) map (_ + 1) map (_ + 2) map (_ * 33)).reverse.head should be(3366)
      }
    }
    it("saves you construction of big lists") {
      val words = List("aal","aas","aba","abo","abs","aby","ace","act","add","ado","ads","adz","aff","aft","aga","age","ago","ags","aha","ahi","ahs","aid","ail","aim","ain","air","ais","ait","ala","alb","ale","all","alp","als","alt","ama","ami","amp","amu","ana","and","ane","ani","ant","any","ape","apo","app","apt","arb","arc","are","arf","ark","arm","ars","art","ash","ask","asp","ass","ate","att","auk")
      def isPalindrome(x:String) = x == x.reverse
      def findPalindrome(s:Seq[String]) = s.find(isPalindrome).getOrElse("")

      findPalindrome(words) should be ("aba") //without view
      findPalindrome(words.view) should be ("aba") //with view as a "search window"
    }

    it("helps to update mutable sequences selectively") {
      def negate(xs: collection.mutable.Seq[Int]) = for (i <- 0 until xs.length) xs(i) = -xs(i)
      val arr = (0 to 9).toArray
      val subarr = arr.view.slice(3,6)
      negate(subarr)
      arr should be (Array(0, 1, 2, -3, -4, -5, 6, 7, 8, 9))
    }
  }
}