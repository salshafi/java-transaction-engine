package com.centennial.engine;

// Implements the interface with a simple business rule.
public class SimpleProcessor implements ITransactionProcessor {

    private static final double MAX_AMOUNT = 5000.00;

    @Override
    public void process(Transaction transaction) throws InsufficientFundsException {
        // Reject transactions above the limit.
        if (transaction.getAmount() > MAX_AMOUNT) {
            throw new InsufficientFundsException(
                "Amount too large for " + transaction.getId());
        }
        System.out.println("Processed: " + transaction);
    }
}