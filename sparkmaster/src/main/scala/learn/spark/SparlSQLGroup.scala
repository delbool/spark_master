package learn.spark

import java.io.File

import org.apache.commons.io.FileUtils
import org.apache.spark.sql.DataFrameWriter
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

object SparlSQLGroup {
  val home = System.getenv("HOME")
  val warehouseLocation = home + "/sparkmaster"

  def main(args: Array[String]): Unit = { 
    // let us first take care of this error message
    /* ==>> Could not locate executable null\bin\winutils.exe in the Hadoop binaries. */
    handleWinUtilsError;
    val sparkSession = getSQLSession;

    val addressDF = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("mode", "DROPMALFORMED")
      .load("file:///" + warehouseLocation + "/input/address_with_header.csv")
      .toDF()

    addressDF.show()

    //val addressGroupDF = addressDF.rdd.groupBy(row => row.get(1));    
    val addressGroupDF = addressDF.rdd.groupBy(row => row.fieldIndex("AppId"));    

    println("============>  GROUPED ADDRESSSES: =====================");
    addressGroupDF.foreach(println)
    val addressGroupRDD = addressGroupDF.map(addr => (addr._1, addr._2));
    
    val outputPath = warehouseLocation + "/JSON_Output";
    //remove destination folder in preparation for new output
    FileUtils.deleteDirectory(new File(outputPath));
    FileUtils.deleteDirectory(new File(outputPath + 1));
    FileUtils.deleteDirectory(new File(outputPath + 2));
    FileUtils.deleteDirectory(new File(outputPath + 3));

    addressGroupRDD.saveAsTextFile(outputPath + 3)
    
  //write.json(outputPath);
  addressDF.toJSON.write.json(outputPath);
//    addressDF.write.mode("append").format("json").save(outputPath)
    
    // Register the DataFrame as a temporary view of 'address'
    addressDF.createOrReplaceTempView("address")

//    val addressDF2 = addressDF.sparkSession.sql("SELECT * FROM address");
    addressDF.select("*").write.json(outputPath + "1");

    addressDF.select("*").printSchema();

    //val addressJsonRdd = addressDF.toJSON.rdd;
    val addressJsonRdd = addressDF.toJSON.rdd;

    addressJsonRdd.saveAsTextFile( warehouseLocation + "/JSON_Output2");
//    addressDF.write.format("json").mode(SaveMode.Overwrite).save(warehouseLocation + "/JSON_Output2");

    while (true) {
      // do nothing - waste time here until you get interrupted
    }
  }

  def handleWinUtilsError {
    System.setProperty("hadoop.home.dir", home + "/sparkmaster/winutils/hadoop-common-2.2.0-bin-master")
  }

  def getSQLSession: SparkSession = {
    val sparkSession = SparkSession.builder()
      .appName("Spark CSV File Reader")
      .master("local")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .getOrCreate();
    return sparkSession;
  }

}