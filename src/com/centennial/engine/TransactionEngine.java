package com.centennial.engine;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionEngine {

    public static void main(String[] args) throws Exception {
        DatabaseService db = new DatabaseService();
        db.createTable();
        ITransactionProcessor processor = new SimpleProcessor(db);

        // A pool of 3 worker threads that will share the work.
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // A small batch of transactions. TX-004 is over the limit.
        List<Transaction> batch = List.of(
            new Transaction("TX-001", 1250.45, "Corporate Business"),
            new Transaction("TX-002", 89.99, "Retail Savings"),
            new Transaction("TX-003", 4300.00, "Investment Premium"),
            new Transaction("TX-004", 9000.00, "Investment Premium"),
            new Transaction("TX-005", 15.50, "Retail Savings"),
            new Transaction("TX-006", 760.00, "Corporate Business")
        );

        // Give each transaction to the pool as its own task.
        for (Transaction t : batch) {
            pool.execute(() -> {
                String worker = Thread.currentThread().getName();
                try {
                    processor.process(t);
                    System.out.println("[" + worker + "] done: " + t.getId());
                } catch (InsufficientFundsException e) {
                    System.out.println("[" + worker + "] rejected: " + e.getMessage());
                }
            });
        }

        // Stop taking new tasks, then wait for the running ones to finish.
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Rows in ledger: " + db.countRows());
    }
}