package com.ist.isuite.iagent.dummy.pojo;

import java.sql.Date;

public class Card {

	private String cardNumber;
	private String customerId;
	private String cardType;
	private Double lastTransactionAmount;
	private String accountNumber;
	private Date lastTransactionDate;
	private String lastTransactionType;
	private String cardStatus;
	 private String currency;
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Double getLastTransactionAmount() {
		return lastTransactionAmount;
	}

	public void setLastTransactionAmount(Double lastTransactionAmount) {
		this.lastTransactionAmount = lastTransactionAmount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
