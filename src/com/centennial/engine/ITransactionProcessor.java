package com.centennial.engine;

// An interface is a contract: any class that implements it
// must provide these methods.
public interface ITransactionProcessor {
	
	// Accept a transaction and process it.
	 void process(Transaction transaction) throws InsufficientFundsException;
	
	

}
