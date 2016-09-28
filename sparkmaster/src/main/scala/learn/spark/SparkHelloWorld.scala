package learn.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkHelloWorld {
  val home = System.getenv("HOME")
  println("Home===============>>> " + home)

   val warehouseLocation = "file:///" + home + "/sparkmaster"

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", home + "/sparkmaster/winutils/hadoop-common-2.2.0-bin-master")
    
    val conf = new SparkConf()
              .setAppName("Hello Spark Application")
              .setMaster("local");
    val sc = new SparkContext(conf);
    
    // address input file
    val addressFile = warehouseLocation + "/input/address.csv";
    val addressRDD = sc.textFile(addressFile);

    val addrCount = addressRDD.count();
    println("Total number of addresses: " + addrCount);
    
    addressRDD.persist();
    
    addressRDD.foreach(line => println(line));
    
    val outputFolder = home + "/sparkmaster/text-output";
    println(addressRDD.coalesce(1).toJavaRDD().count());
    addressRDD.saveAsTextFile(outputFolder);
    
    val addrColumnsRDD = addressRDD.flatMap(row => row.split(","))
    addrColumnsRDD.saveAsTextFile(outputFolder+2)
    
    while (true){
      
    }
  }
}