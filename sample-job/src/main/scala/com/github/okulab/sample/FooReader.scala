package com.github.okulab.sample

import com.github.okulab.sample.model.FooData
import org.apache.spark.internal.Logging
import org.apache.spark.sql.DataFrame

/**
  * @author #okulab-developers<team.dev@okulab.co> on 10/01/2019
  */
trait FooReader  extends Logging{

  /**
    * Use to construct the dataFrame in the read() method
    * Describe the source data
    */
  val readerInfo: FooData

  /**
    * Construct a Seq[DataFrame] from a source which is describe in the readerInfo
    *
    * @return A sequence of DataFrame with raw data inside (no cast done at this step)
    */
  def read(): Seq[DataFrame]

}
