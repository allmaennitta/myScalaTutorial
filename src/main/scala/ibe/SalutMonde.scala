package ibe

import java.text.DateFormat._
import java.util.{Date, Locale}

object
SalutMonde {
  def
  main(args: Array[String]) {
    execute(args)
  }

  def
  execute(args: Array[String]){
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    var txt = "Salut, monde, " +  df.format(now).toString()
    println(txt)
    //return txt
  }
}