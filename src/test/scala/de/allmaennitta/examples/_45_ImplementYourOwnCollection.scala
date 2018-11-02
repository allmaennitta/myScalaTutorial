package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}
// https://docs.scala-lang.org/overviews/core/architecture-of-scala-collections.html
class _45_ImplementYourOwnCollection extends FunSpec with Matchers {

  abstract class Base
  case object A extends Base
  case object U extends Base
  case object G extends Base
  case object C extends Base

  object Base {
    val fromInt: Int => Base = Array(A, U, G, C)
    val toInt: Map[Base with Product with Serializable, Int] = Map(A -> 0, U -> 1, G -> 2, C -> 3)
  }

  describe("A regular expression") {
    it("is able to find 'scala'") {

    }
  }
}
