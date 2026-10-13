package com.centennial.engine;

public class Transaction {
	
	// Each transaction has these three pieces of information.
	private String id;
	private double amount;
	private String accountType;
	
	// The constructor sets the values when we create a new Transaction.
	public Transaction(String id, double amount, String accountType) {
		this.id = id;
		this.amount = amount;
		this.accountType = accountType;
	}
	
	// Getters let other classes read the values.
	public String getId() {
		return id;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
	// toString decides how a Transaction looks when we print it.
	@Override
	public String toString() {
		return "Transaction " + id + " | $" + amount + " | " + accountType;
	}

}
