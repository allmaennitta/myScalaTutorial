package ibe.examples

/**
  * Created by ingo on 23.09.2016.
  */
class Complex (real: Double, imaginary: Double) {
  def re = real       //directly taken from params
  def im = imaginary  //directly taken from params
  override def toString() =
    "" + re + (if (im < 0) "" else "+") + im + "i"
}
