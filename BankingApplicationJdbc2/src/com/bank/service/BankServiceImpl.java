package com.bank.service;

import java.util.List;

import com.bank.dao.BankDao;
import com.bank.dao.BankDaoImpl;
import com.bank.exception.UserException;
import com.bank.model.Transaction;
import com.bank.model.User;

public class BankServiceImpl implements BankService {
	BankDao dao = new BankDaoImpl();
	@Override
	public int createAccount(User user) throws UserException {
		int accountNo = dao.createAccount(user);
		return accountNo;
	}

	@Override
	public boolean deposite(int accountNo, double amount) throws UserException {
		if(amount >0) {
			boolean result = dao.deposite(accountNo, amount);
			return result;
		}
		else {
			throw new UserException("amount can not be negative");
		}
	}

	@Override
	public boolean withdrawl(int accountNo, double amount) throws UserException {
		if(amount >0) {
			boolean result = dao.withdrawl(accountNo, amount);
			return result;
		}
		else {
			throw new UserException("Amount must be positive");
		}
	}

	@Override
	public boolean fundTransfer(int senderAccountNo, int recieverAccountNo, double amount) throws UserException {
		if(amount >0) {
			boolean result = dao.fundTransfer(senderAccountNo, recieverAccountNo, amount);
			return result;
		}
		else {
			throw new UserException("Please Enter Valid amount");
		}
	}

	@Override
	public List<Transaction> printTransaction(int accountNo) throws UserException  {
		List<Transaction> list = dao.getTransactionList(accountNo);
		return list;
	}

	@Override
	public double getBalance(int accountNo) throws UserException {
		User user = dao.readAccount(accountNo);
		
		return user.getBalance();
	}

	@Override
	public boolean IsAlpha(String str) {
		return str != null && str.chars().allMatch(Character:: isLetter); 
	}

	@Override
	public boolean IsNumber(int accountNo) {
		
		return false;
	}
	

}
