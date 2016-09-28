package learn.scala
import Running._

object Running {
   implicit class IntTimes(x: Int) {
      def times [A](f: =>A): Unit = {
         def loop(currentItem: Int): Unit =
         
         if(currentItem > 0){
            f
            loop(currentItem - 1)
         }
         loop(x)
      }
   }
}


object RunDemo {
     def main(args: Array[String]) {
      2 times println("Hello Again.")
   }
}