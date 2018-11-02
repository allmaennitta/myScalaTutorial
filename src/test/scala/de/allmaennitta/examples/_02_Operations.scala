package de.allmaennitta.examples

import org.scalatest._

//import ibe.examples.SalutMonde
import java.text.DateFormat._
import java.util.{Date, Locale}

class _02_Operations extends FunSpec with Matchers {
  describe("A bitwise operation") {
    it("can be performed") {
      val a = 60 /* 60 = 0011 1100 */
      val b = 13 /* 13 = 0000 1101 */
      var c = 0

      a & b should be  (12)   /* 0000 1100 */
      a | b should be  (61)   /* 0011 1101 */
      a ^ b should be  (49)   /* 0011 0001 */
      ~a    should be  (-61)  /* 1100 0011 */
      a << 2  should be (240) /* 1111 0000 */
      a >> 2  should be (15)  /* 1111 */
      a >>> 2 should be (15)  /* 0000 1111 */
    }
  }
}