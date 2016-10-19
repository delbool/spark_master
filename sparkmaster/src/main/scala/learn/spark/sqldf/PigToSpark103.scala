package learn.spark.sqldf

import org.apache.spark.annotation.Experimental
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.SparkSession

// helpful source
// https://sumitpal.wordpress.com/2016/06/17/some-data-gymnastics-with-spark-2-0s-datasets-and-equivalent-code-in-rdds/
object PigToSpark103 {

  case class Claim(pid: String, diag1: String, diag2: String)
  case class Lab(pid: String, lab1: String, lab2: String)
  case class RxClaim(pid: String, drug1: String, drug2: String)

  case class PatientClaims(pid: String, claims: Array[Claim])
  case class PatientLab(pid: String, labs: Array[Lab])
  case class PatientRxClaim(pid: String, rxclaims: Array[RxClaim])

  case class Patient(pid: String, claims: Array[Claim], labs: Array[Lab], rxclaims: Array[RxClaim])

  def main(args: Array[String]): Unit = {

    // set environment variables first
    val home = System.getenv("HOME")
    val warehouseLocation = home + "/sparkmaster"
    System.setProperty("hadoop.home.dir", home + "/sparkmaster/winutils/hadoop-common-2.2.0-bin-master")

    val sparkSession = SparkSession.builder()
      .appName("Patient Info")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .master("local")
      .getOrCreate();

    val sparkContext = sparkSession.sparkContext;

    val claimsData = Seq(Claim("PID1", "diag1", "diag2"), Claim("PID1", "diag2", "diag3"), Claim("PID1", "diag1", "diag5"), Claim("PID2", "diag3", "diag4"), Claim("PID2", "diag2", "diag1"))

    val labsData = Seq(Lab("PID1", "lab1", "lab2"), Lab("PID1", "lab1", "lab2"), Lab("PID2", "lab3", "lab4"), Lab("PID2", "lab3", "lab6"))

    val rxClaimsData = Seq(RxClaim("PID1", "drug4", "drug1"), RxClaim("PID1", "drug3", "drug1"), RxClaim("PID2", "drug3", "drug5"), RxClaim("PID2", "drug2", "drug1"), RxClaim("PID1", "drug5", "drug2"))

    val claimRDD = sparkSession.sparkContext.parallelize(claimsData)
    val labRDD = sparkSession.sparkContext.parallelize(labsData)
    val rxclaimRDD = sparkSession.sparkContext.parallelize(rxClaimsData)

    //Create DataFrame Using Reflection to Infer Schema
    import sparkSession._
    import sparkSession.implicits._
    
    //val claimPairRDD = claimRDD.map(x => (x.pid, x.diag1, x.diag2))  //not used so why is this here
    val claimDS = claimRDD.toDF("pid", "diag1", "diag2").as[Claim]
    val claimsDSGroupedByPID = claimDS.groupByKey(v => v.pid)
    val gClaims = claimsDSGroupedByPID.mapGroups({ case (k, iter) => PatientClaims(k, iter.map(x => Claim(x.pid, x.diag1, x.diag2)).toArray) })

    //val labPairRDD = labRDD.map(x => (x.pid, x)) //not used so why is this here
    val labDS = labRDD.toDF("pid", "lab1", "lab2").as[Lab]
    val labDSGroupedByPID = labDS.groupByKey(v => v.pid)
    val gLabs = labDSGroupedByPID.mapGroups({ case (k, iter) => PatientLab(k, iter.map(x => Lab(x.pid, x.lab1, x.lab2)).toArray) })

    //val rxclaimPairRDD = rxclaimRDD.map(x => (x.pid, x)) //not used so why is this here
    val rxClaimDS = rxclaimRDD.toDF("pid", "drug1", "drug2").as[RxClaim]
    val rxClaimsDSGroupedByPID = rxClaimDS.groupByKey(v => v.pid)
    val gRxClaim = rxClaimsDSGroupedByPID.mapGroups({ case (k, iter) => PatientRxClaim(k, iter.map(x => RxClaim(x.pid, x.drug1, x.drug2)).toArray) })

    val allJoined = gClaims.join(gLabs, "pid").join(gRxClaim, "pid")

    val allJoinedDS = allJoined.as[Patient]

    //  allJoinedDS.show(false) ==  allJoinedDS show false
    allJoinedDS.show(false) // do not truncate when you show
    
    // http://stackoverflow.com/questions/35444971/perform-group-by-on-rdd-in-spark-and-write-each-group-as-individual-parquet-file
    // http://stackoverflow.com/questions/29908892/save-a-large-spark-dataframe-as-a-single-json-file-in-s3
    allJoinedDS.repartition(1).write.mode(SaveMode.Overwrite).json(warehouseLocation + "/OutputPig")

     // More work needed here
//    var i = 0
//    val totalCount = allJoinedDS.count()
//    val finalAllJoinedDS = allJoinedDS.rdd.map(line => {
//      i = i + 1;
//      if (i == 1) {
//        "[" + line + ","
//      } else if (i == totalCount) {
//        line + "]"
//      } else {
//        line + ","
//      }
//    }).toDF().as[Patient]
//    
//    finalAllJoinedDS.repartition(1).write.mode(SaveMode.Overwrite).json(warehouseLocation + "/OutputPig")
    
  }
}