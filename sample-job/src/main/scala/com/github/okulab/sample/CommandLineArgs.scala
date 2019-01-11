package com.github.okulab.sample

import com.beust.jcommander.Parameter

/**
  * @author #okulab-developers<team.dev@okulab.co>
  */
object CommandLineArgs {
  @Parameter(
    names = Array("-h", "--help"), help = true)
  var help = false

  @Parameter(
    names = Array("-d", "--inputPath"),
    description = "hdfs input path",
    required = true)
  var inputPath: String = _
}
