package learn.spark.mapr.lab5.udf

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.storage.StorageLevel

object IncidentsWithUDF {

  // set environment variables first
  val home = System.getenv("HOME")
  val warehouseLocation = home + "/sparkmaster"

  // this must be defined outside the main method. Otherwise you will get the following error message
  // Unable to find encoder for type stored in a Dataset. Primitive types (Int, String, etc) and Product types (case classes) are supported by importing spark.implicits._ Support for serializing other types will be added in future releases
  case class Incidents(incidentNum: String, category: String, description: String, dayOfWeek: String, date: String, time: String, pdDistrict: String, resolution: String,
    address: String, X: Float, Y: Float, pdid: String)

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

    //Create DataFrame Using Reflection to Infer Schema
    import sparkSession._
    import sparkSession.implicits._

    val incidentsCaseRDD = incidentsRDD
      .map(inc => Incidents(inc(0), inc(1), inc(2), inc(3), inc(4), inc(5), inc(6), inc(7), inc(8), inc(9).toFloat, inc(10).toFloat, inc(11)))
    val incidentsDF = incidentsCaseRDD.toDF()

    // it is used so many times so persist it
    incidentsDF.persist(StorageLevel.MEMORY_ONLY)
    // You can also use cache for memory storage only
    /* incidentsDF.cache */

    // register it as a temp table so that we can run queries on it
    incidentsDF.createOrReplaceTempView("incidents")

    println("-----------------------Defining and registering UDF here -----------------------------------")
    def getYear(s:String) = {
      val year = "20" + s.substring(s.lastIndexOf('/')+1);
      year
    }
    sqlContext.udf.register("getYear", getYear _);
    
//    sqlContext.udf.register("getYear", (s:String) => {
//      val year = s.substring(s.lastIndexOf('/')+1);
//      year
//    }
    
    //incidentsDF.groupBy(getYear(incidentsDF("date"))).count().show(5);
    
    // incidents by Year
    val incidentsByYear = sql("select getYear(date) as Year, count(incidentNum) as incCounts FROm inciDents group by "
        + " getYear(date) OrdEr by INCCOUNTS DESC"); 
      
    incidentsByYear.show()
      
   // Incidents for the year 2014
    val incidents2014 = incidentsByYear.filter(row => row(0) == "2014");
    println("--------- Incidents in 2014 ----")
    incidents2014.show()
    // another alternative using the SQL query
    val incidentsOfYear2014 = sql("select getYear(date) as Year, count(incidentNum) as incCounts FROm inciDents "
        + " WHERE getYear(date) = '2014' group by getYear(date) OrdEr by INCCOUNTS DESC"); 

    incidentsOfYear2014.persist();
    
    println("--------- Incidents in 2014 Again----")
    incidentsOfYear2014.show()
    
    //find address and resolution of VANDALISM incidents in 2015
    val addrAndResOfVandalism2015 = sql("select getYear(date) as Year, address, resolution  "
        + " FROM incidents WHERE getYear(date) = '2015' and category = 'VANDALISM'"); 
    println("--------- Address and resolution of VANDALISM incidents in 2015 ----")
    var counter = 0;
    //addrAndResOfVandalism2015.show(100)
    println(addrAndResOfVandalism2015.rdd.partitions.size)
    

    val endTime = System.currentTimeMillis()
    println("Total time taken : " + (endTime - startTime) + " ms")
    while(true){
      
    }
  }
}