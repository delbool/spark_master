package learn.spark.sqldf

import org.apache.spark.sql.SparkSession

object PigToSpark101 {
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

    //val applicantsDF = sparkSession.sparkContext.textFile(warehouseLocation + "/input/applicants.csv")
    //                 .map(line => line.split(","))

    val applicantsDF = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("mode", "DROPMALFORMED")
      .load("file:///" + warehouseLocation + "/input/applicants.csv")
      .toDF()

    applicantsDF.show(10)

    val addressDF = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("mode", "DROPMALFORMED")
      .load("file:///" + warehouseLocation + "/input/address.csv")
      .toDF()

    addressDF.show(10)

    val applicantsJoinAddress = applicantsDF.join(addressDF, applicantsDF("Id") <=> addressDF("AppId"), "left");
    applicantsJoinAddress.show(50)

  }
}