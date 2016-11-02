package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class UnderscoreTest extends FunSpec with Matchers {

  describe("Underscore") {

    it("means different things") {
    //Existential types
    // def foo(l: List[Option[_]]) =

    //Higher kinded type parameters
    //case class A[K[_],T](a: K[T])

    //Ignored variables
    //val _ = 5

    // Ignored parameters
    // List(1, 2, 3) foreach { _ => println("Hi") }

    //Ignored names of self types
    //trait MySeq { _: Seq[_] => }

    //Wildcard patterns
    //Some(5) match { case Some(_) => println("Yes") }

    //Wildcard imports
    //import java.util._

    //Hiding imports
    //import java.util.{ArrayList => _, _}

    //Joining letters to punctuation
    //def bang_!(x: Int) = 5

    //Assignment operators
    //def foo_=(x: Int) { ... }

    //Placeholder syntax
    //List(1, 2, 3) map (_ + 2)

    //Partially applied functions
    //List(1, 2, 3) foreach println _

    //Converting call-by-name parameters to functions
    //def toFunction(callByName: => Int): () => Int = callByName _

    //Example showing why foo(_) and foo _ are different (from http://stackoverflow.com/users/515054/0)
//      trait PlaceholderExample {
//        def process[A](f: A => Unit)
//
//        val set: Set[_ => Unit]
//
//        set.foreach(process _) // Error
//        set.foreach(process(_)) // No Error
//      }
    }

  }
}
