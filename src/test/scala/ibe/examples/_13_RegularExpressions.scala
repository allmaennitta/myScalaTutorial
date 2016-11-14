package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.util.matching.Regex

class _13_RegularExpressions extends FunSpec with Matchers {

  describe("A regular expression") {
    it("is able to find 'scala'") {
      val patternString = "(S|s)cala"
      val patternRaw = "(S|s)cala".r
      val patternFromVar = patternString.r
      val patternFromVarAsRegex = new Regex(patternString)
      val str = "Scala is scalable and coool"
      patternRaw.findFirstIn(str).getOrElse("") should be("Scala")
      patternRaw findFirstIn str getOrElse "" should be("Scala")

      patternFromVar.findFirstIn(str).getOrElse("") should be("Scala")
      patternFromVarAsRegex.findFirstIn(str).getOrElse("") should be("Scala")

      patternFromVar.findAllIn(str).mkString(", ") should be("Scala, scala")

      patternFromVar.replaceFirstIn(str, "Java") should be("Java is scalable and coool")
    }
    it("uses Java Regexp syntax") {
      val txt = "Robe und Rebe bringen Reibereien mit sich."
      ("\\w{2,3}be".r).findAllIn(txt).mkString(",") should be("Robe,Rebe,Reibe")
      (("(Ro|Re|Rei)be").r).findAllIn(txt).mkString(",") should be("Robe,Rebe,Reibe")
    }
    it("can be tested with special syntax") {
      "Hello World" should include regex "(W|w)o.ld".r
      "World" should fullyMatch regex "(W|w)o.ld".r
    }

    it("has some special features") {
      val fourDigits = """(\d\d\d\d)""".r.unanchored //matches for any four digit number within the string
      val berlin__fourDigits__ = """Berlin.*(\d\d\d\d).*""".r.unanchored //anchored makes regexp consider the whole string
      val justFourDigits = """(\d\d\d\d)""".r.anchored //whole string must be four digits

      def copyright(txt : String) : String =  txt match  {
        case justFourDigits(year) => s"j4d: $year"
        case berlin__fourDigits__(year) => s"b4d_: $year"
        case fourDigits(year) => s"4d: $year"
        case _ => ""
      }

      copyright("2016") should be ("j4d: 2016")
      copyright("Berlin, New York: 2016") should be ("b4d_: 2016")
      copyright("New York: 2016") should be ("4d: 2016")
      copyright("New York: 201") should be ("")

    }
  }

}
