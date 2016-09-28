package learn.scala

package society {
  package professional {
    class Executive {
      private[society] var mankind = "AAAA";
      private[professional] var workers = "BBBB"
      private[Executive] var managers = null
      private[this] var secrets = null

      def help(another: Executive) {
        println(another.mankind)
        println(another.workers)
        println(another.managers)
        //println(another.secrets) //ERROR
      }
    }
  }
}

import society.professional.Executive
object Run {
   def main(args: Array[String]): Unit = {
     new Executive()
   }
}