package de.allmaennitta.examples.packagetests

package object packageobject {

  // field
  val CONSTANT = 42

  // method
  def echo(a: Any) : String = {
    a.toString
  }

  // enumeration
  object Margin extends Enumeration {
    type Margin = Value
    val TOP, BOTTOM, LEFT, RIGHT = Value
  }

}
