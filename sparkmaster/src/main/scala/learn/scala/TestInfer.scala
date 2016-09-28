package learn.scala

object TestInfer {
    def main(args: Array[String]) {
    var myVar: Int = 10;
    val myVal: String = "Hello Scala with datatype declaration.";
    var myVar1 = 20;
    val myVal1 = "Hello Scala new without datatype declaration.";

    println(myVar); println(myVal); println(myVar1);
    println(myVal1);
    println("This objec is created from class: " + this.getClass.getName());
    println("Name of this objec is : " + this);
  }
  
}