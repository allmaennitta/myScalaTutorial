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

trait FoodCategories{ //push modularisation further by separating this topic
  case class FoodCategory(name: String, foods: List[Food])
  def allCategories : List[FoodCategory]
}

abstract class Database extends FoodCategories { //common attributes, etc. go here
  def allFoods: List[Food]
  def foodNamed(name: String): Option[Food] =
    allFoods.find(f => f.name == name)
  def allRecipes: List[Recipe]
}

object SimpleDatabase extends Database with SimpleFoods with SimpleRecipes{
  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar))
  )

  def allCategories = categories
}

trait SimpleFoods{
  object Pear extends Food("Pear")
  def allFoods = List(Apple, Orange, Cream, Sugar)
}

trait SimpleRecipes{
  this: SimpleFoods => //necessary to make pear from SimpleFoods visible

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Orange, Cream, Sugar, Pear),
    "Stir it all together."
  )
  def allRecipes= List(FruitSalad)
}

object StudentDatabase extends Database{
  object FrozenFood extends Food("FrozenFood")
  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minutes."
  )

  override def allFoods: List[Food] = List(FrozenFood)
  override def allRecipes: List[Recipe] = List(HeatItUp)
  override def allCategories: List[StudentDatabase.FoodCategory] = List(FoodCategory("edible",
    List(FrozenFood)))
}

abstract class Browser { //common attributes, etc. go here
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

object StudentBrowser extends Browser{
  val database = StudentDatabase
}

class _30_Modularisation extends FunSpec with Matchers {
  describe("") {
    it("") {

    }
  }

}

