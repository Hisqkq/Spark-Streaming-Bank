# Streaming Data Engineering Project

## Overview

This project is a comprehensive Scala application designed to simulate a Data Engineering pipeline around streaming data. It generates fake bank transaction data and processes it in real-time using Apache Spark Structured Streaming. The application calculates various metrics, such as the total amount of transactions, the number of transactions, the average transaction amount, and tracks the balance of a specific bank account. Results are displayed in the terminal at regular intervals.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Setting Up Development Environment on Ubuntu](#setting-up-development-environment-on-ubuntu)
- [Running the Application](#running-the-application)

## Features

- **Data Generation**: Continuously generates fake bank transaction data with fields such as transaction ID, timestamp, account ID, amount, and transaction type.
- **Real-Time Processing**: Utilizes Apache Spark Structured Streaming to process and analyze transaction data in real-time.
- **Metrics Calculation**:
  - Total amount of transactions.
  - Total number of transactions.
  - Average transaction amount.
  - Real-time tracking of a specific account's balance.

## Technologies Used

- **Scala**: Programming language used for application development.
- **Apache Spark 3.5.2:** Framework for large-scale data processing and streaming.
- **sbt**: Build tool for Scala projects.
- **Play JSON**: Library for JSON handling in Scala.

  ## Files

- **build.sbt**: Project configuration and dependency management.
- **Main.scala**: Entry point of the application.
- **TransactionGenerator.scala**: Generates fake transaction data.
- **StreamingProcessor.scala**: Processes streaming data using Spark.

## Prerequisites

### Local Development

- **Java 8 or Java 11**: Required for running Scala and Spark.
- **Scala 2.12+**: Programming language.
- **sbt**: Scala build tool.
- **Apache Spark 3.x**: Data processing framework.

## Installation

### Setting Up Development Environment on Ubuntu

1. **Update Package Index**

```bash
sudo apt update
```

2. **Install Java**

```bash
sudo apt install openjdk-11-jdk -y
```

3. **Install Scala**

```bash
sudo apt install scala -y
```

Verify installation:

```bash
scala -version
```

4. **Install sbt**

```bash
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 99E82A75642AC823
sudo apt update
sudo apt install sbt -y
```

Verify installation:

```bash
sbt sbtVersion
```

5. **Install Apache Spark**

Download the latest version of Apache Spark from the [official website](https://spark.apache.org/downloads.html) and extract the archive:

```bash
wget https://downloads.apache.org/spark/spark-3.2.0/spark-3.2.0-bin-hadoop3.2.tgz
tar xvf spark-3.2.0-bin-hadoop3.2.tgz
sudo mv spark-3.2.0-bin-hadoop3.2 /opt/spark
```

Configure environment variables:

```bash
echo "export SPARK_HOME=/opt/spark" >> ~/.bashrc
echo "export PATH=\$PATH:\$SPARK_HOME/bin:\$SPARK_HOME/sbin" >> ~/.bashrc
source ~/.bashrc
```

Verify installation:

```bash
spark-shell --version
```

## Running the Application

1. **Clone the Repository**

```bash
git clone https://github.com/Hisqkq/Spark-Streaming-Bank.git
cd SparkStreamingBank
```

2 **Compile the Application**

```bash
sbt compile
```

3. **Run the Application**

```bash
sbt run
```

The application will start generating fake transactions and processing them in real-time. Metrics will be displayed in the terminal every 10 seconds.

## Monitoring and Debugging

**Spark Web UI**: Access the Spark Web UI at http://localhost:4040 to monitor streaming queries, batch progress, and resource usage.  
**Logs**: Check application logs for detailed error messages and stack traces to identify issues.
