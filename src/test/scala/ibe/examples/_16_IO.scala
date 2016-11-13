package ibe.examples


import java.io._
import java.nio.file.{FileSystems, Files, Path}

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class _16_IO extends FunSpec with Matchers {

  describe("A file") {

    it("can be written, read and deleted") {
      val file = new File("test.txt")

      val writer = new FileWriter(file)
      writer.write("Hello Java")
      writer.close()

      var result = ArrayBuffer.empty[String]

      val source = Source.fromFile(file)
      val lines = source.getLines()

      while (lines.hasNext){
        result += lines.next()
      }
      source.close()
      result.mkString("","\n","") should be ("Hello Java")

      Files.delete(file.toPath)

      intercept[IOException]{
        source should be ("this test should never be evaluated")
      }
    }
  }



}

