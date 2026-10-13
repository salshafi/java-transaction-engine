# Java Transaction Engine

A console application that processes a batch of financial transactions on a pool of worker threads and saves them to a SQLite database using JDBC.

## Features

- **Object-oriented design:** a `Transaction` model with private fields and getters, an `ITransactionProcessor` interface, and a `SimpleProcessor` class that implements it.
- **Custom exception:** `InsufficientFundsException` rejects any transaction above a set limit.
- **JDBC with SQLite:** transactions are saved with parameterized `PreparedStatement` queries.
- **Multithreading:** a fixed pool of 3 worker threads (`ExecutorService`) processes the transactions side by side.

## Tech Stack

Java SE, JDBC, SQLite (`sqlite-jdbc`), Eclipse

## Project Structure

```
src/com/centennial/engine/
  Transaction.java                 data model
  ITransactionProcessor.java       interface
  InsufficientFundsException.java  custom exception
  SimpleProcessor.java             business rule + save
  DatabaseService.java             JDBC / SQLite access
  TransactionEngine.java           main: thread pool and batch
lib/sqlite-jdbc.jar                SQLite driver
```

## Run It

Compile (Git Bash or Windows):

```bash
mkdir -p bin
javac -d bin -cp "lib/*" src/com/centennial/engine/*.java
```

Run:

```bash
java --enable-native-access=ALL-UNNAMED -cp "bin;lib/*" com.centennial.engine.TransactionEngine
```

On macOS or Linux, use `:` instead of `;` in the classpath.

## Sample Output

```
Saved: Transaction TX-002 | $89.99 | Retail Savings
[pool-1-thread-2] done: TX-002
[pool-1-thread-2] rejected: Amount too large for TX-004
...
Rows in ledger: 5
```

Thread order varies between runs because the workers run in parallel.

## Notes

This is a learning project. The business rule is a simple amount limit, and the database is a local SQLite file (`ledger.db`, not committed).