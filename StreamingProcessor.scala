package com.example.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

/**
 * Objet responsable du traitement des données de streaming des transactions bancaires.
 */
object StreamingProcessor {

  /**
   * Démarre le traitement des transactions depuis le répertoire spécifié et suit les métriques.
   *
   * @param inputDir       Le répertoire où lire les données de transactions.
   * @param accountToTrack L'ID du compte bancaire à suivre spécifiquement.
   */
  def startProcessing(inputDir: String, accountToTrack: String): Unit = {
    val spark = SparkSession.builder()
      .appName("StreamingProcessor")
      .master("local[*]")
      .getOrCreate()

    // Réduire le niveau de journalisation
    spark.sparkContext.setLogLevel("WARN")

    import spark.implicits._

    // Schéma des transactions pour la lecture
    val transactionSchema = spark.read.json(inputDir).schema

    // Lecture des transactions en streaming
    val transactions = spark.readStream
      .schema(transactionSchema)
      .json(inputDir)

    // Calcul du montant total des transactions
    val totalAmount = transactions
      .agg(sum($"amount").as("total_amount"))

    // Calcul du nombre total de transactions
    val totalCount = transactions
      .agg(count($"transaction_id").as("total_transactions"))

    // Calcul du montant moyen des transactions
    val averageAmount = transactions
      .agg(avg($"amount").as("average_amount"))

    // Suivi d'un compte bancaire spécifique
    val accountTransactions = transactions
      .filter($"account_id" === accountToTrack)
      .withColumn("balance_change",
        when($"transaction_type" === "deposit", $"amount")
          .otherwise(-$"amount")
      )

    val accountBalance = accountTransactions
      .groupBy($"account_id")
      .agg(sum($"balance_change").as("balance"))

    // Écrire les métriques dans la console avec des étiquettes claires
    val queryTotalAmount = totalAmount.writeStream
      .outputMode("complete")
      .format("console")
      .option("truncate", "false")
      .option("numRows", "1")
      .option("header", "true")
      .queryName("Total Amount")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    val queryTotalCount = totalCount.writeStream
      .outputMode("complete")
      .format("console")
      .option("truncate", "false")
      .option("numRows", "1")
      .option("header", "true")
      .queryName("Total Transactions")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    val queryAverageAmount = averageAmount.writeStream
      .outputMode("complete")
      .format("console")
      .option("truncate", "false")
      .option("numRows", "1")
      .option("header", "true")
      .queryName("Average Amount")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    val queryAccountBalance = accountBalance.writeStream
      .outputMode("complete")
      .format("console")
      .option("truncate", "false")
      .option("numRows", "1")
      .option("header", "true")
      .queryName(s"Account Balance for $accountToTrack")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    spark.streams.awaitAnyTermination()
  }

}
