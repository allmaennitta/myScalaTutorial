package ibe.examples.xmlparser

import org.scalatest.{FunSpec, Matchers}

class XMLTest extends FunSpec with Matchers {
  describe("XML Object") {
    it("can parse xml") {
      val text = "<div><div>this text contains <span> a subtext </span> and a main text</div> and is part of a main text.</div>"
      XML.parseXML(text) should be ("div", "<div>this text contains <span> a subtext </span> and a main text</div> and is part of a main text.</div>")
      //LEARN MATCHING
    }
  }
}