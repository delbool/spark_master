package learn.spark.mapr

/* This is an exercise from MapR training DEV 361 - Apache Spark 361 */

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Sfpd {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Incidents").setMaster("local");
    val sc = new SparkContext(sparkConf);

    //Map input variables
    val IncidntNum = 0
    val Category = 1
    val Descript = 2
    val DayOfWeek = 3
    val Date = 4
    val Time = 5
    val PdDistrict = 6
    val Resolution = 7
    val Address = 8
    val X = 9
    val Y = 10
    val PdId = 11

    val sfpd = sc.textFile("C:/Downloads/MapR/DEV3600_LAB_DATA/data/sfpd.csv").map(line => line.split(","));
    sfpd.first().foreach(println);
    println(sfpd.count());
    val categories = sfpd.map(incident => incident(Category)).distinct().collect().sortBy(cat=>cat)
    println("------------->> CATEGORIES ---------------------")
    categories.foreach(println);

  }
}