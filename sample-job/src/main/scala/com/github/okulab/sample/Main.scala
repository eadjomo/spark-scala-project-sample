package com.github.okulab.sample

import com.beust.jcommander.JCommander
import com.github.okulab.dlt.commons.connector.Spark
import org.apache.spark.internal.Logging
import org.slf4j.LoggerFactory

/**
  * @author #okulab-developers<team.dev@okulab.co>
  */
object Main extends  Logging{

  lazy val logger = LoggerFactory.getLogger(this.getClass.getName)




  def parseArgs(args: Array[String]): String = {
    val jCommander = new JCommander(CommandLineArgs, args.toArray: _*)

    if (CommandLineArgs.help) {
      jCommander.usage()
      System.exit(0)
    }

    logInfo(s"""|--------------------------START APPLICATION WITH PARAMETERS----------------------------
                |${CommandLineArgs.inputPath}
       """.stripMargin)
    CommandLineArgs.inputPath
  }

  def main(args: Array[String]): Unit = {
    val inputPath = parseArgs(args)
    SimpleJob(inputPath).execute
  }.foreach(df=>df.write.orc("hdfs://127.0.0.1:12345/tmp/hadoop/orc"))

}
