package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _12_PatternMatchingTest extends FunSpec with Matchers {

  describe("A match") {
    it("should dicriminate integers") {
      def matchTest(x:Int):String = x match {
        case 1 => "one"
        case 2 => "two"
        case _ => "many"
      }
      matchTest(1) should be ("one")
      matchTest(2) should be ("two")
      matchTest(3) should be ("many")
      matchTest(55) should be ("many")
      matchTest(-1) should be ("many")
    }

    it("should dicriminate all kind of stuff") {
      def matchTest(x:Any):Any = x match {
        case 1 => "one"
        case "two" => 2
        case y: Int => "scala.Int"
        case _ => "many"
      }
      matchTest("two") should be (2)
      matchTest("test") should be ("many")
      matchTest(1) should be ("one")
      matchTest(3) should be ("scala.Int")
    }
  }

  describe("A person matcher"){
    case class Person(name: String, age: Int) //NOTHING MORE has to be declared for the code below to work
    val hans = Person ("Hans", 22) //even "new" is not necessary for case class
    val takeshi = Person ("Takeshi", 27)

    it("matches people"){
      val name = (person : Person) => person match{
          case Person("Hans", 22) => person.name
          case Person("Ndugu", 22) => person.name
          case Person("Takeshi", 27) => person.name
        }
      name(hans) should be ("Hans")
      name(takeshi) should be ("Takeshi")
    }
  }
}
