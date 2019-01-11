package com.github.okulab.sample.model

import com.fasterxml.jackson.annotation.{JsonProperty, JsonTypeName}

/**
  * @author #okulab-developers<team.dev@okulab.co>
  * @param path
  * @param filter
  * @param delimiter
  * @param hasHeader
  * @param isMultiline
  * @param lineSeparator
  * @param quote
  * @param escape
  * @param mode
  * @param charset
  * @param comment
  * @param nullValue
  * @param dateFormat
  * @param escapeSpecialCharacters
  * @param fields
  */
@JsonTypeName("csv")
case class FooData(@JsonProperty(value = "path", required = true) path: Seq[String],
                         @JsonProperty(value = "filter", required = false) filter: Option[String],
                         @JsonProperty(value = "delimiter", required = true) delimiter: String = ";",
                         @JsonProperty(value = "hasHeader", required = true) hasHeader: String = "false",
                         @JsonProperty(value = "isMultiline", required = false) isMultiline: Option[String],
                         @JsonProperty(value = "lineSeparator", required = false) lineSeparator: Option[String],
                         @JsonProperty(value = "quote", required = false) quote: Option[String],
                         @JsonProperty(value = "escape", required = false) escape: Option[String],
                         @JsonProperty(value = "mode", required = false) mode: Option[String],
                         @JsonProperty(value = "charset", required = false) charset: Option[String],
                         @JsonProperty(value = "comment", required = false) comment: Option[String],
                         @JsonProperty(value = "nullValue", required = false) nullValue: Option[String],
                         @JsonProperty(value = "dateFormat", required = false) dateFormat: Option[String],
                         @JsonProperty(value = "escapeSpecialCharacters", required = false) escapeSpecialCharacters: Option[Boolean],
                         @JsonProperty(value = "fields", required = false) fields: Seq[String]
                        ) {
}
