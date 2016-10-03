package learn.spark.mapr.lab6

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.storage.StorageLevel

object IncidentsMonitor {

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

    val incidentsRDD = sparkSession.sparkContext.textFile("file:///C:/Downloads/MapR/DEV3600_LAB_DATA/data/sfpd.csv").map(line => line.split(","));
    //    val incidents = sparkSession.sparkContext.textFile("file:///C:/Downloads/MapR/DEV3600_LAB_DATA/data/sfpd.csv")
    //    val incidentsRDD = incidents.map(line => line.split(","));
    //    val resolutionRaw = incidentsRDD.map(incident => incident(Resolution))
    val resolutions = incidentsRDD.map(incident => incident(Resolution)).distinct

    resolutions.cache()

    println("------------->> Resolutions Linueage ---------------------")
    println(resolutions.toDebugString);

    val totalResolutions = resolutions.count()
    resolutions.count()
    
    val endTime = System.currentTimeMillis()
    println("Total time taken : " + (endTime - startTime) + " ms")

    while (true) {}
  }
}