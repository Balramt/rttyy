package com.bank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
	private static Connection connection;
	private static  DatabaseUtils object = new DatabaseUtils();
	private DatabaseUtils(){
		
	}
	
	public synchronized static DatabaseUtils getInstance(){
		return object;
	}
	
	public Connection openDatabaseConnection(String username, String password) throws SQLException , ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//connection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.10.2:1521:orcl", username, password);
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
		
		return connection;
	}
	public Connection openDatabaseConnection() throws SQLException , ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//connection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.10.2:1521:orcl", "seed25", "seed25");
		connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "seed");
		return connection;
	}
	public void colseDatabaseConnection() throws SQLException{
		
		if(connection != null ){
		connection.close();
		}
		
	} 

}
