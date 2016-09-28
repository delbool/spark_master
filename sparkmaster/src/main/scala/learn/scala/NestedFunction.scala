package learn.scala

object NestedFunction {
  def main(args: Array[String]) {
    var zero: Int = 0;
    val one = 1;
    val two = 2;
    val three = 3;

//    println(zero + " factorial = " + factorial(zero))
//    println(one + " factorial = " + factorial(one))
//    println(two + " factorial = " + factorial(two))
    println(three + " factorial = " + factorial(three));
    
  }  
  def factorial(i: Int): Int = {
    def fact(k: Int, accumulator: Int): Int = {
      if (k <= 1)
        return accumulator
      else
        fact(k - 1, k * accumulator)
    }
    fact(i, 1)
  }
}