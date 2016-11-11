package ibe.examples

import java.io.{File, FileNotFoundException, PrintWriter}

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class _16_IO extends FunSpec with Matchers {

  describe("A file") {

    it("can be written, read and deleted") {
      val writer = new PrintWriter(new File("java.txt"))
      writer.write("Hello Java")
      writer.close()

      var result = ArrayBuffer.empty[String]
      val lines = Source.fromFile("java.txt")("UTF-8").getLines()

      while (lines.hasNext){
        result += lines.next()
      }
      result.mkString("","\n","") should be ("Hello Java")

      new File("java.txt").delete()

      intercept[FileNotFoundException]{
        Source.fromFile("java.txt")("UTF-8") should be ()
      }
    }
  }



}

