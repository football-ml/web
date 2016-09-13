package utils

import org.apache.spark.sql.{DataFrame, SparkSession}

object CSVUtil {

  def CSVToDF(path: String, ss: SparkSession): DataFrame = {
    val df = ss.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(path)

    val header = df.first
    val data = df.filter(_ (0) != header(0))

    df
  }
}
