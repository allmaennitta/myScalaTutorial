package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

class _10_Classes extends FunSpec with Matchers {

  describe("A class") {
    trait SomethingToSay {
      //that's a scala-"interface"
      def getTxt: String
    }

    abstract class ClassWithConstructor() extends SomethingToSay {
      override def toString: String = {
        "I say " + this.getTxt
      }
    }

    it("has a mutable and thus public constructor arg with a default") {
      class MyClass(var bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt: String = this.bar
      }

      val myClass = new MyClass
      myClass.toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myBar = new MyClass()
      myBar.bar should be("meeeh")
      myBar.bar = "uiuiui"
      myBar.bar should be("uiuiui")
      myBar.toString() should be("I say uiuiui")
    }

    it("has a just readable (aka final) constructor arg") {
      class MyClass(val bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt: String = bar
        // def setBar(txt : String){bar = txt} // reassignment to val
      }

      class MyShorterClass(bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt: String = bar

        // def setBar(txt : String){this.bar = txt} // reassignment to val
      }

      new MyClass().toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myClass = new MyClass()
      //noinspection ScalaUnusedSymbol
      val myShorterClass = new MyShorterClass()
      //myClass.bar("uiuiui") //=> just Int-function offered to get position of char-array
      //myShorterClass.bar("uiuiui") //=> bar is not even visible from outside

      myClass.bar(1) should be('e') //doesn't change the string, but treats string as array and returns char at pos 1
    }

    it("has overwritable getters and setters, aka accesors and mutators") {
      class MyClass(private var _name: String = "Karl-Heinz") {
        def name: String = _name
        // accessor
        def name_=(aName: String) {_name = aName }  // mutator
      }

      val clz = new MyClass("Horst")
      clz.name should be("Horst") //as accessor
      clz.name = "Johnny"           //as mutator
      clz.name should be("Johnny")
    }

    /**
      * “Methods which act as accessors
      * of any sort ... should be declared without
      * parentheses, except if they have side effects.”
      * Scala Style Guide
      */
    it("has accessors, which per styleguide definition should have no parentheses") {
      class MyDummy(_myVal: Int) {
        def myVal = _myVal
      }

      val dummy = new MyDummy(8)
      dummy.myVal should be(8)
    }


    it("has visible fields which are not part of the constructor") {
      class Dummy {
        var accessible: String = "valar"
        val readable: String = "morghulis"
      }

      val dummy = new Dummy()
      dummy.accessible should be("valar")
      dummy.accessible = "dracaris"
      dummy.accessible should be("dracaris")
      dummy.readable should be("morghulis")
      //dummy.readable = "error" //=> not allowed
    }

    it("has invisible class-private-fields") {
      class Dummy(secret: String) {
        private val topsecret: String = secret

        //even if not readable from outside one instance can see an other instances private fields
        //order is modelled through integer values, 0 means equal
        def readOtherObjectsProperty(that: Dummy): String = that.topsecret
      }

      val dummy = new Dummy("I know nothing!")
      //dummy.topsecret //=> nor readable nor writable

      val anotherDummy = new Dummy("You know too much!")
      dummy.readOtherObjectsProperty(anotherDummy) should be("You know too much!")
    }

    it("has invisible object-private-fields") {
      class VeryTopSecret {
        //noinspection ScalaUnusedSymbol
        private[this] var topsecret: String = "readableJustByInstance"
        //def readOtherObjectsProperty(that: VeryTopSecret): String = that.topsecret //=> that.topsecret is inaccessible
      }
    }

    it("has block fields or / and lazy fields") {
      class LazyLooner {
        lazy val blockfield: String = {
          val buffer = new ArrayBuffer[String]()
          for (_ <- 1 to 10) {buffer.append("b") }
          buffer.mkString
        }
      }

      //due to "lazy" keyword the field is only accessed as soon as it is read
      new LazyLooner().blockfield should be("bbbbbbbbbb")

    }
  }


