package com.example.streaming

/**
 * Point d'entrée principal de l'application de streaming de données.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val outputDir = "data/transactions"
    val accountToTrack = "account_61"
    val intervalMillis = 1000L // Génère une transaction chaque seconde

    // Démarre la génération des transactions
    TransactionGenerator.startGenerating(outputDir, intervalMillis)

    // Démarre le traitement des transactions
    StreamingProcessor.startProcessing(outputDir, accountToTrack)
  }
}
