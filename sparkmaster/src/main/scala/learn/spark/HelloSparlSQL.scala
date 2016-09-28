package learn.spark

import org.apache.spark.sql.DataFrameWriter
import org.apache.spark.sql.SparkSession

object HelloSparlSQL {
  val home = System.getenv("HOME")
  println("Home===============>>> " + home)
  
  val warehouseLocation = "file:///" + home + "/sparkmaster"
  def main(args: Array[String]): Unit = {
    // let us firs take care of this error message
    /* ==>> Could not locate executable null\bin\winutils.exe in the Hadoop binaries. */
    handleWinUtilsError;
    val sparkSession = getSQLSession;
    
    val addressDF = sparkSession.read
              .format("com.databricks.spark.csv")
              .option("header", "true")
              .option("mode", "DROPMALFORMED")
              .load(warehouseLocation + "/input/address_with_header.csv")
              .toDF()
              
    addressDF.show()
    
    // Register the DataFrame as a temporary view of 'address'
    addressDF.createOrReplaceTempView("address")
        
    val addressDF2 = addressDF.sparkSession.sql("SELECT * FROM address");
    
    val addressJsonRddTemp = addressDF2.toJSON;

    addressDF2.toJSON.printSchema();

    val addressJsonRdd = addressDF2.toJSON.rdd;

    addressJsonRdd.saveAsTextFile(warehouseLocation + "/JSON_Output");
    
    while (true){
      // do nothing - waste time here until you get interrupted
    }

    
  }
  
  def handleWinUtilsError {
    System.setProperty("hadoop.home.dir", home + "/sparkmaster/winutils/hadoop-common-2.2.0-bin-master")
  }
  
  def getSQLSession :SparkSession = {
//   val conf = new SparkConf()
//              .setAppName("Hello Spark Application")
//              .setMaster("local");
//    val sc = new SparkContext(conf);
    val sparkSession = SparkSession.builder()
        .appName("Spark CSV File Reader")
        .master("local")
        .config("spark.sql.warehouse.dir", warehouseLocation)
        .getOrCreate();
       return sparkSession;
  }
}