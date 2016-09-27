package ibe.examples

import org.scalatest.{FunSpec, Matchers}

/**
  * Created by ingo on 27.09.2016.
  */
class TreeOperationsTest extends FunSpec with Matchers {

  describe("TreeOperations$Test") {

    it("should eval") {
      type Environment = String => Int
      val exp: Tree = Sum(Sum(Var("x"), Var("x")),Sum(Const(7), Var("y"))) //(x+x)+(7+y)
      val env: Environment = {
        case "x" => 1
        case "y" => 2
      }
      println("Expression: " + exp)
      TreeOperations.eval(exp, env) should be (11)
    }

  }
}
