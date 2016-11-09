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

    object Test {
      def apply(x: Int) = x * 2

      def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
    }

    it("uses unapplying / extraction for matching") {
      def isBiggerThan(int : Int) : String = Test(int) match {
        case Test(num) => Test(int) +" is bigger two times than "+num
        case _ => "cannot calculate"
      }

      isBiggerThan(Test(5)) should be ("20 is bigger two times than 10")
      Test(10) should be (20)
    }

  }

}

