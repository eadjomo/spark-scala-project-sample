package com.github.okulab.sample

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.okulab.dlt.commons.connector.Spark
import com.github.okulab.dlt.commons.exception.DatalakeValidationException
import com.github.okulab.dlt.fs.Utils
import com.github.okulab.sample.core.FooReader
import com.github.okulab.sample.model.FooData

import scala.util.{Failure, Success, Try}

/**
  * @author #okulab-developers<team.dev@okulab.co>
  * @param configPath
  */
case class SimpleJob (configPath:String) {

  def execute={

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    lazy val readerData = Try(mapper.readValue(Spark().sparkSession.read.textFile(configPath).collect().mkString,
      classOf[FooData]))

 readerData match {
      case Success(readerData)=> FooReader(readerData).read()
      case Failure(e) => throw DatalakeValidationException(e.getMessage )
    }
  }
}
