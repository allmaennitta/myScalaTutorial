package ibe.examples

import org.scalatest._
import ibe.examples.SalutMonde

class SalutMondeSpec extends FunSpec with Matchers {

  describe("An example Salut Monde app"){
    it ("should deliver a text with >0 chars") {
      assert(SalutMonde.execute().length > 0);
    }
  }

}