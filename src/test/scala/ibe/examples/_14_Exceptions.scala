package ibe.examples

import java.io.IOException

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

class _14_Exceptions extends FunSpec with Matchers {

  describe("An exception") {
    it("is never 'checked' in Scala") {
      intercept[IllegalArgumentException] {
        throw new IllegalArgumentException("testing Exceptions")
      }
      intercept[ArithmeticException] {
        5 / 0
      }

      var log = ArrayBuffer[String]()
      try {
        throw new IOException("von iharddisk air kann nicht gelesen werden")
        log += "should never happen"
      } catch {
        case ex: IOException if ex.getMessage contains ("iharddisk") => {
          log += "stop buying hipster stuff"
        }
        case ex: IOException => {
          log += "some io-stuff happened"
        }
        case ex: Throwable => log += "an unspecified exception occured"
      } finally {
        log += ", oh myyy"
      }

      log.toList should be(List("stop buying hipster stuff", ", oh myyy"))
    }

  }
}

