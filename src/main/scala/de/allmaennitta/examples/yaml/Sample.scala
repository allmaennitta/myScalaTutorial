package de.allmaennitta.examples.yaml

import org.assertj.core.util.Preconditions
import java.util.{List => JList, Map => JMap}
import collection.JavaConversions._
import com.fasterxml.jackson.annotation.JsonProperty

class Sample(
              @JsonProperty("name") _name: String,
              @JsonProperty("parameters") _parameters: JMap[String, String],
              @JsonProperty("things") _things: JList[Thing]) {
  val name = Preconditions.checkNotNull(_name, "name cannot be null")
  val parameters: Map[String, String] = Preconditions.checkNotNull(_parameters, "parameters cannot be null").toMap
  val things: List[Thing] = Preconditions.checkNotNull(_things, "things cannot be null").toList
}