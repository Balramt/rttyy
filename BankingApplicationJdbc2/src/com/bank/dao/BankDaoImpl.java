package com.bank.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import com.bank.exception.UserException;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.utils.DatabaseUtils;







public class BankDaoImpl implements BankDao {
	
	private DatabaseUtils dbUtil = null;

	public BankDaoImpl() {
		dbUtil = DatabaseUtils.getInstance();
	}
	List<User> list = new ArrayList<User>();
	 int  accountNo = 1000;

	@Override
	public int createAccount(User user) throws UserException {
	
			
			Connection connection;
			try {
				connection = dbUtil.openDatabaseConnection();
				String query1 = "select max(aac_no) as accNo from user1";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query1);
				
				if(rs.next()){
					accountNo=rs.getInt("accNo");
					System.out.println(accountNo);
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(accountNo<10){
				accountNo+=1000;
			}
			
			

			
			try{
				connection = dbUtil.openDatabaseConnection();
			String query = "Insert into user1(aac_no, User_name, Mob_no, Email,Balance) values(?, ?, ?, ?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			user.setAccountNo(accountNo++);
			pstmt.setInt(1, accountNo);
			pstmt.setString(2, user.getUserName());
			pstmt.setLong(3, user.getMobileNo());
			pstmt.setString(4, user.getEmailId());
			pstmt.setDouble(5, user.getBalance());
			int result = pstmt.executeUpdate();
			
			dbUtil.colseDatabaseConnection();
			if (result >= 1) {
				return user.getAccountNo();
			}
		} catch (SQLException | ClassNotFoundException e) {
			
			System.out.println(e);
		}
			return 0;
	
		
		/*
		user.setAccountNo(accountNo++);
		list.add(user);
		return user.getAccountNo();*/
	}

	@Override
	public User readAccount(int accountNo) throws UserException {
		try{
			Connection conection = dbUtil.openDatabaseConnection();
			Statement stmt = conection.createStatement();
			String query = "Select * from user1 where aac_no = "+accountNo;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int account_No = rs.getInt("AAC_No");
				String user_name = rs.getString("USER_name");
				long user_mobile = rs.getLong("MOB_NO");
				String email = rs.getString("EMAIL");
				double balance = rs.getDouble("BALANCE");
				
				User user = new User(user_name, user_mobile, email);
				user.setAccountNo(account_No);
				user.setBalance(balance);
				if(user.getAccountNo() == accountNo){
			return user;
				}
			}
			}catch(SQLException | ClassNotFoundException e){
				System.out.println(e);
			}
		throw new UserException("Invalid Account number");
		
		
		
		
		
		/*Iterator<User> itr = list.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			if (user.getAccountNo() == accountNo) {
				return user;
			}
		}
		throw new UserException("Invalid Account number");*/
	}

