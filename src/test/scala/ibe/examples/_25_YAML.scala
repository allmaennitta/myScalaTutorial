package ibe.examples

import java.io.StringReader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import ibe.examples.yaml.Sample
import org.scalatest.{FunSpec, Matchers}

class _25_YAML extends FunSpec with Matchers {

  /** From Greg Kopff, http://stackoverflow.com/questions/19441400/working-with-yaml-for-scala **/
  describe("YAML") {
    it ( "can be transformed to classes") {
      val yaml =
        """
          |name: test
          |parameters:
          |  "VERSION": 0.0.1-SNAPSHOT
          |
          |things:
          | - colour: green
          |   priority: 128
          | - colour: red
          |   priority: 64
        """.stripMargin

      val mapper = new ObjectMapper(new YAMLFactory())
      val sample: Sample = mapper.readValue(new StringReader(yaml), classOf[Sample])

      sample.name should be ("test")
      sample.things.length should be (2)
      sample.things(0).colour should be ("green")
      sample.things(1).priority should be (64)
    }
  }


}

