package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

class _35_XML extends FunSpec with Matchers {

  describe("XML-field content") {
    it("can be selected and added") {
      val liste = <einkaufsliste>
        <artikel><name>Brot</name><preis>3.50</preis></artikel>
        <artikel><name>Apfel</name><preis>0.29</preis></artikel>
        <artikel><name>Eier</name><preis>1.19</preis></artikel>
      </einkaufsliste>

      (liste \\ "preis").map(_.text.toDouble).sum should be (4.98)
    }

    it("can be selected by attribute") {
      val liste = <einkaufsliste>
        <artikel preis="3.50"><name>Brot</name> </artikel>
        <artikel preis="0.29"><name>Apfel</name></artikel>
        <artikel preis="1.19"><name>Eier</name> </artikel>
      </einkaufsliste>

      (liste \\ "artikel" \\ "@preis").map(_.toString().toDouble).sum should be (4.98)
      //TODO: why double backslash before 'preis'?
    }

    it("can be selected and printed out") {
      val foo = <foo>
        <bar type="greet">hi</bar>
        <bar type="count">1</bar>
        <bar type="color">yellow</bar>
      </foo>

      (foo \\ "bar").map(_.text).mkString(" ") should be ("hi 1 yellow")
    }


  }



}

