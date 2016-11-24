package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.language.implicitConversions
import scala.util.Random

class _11b_Traits extends FunSpec with Matchers {

  /**
    * If
    * - you need constructor arguments
    * - need to call the trait directly from Java
    * then better use an abstract class.
    *
    * Or (for Java calls) use a Wrapper (=> see below)
    */
  describe("A trait") {
    it("can't take arguments.") {
      //trait Animal(name: String) //=> this won't compile
    }

    it("can even be instantiated as itself... but it's not looking nice :)") {
      trait Floskel {
        val preArr = Array("Naja,", "Also,", "Nü,")
        val postArr = Array(", gell?", ", ne?", ", niwaa?")
        val numberOfPrefixes : Int = preArr.length
        val numberOfSuffixes : Int = postArr.length

        def floskelize(phrase: String): String = {
          val pre = preArr(new Random().nextInt(numberOfPrefixes))
          val post = postArr(new Random().nextInt(numberOfSuffixes))
          val result = s"$pre $phrase$post"
          result
        }
      }

      val pre = new Array[Int](new Floskel {}.numberOfPrefixes)
      val post = new Array[Int](new Floskel {}.numberOfSuffixes)
      val iterations = 1000

      for (_ <- 1 to iterations) {
        val sample = new Floskel {}.floskelize("ich bin der Martin")
        println(sample)

        for (index <- 0 until new Floskel {}.numberOfPrefixes) {
          if (sample.contains(new Floskel {}.preArr(index))) {
            pre(index) += 1
          }
        }
        for (index <- 0 until new Floskel {}.numberOfSuffixes) {
          if (sample.contains(new Floskel {}.postArr(index))) {
            post(index) += 1
          }
        }
      }

      //all prefixes and suffixes should be contained in a
      for (x: Int <- post ++ pre) {
        val percentage = (x.toDouble / 1000 * 100).toInt
        println(s"Probablility is 0.$percentage")
        percentage should be(33 +- 4)
      }
    }

    it("can have abstract fields and methods"){
      trait ATrait {
        def speak()   // no body makes the method abstract
      }
    }
  }

  describe("A Helpful predefined traits") {
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
}

