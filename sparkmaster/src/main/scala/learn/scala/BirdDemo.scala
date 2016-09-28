package learn.scala

abstract class Bird {
  def flyMessage: String
  def fly() = println(flyMessage)
  def swim() = println("I'm swimming")
}

class Pigeon extends Bird {
  val flyMessage = "I am pigeon, I'm a good flyer"
}

class Hawk extends Bird {
  val flyMessage = "I am hawk, I'm an excellent flyer"
}

object BirdDemo {
  def main(args: Array[String]): Unit = {
    val birds = List(new Pigeon, new Hawk)

    //for (bird <- birds) bird.fly();   
    //birds.foreach(wefitu => wefitu.fly())
    for (a <- birds){
        println(a.flyMessage)
        a.fly()
    }
  }
}