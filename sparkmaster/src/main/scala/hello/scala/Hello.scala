package hello.scala

object Hello {
  def main(args: Array[String]): Unit = {
    println("HI");

    val list = List(1, 2, 3, 4, 5, 6);
    println("list.length ==> " + list.length)
    println("list.size ==> " + list.size)

    val newList = List;
    val seq = list.map(e => if (e > 2) {
      Some(e)
    } else {
      None
    });
    println(seq)

    val list2 = list.map(e => List(e - 1, e, e + 1))
    println(list2);

    val list3 = list2.flatMap(e => e)
    println(list3);

    // add square bracket at start and end
    var i = 0;
    val list4 = list3.map(e => {
      i += 1;
      if (i == 1) {
        "[" + e
      } else if (i == list3.size) {
        e + "]"
      } else {
        e
      }
    })

    println(list4);
    

    val list5 = "[" + list3 + "]";
    println(list5);

  }

}