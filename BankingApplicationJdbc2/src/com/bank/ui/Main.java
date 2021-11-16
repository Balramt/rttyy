package com.bank.ui;

import java.util.Scanner;

import com.bank.exception.UserException;
import com.bank.model.User;
import com.bank.service.BankService;
import com.bank.service.BankServiceImpl;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankService service = new BankServiceImpl();
		boolean flag = true;
		while (flag) {
			System.out.println("**********Welcome to Banking Application************");
			System.out.println("Enter 1.Create Account\n2.Deposite\n3.Get Balance\n4.Withdrawl\n5.FundTransfer"
					+ "\n6.PrintTransaction\n7.Exit");
			System.out.println("Enter yout choice: ");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				System.out.println("Enter Name");
				String name = sc.next();
				System.out.println("Enter Mobile Number");
				Long mobileNO = sc.nextLong();
				System.out.println("Enter Email Id");
				String emailId = sc.next();
				User user = new User(name, mobileNO, emailId);
				try {
					int accountNo = service.createAccount(user);
					System.out.println("Account is created with: " + accountNo);
				} catch (UserException e) {

					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("Enter Account No.");
				int accountNo = sc.nextInt();
				System.out.println("Enter Amount to be deposited");
				double amount = sc.nextDouble();
				try {
					boolean result = service.deposite(accountNo, amount);
					if (result) {
						System.out.println("Amount is deposited:" + amount);
					} else {
						System.out.println("Transaction Failed");
					}

				} catch (UserException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("Enter Account No.");
				int accNo = sc.nextInt();
				try {
					double bal = service.getBalance(accNo);
					System.out.println("Your account balance is :" + bal);
				} catch (UserException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Enter Account No.");
				int aNo = sc.nextInt();
				System.out.println("Enter amount to be withdrawl");
				double amnt = sc.nextDouble();
				try {
					boolean result = service.withdrawl(aNo, amnt);
					if (result) {
						System.out.println("Amount is withdrawl: " + amnt);
					} else {
						System.out.println("Transaction Failed");
					}
				} catch (UserException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				System.out.println("Enter Sender account No.");
				int senderAccountNo = sc.nextInt();
				System.out.println("Enter Reciever account No");
				int recieverAccountNo = sc.nextInt();
				System.out.println("Enter the amount to be transfer");
				double famount = sc.nextDouble();
				try {
					boolean result = service.fundTransfer(senderAccountNo, recieverAccountNo, famount);
					if (result) {
						System.out.println("Amount is transfer: " + famount);
					} else {
						System.out.println("Transaction Failed");
					}
				} catch (UserException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				System.out.println("Enter the acccount No.");
				int transAccountNo = sc.nextInt();
				try {
					System.out.println(service.printTransaction(transAccountNo));
				} catch (UserException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				flag = false;
				System.out.println("Exit");
				break;
			default:
				System.out.println("Invalid Choice ");
			}
		}
	}
}
