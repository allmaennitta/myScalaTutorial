package ibe.examples

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class ComplexTest extends FunSuite  with Matchers  {

  test("testToString") {
    val c = new Complex(0.5, 0.4)
    c.toString() should equal ("0.5+0.4i")
  }

}
