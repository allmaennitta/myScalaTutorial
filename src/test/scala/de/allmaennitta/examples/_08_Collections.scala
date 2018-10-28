package de.allmaennitta.examples

import org.scalatest.{FunSpec, Matchers}

import scala.Array._

class _08_Collections extends FunSpec with Matchers {
  /**
    * Thanks to Scala's ability to "stack" traits, a vast and easily amplifiable amount of collections exists.
    *
    * Sequences(Seq) are either indexed or linear.
    * The indexed ones are optimized for random access per index (e.g. "I want the 5000th element")
    *     * indexed sequences (Array, Range, String, Vector, Stringbuilder, ArrayBuffer)
    * The linear ones are optimized to be split into head, tail and isEmpty (you hear the recursion bell ringing?)
    *     * linear sequences (List, Queue, Stack, Stream, ListBuffer, LinkedList, MutableList)
    *
    * Map = key-value-pairs with unique key
    *     * Map (HashMap, SortedMap, ListMap, WeakHashMap, TreeMap, LinkedHashMap)
    *
    * Set = collection of unique elements
    *     Set (BitSet, HashSet, ListSet, SortedSet, TreeSet)
    *
    * Many collections exist in a mutable and a immutable version
    *     * collection.mutable.Set
    *     * collection.immutable.Set
    *
    * There are Pseudo Collections implementing methods of other collections without strictly speaking being a collection
    *     * pseudo-collections (Enumeration, Iterator, Option, Some and None, Tuple)
    *
    *
    * For a structuring of the many offered methods please
    * @see http://alvinalexander.com/scala/how-to-choose-scala-collection-method-solve-problem-cookbook
    */


  describe("the type of a collection"){
    it("is determined by inference "){
      List(1, 2.0, 33D, 400L).head.getClass.getCanonicalName should be ("double")
    }
    it("can be determined manually"){
      List[Number](1, 2.0, 33D, 400L).head.getClass.getCanonicalName should be ("java.lang.Integer")
      List[Number](1, 2.0, 33D, 400L).last.getClass.getCanonicalName should be ("java.lang.Long")
      List[AnyVal](1, 2.0, 33D, 400L).head.getClass.getCanonicalName should be ("java.lang.Integer")
      List[AnyVal](1, 2.0, 33D, 400L).last.getClass.getCanonicalName should be ("java.lang.Long")
    }
  }
  describe("An immutable collection") {
    it("is not that immutable apparently") {
      //which is explainable by the fact that :+ is replacing the whole collection each time
      var sisters = collection.immutable.Vector("Banane")
      sisters = sisters :+ "Apfel"
      sisters.mkString(",") should be ("Banane,Apfel")
    }
    it("but it still is immutable"){
      var sisters = collection.immutable.Vector("Banane")
      //sisters(0) = "Ananas" //does not compile, because you try to mutate an immutable object")
    }
  }
}