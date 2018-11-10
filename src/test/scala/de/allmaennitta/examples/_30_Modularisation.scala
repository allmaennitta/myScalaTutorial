package de.allmaennitta.examples

import java.io.StringReader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import de.allmaennitta.examples.yaml.Sample
import org.scalatest.{FunSpec, Matchers}

abstract class Food(val name: String) {
  override def toString = name
}

class Recipe(
              val name: String,
              val ingredients: List[Food],
              val instructions: String
            ) {
  override def toString = name
}

object Apple extends Food("Apple")

object Orange extends Food("Orange")

object Cream extends Food("Cream")

object Sugar extends Food("Sugar")

object FruitSalad extends Recipe(
  "fruit salad",
  List(Apple, Orange, Cream, Sugar),
  "Stir it all together."
)

abstract class Database {
  def allFoods: List[Food]
  def foodNamed(name: String): Option[Food] =
    allFoods.find(f => f.name == name)
  def allRecipes: List[Recipe]
  case class FoodCategory(name: String, foods: List[Food])

  def allCategories : List[FoodCategory]
}

object SimpleDatabase extends Database {
  def allFoods = List(Apple, Orange, Cream, Sugar)
  def allRecipes: List[Recipe] = List(FruitSalad)

  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar))
  )

  def allCategories = categories
}

abstract class Browser {
  val database: Database
  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe => //hard link! XXX
      recipe.ingredients.contains(food))

  def displayCategory(category: database.FoodCategory)={
    println(category)
  }
}


object SimpleBrowser extends Browser{
  val database = SimpleDatabase
}

class _30_Modularisation extends FunSpec with Matchers {
  describe("") {
    it("") {

    }
  }

}

