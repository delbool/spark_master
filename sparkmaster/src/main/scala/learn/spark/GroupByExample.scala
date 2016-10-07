package learn.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object GroupByExample {
  def main(args: Array[String]): Unit = {
    println("----------------------Staring ------------------------------")
    val sparkConf = new SparkConf().setAppName("GroupBy Example").setMaster("local");
    val sc = new SparkContext(sparkConf);
    sc.setLogLevel("WARN")

    val inputrdd = sc.parallelize(Seq(("key1", 1), ("key2", 2), ("key1", 3)))
    inputrdd.foreach(println)
    inputrdd.saveAsTextFile("file:///home/spark/sparkmaster/inputrdd")
    
    val grouped1 = inputrdd.groupByKey()
    println("=========>> Printing grouped ...")
    grouped1.foreach(println)
    grouped1.saveAsTextFile("file:///home/spark/sparkmaster/grouped1")

    println("=========>> Printing grouped after collect...") 
    grouped1.collect().foreach(println)
    grouped1.saveAsTextFile("file:///home/spark/sparkmaster/grouped1Collect")

    val grouped2 = inputrdd.groupBy {
      x => if ((x._2 % 2) == 0) { 
        "evennumbers" } else { "oddnumbers"     
        } 
      }
    println("GroupBy ...")
    grouped2.foreach(println)
    grouped2.saveAsTextFile("file:///home/spark/sparkmaster/grouped2")
       
    val grouped3 = inputrdd.groupBy(x => x._1)
    println("-------->>>Grouping by first element of tuple")
    grouped3.foreach(println)
    
    grouped3.saveAsTextFile("file:///home/spark/sparkmaster/RUBISH")
  }
}