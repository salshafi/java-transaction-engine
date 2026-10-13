package com.centennial.engine;

public class TransactionEngine {

    public static void main(String[] args) throws Exception {
        DatabaseService db = new DatabaseService();
        db.createTable();
        ITransactionProcessor processor = new SimpleProcessor(db);

        Transaction ok = new Transaction("TX-001", 1250.45, "Corporate Business");
        Transaction big = new Transaction("TX-002", 9000.00, "Investment Premium");

        try {
            processor.process(ok);
            processor.process(big); // rejected by the rule
        } catch (InsufficientFundsException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        System.out.println("Rows in ledger: " + db.countRows());
    }
    }

