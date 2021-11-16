package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class User{
	private int accountNo;
	private String userName;
	private Long mobileNo;
	private String emailId;
	private double balance =1000 ;
	private List<Transaction> transactionList = new ArrayList<Transaction>();
	public User(String userName, Long mobileNo, String emailId) {
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
	}
	

	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "User [accountNo=" + accountNo + ", userName=" + userName + ", mobileNo=" + mobileNo + ", emailId="
				+ emailId + ", balance=" + balance + "]";
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void addTransactionList(Transaction transaction) {
		transactionList.add(transaction); 
	}

	
	
	


}
