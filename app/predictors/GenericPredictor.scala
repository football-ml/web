package predictors

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.ClassificationModel
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import utils.CSVUtil

trait GenericPredictor {
  def loadData(dataSet: String, ss: SparkSession): DataFrame = {
    CSVUtil.CSVToDF(s"data/$dataSet", ss)
  }

//  def buildModel(): ClassificationModel
}
