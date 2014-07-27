package org.ngi.db;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.ngi.property.ReadConfig;


public class DatabaseConnection {
	
	private static DatabaseConnection dbConnection;
	private Connection conn;
	private String hostName;
	
	private String mySqlDriver="";
	private String mySqlDbUrl="";
	private String mySqlUserName="";
	private String mySqlPassword="";
	
	
	/**
	 * 
	 * @param propertyFilePath
	 */
	private DatabaseConnection(String propertyFilePath){
		
		ReadConfig config = ReadConfig.getInstance(propertyFilePath);
		mySqlDriver = config.ReadValue("MySqlDriver");
		mySqlDbUrl = config.ReadValue("MySqlDbUrl");
		mySqlUserName = config.ReadValue("MySqlUserName");
		mySqlPassword = config.ReadValue("MySqlPassword");
		this.hostName = getHostName();
		createDBConnection();
	}
	
	
	/**
	 * This method will create 
	 * a database connection.
	 */
	private void createDBConnection() {
		try {
			Class.forName(mySqlDriver);
			this.conn = DriverManager.getConnection(mySqlDbUrl, mySqlUserName, mySqlPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static DatabaseConnection getInstance(String propertyFilePath){
		if(dbConnection==null){
			dbConnection = new DatabaseConnection(propertyFilePath);
		}
		
		return dbConnection;
	}
	
	
	/**
	 * This method will return 
	 * Host Name of the server.
	 * 
	 * @return hostName
	 */
	private String getHostName(){
		String hostName = null;
		try{
			InetAddress Addr = InetAddress.getLocalHost();
			hostName = Addr.getHostName();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return hostName;
	}
	
	
	public int executeUpdate(String query) throws Exception{
		Statement Stat = this.conn.createStatement();
		return Stat.executeUpdate(query);
	}
	
	
	
	
}
