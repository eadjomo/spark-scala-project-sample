package com.github.okulab.sample

import com.github.okulab.dlt.commons.connector.Spark
import com.github.okulab.dlt.test.{LocalClusterSpec, SparkSqlSpec}
import org.apache.hadoop.fs.Path
import org.scalatest.{FeatureSpec, Matchers}

/**
  * @author #okulab-developers<team.dev@okulab.co>
  */
class SimpleJobIntegrationTest extends FeatureSpec with SparkSqlSpec with LocalClusterSpec with Matchers {

  val inputCsvPath: String = "hdfs://127.0.0.1:12345/tmp/hadoop/csv"

  override def beforeAll(): Unit = {
    super.beforeAll()
      Spark(sc, sqlc, fsContext)
     fsContext.mkdirs(new Path(inputCsvPath))
    uploadResource("csv/foo.json", "csv")
    uploadResource("csv/simplefile.csv", "csv")
    }


  feature("Read Csv file") {
    scenario("count data in csv file") {
    val lines=  SimpleJob("hdfs://127.0.0.1:12345/tmp/hadoop/csv/foo.json").execute
      lines.head.show()
      lines.head.count() should equal(3)
    }

    scenario("From Main") {
      Main.main(Array("-d", "hdfs://127.0.0.1:12345/tmp/hadoop/csv/foo.json"))
      sc.read.orc("hdfs://127.0.0.1:12345/tmp/hadoop/orc").count() should equal(3)
    }
  }



  override def afterAll() : Unit = {
    super.afterAll()
  }

}
