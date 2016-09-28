package learn.scala

object FuncDefaultValue {
  def main(args: Array[String]): Unit = {
    println("Returned Value : " + addInt());
    println("Returned Value : " + addInt(100));
    println("Returned Value : " + addInt(100, 300));
  }
  
  def addInt(a:Int = 5, b:Int = 7) : Int = {
    return a + b
  }
}