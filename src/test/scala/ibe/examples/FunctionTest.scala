package ibe.examples

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class FunctionTest extends FunSpec with Matchers {

  describe("In Scala a function") {
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

    it("is evaluates its params lazily 'call-by-name'") {
      val start = System.currentTimeMillis()
      val end = delayed(time())
      (end - start) should (be >= 10L and be <= 15L)
    }
  }

  describe("In Scala a function") {
    it("has named params") {
      def division(dnd: Int, dor: Int): Double = {
        return dnd / dor
      }
      (division(dor = 5, dnd = 10)) should not be (0.5)
      (division(dor = 5, dnd = 10)) should be(2)
    }
    it("has named params and defaults") {
      def division(dnd: Int = 10, dor: Int = 5): Double = {
        return dnd / dor
      }
      division() should not be (0.5)
      division() should be(2)
    }

    it("has a variable number of params") {
      def concatTxt(fragments: String*): String = {
        var phrase: String = ""
        for (f <- fragments) {
          phrase += f
        }
        return phrase
      }

      val result = concatTxt("super", "cale", "frage", "listig", "exqui", "ale", "gorisch")
      result should be("supercalefragelistigexquialegorisch")
    }

    it("can call itself recursively and reverse letters") {
      def reverseString(word : String) : String =  {
        if(word == null || word.equals("")){
          return word;
        } else {
          val nextIterationWord = word.substring(1, word.length())
          val firstLetter = word.substring(0, 1)
          println(nextIterationWord + firstLetter)
          return reverseString(nextIterationWord) + firstLetter
        }
      }

      (reverseString("Reliefpfeiler")) should be ("reliefpfeileR")
      (reverseString("Ingo")) should be ("ognI")
    }

    it("can call itself recursively and remove duplicates") {
      def removeDuplicates(word : String) : String =  {
        if(word == null || word.length() <= 1)
          return word;
        else if( word.charAt(0) == word.charAt(1) )
          return removeDuplicates(word.substring(1, word.length()));
        else
          return word.charAt(0) +
            removeDuplicates(word.substring(1, word.length()));
      }
      (removeDuplicates("Azziiiiiizaaaaaam, kheiiiiliii dussssset daraaaaaam!")) should be ("Azizam, kheili duset daram!")
    }

    it("can be a higher-order function"){
      def apply(f: Int => String, v: Int) = f(v)
      def layout[A](x: A) = "[" + x.toString()+ "]"

      apply(layout, 10) should be ("[10]")
    }

    it("can be a anonymous function"){
      //anonymous functions in source code are called function literals
      //at runtime they are instantiated in objects called function values
      var inc = (x:Int) => x+1
      inc(7)-1 should be (7)

      var mul = (x:Int, y: Int) => x*y
      mul(3,4) should be (12)

      println(System.getProperty("user.home"))
      var userDir = () => {System.getProperty("user.home")}
      userDir() should be ("/Users/ibeyerlein")
    }

    it("can be a applied partially"){
      //anonymous functions in source code are called function literals
      //at runtime they are instantiated in objects called function values
      def log(date : String, message : String): String ={
        return s"$date ----- $message"
      }

      val partialLog = log("12:24:00", _ : String)
      partialLog("hallooo") should be ("12:24:00 ----- hallooo")
      partialLog("xyz") should be ("12:24:00 ----- xyz")
    }

    it("can be curried"){
      def strcat1(s1: String) (s2: String) = s1 + s2
      def strcat2(s1: String) = (s2: String) => s1 + s2

      strcat1("foo") ("bar") should be ("foobar")
      strcat2("foo") ("bar") should be ("foobar")
    }
  }
}
