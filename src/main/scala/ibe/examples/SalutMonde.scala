package ibe.examples

import java.text.DateFormat._
import java.util.{Date, Locale}

object SalutMonde {
  def execute(): String = {
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    "Salut, monde, " +  df.format(now).toString()
  }
}