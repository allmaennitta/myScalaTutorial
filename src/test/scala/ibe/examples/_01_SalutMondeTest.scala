package ibe.examples

import org.scalatest._
//import ibe.examples.SalutMonde
import java.text.DateFormat._
import java.util.{Date, Locale}

class _01_SalutMondeTest extends FunSpec with Matchers {

  describe("An example Salut Monde app"){

    object SalutMonde {
      def main(args: Array[String]): Unit = { execute()}

      def execute(): String = {
        val now = new Date
        val df = getDateInstance(LONG, Locale.FRANCE)
        "Salut, monde, " +  df.format(now).toString()
      }
    }

    it ("should deliver a text with >0 chars") {
      val salut : String = SalutMonde.execute()
      println(salut)
     salut should startWith ("Salut, monde")
    }
  }

  describe("A complex number"){
    class Complex (real: Double, imaginary: Double) {
      def re = real       //directly taken from params
      def im = imaginary  //directly taken from params
      override def toString() : String =                // : String could be omitted because it is deduced
      "" + re + (if (im < 0) "" else "+") + im + "i" //no return necessary, the last value is the one taken
    }

    it("can be prettyprinted") {
      val c = new Complex(0.5, 0.4)
      c.toString() should equal ("0.5+0.4i")
    }
  }

}