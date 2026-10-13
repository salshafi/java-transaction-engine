package com.centennial.engine;

import java.sql.SQLException;

// Implements the interface: checks a rule, then saves to the database.
public class SimpleProcessor implements ITransactionProcessor {

    private static final double MAX_AMOUNT = 5000.00;
    private final DatabaseService database;

    public SimpleProcessor(DatabaseService database) {
        this.database = database;
    }

    @Override
    public void process(Transaction transaction) throws InsufficientFundsException {
        // Reject transactions above the limit.
        if (transaction.getAmount() > MAX_AMOUNT) {
            throw new InsufficientFundsException(
                "Amount too large for " + transaction.getId());
        }
        try {
            database.save(transaction);
            System.out.println("Saved: " + transaction);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}