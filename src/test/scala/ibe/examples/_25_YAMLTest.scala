package ibe.examples

import java.io.StringReader
import java.util.{List => JList, Map => JMap}

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.assertj.core.util.Preconditions
import org.scalatest.{FunSpec, Matchers}

import scala.collection.JavaConversions._

class _25_YAMLTest extends FunSpec with Matchers {

  /** From Greg Kopff, http://stackoverflow.com/questions/19441400/working-with-yaml-for-scala **/
  describe("YAML") {

    class Sample(
      @JsonProperty("name") _name: String,
      @JsonProperty("parameters") _parameters: JMap[String, String],
      @JsonProperty("things") _things: JList[Thing]) {
      val name = Preconditions.checkNotNull(_name, "name cannot be null")
      val parameters: Map[String, String] = Preconditions.checkNotNull(_parameters, "parameters cannot be null").toMap
      val things: List[Thing] = Preconditions.checkNotNull(_things, "things cannot be null").toList
    }

    class Thing(
      @JsonProperty("colour") _colour: String,
      @JsonProperty("priority") _priority: Int) {
        val colour = Preconditions.checkNotNull (_colour, "colour cannot be null")
        val priority = Preconditions.checkNotNull (_priority, "priority cannot be null")
      }

        it ( "can be transformed to classes"
    )
    {
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

