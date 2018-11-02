package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

//noinspection SimplifiableFoldOrReduce,SimplifiableFoldOrReduce
class _08b_List extends FunSpec with Matchers {
  /**
    * Under "List" I mention a lot of collection methods that are equally available for other types of
    * collections.
    */

  describe("A list, to mention the basics,") {

    it("is immutable") {
      //noinspection ScalaUnusedSymbol
      val nums = List(1, 2, 3, 4)
      List() should be(List.empty)
      List().isEmpty should be(true)
    }

    it("can be constructed and noted in various ways") {
      Nil should be(List())
      val listNum = 1 :: 2 :: 3 :: Nil
      listNum should be (List(1, 2, 3))

      val listFruit = List("apple", "cherry", "pear")

      listNum.:::(listFruit) should be(List("apple", "cherry", "pear", 1, 2, 3))  //don't know why it is
      // "reversed" this way
      listNum ::: listFruit should be(List(1, 2, 3, "apple", "cherry", "pear")) //which makes it pretty expressive :)

      listNum :: listFruit should be(List(List(1, 2, 3), "apple", "cherry", "pear"))

      3 :: 2 :: 1 :: Nil should be(List(3, 2, 1))

      //List(1,2) + 3 isn't supported anymore because it takes much more time than prepending with :: and reversing
    }
  }

  describe("A list") {
    val listNum = List(1, 2, 3)
    val listFruit = List("apple", "cherry", "pear")

    it("can append elements resulting in a new list") {
      var myList = List(1, 2, 3)
      myList = 44 +: myList :+ 55
      myList should be(List(44, 1, 2, 3, 55))
      //=> consider using ArrayBuffer for appending new elements
    }

    it("can be concatenated") {
      List.concat(listNum, listFruit) should be(List(1, 2, 3, "apple", "cherry", "pear"))
    }

    it("can be flattened") {
      List(List(1, 2), List(3, 4)).flatten should be(List(1, 2, 3, 4))
    }

    it("can be grouped") {
      val x = List(15, 10, 5, 8, 20, 12)

      x.groupBy(_ > 10) should be(Map(false -> List(10, 5, 8), true -> List(15, 20, 12)))

      x.partition(_ > 10) should be(List(15, 20, 12), List(10, 5, 8))

      x.span(_ < 20) should be(List(15, 10, 5, 8), List(20, 12))

      x.splitAt(2) should be(List(15, 10), List(5, 8, 20, 12))

      x.sliding(size = 2).toList should be(List(List(15, 10), List(10, 5), List(5, 8), List(8, 20), List(20, 12))) //is originally returning an iterator
      x.sliding(size = 2, step = 3).toList should be(List(List(15, 10), List(8, 20)))

      val pairs = List(("soup", "cream"), ("fries", "ketchup"))
      val (plaits, toppings) = pairs.unzip
      plaits should be(List("soup", "fries"))
      toppings should be(List("cream", "ketchup"))
    }

    it("can be split or reduced in various ways") {
      listFruit.head should be("apple")
      listFruit.tail should be(List("cherry", "pear"))
      listFruit.init should be(List("apple", "cherry"))
      listFruit.last should be("pear")

      listNum.dropRight(1) should be(List(1, 2))
      listNum.drop(1) should be(List(2, 3))

      List(1, 1, 1, 3, 3, 4).distinct should be(List(1, 3, 4)) //distinct can also be used with objects
      List(1, 1, 1, 3, 3, 4).toSet.toList should be(List(1, 3, 4))
    }

    it("can be walked through doing something pairwise and recursively") {
      val nums = Range(1, 4).toList
      nums.reduceLeft((x, y) => x + y) should be(6) //long version
      nums.reduceLeft(_ + _) should be(6) //short version
      nums.reduceRight(_ + _) should be(6) //starting from the other end, doesn't matter for commutative ops
      nums.reduce(_ + _) should be(6) // method decides where to start
      nums.foldLeft(20)(_ + _) should be(26) //same like reduce but with an inital value
      nums.scanLeft(0)(_ + _) should be(List(0, 1, 3, 6)) //0, 0+1, 1+2, 3+3// Last element is the sum
      nums.scanRight(0)(_ + _) should be(List(6, 5, 3, 0)) //3+3, 3+2, 0+3, 0 // Last element is the sum
    }

    it("can be filled quickly") {
      List.range(1,5) should be (List(1,2,3,4))
      List.fill(3)("apples") should be(List("apples", "apples", "apples")) //fill list 3 times with "apples"
    }

    it("can be reversed") {
      listFruit.reverse should be(List("pear", "cherry", "apple"))
    }

    it("can be printed as text") {
      List(1, 2, 3, 4).mkString("[", ",", "]") should be("[1,2,3,4]")
      listNum.addString(new StringBuilder(), "//").toString() should be("1//2//3")
    }

    it("can be checked ") {
      listNum.contains(2) should be(true)
      listNum.contains(4) should be(false)
    }

    /**
      * {@link de.allmaennitta.examples._12_Traits}
      */
    it("can be sorted"){
      List("Magnolia", "Daisy", "Rose").sorted should be (List("Daisy", "Magnolia", "Rose"))
      List(2,1,3).sorted should be (List(1,2,3))
      List(2,1,3).sortWith(_ < _)  should be (List(1,2,3))

      case class Dog (wonPrizes : Int)
      List(new Dog(7), new Dog(2), new Dog(5)).sortWith(
        (d1, d2) => d1.wonPrizes < d2.wonPrizes) should be (
          List(new Dog(2), new Dog(5), new Dog(7)))


      def sortByLength(s1: String, s2: String) = { s1.length < s2.length }

      List("Magnolia", "Daisy", "Rose").sortWith(sortByLength) should be (List("Rose", "Daisy", "Magnolia"))
    }

    it("can be evaluated") {
      List(2, 6, 3, 1, 4).max should be(6)
      List(2, 6, 3, 1, 4).min should be(1)

      "hello world".count(_ == 'o') should be(2)
    }
    it("can be merged with other list") {
      val freeIndices = List(1, 224, 334, 335, 340)
      val entriesToSave = List("a", "b", "c", "d", "e")
      freeIndices zip entriesToSave should be(List((1,"a"), (224,"b"), (334,"c"), (335,"d"), (340,"e")))
      //can be evaluated e.g. by for((index,value) <- entries){ ... }
    }

    it("can be multidimensional") {
      val matrixLists: List[List[Int]] =
        List(
          List(1, 0, 0),
          List(0, 1, 0),
          List(0, 0, 1)
        )

      val matrixArrays: Array[Array[Int]] =
        Array(
          Array(1, 0, 0),
          Array(0, 1, 0),
          Array(0, 0, 1)
        )

      matrixLists should be(matrixArrays)
    }
  }


