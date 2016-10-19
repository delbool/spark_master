package learn.spark.sqldf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.avro.data.Json

object PigToSpark102 {

  case class Applicants(Id: String, FirstName: String, LastName: String, DateOfBirth: String, ApprovalStatus: String, BirthCountryCode: String)
  case class Address(Id: String, AppId: String, AddressType: String, StreetAddress: String, City: String, State: String, Zip: String)

  def main(args: Array[String]): Unit = {
    // set environment variables first
    val home = System.getenv("HOME")
    val warehouseLocation = home + "/sparkmaster"
    System.setProperty("hadoop.home.dir", home + "/sparkmaster/winutils/hadoop-common-2.2.0-bin-master")
    
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

    val applicantsRDD1 = applicantsRDD.map(row => Row.fromSeq(row.toSeq));
    val applicantSchemaStr = "Id,FirstName,LastName,DOB,ApprovalStatus,BirthCountry";
    val applicantSchema = StructType(applicantSchemaStr.split(",").map(fieldName => StructField(fieldName, StringType, true)))
    val applicantDataFrame = sparkSession.sqlContext.createDataFrame(applicantsRDD1, applicantSchema)


    val addressRDD1 = addressRDD.map(add => Row.fromSeq(add.toSeq)) 
    val addressSchemaStr = "Id,AppId,AddressType,StreetAddress,City,State,Zip";
    val addressSchema = StructType(addressSchemaStr.split(",").map(fieldName => StructField(fieldName, StringType, true)))
    val addressDataFrame = sparkSession.sqlContext.createDataFrame(addressRDD1, addressSchema)

    applicantDataFrame.show()
    addressDataFrame.show()
    
    val addressRdd2 = addressDataFrame.rdd.map(add => (add(add.fieldIndex("AppId")), add)).groupByKey();
    addressRdd2.foreach(println)
    
    addressRdd2.toDF();

//    val outputPath = warehouseLocation + "/Output9";
//    addressRdd2.toDF().write.mode("append").format("json").save(outputPath);
    
//    val applicantsJoinAddress = applicantsDF.join(addressGroupDF, applicantsDF("Id") <=> addressGroupDF(0), "left");
//    applicantsJoinAddress.show(50)

  }
}