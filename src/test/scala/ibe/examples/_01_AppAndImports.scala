package ibe.examples

import org.scalatest._

//import ibe.examples.SalutMonde
import java.text.DateFormat._
import java.util.{Date, Locale}

class _01_AppAndImports extends FunSpec with Matchers {

  describe("A JavaClass") {
    object SalutMonde {
      def execute(): String = {
        val now = new Date
        val df = getDateInstance(LONG, Locale.FRANCE)
        "Salut, monde, " + df.format(now)
      }
    }

    it("can be used by Scala without complications") {
      val salut: String = SalutMonde.execute()
      salut should startWith("Salut, monde")
    }
  }



  describe("A Scala application"){

    /**
      * 1. Put the code into a file named HelloWorld.scala
      * 2. Run 'scalac HelloWorld.scala', which compiles the class
      * 3. Start the app by 'scala HelloWorld'
      *
      * Commandline params are available through implicit args-Array in parent App
      */
    it("can be build by extending App"){
      object HelloWorld extends App {
        println("Hello, world")
        println(args.mkString(", "))
      }
    }
    it("can be launched like that"){
      object HelloWorld {
        /**
          * the scala equivalent of public static void main
          */
        def main(args: Array[String]): Unit = {
          println("Hello, world")
          println(args.mkString(", "))
        }
      }
    }
  }

  describe("An import"){
    it("can be made in a slightly enhanced and simplified way and should still be " +
      "on top and directly below package statement"){

      import javax.swing.{JInternalFrame => JIFrame} //classes can be aliased
      import java.util.{Date, Locale} //already imported on top, just repeating here to show the notation

      val frame = new JIFrame()
      frame should not be Nil
    }
    it("can be made in a static way"){
      import java.lang.Math._ //import statements can be anywhere
      PI should be (3.14 +- 0.1)
    }
  }

}