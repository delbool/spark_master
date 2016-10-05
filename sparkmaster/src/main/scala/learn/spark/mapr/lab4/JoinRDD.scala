package learn.spark.mapr.lab4

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

object JoinRDD {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Incidents").setMaster("local");
    val sc = new SparkContext(sparkConf);

  val home = System.getenv("HOME")
  val warehouseLocation = home + "/sparkmaster"
    
    val sparkSession = SparkSession.builder()
      .appName("Incidents")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();

    
    val addCatRDD = sc.textFile("file:///C:/Downloads/BIG_DATA/MapR/DEV3600_LAB_DATA/data/J_AddCat.csv")
        .map(line => line.split(",")).map(x => (x(1),x(0)));
    val addDistRDD = sc.textFile("file:///C:/Downloads/BIG_DATA/MapR/DEV3600_LAB_DATA/data/J_AddDist.csv")
        .map(line => line.split(",")).map(y => (y(1), y(0)));
    
//    val joinedRDD  = addCatRDD.join(addDistRDD);
//    joinedRDD.foreach(println)
//    joinedRDD.collect()
//    
//    println("print the first 10 joined members")
//    joinedRDD.take(10).foreach(println)

    val leftJoinedRDD  = addCatRDD.leftOuterJoin(addDistRDD);
    leftJoinedRDD.foreach(println)
    leftJoinedRDD.collect()
    
    println("print the first 10 joined members")
    leftJoinedRDD.take(10).foreach(println)
    
    val joinedDF = sparkSession.createDataFrame(leftJoinedRDD)
    joinedDF.write.format("json").mode(SaveMode.Overwrite).save(warehouseLocation + "/Joins")
    
  }
}