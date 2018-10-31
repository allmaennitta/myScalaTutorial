package de.allmaennitta.examples

import java.io.IOException

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class _05_Functions extends FunSpec with Matchers {

  describe("A function") {
    it("returns a tuple of values") {
      def returnPair() = {
        val x = 42
        val y = "The answer to all questions"
        (x, y) //no return is needed. The last value in the function is considered the return value
      }

      val (myInt: Int, myString: String) = returnPair()
      myInt should not be 43
      myInt should be(42)
      myString should be("The answer to all questions")
    }

    it("can have the return type 'Unit' which is equivalent to the Java 'void'") {
      var sideEffectVictim = 55
      def sideEffectProvoker(): Unit = {
        sideEffectVictim = sideEffectVictim - 44 //with regards to 'why variable access' see closure below
      }

      sideEffectVictim should be (55)
      sideEffectProvoker()
      sideEffectVictim should be (11)
    }

    it("has named params") {
      def division(dnd: Int, dor: Int): Double = {
        dnd / dor
      }
      division(dor = 5, dnd = 10) should not be 0.5
      division(dor = 5, dnd = 10) should be(2)
    }

    it("has defaults") {
      def division(dnd: Int = 10, dor: Int = 5): Double = {
        dnd / dor
      }
      division() should not be 0.5
      division() should be(2)
    }

    it("has a variable number of params") {
      def concatTxt(fragments: String*): String = {
        var phrase: String = ""
        for (f <- fragments) {
          phrase += f
        }
        phrase
      }

      val result = concatTxt("super", "cale", "frage", "listig", "exqui", "ale", "gorisch")
      result should be("supercalefragelistigexquialegorisch")
    }

    it("can be variable for variable types"){
      //printAll(fruits: _*)
    }

    it("can be a higher-order function") {
      def apply(f: Int => String, v: Int) = f(v)
      def layout[A](x: A) = "[" + x.toString + "]"

      apply(layout, 10) should be("[10]")
    }

    it("can return a function"){
      def greeting(language: String) = (name: String) => {
        val english = () => "Hello, " + name
        val spanish = () => "Buenos dias, " + name
        language match {
          case "english" => println("returning 'english' function")
            english()
          case "spanish" => println("returning 'spanish' function")
            spanish()
        }
      }
      val sayHello = greeting("english")
      sayHello("Alex") should be ("Hello, Alex")
    }

    it("can be a anonymous function") {
      //anonymous functions in source code are called function literals
      //at runtime they are instantiated in objects called function values
      val inc = (x: Int) => x + 1
      inc(7) - 1 should be(7)

      val mul: (Int,Int) => Int = (x: Int, y: Int) => x * y //with explicit type definition
      mul(3, 4) should be(12)

      println(System.getProperty("user.home"))
      val userDir = () => {System.getProperty("user.home") }
      userDir() should fullyMatch regex """(/Users/ibeyerlein|C:\\Users\\ingo)""".r
    }

    it("can be an abbreviated anonymous function"){
      val list = List.range(1,17)

      val transformed_list = list.filter( (i:Int) => i%2 == 0 )
      val tl1 = transformed_list.filter( i => i%4 == 0 ) //type is inferred from list type
      val tl2 = tl1.filter( _%8 == 0 ) //variable is not necessary if it is used just once
      tl2 should be (List(8,16))
    }

    it("can be defined in various 'styles'"){
      def modMethod1(i: Int) = i % 2 == 0
      def modMethod2(i: Int) = { i % 2 == 0 }
      def modMethod3(i: Int): Boolean = i % 2 == 0
      def modMethod4(i: Int): Boolean = { i % 2 == 0 }
    }

    it("can be passed as arguments"){
      val double = (i: Int) => { i * 2 }
      List(1,2,3).map(double) should be (List(2,4,6))
    }

    it("can be passed as arguments if the function has the appropriate signature"){
      val double = (i: Int) => { i * 2 }
      def integerListModifier(list: List[Int], modFunction: Int => Int) : List[Int] = {
        list.map(modFunction)
      }
      integerListModifier(List(1,2,3), double) should be (List(2,4,6))
    }

    it("can be applied partially") {
      //anonymous functions in source code are called function literals
      //at runtime they are instantiated in objects called function values
      def log(date: String, message: String): String = {
        s"$date ----- $message"
      }

      val partialLog = log("12:24:00", _: String)
      partialLog("hallooo") should be("12:24:00 ----- hallooo")
      partialLog("xyz") should be("12:24:00 ----- xyz")
    }

    //which should not be mixed with the fact that functions can be realized as PartialFunctions
    it("can be PartialFunctions, in the mathematical sense, that not for all inputs there is an output"){
      val dnd = 42
      val divide = new PartialFunction[Int, Int] {
        def apply(dor: Int) = dnd / dor
        def isDefinedAt(dor: Int) = dor != 0
      }
      if (divide.isDefinedAt(1)) divide(1) should be (42)
      divide.isDefinedAt(0) should be (false)
    }


    //currying means that one param can be added at one time and another dynamcially later
    it("can be curried") {
      def strcat1(s1: String)(s2: String) = s1 + s2
      strcat1("foo")("bar") should be("foobar")

      def strcat2(s1: String) = (s2: String) => s1 + s2
      strcat2("foo")("bar") should be("foobar")

      def joinTogetherWithFoo(p: String => String): String = {
        p("FOOOOOO")
      }

      joinTogetherWithFoo(strcat1("Bar")) should be("BarFOOOOOO")
    }

    it("can be curried even more") {
      val nums = List(1, 2, 3, 4, 5, 6, 7, 8)

      //at this point we only know by which value a value should be divisible.
      //the concrete value is known only within the function
      //that's why we call the function modN with just one param, even if it needs two params
      filter(nums, modN(2)) should be(List(2, 4, 6, 8))
      filter(nums, modN(3)) should be(List(3, 6))

      def filter(xs: List[Int],     // a list
        p: Int => Boolean) // gets filtered by a boolean filter function
      : List[Int] = {    // objective is to gain a filtered list
        if (xs.isEmpty) xs //trivial
        else if (p(xs.head)) xs.head :: filter(xs.tail, p) //true: head der liste dem nächsten ergebnis voranstellen
        else filter(xs.tail, p) //nächster wert
      }

      def modN(n: Int)(x: Int) = (x % n) == 0 //when rest of division is 0 => x is divisible by n

    }

    /**
     * For closure example below
     */
    class Foo {
      // a method that takes a function and a string, and passes the string into
      // the function, and then executes the function
      def exec(f:(String) => String, name: String) : String = {
        f(name)
      }
    }

    // Closure functionality means that a function refers to value in the scope the function was designed in
    // E.g. here sayHello method references the variable hello from within the exec method of the Foo class on the
    // first run (where hello was no longer in scope), but on the second run, it also picked up the change to the
    // hello variable (from Hello to Hola).
    it("can be a closure") {
        var hello = "Hello"
        def sayHello(name: String): String = {
          s"$hello, $name"
        }

        sayHello("Hugo") should be("Hello, Hugo")

        // execute sayHello from the exec method foo
        val foo = new Foo
        foo.exec(sayHello, "Al") should be("Hello, Al")

        // change the local variable 'hello', then execute sayHello from
        // the exec method of foo, and see what happens
        hello = "Hola"
        foo.exec(sayHello, "Ayo") should be("Hola, Ayo")
    }
  }


  describe("A function can have by-name-params, being evaluated lazily") {
    def time() = {
      System.currentTimeMillis()
    }

    def delayed(t: => Long) = { // t: => Long is a "by-name parameter", like "give me something that will generate a
                                // Long value on request
      Thread sleep 10
      t
    }

    it("evaluates its params immedately") {
      val start = System.currentTimeMillis()
      val end = time()
      (end - start) should be < 5L
    }

    it("evaluates its params lazily 'call-by-name'") {
      val start = System.currentTimeMillis()
      val end = delayed(time())

      (end - start) should (be >= 10L and be <= 45L)
    }
  }



  describe("A recursive function") {
    //it can do anything for which an iteration could be used - and vice versa
    it("can reverse letters") {
      def reverseString(word: String): String = {
        if (word == null || word.equals("")) {
          word
        } else {
          val nextIterationWord = word.substring(1, word.length())
          val firstLetter = word.substring(0, 1)
          println(nextIterationWord + firstLetter)
          reverseString(nextIterationWord) + firstLetter
        }
      }

      reverseString("Reliefpfeiler") should be("reliefpfeileR")
      reverseString("Ingo") should be("ognI")
    }

    it("can remove duplicates") {
      def removeDuplicates(word: String): String = {
        if (word == null || word.length() <= 1)
          word
        else if (word.charAt(0) == word.charAt(1))
          removeDuplicates(word.substring(1, word.length()))
        else
          word.charAt(0) + removeDuplicates(word.substring(1, word.length()))
      }
      removeDuplicates("Azziiiiiizaaaaaam, kheiiiiliii dussssset daraaaaaam!") should be("Azizam, kheili duset daram!")
    }

  }
}
