package learn.scala

class Person(val name: String, val female: Boolean) {

  def printNames(people: List[Person]) {
    println("Priniting Names");
    for (person <- people if !person.female; if person.name.contains("Ewing")) {
      println(person.name);
    }
    
    val names = for(person <- people) yield person.name
    for(name <- names) println("Name: " + name)
    
    println("\nNested Yeild ...")
    for ( name <- for(person <- people) yield person.name) 
      println("Name: " + name)
  }

  def printNestedNames(groups: List[List[Person]]) {
    println("\nPriniting Names from Nested Loops");
    for(people <- groups ; person <- people ) {
      println(person.name);
    }
  }
}

object Person {
  def main(args: Array[String]): Unit = {
    val people: List[Person] = List(
      new Person("Cliff Barnes", false),
      new Person("J. R. Ewing", false),
      new Person("Sue Ellen Ewing", true),
      new Person("Ellie Ewing", true),
      new Person("Bobby Ewing", false),
      new Person("Donna Culver Krebbs", true));

    val p = new Person("", true);
    p.printNames(people);

    val ewings = List(
      new Person("J. R. Ewing", false),
      new Person("Sue Ellen Ewing", true),
      new Person("Ellie Ewing", true),
      new Person("Bobby Ewing", false))

    val outsiders = List(
      new Person("Cliff Barnes", false),
      new Person("Donna Culver Krebbs", true))

    val groups = List(ewings, outsiders);
    p.printNestedNames(groups);
  }
}