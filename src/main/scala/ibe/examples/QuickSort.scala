package ibe.examples

object QuickSort {
  def quicksortImperative(xs: Array[Int]){

    def swap(i: Int, j: Int){
      val t = xs(i); xs(i)=xs(j);xs(j)=t
    }

    def sort(l: Int, r: Int){
      val pivot = xs((l+r)/2) //choose as pivot a value in the middle
      var i = l; var j = r //start at both ends
      while (i <= j) { //j may never overtake i walking to the left
        while (xs(i) < pivot) i += 1 //i walks to the right as long as < pivot
        while (xs(j) > pivot) j -= 1 //j walks to the left as long as > pivot
        if (i <= j) { //if i and j have come to a standstill and i hasn't overtaken j yet
          swap(i,j)
          println("after swap:\n"+xs.mkString +" Pivot: " + pivot)
          i +=1
          j-= 1
        }
      }
      if (l < j) sort(l,j)
      if (j<r) sort(i,r)
    }

    println(xs.mkString)
    sort(0,xs.length - 1)
  }

  def quicksortFunctional(xs: Array[Int]): Array[Int] = {
    println(s"Initial: ${xs.mkString}")
    if (xs.length <= 1) {
      println(s"length1: ${xs.mkString}")
      xs
    } else {
      val pivot = xs(xs.length / 2)
      println(s"pivot: ${pivot} (${xs.mkString})")

      val arrGT = quicksortFunctional(xs filter (pivot >))
      println(s"arrGT: ${arrGT.mkString}")
      val valEqu = xs filter (pivot ==)
      println(s"EQ: ${valEqu.mkString}")
      val arrLT = quicksortFunctional(xs filter (pivot <))
      println(s"arrLT: ${arrLT.mkString}")
      Array.concat(arrGT, valEqu, arrLT)
    }
  }
}