  describe("A List of Objects") {

    case class Balloon(var color: String = "blue", var filled: Boolean = false) {}

    val balloons = (new ArrayBuffer[Balloon]
      :+ Balloon("g") //Variables can be assigned by name, which makes it for Boolean more readable
      :+ Balloon("y", filled = true)
      :+ Balloon("r", filled = true)
      :+ Balloon("g", filled = true)
      :+ Balloon("r")
      :+ Balloon("r", filled = true)).toList

    //printf("Original balloons:%n%s%n", balloons)

    it("can be checked for criteria") {

      balloons.exists(
        (b: Balloon) => b.color == "y" && b.filled
      ) should be(true)

      balloons.exists(
        (b: Balloon) => b.color == "y" && !b.filled
      ) should be(false)

      balloons.contains(Balloon("y", filled = true)) should be(true)
      balloons.contains(Balloon("y")) should be(false)

      balloons.filterNot(               //red balloons are removed
        (b: Balloon) => b.color == "r"
      ).exists(                         // and thus shouldn't exist anymore
        (b: Balloon) => b.color == "r"
      ) should be(false)

      balloons.filterNot(               //red balloons are removed
        (b: Balloon) => b.color == "r"
      ).exists(                         // and thus yellow ones still should exist
        (b: Balloon) => b.color == "y"
      ) should be(true)


      def isRed(b: Balloon): Boolean = b.color == "r"
      val justRed = balloons.filter(isRed)
      printf("Red balloons:%n%s%n", justRed)

      justRed.forall(isRed) should be(true)

      var countFilled: Int = 0
      for (l <- justRed.permutations) {
        for (balloon <- l) {
          println(balloon)
          if (balloon.filled) countFilled += 1
        }
        println("------")
      }
      countFilled should be(6) //with 3 permutation groups and 2 filled per group
    }

    it("can be processed even in a way that is much more adequate for iterators") {
      val justRed = (new ArrayBuffer[Balloon]
        :+ Balloon("r", filled = true)
        :+ Balloon("r")
        :+ Balloon("r", filled = true)).toList

      justRed.dropWhile((b: Balloon) => b.filled) should be(List(
        Balloon("r"),
        Balloon("r", filled = true)
      ))

      justRed.takeWhile((b: Balloon) => b.filled) should be(List(
        Balloon("r", filled = true)
      ))
    }
  }

/**
  * Example by https://www.garysieling.com/blog/scala-collect-example
  */
  describe("Special functions like") {
    it("'tabulate' and 'collec' help to transform lists in an easy way") {
      List.tabulate(6)(n => n * n) should be(List(0, 1, 4, 9, 16, 25))
      List.tabulate(5)(_ + 2) should be(List(2, 3, 4, 5, 6))
      List.tabulate(2, 3)(_ + 3 * _) should be(List(List(0, 3, 6), List(1, 4, 7))) //first _+ seems to be only there for
      //validity-reasons 3*_ is calculated

      /** converts a limited amount of data types into Int. Ignores other data types **/
      val convertFn: PartialFunction[Any, Int] = {
        case i: Int => i;
        case s: String => s.toInt;
        case Some(s: String) => s.toInt
      }

      List(0, 1, "2", "3", Some(4), Some("5")).collect(convertFn) should be(List(0, 1, 2, 3, 5))
    }

    it("'aggregate' can help to calculate parallewith trees") {
      val listOfBinaries = List("001", "010", "11", "100")
      listOfBinaries.aggregate(0)( //constant to which later values are added
        {
          (sum, str) => sum + Integer.parseInt(str, 2)
        }, //turn binary into Int and add it to sum
        {
          (p1, p2) => p1 + p2
        } //add sums
      ) should be(10)
    }
    it("'union', 'intersection' and 'difference' help to do it the 'Venn' way") {
      List(1, 3).union(List(2, 3)) should be(List(1, 3, 2, 3))
      List(1, 2, 2, 3).intersect(List(2, 2)) should be(List(2, 2))
      List(1, 2, 2, 3).diff(List(2, 2)) should be(List(1, 3))
    }
  }
}