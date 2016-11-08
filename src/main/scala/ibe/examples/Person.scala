package ibe.examples

object Person {
  def apply(name : String, age : Int): Person ={
    new Person(name, age)
  }

  def unapply(arg: Person): Option[(String, Int)] = Option(arg.name, arg.age)
}

class Person (val name : String, val age : Int){

  def canEqual(other: Any): Boolean = other.isInstanceOf[Person]

  override def equals(other: Any): Boolean = other match {
    case that: Person =>
      (that canEqual this) &&
        name == that.name &&
        age == that.age
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, age)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}