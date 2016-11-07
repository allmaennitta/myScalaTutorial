package ibe.examples

import org.scalatest.{FunSpec, FunSuite, Matchers}

/**
  * Created by ingo on 26.09.2016.
  */
class _03_IfTest extends FunSpec with Matchers  {

  describe("An If statement"){
    it("is nothing at all exceptional"){
      def returnATuple(x : Int) = {
        if (x>2) { (1,"A",3)}
        else if (x==2) (1,true)
        else if (x<2) 1
        else "Hier läuft etwas gigantisch falsch"
      }

      returnATuple(0) should be (1)
      returnATuple(2) should be (1,true)
      returnATuple(3) should be (1,"A",3)
    }
  }


}
