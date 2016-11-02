package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class _10_SetTest extends FunSpec with Matchers {

  describe("A set") {
    it("is immutable") {
      Set(1,3,3,3,7) should be (Set(1,3,7))
      Set(1,3,3,3,7) should not be (Array(1,3,7))
    }

  }
}