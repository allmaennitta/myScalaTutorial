package ibe.examples

import org.scalatest.{FunSpec, Ignore, Matchers}

import scala.language.postfixOps

class _A_Comparison_Iterative_Recursive_With_QuickSort extends FunSpec with Matchers {

  describe("A imperative QuickSort algorithm") {
    def quicksortImperative(xs: Array[Int]) {

      def swap(i: Int, j: Int) {
        val t = xs(i)
        xs(i) = xs(j)
        xs(j) = t
      }

      def sort(l: Int, r: Int) {
        val pivot = xs((l + r) / 2) //choose as pivot a value in the middle
        var i = l
        var j = r //start at both ends
        while (i <= j) {
          //j may never overtake i walking to the left
          while (xs(i) < pivot) i += 1 //i walks to the right as long as < pivot
          while (xs(j) > pivot) j -= 1 //j walks to the left as long as > pivot
          if (i <= j) {
            //if i and j have come to a standstill and i hasn't overtaken j yet
            swap(i, j)
            println("after swap:\n" + xs.mkString + " Pivot: " + pivot)
            i += 1
            j -= 1
          }
        }
        if (l < j) sort(l, j)
        if (j < r) sort(i, r)
      }

      println(xs.mkString)
      sort(0, xs.length - 1)
    }

    it("should quicksort") {
      println("------------------------------------")
      val array = Array(1, 5, 3, 7, 2, 6, 4)
      quicksortImperative(array)
      println(s"Result: ${array.mkString}")
      array should equal(Array(1, 2, 3, 4, 5, 6, 7))
    }
  }

  describe("A functional QuickSort algorithm") {
    def quicksortFunctional(xs: Array[Int]): Array[Int] = {
      println(s"Initial: ${xs.mkString}")
      if (xs.length <= 1) {
        //Trivialer Fall
        println(s"length1: ${xs.mkString}")
        xs //Bei einem einzelnen Element wird das Element zurückgegeben
      } else {
        val pivot = xs(xs.length / 2) //Wahl eines mittigen Pivot-Elements aus der Liste
        println(s"pivot: $pivot (${xs.mkString})")

        val arrGT = quicksortFunctional(xs filter (pivot >)) //Wiederholung für alle Werte, die größer sind
        println(s"arrGT: ${arrGT.mkString}")
        val valEqu = xs filter (pivot ==) //Liste der Werte, die gleich dem Pivot sind
        println(s"EQ: ${valEqu.mkString}")
        val arrLT = quicksortFunctional(xs filter (pivot <)) //Wiederholung für alle Werte, die kleiner sind
        println(s"arrLT: ${arrLT.mkString}")
        Array.concat(arrGT, valEqu, arrLT) //Verknüpfung der kleineren, gleichgroßen, größeren Wertlisten
      }
    }

    it("should quicksort") {
      println("------------------------------------")
      val array = Array(1, 5, 3, 7, 2, 6, 4)
      val result = quicksortFunctional(array)
      println(s"Result: ${result.mkString}")
      result should equal(Array(1, 2, 3, 4, 5, 6, 7))
    }
  }
}
