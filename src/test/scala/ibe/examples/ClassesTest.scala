package ibe.examples

import org.scalatest.{FunSpec, Matchers}

class ClassesTest extends FunSpec with Matchers {

  describe("A class has constructor args and ") {
    trait SomethingToSay{
      def getTxt(): String
    }

    abstract class ClassWithConstructor() extends SomethingToSay{
      override def toString(): String = {
        ("I say " + this.getTxt())
      }
    }

    it("has a mutable and outside visible field") {
      class MyClass(var bar: String = "meeeh")  extends ClassWithConstructor {
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

      myClass.bar(1) should be ("e") //doesn't change the string, but treats string as array and returns char at pos 1
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
      myBar.getBar(1) should be ("e") //doesn't change the string, but treats string as array and returns char at pos 1
    }
  }

  describe("A class"){

    class Point(init_x: Int, init_y: Int){
      private var x = init_x
      private var y = init_y
      def getX = x
      def getY = y
      def move(dx : Int, dy : Int) {
        x = x + dx
        y = y + dy
      }
      override def toString(): String ={ //<= please pay attention to "override"-keyword
        s"Point is at x: $x and y: $y."
      }
    }

    it("can be a moving point"){
      val pt = new Point(0,0)
      pt.toString() should be ("Point is at x: 0 and y: 0.")
      pt.move(2,2)
      pt.toString() should be ("Point is at x: 2 and y: 2s.")
    }

    it("can be extended to a 3dimensional location"){
      //LOCATION
    }
  }
}
