package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}
import scala.language.implicitConversions

class _11c_CastingAndPackages extends FunSpec with Matchers {

  describe("A class") {
    it("can be identified by its name and it can be cast") {
      case class Person (name: String){}

      val obj : Any = new Person("Helga").asInstanceOf[Any]
      //obj.name => geht nicht
      obj.getClass.toString should be ("class de.allmaennitta.examples._11c_CastingAndPackages$$anonfun$1$$anonfun$apply$mcV$sp$1$Person$3")
      obj.asInstanceOf[Person].name should be ("Helga")
    }
  }

  describe("A package hierarchy") {
    it("can be created within the same file") {

      new de.allmaennitta.examples.packagetests.packageexample.MyDummy()
      new de.allmaennitta.examples.packagetests.packageexample.onestepdeeper.MyDummyOneStepDeeper()
    }
  }
}
