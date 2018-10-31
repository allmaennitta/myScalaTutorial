package de.allmaennitta.examples

import java.io._
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{FileSystems, Files, Path, Paths}

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class _16_IO extends FunSpec with Matchers {

  describe("A text file") {

    it("can be written, read and deleted") {
      val file = new File("test.txt")

      val bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))
      bw.write("Wir leben an der Côte d'Azur.\n")
      bw.write("Wir leben in der Nähe von Mata las Cañas.\n")
      bw.close()

      var result = ArrayBuffer.empty[String]
      val source = Source.fromFile(file)
      val lines = source.getLines()

      while (lines.hasNext) {
        result += lines.next()
      }
      source.close()
      result.mkString(" ") should be("Wir leben an der Côte d'Azur. Wir leben in der Nähe von Mata las Cañas.")

      Files.delete(file.toPath)

      intercept[IOException] {
        source should be("this test should never be evaluated")
      }
    }
  }
  describe("A binary file") {
    it("can be written, read and deleted") {
      val uri = this.getClass.getClassLoader.getResource("BinaryFile.bin").toURI
      val file = new File(uri)
      val file_out = new File(file.getAbsolutePath + ".copy")
      val byteArray = Files.readAllBytes(Paths.get(uri))

      byteArray.toList(0) should be (-54)
      byteArray.toList.length should (be > 100)

      var in = None: Option[BufferedInputStream]
//      var out = None: Option[BufferedOutputStream]
      try {
        in = Some(new BufferedInputStream(Files.newInputStream(Paths.get(uri))))
        Stream.continually(in.get.read())
          .map((i: Int) => {
            println(i);
            i
          }
//        out = Some(new BufferedOutputStream(new FileOutputStream(file_out)))

        //val byteArray = Files.readAllBytes(Paths.get("/path/to/file"))

//        Stream.continually(in.get.read)
//          .map((i: Int) => {
//            println(i);
//            i
//          })
//          .takeWhile(-1 !=)
)
//          .map(out.get.write)
      } catch {
        case e: IOException => e.printStackTrace
      } finally {
        println("entered finally ...")
//        if (in.isDefined) in.get.close
//        if (out.isDefined) out.get.close
      }
    }
  }


}

