package de.allmaennitta.examples

import de.allmaennitta.examples.StudentDatabase.FrozenFood
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

case class FoodCategory(name: String, foods: List[Food]);

trait FoodCategories { //push modularisation further by separating this topic
  def allCategories: List[FoodCategory]
}

abstract class Database extends FoodCategories { //common attributes, etc. go here
  def allFoods: List[Food]

  def foodNamed(name: String): Option[Food] =
    allFoods.find(f => f.name == name)

  def allRecipes: List[Recipe]
}

object SimpleDatabase extends Database with SimpleFoods with SimpleRecipes {
  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar))
  )

  def allCategories = categories
}

trait SimpleFoods {
  object Pear extends Food("Pear")

  def allFoods = List(Apple, Orange, Cream, Sugar)
}

trait SimpleRecipes {
  this: SimpleFoods => //necessary to make pear from SimpleFoods visible

  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Orange, Cream, Sugar, Pear),
    "Stir it all together."
  )

  def allRecipes = List(FruitSalad)
}

object StudentDatabase extends Database {

  object FrozenFood extends Food("FrozenFood")

  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for 10 minutes."
  )

  override def allFoods: List[Food] = List(FrozenFood)

  override def allRecipes: List[Recipe] = List(HeatItUp)

  override def allCategories: List[FoodCategory] = List(FoodCategory("edible",
    List(FrozenFood)))
}

abstract class Browser (val database: Database) {

  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe => //hard link! XXX
      recipe.ingredients.contains(food))

  def displayCategory(category: FoodCategory) = {
    println(category)
  }
}

class _30_Modularisation extends FunSpec with Matchers {
  describe("Both, a wired modularised database and browser") {
    val db: Database = SimpleDatabase
    object browser extends Browser {
      val database: db.type = db //db.type => singleton-type, tell the compiler, that both types
      // are the same object
      //not necessary in this case, but it might be in other situations
    }

    it("works") {
      val apple = db.foodNamed("Apple").get
      for (recipe <- browser.recipesUsing(apple))
        println(recipe)
    }
    it("makes it possible to have same kind of object differently ") {
      StudentDatabase.allCategories should be(List(FoodCategory("edible", List(FrozenFood))))
    }
  }
}

