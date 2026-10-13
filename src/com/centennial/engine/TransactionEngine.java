package com.centennial.engine;

public class TransactionEngine {

    public static void main(String[] args) {
        ITransactionProcessor processor = new SimpleProcessor();

        Transaction ok = new Transaction("TX-001", 1250.45, "Corporate Business");
        Transaction big = new Transaction("TX-002", 9000.00, "Investment Premium");

        try {
            processor.process(ok);
            processor.process(big); // this one should be rejected
        } catch (InsufficientFundsException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
