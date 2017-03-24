package de.allmaennitta.examples

import java.time.{LocalDate, Month}

import org.scalatest.{FunSpec, Matchers}

//noinspection SimplifiableFoldOrReduce,SimplifiableFoldOrReduce
class _08c_Stream extends FunSpec with Matchers {


  describe("A stream") {
    it("is basically a lazy list") {
      val myStream = 1 #:: 2 #:: 3 #:: Stream.empty // #:: chains elements layzily
      //so items are fetched only if requested which prevents infinite loop

      myStream.filter((i: Int) => i % 2 != 0).toList should be(List(1, 3))
    }

    it("can deliver new entries, as long as entries are needed; a fibonacci-generator shows the concept") {
      def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)
      def fibFromEndless(a: Int, b: Int): List[Int] = a :: fibFromEndless(b, a + b)

      fibFrom(1, 1).take(10).toList should be(List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55))

      intercept[StackOverflowError] {
        //here the infinite list hits the limits
        fibFromEndless(1, 1).take(10) should be(List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55))
      }
    }

    it("has a range of other operations") {
      case class Dog(name: String, race: String, bdate: LocalDate)

      val dogs = Stream(
        Dog("a", "Setter", LocalDate.of(2007, Month.JANUARY, 1)),
        Dog("b", "Huski", LocalDate.of(2006, Month.JANUARY, 1)),
        Dog("c", "Boxer", LocalDate.of(2005, Month.JANUARY, 1)),
        Dog("d", "Shepherd", LocalDate.of(2004, Month.JANUARY, 1)),
        Dog("e", "Setter", LocalDate.of(2003, Month.JANUARY, 1)),
        Dog("f", "Huski", LocalDate.of(2002, Month.JANUARY, 1)),
        Dog("g", "Boxer", LocalDate.of(2001, Month.JANUARY, 1))
      )

      dogs.filter { dog => dog.bdate.isBefore(LocalDate.of(2004, Month.JANUARY, 1)) }
        .sortBy(dog => dog.bdate)(Ordering.fromLessThan(_ isBefore _)).reverse
        .map((d: Dog) => d.name) should be(
        Stream("e", "f", "g")
      )

      dogs.groupBy(_.race)("Boxer") //die in der Ergebnismap für key "Boxer" gespeicherte Liste von Hunden
        .map((d: Dog) => d.name) should be(//soll als Stream aus deren name-Attribut gespeichert werden
        Stream("c", "g"))

      (for (d <- dogs) yield d.name.capitalize) should be (Stream("A", "B", "C", "D", "E", "F", "G"))
    }
  }
}