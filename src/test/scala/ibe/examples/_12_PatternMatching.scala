package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _12_PatternMatching extends FunSpec with Matchers {

  describe("A match") {
    it("should dicriminate integers") {
      def matchTest(x: Int): String = x match {
        case 1 => "one"
        case 2 | 3 | 4 => "some"
        case _ => "many"
      }

      matchTest(1) should be("one")
      matchTest(2) should be("some")
      matchTest(3) should be("some")
      matchTest(55) should be("many")
      matchTest(-1) should be("many")
    }

    it("should dicriminate all kind of stuff") {
      def matchTest(x: Any): Any = x match {
        case 1 => "one"
        case "two" => 2
        case _: Int => "scala.Int"
        case _ => "many"
      }

      matchTest("two") should be(2)
      matchTest("test") should be("many")
      matchTest(1) should be("one")
      matchTest(3) should be("scala.Int")
    }

    it("can be assigned directly to a variable, well, the result of the match, of course") {

      intercept[IllegalArgumentException] {

        //for the setting to make sense this could be a system property
        val currentEnv = "halligalli"

        //noinspection ScalaUnusedSymbol
        val isProductionEnv = currentEnv match { //is computed exactly once at initialisation
          case "prod" | "production" | "Prod" | "Production" => true
          case "test" | "develop" | "development" => false
          //for the default case instead of using _ you can set a variable which can be evaluated
          case unknown => throw new IllegalArgumentException(s"'$unknown' is not a valid env name.")
        }

        fail("Error expected but was not triggered")
      }
    }

    it("can match collection patterns") {
      (List(0, 1, 2) match {
        case List(0, _, _) => true
      }) should be(true)

      (List(1, 3, 2) match {
        case List(0, _, _) => true
        case _ => false
      }) should be(false)

      (List(0, 1, 2, 3, 4) match {
        case List(0, _*) => true
      }) should be(true)

      (List(1, 3) match {
        case _: List[Int] => true
      }) should be(true)

      def multiply(list: List[Int]) : Int = list match {
        case Nil => 1 //necessary, otherwise: WARNING: MATCH IS NOT EXHAUSTIVE
        case n :: rest => n * multiply(rest)
      }
      multiply(List(1,3, 2)) should be (6)

    }

    it("can match type patterns") {
      (23F match {
        case _: Float => true
      }) should be(true)
    }

    it("can include additional ifs") {
      (24F match {
        case f: Float if f%2 == 0 => true
      }) should be(true)

      (23F match {
        case f: Float if f%2 != 0 => true
      }) should be(true)
    }


    it("can not prevent type erasure") {
      (List(1, 3) match {
        case _: List[String] => true
        case _ => false
      }) should be (true) //be(false) => CAUTION, due to TYPE ERASURE the type doesn't matter!
    }

  }

  describe("A person matcher") {

    case class Person(name: String, age: Int)

    //NOTHING MORE has to be declared for the code below to work
    val hans = Person("Hans", 22)
    //even "new" is not necessary for case class
    val takeshi = Person("Takeshi", 27)
    val ndugu = Person("Ndugu", 31)


    it("matches people") {
      val name = (person: Person) => person match {
        case Person("Hans", 22) => person.name
        case Person("Ndugu", 22) => person.name
        case Person("Takeshi", 27) => person.name
        case Person(_, 31) => person.name //Also this is possible
      }
      name(hans) should be("Hans")
      name(takeshi) should be("Takeshi")
      name(ndugu) should be("Ndugu")
    }
  }
}
