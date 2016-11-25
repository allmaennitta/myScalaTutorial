package ibe.examples

import java.io.IOException

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class _05_Function extends FunSpec with Matchers {

  describe("A function ") {
    def time() = {
      System.currentTimeMillis()
    }

    def delayed(t: => Long) = {
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

      (end - start) should (be >= 10L and be <= 20L)
    }
  }

  describe("In Scala a function") {
    it("returns a tuple of values") {
      def returnPair() = {
        val x = 42
        val y = "The answer to all questions"
        (x, y)
      }

      val (myInt: Int, myString: String) = returnPair()
      myInt should not be 43
      myInt should be(42)
      myString should be("The answer to all questions")
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

    it("can be a anonymous function") {
      //anonymous functions in source code are called function literals
      //at runtime they are instantiated in objects called function values
      val inc = (x: Int) => x + 1
      inc(7) - 1 should be(7)

      val mul = (x: Int, y: Int) => x * y
      mul(3, 4) should be(12)

      println(System.getProperty("user.home"))
      val userDir = () => {System.getProperty("user.home") }
      userDir() should fullyMatch regex """(/Users/ibeyerlein|C:\\Users\\ingo)""".r
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
      : List[Int] = {    // target is to gain a filtered list
        if (xs.isEmpty) xs //trivial
        else if (p(xs.head)) xs.head :: filter(xs.tail, p) //true: head der liste dem nächsten ergebnis voranstellen
        else filter(xs.tail, p) //nächster wert
      }

      def modN(n: Int)(x: Int) = (x % n) == 0 //when rest of division is 0 => x is divisible by n

    }

    //a closure is a function depending on an outside value
    it("can be a closure") {
      val justStandingHere = "Booo!"
      val concatString = (s: String) => s + justStandingHere

      concatString("I'm so easily startled! ") should be("I'm so easily startled! Booo!")
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
