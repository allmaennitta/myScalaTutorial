package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.language.implicitConversions

class _18_Implicits extends FunSpec with Matchers {

  /** *
    * Implicits are sort of "magic" and have some quite severe drawbacks.
    * Demo is more for passive knowledge.
    */
  describe("Implicit values and functions") {
    it("can be looked up in scope") {
      implicit val a = 7
      implicit val b = "Hello"
      //implicit val c = 7 //=> ambiguous implicit values

      def greet(implicit greeting: String, code: Int) = s"$greeting, 00$code!"

      greet should be("Hello, 007!")
    }
    it("can be converted if there is an implic converter present") {
      implicit def agentCodename(i: Int) : String = s"00$i" //converts int to string

      def hello(name: String) = s"Hello, $name!" //takes string

      hello(7) should be("Hello, 007!") //offers int which can be converted to string by implicit function
    }
  }

  /**
    * Implicits are helpful for Typeclasses
    * The idea of typeclasses is that you provide evidence (WrapperCanFoo) that a class (Wrapper) satisfies an interface (CanFoo).
    */
  describe("Typeclasses") {
    trait CanFoo[A] {
      def foos(x: A): String
    }

    object CanFoo {
      def apply[A: CanFoo]: CanFoo[A] = implicitly
    }

    case class Wrapper(wrapped: String)

    implicit object WrapperCanFoo extends CanFoo[Wrapper] {
      def foos(x: Wrapper) = x.wrapped
    }

    def foo[A: CanFoo](thing: A) = CanFoo[A].foos(thing) //

    it("is kind of rocket science to me, to be honest :)") {
      foo(Wrapper("hi")) should be("hi")

    }

    /***
      * Don't forget: your implicit class must be defined in a class, object, package object
      * e.g. by import de.mytoys.utils.StringUtils._
      */
    it("helps to add your own functionality to String, for example") {
      implicit class StringImprovements(s: String) {
        def increment = s.map(c => (c + 1).toChar)
      }
      "HAL".increment should be ("IBM") //Das ist schon krass :)
    }
  }


}

