package ibe.examples

import java.text.DateFormat._
import java.util.{Date, Locale}

object SalutMonde {
  def execute(args: Array[String]){
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    val txt = "Salut, monde, " +  df.format(now)
    println(txt)
    //return txt
  }
}