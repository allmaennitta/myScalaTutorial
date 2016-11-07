package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class ClassesTest extends FunSpec with Matchers {

  describe("A class has constructor args and ") {
    trait SomethingToSay {
      def getTxt(): String
    }

    abstract class ClassWithConstructor() extends SomethingToSay {
      override def toString(): String = {
        ("I say " + this.getTxt())
      }
    }

    it("has a mutable and outside visible field") {
      class MyClass(var bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt(): String = this.bar
      }

      val myClass = new MyClass
      myClass.toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myBar = new MyClass()
      myBar.bar should be("meeeh")
      myBar.bar = "uiuiui"
      myBar.bar should be("uiuiui")
      myBar.toString() should be("I say uiuiui")
    }

    it("has a final field") {
      class MyClass(val bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt(): String = this.bar

        // def setBar(txt : String){this.bar = txt} // reassignment to val
      }
      class MyShorterClass(bar: String = "meeeh") extends ClassWithConstructor {
        override def getTxt(): String = this.bar

        // def setBar(txt : String){this.bar = txt} // reassignment to val
      }

      new MyClass().toString() should be("I say meeeh")
      new MyClass().bar should be("meeeh")
      val myClass = new MyClass()
      val myShorterClass = new MyShorterClass()
      //myClass.bar("uiuiui") //=> just Int-function offered to get position of char-array
      //myShorterClass.bar("uiuiui") //=> bar is not even visible from outside

      myClass.bar(1) should be("e") //doesn't change the string, but treats string as array and returns char at pos 1
    }

    it("has a just readable field") {
      class MyClass(var initial_bar: String = "meeeh") extends ClassWithConstructor {
        private val bar = initial_bar

        override def getTxt(): String = this.bar

        def getBar = bar
      }

      new MyClass().toString() should be("I say meeeh")
      new MyClass().getBar should be("meeeh")
      val myBar = new MyClass()
      myBar.getBar should be("meeeh")
      //myBar.bar should be("meeeh") => inaccessible
      myBar.getBar(1) should be("e") //doesn't change the string, but treats string as array and returns char at pos 1
    }
  }

  describe("A class") {
    /** *
      * A simple point class
      *
      * @param init_x
      * @param init_y
      */
    class Point(val init_x: Int, val init_y: Int) extends Equal {
      protected var x = init_x
      protected var y = init_y

      def getX = x

      def getY = y

      def move(dx: Int, dy: Int) {
        x = x + dx
        y = y + dy
      }

      override def toString(): String = {
        //<= please pay attention to "override"-keyword
        s"Point is at x: $x and y: $y."
      }

      override def isEqualInAStrangeWay(obj: Any): Boolean =
        obj.isInstanceOf[Point] &&
          obj.asInstanceOf[Point].x == x
    }
    /** *
      * A subclass of point, amplified by a third dimension
      *
      * @param init_x
      * @param init_y
      * @param init_z
      */
    class Location(override val init_x: Int,
                   override val init_y: Int,
                   val init_z: Int) extends Point(init_x, init_y) {
      protected var z: Int = init_z

      def move(dx: Int, dy: Int, dz: Int) {
        x = x + dx
        y = y + dy
        z = z + dz
      }

      override def toString(): String = {
        //<= please pay attention to "override"-keyword
        s"Location is at x: $x, y: $y, z: $z."
      }
    }
    /** *
      * A trait enabling e.g. points to be compared
      * (which of course could also be done by implementing hash and equals
      */
    trait Equal {
      def isEqualInAStrangeWay(x: Any): Boolean

      def isNotEqualInAStrangeWay(x: Any): Boolean = !isEqualInAStrangeWay(x)
    }

    it("works as it should") {
      val pt = new Point(0, 0)
      pt.toString() should be("Point is at x: 0 and y: 0.")
      pt.move(2, 2)
      pt.toString() should be("Point is at x: 2 and y: 2.")
    }

    it("can be extended to a 3dimensional location") {
      val loc = new Location(0, 0, 0)
      loc.toString() should be("Location is at x: 0, y: 0, z: 0.")
      loc.move(2, 2, 2)
      loc.toString() should be("Location is at x: 2, y: 2, z: 2.")
      loc.move(1, 2)
      loc.toString() should be("Location is at x: 3, y: 4, z: 2.")
    }

    it("can be extended by a trait") {
      val p1 = new Point(2, 3)
      val p2 = new Point(2, 4)
      p1.isEqualInAStrangeWay(p2) should be(true)

      val p3 = new Point(3, 3)
      p1.isNotEqualInAStrangeWay(p3) should be(true)
      p1.isNotEqualInAStrangeWay(2) should be(true)
    }
  }

  describe("A class") {
    it("can have a companion object") {
      //which is a singleton and can have the kind of fields and methods that would be static in Java

      MyString("hello", " world!").toString should be("hello world!")
      MyString("hello").toString should be("hello")

    }

  }

}
