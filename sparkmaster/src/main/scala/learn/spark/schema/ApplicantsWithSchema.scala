package learn.spark.schema

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row

object ApplicantsWithSchema {
  def main(args: Array[String]): Unit = {
    // set environment variables first
    val home = System.getenv("HOME")
    val warehouseLocation = home + "/sparkmaster"

    val sparkSession = SparkSession.builder()
      .appName("Applicants")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();
    
    // here is the schema or columns of a table - metadata
    val applicantSchemaStr = "Id,FirstName,LastName,DOB,ApprovalStatus,BirthCountry";
    // covert the strings to schema fields
    val applicantSchema = StructType(applicantSchemaStr.split(",").map(fieldName => StructField(fieldName, StringType, true)))
    println("=================>> Number of columns: " + applicantSchema.fields.length)
    
    // read the data file (sqoop file for example) and map it to the schema to create a Row RDD
    val applicantRDD = sparkSession.sparkContext.textFile(warehouseLocation + "/input/applicants.csv")
                        .map(line => line.split(","))
                        .map(app => Row(app(0), app(1), app(2), app(3),app(4), app(5)));
    
    // create a DataFrame from the Row RDD and the schema
    val applicantDataFrame = sparkSession.sqlContext.createDataFrame(applicantRDD, applicantSchema)
    applicantDataFrame.show()
    
    // create it a temp table to do whatever SQL queries you want on the "applicants" named table
    applicantDataFrame.createOrReplaceTempView("applicants")

  }
}