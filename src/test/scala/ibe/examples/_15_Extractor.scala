package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _15_Extractor extends FunSpec with Matchers {

  describe("An extractor object") {
    object Email {
      /** *
        * injection method
        */
      def apply(user: String, domain: String) = {
        user + "@" + domain
      }

      /**
        * extraction method
        */
      def unapply(str: String): Option[(String, String)] = {
        val parts = str split "@"
        if (parts.length == 2) {
          Some(parts(0), parts(1))
        } else None
      }
    }

    it("is converting composites in objects and vice versa") {
      Email.apply("hallodrio", "gmail.com") should be("hallodrio@gmail.com")
      Email("hallodrio", "gmail.com") should be("hallodrio@gmail.com") //that's implicit "apply"
      Email.unapply("hallodrio@gmail.com") should be(Some("hallodrio", "gmail.com"))
      Email.unapply("Hallo Welt") should be(None)
    }
  }
  describe("An extractor object used by a match") {

    object Twice {
      /***
        * Injector should be the "uncritical operation. Every number can be multiplicated
        * or every email (even if not valid) can be composed out of two parts and an @ in the middle
        */
      def apply(x: Int) = x * 2

      /**
        * Extractor is the inverse "critical" operation, with the result that the output must be an Option
        */
      def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
    }


    it("uses unapplying / extraction for matching") {
      val x = Twice(21)
      x should be (42)
      x match { case Twice(n) => n should be (21) }
    }

  }

}

