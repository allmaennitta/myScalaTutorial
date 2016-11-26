package ibe.examples.yaml

import com.fasterxml.jackson.annotation.JsonProperty
import org.assertj.core.util.Preconditions

class Thing(
             @JsonProperty("colour") _colour: String,
             @JsonProperty("priority") _priority: Int) {
  val colour = Preconditions.checkNotNull (_colour, "colour cannot be null")
  val priority = Preconditions.checkNotNull (_priority, "priority cannot be null")
}