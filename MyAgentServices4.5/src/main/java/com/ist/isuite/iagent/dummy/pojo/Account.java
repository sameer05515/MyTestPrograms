package com.ist.isuite.iagent.dummy.pojo;

import java.sql.Date;

public class Account {

	private String accountNo;
	private String customerId;
	private Double accountBalance;
	private String accountType;
	private Date lastTransactionDate;
	private String lastTransactionType;
	private Double lastTransactionAmount;
    private String currency;
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public String getLastTransactionType() {
		return lastTransactionType;
	}

	public void setLastTransactionType(String lastTransactionType) {
		this.lastTransactionType = lastTransactionType;
	}

	public Double getLastTransactionAmount() {
		return lastTransactionAmount;
	}

	public void setLastTransactionAmount(Double lastTransactionAmount) {
		this.lastTransactionAmount = lastTransactionAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
