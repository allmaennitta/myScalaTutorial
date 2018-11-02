package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.language.implicitConversions
import scala.util.Random

class _12_Traits extends FunSpec with Matchers {

  /**
    * If
    * - you need constructor arguments
    * - need to call the trait directly from Java
    * then better use an abstract class.
    *
    * Or (for Java calls) use a Wrapper (=> see below)
    */
  describe("A trait basically") {
    it("can NOT take arguments.") {
      //trait Animal(name: String) //=> this won't compile
    }

    it("can have abstract fields and methods") {
      //it is the Scala way of "interface"
      trait ATrait {
        val myText: String

        def speak() // no body makes the method abstract
      }
    }

    it("can only be implemented in java by wrapping it in a class") {
      //this is, because on Bytecode-level a trait _is_ a class
      trait ATrait {
        def speak()

        // until this point the trait could still be implemented, because there are no
        // implemented methods
        def speak2() = "hallo" //now there is a method with implementation which prevents direct
        // use in java via 'implements'
      }

      abstract class ATraitWrapper extends ATrait {} //now ATraitWrapper can be extended in Java
      // extending the ATrait
    }

    it("can be specifically called") {
      trait A {
        def hello = "I'm A"
      }
      trait B extends A {
        override def hello = "I'm B"
      }
      trait C extends A {
        override def hello = "I'm C"
      }

      class ClassForTest extends A with B with C {
        def getAAsSuper() = super.hello

        def getA() = super[A].hello

        def getB() = super[B].hello

        def getC() = super[C].hello
      }

      new ClassForTest().hello should be("I'm C")
      new ClassForTest().getAAsSuper() should be("I'm C")
      new ClassForTest().getA() should be("I'm A")
      new ClassForTest().getB() should be("I'm B")
      new ClassForTest().getC() should be("I'm C")
    }

    it("can not only be added to classes but also to specific instances at runtime") {
      //could be used e.g. for logging or debugging purposes

      class DavidBanner
      trait Angry {
        val howAreYou = "I am very angry!"
      }

      val hulk = new DavidBanner with Angry
      hulk.howAreYou should be("I am very angry!")
    }
  }

  describe("A trait") {
    abstract class IntQueue {
      def get(): Int

      def put(x: Int)
    }
    import scala.collection.mutable.ArrayBuffer
    class BasicIntQueue extends IntQueue {
      private val buf = new ArrayBuffer[Int]

      def get() = buf.remove(0)
      def put(x: Int) = {
        buf += x
      }
    }

    it("can be stacked with other traits") {
      val queue = new BasicIntQueue
      queue.put(20)
      queue.get() should be(20)
      an[IndexOutOfBoundsException] should be thrownBy {
        queue.get()
      }

      trait Doubling extends IntQueue { // restrains use on classes extending IntQueue
        //abstract override is an explicit marker that the trait is dynamically bound
        abstract override def put(x: Int) = {
          super.put(2 * x)
        }
      }

      class MyQueue extends BasicIntQueue with Doubling
      val doubledQueue = new MyQueue
      doubledQueue.put(20)
      doubledQueue.get() should be(40)

      trait Incrementing extends IntQueue {
        abstract override def put(x: Int) = {
          super.put(x+1)
        }
      }
      trait Filtering extends IntQueue {
        abstract override def put(x: Int) = {
          if (x >= 0) super.put(x)
        }
      }

      val filteredIncrementQueue =  ( new BasicIntQueue with Incrementing with Filtering)
      filteredIncrementQueue.put(-20)
      filteredIncrementQueue.put(20)
      filteredIncrementQueue.get() should be(21)
    }
  }

  describe("A Java Interface") {
    it("can be implemented like a trait") {
      class Peach
      class Apple extends Comparable[Peach] {
        override def compareTo(peach: Peach): Int = 0
      }
    }
  }

  describe("Use of a trait can be limited") {
    it("by a so called 'self-type'") {
      trait StarfleetWarpCore {
        this: Starship =>
        // this: Starship with WarpCoreEjector with FireExtinguisher => //variation with even
        // more security precautions
      }

      class Starship extends StarfleetWarpCore
      // class Warbird extends StarfleetWarpCore //contradicts selftype -> doesn't compile

      new Starship() shouldBe a [StarfleetWarpCore]
    }

    it("by requiring a specific structural type to be able to implement the trait") {
      trait StarfleetWarpCore {
        this: {def ejectWarpCore(password: String): Boolean
          //def any more methods
        } =>
      }
      class Starship extends StarfleetWarpCore {
        def ejectWarpCore(password: String): Boolean = {
          if (password == "1234") true else false
        }
      }
      //class StarshipInBadShape extends StarfleetWarpCore // does not compile

      new Starship().ejectWarpCore("1234") should be(true)
    }

    it("by the trait extending a specific class") {
      class StarfleetComponent
      class RomulanComponent
      trait StarfleetWarpCore extends StarfleetComponent // a trait extends from a class
      // and thus signals, that it may only be extended
      // if this class is extended

      class Starship extends StarfleetComponent with StarfleetWarpCore
      // class Warbird extends RomulanComponent with StarfleetWarpCore
      // new Warbird () should not be ("able to be compiled")

      new Starship() shouldBe a [StarfleetWarpCore]
    }
  }


  describe("A Helpful predefined trait") {
    it("is the Ordered-Trait") {

      class Dummy(private var prop: String) extends Ordered[Dummy] {

        //even if not readable from outside one instance can see an other instances private fields
        //order is modelled through integer values, 0 means equal
        override def compare(that: Dummy): Int = this.prop compare that.prop
      }

      val dummy = new Dummy("foo")
      //dummy.topsecret //=> nor readable nor writable

      val anotherDummy = new Dummy("bar")
      dummy.compare(anotherDummy) should be(4) //... but still comparable
      anotherDummy.compare(dummy) should be(-4)

      val yetAnotherDummy = new Dummy("foo")
      dummy.compare(yetAnotherDummy) should be(0)
    }
  }


  describe("A directly instantiated trait") {
    it("is possible, (because in bytecode it is a class,) but it's not looking nice :)") {

      trait Floskel {
        val preArr = Array("Naja,", "Also,", "Nü,")
        val postArr = Array(", gell?", ", ne?", ", niwaa?")
        val numberOfPrefixes: Int = preArr.length
        val numberOfSuffixes: Int = postArr.length

        def floskelize(phrase: String): String = {
          val pre = preArr(new Random().nextInt(numberOfPrefixes))
          val post = postArr(new Random().nextInt(numberOfSuffixes))
          val result = s"$pre $phrase$post"
          result
        }
      }

      val floskel = new Floskel {
      }
      val pre = new Array[Int](floskel.numberOfPrefixes)
      val post = new Array[Int](floskel.numberOfSuffixes)
      val iterations = 1000

      for (_ <- 1 to iterations) {
        val sample = floskel.floskelize("ich bin der Martin")
        //println(sample)

        for (index <- 0 until floskel.numberOfPrefixes) {
          if (sample.contains(floskel.preArr(index))) {
            pre(index) += 1
          }
        }
        for (index <- 0 until floskel.numberOfSuffixes) {
          if (sample.contains(floskel.postArr(index))) {
            post(index) += 1
          }
        }
      }

      //all prefixes and suffixes should be contained to equal parts in the answers
      for (x: Int <- post ++ pre) {
        val percentage = (x.toDouble / 1000 * 100).toInt
        println(s"Probablility is 0.$percentage")

        percentage should be(33 +- 4) // <==== That's what the whole test is about
      }
    }
  }
}