	@Override
	public boolean deposite(int accountNo, double amount) throws UserException {
		User user = readAccount(accountNo);
		String recieverName = user.getUserName();
		String depositerId = Integer.toString(user.getAccountNo());
		double a = user.getBalance() + amount;
		try {
			Connection conection = dbUtil.openDatabaseConnection();
			String query = "update user1 set balance=? where aac_no=?";
			PreparedStatement pstmt = conection.prepareStatement(query);
			pstmt.setDouble(1, a);
			pstmt.setInt(2, accountNo);
			int result = pstmt.executeUpdate();
			dbUtil.colseDatabaseConnection();
			if(result !=0){
				addTransaction(amount, "Deposite", depositerId, "By Cash");
				return true;
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return false;
		
		/*user.setBalance(a);
		Transaction t = new Transaction(amount, "Deposite", depositerId, recieverName);
		user.addTransactionList(t);
		return true;*/
	}
	

	@Override
	public boolean withdrawl(int accountNo, double amount) throws UserException {
		User user = readAccount(accountNo);
		if (user.getBalance() >= amount) {
			String recieverId = Integer.toString(user.getAccountNo());
			String depositerName = user.getUserName();
			double a = user.getBalance() - amount;
			
			try {
				Connection conection = dbUtil.openDatabaseConnection();
				String query = "update user1 set balance=? where aac_no=?";
				PreparedStatement pstmt = conection.prepareStatement(query);
				pstmt.setDouble(1, a);
				pstmt.setInt(2, accountNo);
				int result = pstmt.executeUpdate();
				dbUtil.colseDatabaseConnection();
				if(result !=0){
					addTransaction(amount, "withdrawl", "Self", recieverId);
					return true;
				}
			} catch (ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			return false;
			
			
			
			
			
			
			
			/*user.setBalance(a);
			Transaction t = new Transaction(amount, "Deposite", depositerName, recieverId);
			user.addTransactionList(t);
			return true;*/
		} else {
			throw new UserException("Amount is lesser in your account");
		}
	}

	@Override
	public boolean fundTransfer(int senderAccountNo, int recieverAccountNo, double amount) throws UserException {
		User user = readAccount(senderAccountNo);
		User user1 = readAccount(recieverAccountNo);
		if (user.getBalance() >= amount) {
			double a = user.getBalance() - amount;
			//user.setBalance(a);
			String depositorId = Integer.toString(user.getAccountNo());
			double a1 = user1.getBalance() + amount;
			//user1.setBalance(a1);
			String recieverId = Integer.toString(user1.getAccountNo());
			
			try {
				Connection conection = dbUtil.openDatabaseConnection();
				String query = "update user1 set balance=? where aac_no=?";
				PreparedStatement pstmt = conection.prepareStatement(query);
				pstmt.setDouble(1, a);
				pstmt.setInt(2, senderAccountNo);
				int result = pstmt.executeUpdate();
				dbUtil.colseDatabaseConnection();
					addTransaction(amount, "FundTransfer", depositorId, recieverId);
					
				
			} catch (ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			
			
			try {
				Connection conection = dbUtil.openDatabaseConnection();
				String query = "update user1 set balance=? where aac_no=?";
				PreparedStatement pstmt = conection.prepareStatement(query);
				pstmt.setDouble(1, a1);
				pstmt.setInt(2, recieverAccountNo);
				int result = pstmt.executeUpdate();
				dbUtil.colseDatabaseConnection();
				
					addTransaction(amount, "Withdrawl", depositorId, recieverId);
					
				
			} catch (ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println(e);
				e.printStackTrace();
			}
			
			return true;
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*Transaction t1 = new Transaction(amount, "FundTransfer", depositorId, recieverId);
			user.addTransactionList(t1);
			user1.addTransactionList(t1);
			return true;*/
		} else {
			throw new UserException("Amount is lesser in Sender account");
		}
	}

	@Override
	public List<Transaction> getTransactionList(int accountNo) throws UserException {
		User user = readAccount(accountNo);
		
		try{
			List<Transaction> list = new ArrayList<>();
			Connection connection = dbUtil.openDatabaseConnection();
			
			Statement stmt = connection.createStatement();
			String query = "Select * from transaction1";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				int tid = rs.getInt("trscn_id");
				LocalDate tdate = rs.getDate("trscn_date").toLocalDate();
				double tamount = rs.getDouble("trscn_amount");
				String ttype = rs.getString("trscn_type");
				String tsender = rs.getString("senderid");
				String treciever = rs.getString("recieverid");
				
				
				Transaction t = new Transaction(tamount, ttype, tsender, treciever);
				t.setTransactionId(tid);
				t.setTransactionDate(tdate);
				list.add(t);
				
				
					
				}
			dbUtil.colseDatabaseConnection();
			return list;
				
			
		}catch(SQLException | ClassNotFoundException e){
		System.out.println(e);
		}
		return null;
		
		
		
		
		//return user.getTransactionList();
	}

	@Override
	public void addTransaction(double transactionAmount, String transactionType, String senderId, String recieverId) {
		
		
		Transaction t = new Transaction(transactionAmount, transactionType, senderId, recieverId);
		//long trscnId = t.getTransactionId();
		try {
			Connection connection = dbUtil.openDatabaseConnection();
			String query = "Insert into transaction1 (trscn_id, trscn_date, trscn_amount, trscn_type, senderId, recieverId) values(?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, t.getTransactionId());
			pstmt.setDate(2, Date.valueOf(t.getTransactionDate()));
			pstmt.setDouble(3, t.getTransactionAmount());
			pstmt.setString(4, t.getTransactionType());
			pstmt.setString(5, t.getSenderId());
			pstmt.setString(6, t.getRecieverId());
			 pstmt.executeUpdate();
			dbUtil.colseDatabaseConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
