package learn.spark.mapr.lab4

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.storage.StorageLevel

object IncidentsPairRDD {
    // set environment variables first
  val home = System.getenv("HOME")
  val warehouseLocation = home + "/sparkmaster"

  def main(args: Array[String]): Unit = {

    val startTime = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("Incidents")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();

    // making the logs less noisy
    sparkSession.sparkContext.setLogLevel("WARN")

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

    val incidentsRDD = sparkSession.sparkContext.textFile("file:///C:/Downloads/BIG_DATA/MapR/DEV3600_LAB_DATA/data/sfpd.csv").map(line => line.split(","));
    val incidentsPairRdd = incidentsRDD.map(inc => (inc(Category), 1));
    incidentsPairRdd.take(20).foreach(println)
    
    val incidentsPairRDDReduced = incidentsPairRdd.reduceByKey((a,b) => a + b )
    println("\n==========>> Now reduced ones ")
    incidentsPairRDDReduced.take(10).foreach(println)
    
    val incidentsPairRDDSorted = incidentsPairRDDReduced.sortByKey()
    println("\n==========>> Now sorted ones sorted by Category")
    incidentsPairRDDSorted.take(10).foreach(println)
    
    val incidentsPairRDDSortedByValue = incidentsPairRDDReduced
          .map(tuple => (tuple._2,tuple._1))
          .sortByKey(false)
          .map(st => (st._2, st._1))
    
    println("\n==========>> Now sorted ones this time sorted by the count of incidents")
    incidentsPairRDDSortedByValue.take(10).foreach(println)
    
    
    
  }
}