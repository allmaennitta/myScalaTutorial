package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

class _35_XML extends FunSpec with Matchers {

  describe("XML-field content") {
    val liste = <einkaufsliste>
      <artikel><name>Brot</name><preis>3.50</preis></artikel>
      <artikel><name>Apfel</name><preis>0.29</preis></artikel>
      <artikel><name>Eier</name><preis>1.19</preis></artikel>
    </einkaufsliste>

    it("can be selected and added") {
      (liste \\ "preis").map(_.text.toDouble).sum should be (4.98)
    }

    val foo = <foo>
      <bar type="greet">hi</bar>
      <bar type="count">1</bar>
      <bar type="color">yellow</bar>
    </foo>

    it("can be selected and printed out") {
      (foo \\ "bar").map(_ .text).mkString(" ") should be ("hi 1 yellow")
    }


  }



}

