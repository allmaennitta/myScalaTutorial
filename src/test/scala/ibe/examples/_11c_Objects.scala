package ibe.examples

import ibe.packageobject.Margin.Value
import ibe.packageobject.TestObject
import org.scalatest.{FunSpec, Matchers}

import scala.language.implicitConversions

class _11c_Objects extends FunSpec with Matchers {

  describe("An object") {
    /**
      * Methods in an object can be called from Java as "static methods"
      * Utility-classes with static methods would be simple objects in Scala
      */

    it("is always a singleton") {
      object Dummy {
        var test: String = "foo"
      }
      val obj = Dummy
      obj.test = "bar"
      obj.test should be("bar")

      val obj2 = Dummy
      obj.test should be("bar")
    }

    it("can accompaign a class and thus be a so called companion object") {
      //which is a singleton and can have the kind of fields and methods that would be static in Java

      class MyString private(val jString: String) {
        private var extraData = ""

        override def toString: String = jString + extraData
      }

      object MyString {
        def apply(base: String, extras: String = "default!"): MyString = {
          val s = new MyString(base)
          s.extraData = extras
          s
        }
      }
      //new MyString("test").toString should be ("test") //thanks to "class MyString private" not accessible
      MyString("hello", " world!").toString should be("hello world!")
      MyString("hello ").toString should be("hello default!")
    }

    it("can have a minimal companion") {

      class Brain private {
        override def toString = "This is the brain."
      }

      object Brain {
        val brain = new Brain
        def getInstance: Brain = brain
      }

      Brain.getInstance.toString should be("This is the brain.")
    }

    it("can use the comanion object to create static members") {
      // Pizza class
      class Pizza(var crustType: String) {
        override def toString = "Crust type is " + crustType
      }

      // companion object
      object Pizza {
        val CRUST_TYPE_THIN = "thin"
        val CRUST_TYPE_THICK = "thick"
      }

      new Pizza(Pizza.CRUST_TYPE_THICK).crustType should be("thick")
    }
  }

  describe("a package object") {
    it("can be called directly by the classes in the package") {
      TestObject.packageConstant should be(42)
      TestObject.packageEcho("fooooo") should be("fooooo")
      TestObject.packageEnumValue.toString should be("BOTTOM")
    }
  }

  describe("the factory pattern"){
    trait Animal {
      def speak
    }


    object Animal {
      sealed trait Type
      case object DOG extends Type
      case object CAT extends Type

      private class Dog extends Animal {
        override def speak { println("woof") }
      }
      private class Cat extends Animal {
        override def speak { println("meow") }
      }
      // the factory method
      def apply(t: Type): Animal = {
        t match {
          case DOG => new Dog
          case CAT => new Cat
        }
      }
    }

    it("can be realized by using 'apply'"){
      Animal(Animal.DOG).getClass.toString should be ("class ibe.examples._11c_Objects$$anonfun$3$Animal$3$Dog")
      Animal(Animal.CAT).getClass.toString should be ("class ibe.examples._11c_Objects$$anonfun$3$Animal$3$Cat")
    }
  }
}