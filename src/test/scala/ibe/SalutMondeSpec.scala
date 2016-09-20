package ibe

import org.scalatest._
import ibe.SalutMonde.execute

class SalutMondeSpec extends FlatSpec with Matchers {

  "An app" should "be able to greet with date" in {
    //var salutMonde = new SalutMonde

    var txt = execute()
  }

//  it should "throw NoSuchElementException if an empty stack is popped" in {
//    val emptyStack = new Stack[Int]
//    a [NoSuchElementException] should be thrownBy {
//      emptyStack.pop()
//    }
//  }
}