package ibe.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

class _11a_InheritanceTest extends FunSpec with Matchers {

  //noinspection ScalaDocMissingParameterDescription,ScalaDocMissingParameterDescription
  describe("A Point-class") {

    /** *
      * A trait enabling e.g. points to be compared
      * (which of course could also be done by implementing hash and equals
      * or even simpler: by using a "case class"
      */
    trait StrangeEqual {
      def isEqualInAStrangeWay(x: Any): Boolean

      def isNotEqualInAStrangeWay(x: Any): Boolean = !isEqualInAStrangeWay(x)
    }

    /** *
      * A simple point class
      *
      * @param init_x
      * @param init_y
      */
    class Point(val init_x: Int, val init_y: Int) extends StrangeEqual {
      protected var x : Int = init_x
      protected var y : Int = init_y

      def getX : Int = x

      def getY  : Int = y

      def move(dx: Int, dy: Int) {
        x = x + dx
        y = y + dy
      }

      override def toString: String = {
        //<= please pay attention to "override"-keyword
        s"Point is at x: $x and y: $y."
      }
      override def isEqualInAStrangeWay(obj: Any): Boolean =
        obj.isInstanceOf[Point] &&
          obj.asInstanceOf[Point].x == x
    }

    it("works as it should") {
      val pt = new Point(0, 0)
      pt.toString() should be("Point is at x: 0 and y: 0.")
      pt.move(2, 2)
      pt.toString() should be("Point is at x: 2 and y: 2.")
    }
    it("can be extended by a trait") {
      val p1 = new Point(2, 3)
      val p2 = new Point(2, 4)
      p1.isEqualInAStrangeWay(p2) should be(true)

      val p3 = new Point(3, 3)
      p1.isNotEqualInAStrangeWay(p3) should be(true)
      p1.isNotEqualInAStrangeWay(2) should be(true)
    }

    /** *
      * A subclass of point, amplified by a third dimension
      *
      * @param init_x
      * @param init_y
      * @param init_z
      */
    //noinspection ScalaDocMissingParameterDescription,ScalaDocMissingParameterDescription,ScalaDocMissingParameterDescription
    class Location(
      override val init_x: Int,
      override val init_y: Int,
      val init_z: Int) extends Point(init_x, init_y) {
      protected var z: Int = init_z

      def move(dx: Int, dy: Int, dz: Int) {
        x = x + dx
        y = y + dy
        z = z + dz
      }

      override def toString: String = {
        //<= please pay attention to "override"-keyword
        s"Location is at x: $x, y: $y, z: $z."
      }
    }

    it("can be extended to a 3dimensional Location by sub-classing") {
      val loc = new Location(0, 0, 0)
      loc.toString() should be("Location is at x: 0, y: 0, z: 0.")
      loc.move(2, 2, 2)
      loc.toString() should be("Location is at x: 2, y: 2, z: 2.")
      loc.move(1, 2)
      loc.toString() should be("Location is at x: 3, y: 4, z: 2.")
    }
  }
  describe("An inherited class"){
    case class Address(city : String)

    class Person (var name: String, var address: Address) {
      override def toString : String = if (address == null) name else s"$name @ $address"
    }
    class Employee (name: String, address: Address, var age: Int)
      extends Person (name, address) {

    }
    it(""){

    }
  }
}