  describe("A method in a class") {
    /**
      * Intellij-CodeCheck has a config-option that fields and methods of a specific visibility
      * should have an EXPLICIT return type (CodeStyle - Scala - TypeAnnotations)
      *
      * More info about privacy-levels is in
      *
      * @see  de.allmaennitta.examples._10_Classes
      */
    it("can be public, package-specific, package-, protected, private or object-private-visible") {

      //noinspection ScalaUselessExpression
      class Dummy() {
        def pass() = {}
        //without access modifier methods are public
        def sayInPublic(): String = {
          pass()
          "foo"
        }

        //visible in "allmaennitta"- and including packages
        private[allmaennitta] def sayInMoreGeneralPackage(): String = {
          pass()
          "foo"
        }

        // visible just in "allmaennitta.examples"
        private[examples] def sayInMoreSpecificPackage(): String = {
          pass()
          "foo"
        }

        //visible for subclasses
        protected def sayProtected(): String = {
          pass()
          "foo"
        }

        //visible in objects of the same class
        private def sayInPrivate(): String = {
          pass()
          "foo"
        }
        //visible in just the specified object
        private[this] def sayInObjectPrivate(): String = {
          pass()
          "foo"
        }
        //method to test private vs object-private
        def returnOtherObjectsMethodResult(otherObject: Dummy): String = {
          otherObject.sayInPrivate()
        }

        sayInPrivate() should be("foo")
        sayInObjectPrivate() should be("foo")
      }

      new Dummy().sayInPublic() should be("foo")
      new Dummy().sayInMoreGeneralPackage() should be("foo")
      new Dummy().sayInMoreSpecificPackage() should be("foo")
      new Dummy().returnOtherObjectsMethodResult(new Dummy()) should be("foo")

      class SubDummy extends Dummy {
        this.sayProtected() should be("foo")
      }

    }


    describe("A class") {
      it("can have a primary constructor. The constructor 'is' the whole class") {

        class Dummy(a: Int, b: Int) {
          var x = 0
          def foo(): String = {"bar" }
          x += a
          def checkXEarly(): Int = {x }
          val earlyX: Int = x
          def bar(): String = {"foo" }
          x += b
          def checkXLate(): Int = {x }
          val lateX: Int = x
        }

        val dummy = new Dummy(2, 3)
        dummy.checkXEarly() should be(5)
        dummy.checkXLate() should be(5)
        dummy.earlyX should be(2)
        dummy.lateX should be(5)
      }

      it("can have several auxiliary constructors which must refer to one former constructor") {

        class AlleGutenDingeSind(val eins: Int, val zwei: Int, val drei: Int) {
          var vier: Int = _

          def this(eins: Int) {this(eins, 0, 0) }
          def this(vier_init: String) {
            this(0)
            vier = vier_init.toInt
          }

          new AlleGutenDingeSind(1, 2, 3).zwei should be(2)
          new AlleGutenDingeSind(3).eins should be(3)
          new AlleGutenDingeSind(3).drei should be(0)
          new AlleGutenDingeSind("4").vier should be(4)
          new AlleGutenDingeSind(3).zwei should be(0)

        }

      }
      it("can have an idividual equals / hashCode implementation") {

        class Example(val x: Int, y: String) {
          var z = 0
          //noinspection ScalaUnusedSymbol
          private var zz = 0
          //noinspection ScalaUnusedSymbol
          private[this] var zzz = 0

          // completed by Intellij Generator
          // x (preselected) and z, zz, zzz are offered for equal-selection
          // x is offered for hashCode-selection

          def canEqual(other: Any): Boolean = other.isInstanceOf[Example]
          override def equals(other: Any): Boolean = other match {
            case that: Example =>
              (that canEqual this) &&
                x == that.x
            case _ => false
          }
          override def hashCode(): Int = {
            val state = Seq(x)
            state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
          }
        }
        //Equal-Check is done by using '=='!
        (new Example(3, "foo") == new Example(3, "bar")) should be(true)
      }

      it("can have inner classes, which are bound to the object and not to the class like in java") {

        class PandorasBox {

          case class Thing(name: String)

          var things = new collection.mutable.ArrayBuffer[Thing]()
          things += Thing("Evil Thing #1")
          things += Thing("Evil Thing #2")
          def addThing(name: String) {things += Thing(name) }
        }

        new PandorasBox().Thing("test").name should be("test")
        val box1 = new PandorasBox()

        box1.things(1).name should be("Evil Thing #2")
      }
    }

    describe("For fluent programming a class") {
      it("should return itself by 'this' if it can't be extended") {
        final class Service() {
          private var name = ""
          private var priority: Int = 0

          def setName(name: String): Service = {
            this.name = name
            this
          }
          def setPriority(priority: Int): Service = {
            this.priority = priority
            this
          }
          override def toString = s"Service: '$name' runs with priority $priority"
        }
        new Service()
          .setName("main")
          .setPriority(5)
          .toString should be("Service: 'main' runs with priority 5")
      }

      it("should return itself by 'this.type' if it can be extended") {
        class Service {
          protected var name = ""
          protected var priority: Int = 0

          def setName(name: String): this.type = {
            this.name = name
            this
          }
          def setPriority(priority: Int): this.type = {
            this.priority = priority
            this
          }
          override def toString = s"Service: '$name' runs with priority $priority"
        }

        class JavaService extends Service {
          protected var javaVersion = "8"

          def setJavaVersion(javaVersion: String): this.type = {
            this.javaVersion = javaVersion
            this
          }
          override def toString = s"Service: '$name' runs with priority $priority and has javaVersion: $javaVersion"
        }

        new JavaService()
          .setName("main")
          .setJavaVersion("7")
          .setPriority(5)
          .toString should be("Service: 'main' runs with priority 5 and has javaVersion: 7")
      }
    }


    describe("A case class") {

      case class Dog(color: String = "brown", race: String = "terrier") {
        def bark(): String = {s"I'm a $color $race" }
      }

      it("is a normal per default immutable class with built-in hash/equals/toString per fields") {
        val leo = Dog()
        //no new necessary
        val fiffi = Dog()
        leo.bark() should be("I'm a brown terrier")
        leo.toString should be("Dog(brown,terrier)")
        leo.color should be("brown")
        leo should be(fiffi) //hash and equals by fields is already built-in
        //leo.color = "white" //=> reassignment to val
      }

      it("can be modified best by simply copying") {
        val maxi = Dog(color = "yellow", race = "boxer")
        val urs = maxi.copy(race = "pintcher")
        maxi should not be urs
        urs.race should be("pintcher")
      }
    }


    describe("An abstract class") {
      it("can have unimplemented methods or fields") {
        abstract class Dog(color: String = "brown", race: String = "terrier") {
          val age: Int
          def bark(): String
        }
      }
    }
  }
}
