package learn.scala

object FunctionCallByName {
  def main(args: Array[String]) {
    delayed(time());
  }

  def time() : Long = {
    println("Getting time in nano seconds")
    return System.nanoTime
  }
  def delayed(t: => Long) = {
//  def delayed(t:Long) = {
    println("In delayed method")
    println("Param: " + t)
    println("STILL In delayed method")
  }
}