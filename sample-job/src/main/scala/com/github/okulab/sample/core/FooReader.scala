package com.github.okulab.sample.core

import com.github.okulab.dlt.commons.connector.Spark
import com.github.okulab.sample.model.FooData
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import scala.collection.mutable.{Map => MutableMap}

/**
  * CsvReader
  *
  * @author #okulab-developers<team.dev@okulab.co>
  */
case class FooReader(readerInfo: FooData) extends com.github.okulab.sample.FooReader {

  /**
    *
    * @return A sequence which contain the DataFrames obtain from CSV Files.
    *         Can return multiple Df if an option is set in the readerInfo:
    *         each Df have the same schema (and represent the same source)
    *         but each Df will be process individually and sequentially.
    */
  override def read(): Seq[DataFrame] = {

    readerInfo.path.map{ p=>
      logInfo("CsvReader >> with databricks lib read file : " + p)
      //generate schema
      val schema: StructType = (if (readerInfo.hasHeader.toBoolean &&
        (readerInfo.fields == null || readerInfo.fields.length == 0)) null
      else StructType(readerInfo.fields.map(fieldName => StructField(fieldName, StringType, true)).toArray))
      //simple use of databricks-csv lib
      Spark().sparkSession
        .read
        .format("csv")
        .option("header", readerInfo.hasHeader)
        .option("delimiter", readerInfo.delimiter)
        .options(getOptions())
        .schema(schema)
        .load(p)
    }


  }

    /**
    * Get list of options to apply on reader
    *
    * @return Map[String,String]
    */
   def getOptions(): Map[String, String] = {
    val options = MutableMap[String, String]()
    if (readerInfo.charset.isDefined) options("charset") = readerInfo.charset.get
    if (readerInfo.quote.isDefined) options("quote") = readerInfo.quote.get
    if (readerInfo.escape.isDefined) options("escape") = readerInfo.escape.get
    if (readerInfo.mode.isDefined) options("mode") = readerInfo.mode.get
    if (readerInfo.comment.isDefined) options("comment") = readerInfo.comment.get
    if (readerInfo.nullValue.isDefined) options("nullValue") = readerInfo.nullValue.get
    if (readerInfo.dateFormat.isDefined) options("dateFormat") = readerInfo.dateFormat.get
    options.toMap
  }

}
