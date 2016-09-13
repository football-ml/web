package predictors

import org.apache.spark.ml.{Pipeline, PipelineModel, PipelineStage}
import org.apache.spark.ml.classification.{DecisionTreeClassificationModel, DecisionTreeClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{Bucketizer, StringIndexer, VectorAssembler, VectorIndexer}
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType}


case class DecisionTreePredictor(dataSet: String, ss: SparkSession) extends GenericPredictor {
  def buildModel(saveModel: Boolean = false): PipelineModel = {
    val data = loadData(dataSet, ss)
      .withColumn("id", org.apache.spark.sql.functions.monotonically_increasing_id().cast(DoubleType))
      .select(
        "id",
        "team_h_win_perc",
        "team_h_dra_perc",
        "team_value_ratio",
        "tm_pred_p",
        "tm_pred",
        "winner"
      )

    data.show()

    val buckets = Array(Double.NegativeInfinity, -0.5, 0.0, 0.5, Double.PositiveInfinity)

    val bucketizer = new Bucketizer()
      .setInputCol("team_value_ratio")
      .setOutputCol("team_value_ratio_bin")
      .setSplits(buckets)

    val stringIndexer = new StringIndexer()
      .setInputCol("tm_pred")
      .setOutputCol("tm_pred_idx")

    val vectorIndexer = new VectorIndexer()
      .setInputCol("team_h_last_w")
      .setOutputCol("team_h_last_w_")

    val assembler = new VectorAssembler()
      .setInputCols(Array("team_value_ratio", "tm_pred_idx", "tm_pred_p"))
      .setOutputCol("features")

    val splits = data.randomSplit(Array(0.8, 0.2), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val decisionTreeClassifier = new DecisionTreeClassifier()
      .setFeaturesCol("features")
      .setLabelCol("id")

    val pipeline = new Pipeline()
      .setStages(
        Array(
//          bucketizer,
//          vectorIndexer,
          stringIndexer,
          assembler,
          decisionTreeClassifier
        ))

    val model = pipeline.fit(training)
    val predictions = model.transform(test)

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
      .setLabelCol("id")

    val accuracy = evaluator
      .evaluate(predictions)

    println("Test Error = " + (1.0 - accuracy))

    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println("Learned classification tree model:\n" + treeModel.toDebugString)
    model
  }
}
