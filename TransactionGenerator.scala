package com.example.streaming

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.{Files, Paths}
import java.util.UUID
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json._

/**
 * Classe représentant une transaction bancaire.
 */
case class Transaction(
  transaction_id: String,
  timestamp: Long,
  account_id: String,
  amount: Double,
  transaction_type: String
)

/**
 * Objet responsable de la génération de transactions bancaires factices.
 */
object TransactionGenerator {

  private val transactionTypes = Seq("deposit", "withdrawal")
  private val accountIds = (1 to 100).map(id => s"account_$id").toList

  /**
   * Démarre la génération des transactions et les écrit dans le répertoire spécifié.
   *
   * @param outputDir      Le répertoire où les fichiers de transactions seront écrits.
   * @param intervalMillis L'intervalle en millisecondes entre chaque génération de transaction.
   */
  def startGenerating(outputDir: String, intervalMillis: Long): Unit = {
    Files.createDirectories(Paths.get(outputDir))

    Future {
      while (true) {
        val transaction = generateTransaction()
        writeTransactionToFile(transaction, outputDir)
        Thread.sleep(intervalMillis)
      }
    }
  }

  /**
   * Génère une transaction bancaire factice.
   *
   * @return Un objet Transaction avec des données aléatoires.
   */
  private def generateTransaction(): Transaction = {
    Transaction(
      transaction_id = UUID.randomUUID().toString,
      timestamp = System.currentTimeMillis(),
      account_id = accountIds(Random.nextInt(accountIds.size)),
      amount = BigDecimal(Random.nextDouble() * 1000).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble,
      transaction_type = transactionTypes(Random.nextInt(transactionTypes.size))
    )
  }

  /**
   * Écrit une transaction dans un fichier au format JSON.
   *
   * @param transaction La transaction à écrire.
   * @param outputDir   Le répertoire où le fichier sera écrit.
   */
  private def writeTransactionToFile(transaction: Transaction, outputDir: String): Unit = {
    val fileName = s"$outputDir/transaction_${transaction.transaction_id}.json"
    val bw = new BufferedWriter(new FileWriter(fileName))
    val jsonString = Json.toJson(transaction).toString()
    bw.write(jsonString)
    bw.close()
  }

  implicit val transactionWrites: Writes[Transaction] = Json.writes[Transaction]

}
