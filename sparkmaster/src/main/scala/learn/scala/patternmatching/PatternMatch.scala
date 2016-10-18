package learn.scala.patternmatching

object PatternMatch {
  def main(args: Array[String]): Unit = {
    println(matchTest("ABCD"));
  }
  
  def matchTest(x : Any): String = x match {
    case 1 => {"One"}
    case 2 => "Two"
    case y: Int => "scala.Int"
    case "ABC" => "Letter 'A' Selected"
    case _ => "many" // catch any
  }
}