package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _07_StringTest extends FunSpec with Matchers {

  describe("A String"){

    it ("can be formatted for output") {
      val foo = "FOO"
      val bar = "BAR"
      val zahl = 0.23444
      s"$foo and $bar are some funny names." should be ("FOO and BAR are some funny names.")
      s"1 + 1 = ${1+1}" should be ("1 + 1 = 2")
      f"Die Zahl $zahl%.3f ist schön." should be ("Die Zahl 0,234 ist schön.")
      //can be customized
      //see http://docs.scala-lang.org/overviews/core/string-interpolation.html

    }


    it ("can be formatted on the console for output like in Java") {
      val stream = new java.io.ByteArrayOutputStream()

      Console.withOut(stream) {   //all printlns in this block will be redirected to the stream
        printf("%s und %.3f sind Gleitkommazahlen","0,276", 0.276)
      }

      stream.toString("UTF-8") should be ("0,276 und 0,276 sind Gleitkommazahlen")
    }
  }

}