package ibe.examples

import org.scalatest._

//import ibe.examples.SalutMonde
import java.text.DateFormat._
import java.util.{Date, Locale}

class _01_IntroductionTest extends FunSpec with Matchers {

  describe("An example Salut Monde app") {

    object SalutMonde {
      def main(args: Array[String]): Unit = {
        execute()
      } //the scala equivalent of public static void main

      def execute(): String = {
        val now = new Date
        val df = getDateInstance(LONG, Locale.FRANCE)
        "Salut, monde, " + df.format(now).toString()
      }
    }

    it("should deliver a text with >0 chars") {
      val salut: String = SalutMonde.execute()
      salut should startWith("Salut, monde")
    }
  }

  describe("A complex number") {
    class Complex(real: Double, imaginary: Double) {

      def im = imaginary //directly taken from params
      def re = real //not sure if you really should do that. Without var statement the values are just readable anyway

      override def toString(): String = // : String could be omitted because it is deduced
      "" + re + (if (im < 0) "" else "+") + im + "i" //no return necessary, the last value is the one taken
    }

    it("can be prettyprinted") {
      val c = new Complex(0.5, 0.4)
      c.toString() should equal("0.5+0.4i")
    }
  }

  describe("Imports"){
    it("can be made in a slightly enhanced and simplified way and should still be " +
      "on top and directly below package statement"){

      import java.io.File //any java-import is possible
      import scala.math.{Pi, E} //several classes from same package can be imported like that
      import scala.Int._ //all of a package can be imported using _
      import javax.swing.{JInternalFrame => JIFrame} //classes can be aliased

      val frame = new JIFrame()
      frame should not be Nil
    }
  }

}