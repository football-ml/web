package predictors

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.sql.SparkSession

case class NaiveBayesPredictor(dataSet: String, ss: SparkSession) extends GenericPredictor {
//  def buildModel(saveModel: Boolean = false): NaiveBayesModel = {
//    val trainData = loadData(dataSet, ss)
//
//    val splits = trainData.randomSplit(Array(0.8, 0.2), seed = 11L)
//    val training = splits(0)
//    val test = splits(1)
//
//    val assembler = new VectorAssembler().setInputCols(Array(
//      "att1",
//      "att2"
//    )).setOutputCol("features")
//
//    val indexer = new StringIndexer().setInputCol("label").setOutputCol("labelIndexed")
//
//    val nb = new NaiveBayes()
//      .setLambda(1.0)
//      .setModelType("multinomial")
//
//    val pipeline = new Pipeline().setStages(Array(assembler, indexer, nb))
//
//    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
//
//    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
//    val roundedAccuracy = accuracy - (accuracy % 0.0001)
//
//    if (saveModel) {
//      model.save(sc, s"models/NaiveBayes/${roundedAccuracy}-${dataSet}")
//    }
//    println(s"Naive Bayes predicts test data with ${roundedAccuracy} accuracy")
//    model
//  }
}
