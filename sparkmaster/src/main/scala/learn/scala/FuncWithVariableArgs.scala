package learn.scala

object FuncWithVariableArgs {
  def main(args: Array[String]): Unit = {
    printStrings("Hello", "Sacala", "Python");
  }
  
  def printStrings(args:String *) : Unit = {
    var i : Int = 0;
    for (arg <- args){
      println("Arg value[" + i + "] = " + arg);
      i = i + 1;
    }
  }
}