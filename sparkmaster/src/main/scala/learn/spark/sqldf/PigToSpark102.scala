package learn.spark.sqldf

import org.apache.spark.sql.SparkSession

object PigToSpark102 {

  case class Applicants(Id: String, FirstName: String, LastName: String, DateOfBirth: String, ApprovalStatus: String, BirthCountryCode: String)
  case class Address(Id: String, AppId: String, AddressType: String, StreetAddress: String, City: String, State: String, Zip: String)

  def main(args: Array[String]): Unit = {
    // set environment variables first
    val home = System.getenv("HOME")
    val warehouseLocation = home + "/sparkmaster"
    val startTime = System.currentTimeMillis()

    val sparkSession = SparkSession.builder()
      .appName("Incidents")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();

    // first load the files and convert them to RDD not DF.
    val addressRDD = sparkSession.sparkContext.textFile("file:///" + warehouseLocation + "/input/address.csv").map(line => line.split(","));
    val applicantsRDD = sparkSession.sparkContext.textFile("file:///" + warehouseLocation + "/input/applicants.csv").map(line => line.split(","));

    //Create DataFrame Using Reflection to Infer Schema
    import sparkSession._
    import sparkSession.implicits._

    val applicantsRDD1 = applicantsRDD.map(app => Applicants(app(0), app(1), app(2), app(3), app(4), app(5)));
    println("=================>> Step 1")
    val applicantsDF = applicantsRDD1.toDF()
    println("=================>> Step 2")

    val addressRDD1 = addressRDD.map(add => Address(add(0), add(1), add(2), add(3), add(4), add(5), add(6)))
    println("=================>> Step 3")
    val addressDF = addressRDD1.toDF();
    println("=================>> Step 4")

//    val addressGroupDF = addressDF.groupByKey("AppId")
//    addressGroupDF.rdd.foreach(println)
    
    //addressGroupDF.show(8)

//    val applicantsJoinAddress = applicantsDF.join(addressGroupDF, applicantsDF("Id") <=> addressGroupDF(0), "left");
//    applicantsJoinAddress.show(50)

  }
}