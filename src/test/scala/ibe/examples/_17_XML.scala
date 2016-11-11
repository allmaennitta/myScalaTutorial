package ibe.examples

import java.io.{File, FileNotFoundException, PrintWriter}

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class _17_XML extends FunSpec with Matchers {

  describe("XML") {
    val liste = <einkaufsliste>
      <artikel><name>Brot</name><preis>3.50</preis></artikel>
      <artikel><name>Apfel</name><preis>0.29</preis></artikel>
      <artikel><name>Eier</name><preis>1.19</preis></artikel>
    </einkaufsliste>

    it("can be parsed") {
      (liste \\ "preis").map(_.text.toDouble).sum should be (4.98)
    }
  }



}

