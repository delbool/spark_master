package learn.scala
/*
 * SAkhd kashd kashaskjdhaskjdh askjkasdhask dhkasjhdksjadhkasjdh kasjhd
 * adsajlasjd ladjals djlaskdjalskd asldkjsalkdjaslkjdasldjasaslkd jas
 * asdlkjaslkdjas dlaskdj lasdjlasdjalskdjalsjd ljalskjdlkasj
 * asdaslkdjasldjasl lkjasd asljk
 */
object HelloWorld {
  val name = "Abraham";
  val test = null;
  var aaa: String = "AAAAAA";
  //aaa = "BBBB";
  val lines = """
        This is 
        Text that runs 
         Accross multiple
        Lines.""";
  val test2 = aaa;
  def main(args: Array[String]): Unit = {
    println("Hello World")
    println("My name is " + name + " " + test2);
    println("My name is " + lines);
    var a = 10; val b = 20;
    a = 15;
    var sum: Int = add(a, b);
    println("\nThe sume of " + a + " and " + b + " = " + sum);
    
    var inc = (x:Int) => x + 5;
    var myVal = inc(20) - 4;
    println("myVal: " + myVal);
  }

  def add(a: Int, b: Int): Int = {
    return a + b;
  }
}