package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.language.implicitConversions
import scala.util.Random

class _11c_ClassMetaTest extends FunSpec with Matchers {

  describe("A class from a meta point of view") {
    it("can be cast") {
      case class Person (name: String){}

      val obj : Any = new Person("Helga").asInstanceOf[Any]
      //obj.name => geht nicht
      obj.getClass.toString should be ("class ibe.examples._11c_ClassMetaTest$$anonfun$1$$anonfun$apply$mcV$sp$1$Person$3")
      obj.asInstanceOf[Person].name should be ("Helga")


    }

  }
}
