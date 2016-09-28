package learn.scala

object FuncWithNamedArgs {
  def main(args: Array[String]) {
    printInt(b = 5, a = 7);
    printInt(5, 7);
  }

  def printInt(a: Int, b: Int) = {
    print("Value of a : " + a);
    println("\tValue of b : " + b);
  }
}