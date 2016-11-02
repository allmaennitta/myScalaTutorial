package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class ClassesTest extends FunSpec with Matchers {

  describe("Classes") {
    trait SomethingToSay{
      def getTxt(): String
    }

    abstract class ClassWithConstructor() extends SomethingToSay{
      override def toString(): String = {
        ("I say " + this.getTxt())
      }
    }

    it("has constructor args and mutable fields") {
      class MyClass(var bar: String = "meeeh")  extends ClassWithConstructor {
        override def getTxt(): String = this.bar
        def bar(txt: String) {
          bar = txt
        }
      }

      val myClass = new MyClass
      myClass.toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myBar = new MyClass()
      myBar.bar should be("meeeh")
      myBar.bar("uiuiui")
      myBar.bar should be("uiuiui")
      myBar.toString() should be("I say uiuiui")
    }

    it("has constructor args and immutable fields") {
      class MyClass(var bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt(): String = this.bar
      }

      new MyClass().toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myBar = new MyClass()
      myBar.bar should be("meeeh")

      //myBar.bar("uiuiui") //=> not allowed because var is private
      myBar.bar(1) should be ("e") //doesn't change the string, but treats string as array and returns char at pos 1

      //it would be also possible to use 'val' instead of 'var' in constructor args... that would make it double proof :)
    }

    it("has constructor args and no accessible fields") {
      class MyClass(bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt(): String = this.bar
      }

      new MyClass().toString() should be("I say meeeh")
      // new MyClass().bar should be ("meeeh") => not visible
    }
  }
}
