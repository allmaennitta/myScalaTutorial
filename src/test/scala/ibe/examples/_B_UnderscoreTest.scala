package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _B_UnderscoreTest extends FunSpec with Matchers {

  describe("Underscore") {

    it("means different things") {
      //Existential types
      //http://stackoverflow.com/questions/15186520/scala-any-vs-underscore-in-generics
      def foo(l: List[Option[_]]) = l //In List-case it is equal to "Any"... but not with a Set
      foo(List(Option(1), Option(2)))(0).getOrElse(0) should be(1)

      //Higher kinded type parameters
      case class A[K[_], T](a: K[T]) //Compiler needs not to know of which type K may be
      val list = List[Int](1, 5, 8)
      val a = A(list)
      a.a should be(List(1, 5, 8))

      //Ignored variables
      def f() = {
        println("I ran"); 42
      }
      /*def g(): Unit = {f()} //outdated: warning: discarded non-unit value!*/
      def g(): Unit = {
        val _ = f()
      }

      g() should not be 42

      // Ignored parameters
      List(1, 2, 3) foreach { _ => println("Hi") }

      //Ignored names of self types
      trait MySeq {
        _: Seq[_] =>
      }

      //Wildcard patterns
      (Some(5) match { case Some(_) => true }) should be (true)

      //Wildcard imports
      import java.io.File._

      //Hiding imports
      import java.io.{File => _, _} /* Technically an alias to nothing */

      //Joining letters to punctuation
      def bang_!(x: Int) = 5
      bang_!(7) should be (5)

      //Placeholder syntax
      List(1, 2, 3) map (_ + 2) should be (List(3, 4, 5))


      //Partially applied functions
      List(1, 2, 3) foreach println _

    }

  }
}
