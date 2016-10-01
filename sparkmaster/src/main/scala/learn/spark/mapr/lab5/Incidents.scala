package learn.spark.mapr.lab5

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

object Incidents {

  // set environment variables first
  val home = System.getenv("HOME")
  val warehouseLocation = home + "/sparkmaster"

  // this must be defined outside the main method. Otherwise you will get the following error message
  // Unable to find encoder for type stored in a Dataset. Primitive types (Int, String, etc) and Product types (case classes) are supported by importing spark.implicits._ Support for serializing other types will be added in future releases
  case class Incidents(incidentNum: String, category: String, description: String, dayOfWeek: String, date: String, time: String, pdDistrict: String, resolution: String,
    address: String, X: Float, Y: Float, pdid: String)

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("Incidents")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();

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
//    incidentsRDD.first().foreach(println);
//    println(incidentsRDD.count());
//    val categories = incidentsRDD.map(incident => incident(Category)).distinct().collect().sortBy(cat => cat)
//    println("------------->> CATEGORIES ---------------------")
//    categories.foreach(println);

    //Create DataFrame Using Reflection to Infer Schema
    import sparkSession._
    import sparkSession.implicits._

    val incidentsCaseRDD = incidentsRDD
      .map(inc => Incidents(inc(0), inc(1), inc(2), inc(3), inc(4), inc(5), inc(6), inc(7), inc(8), inc(9).toFloat, inc(10).toFloat, inc(11)))
    val incidentsDF = incidentsCaseRDD.toDF()
   
    // register it as a temp table so that we can run queries on it
    incidentsDF.createOrReplaceTempView("incidents")

//    // show top 5 Districts with highest number of incidents
//    val districtsWithTopIncidents = sparkSession.sqlContext.sql("SELECT PdDistrict, count(IncidentNum) as incidentCount FROM "
//      + " incidents GROUP BY PdDistrict ORDER BY incidentCount DESC LIMIT 5");
//
//    districtsWithTopIncidents.show()
//
//    // show top 10 resolutions
//    val top10ResolutionsDF = sparkSession.sqlContext.sql("SELECT Resolution, count(Resolution) as resolutionCounts FROM "
//      + "incidents GROUP BY Resolution ORDER BY resolutionCounts DESC LIMIT 10");
//    top10ResolutionsDF.show();
//
//    // show top 10 categories resolutions
//    sparkSession.sqlContext.sql("SELECT Category, count(Category) as categoryCounts FROM "
//      + "incidents GROUP BY Category ORDER BY categoryCounts DESC LIMIT 10").show();
//
//    // save top 10 resolutions to a JSON file
//    println("-------------------------->>>> Saving 10 top resolutions to JSON file ...")
//    top10ResolutionsDF.write.format("json").mode(SaveMode.Overwrite).save(warehouseLocation + "/Resolutions");
    
    println("-----------------------Additional Testing -----------------------------------")
    incidentsDF.take(10).foreach { println };
    incidentsDF.printSchema()
    incidentsDF.columns.foreach(col => println(col))
    incidentsDF.explain()
    
    // drop columns from data frame
    //incidentsDF.drop("dayOfWeek", "date", "time", "pdDistrict", "resolution", "address").take(10).foreach(println);
    //incidentsDF.drop("dayOfWeek", "date", "time", "pdDistrict", "resolution", "address").printSchema();
    incidentsDF.drop("dayOfWeek", "date", "time", "pdDistrict", "resolution", "address").explain();

    
        
  }
}