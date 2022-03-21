package com.p.db.migration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) throws Exception {
		DatabaseMetaData dbmd = conn.getMetaData();
		
		
		///////////
		System.out.println("////////////////////////////////////////////////////////////////////");
		System.out.println("Driver Name: "+dbmd.getDriverName());  
		System.out.println("Driver Version: "+dbmd.getDriverVersion());  
		System.out.println("UserName: "+dbmd.getUserName());  
		System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());  
		System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());  
		System.out.println("////////////////////////////////////////////////////////////////////");
		/////////////
		
		ResultSet rs = dbmd.getTables(null, null, "%", null);
		while (rs.next()) {
			System.out.println(rs.getString(3));
		}
	}

	static Connection conn;

	static Statement st;

	static {
		try {
			// Step 1: Load the JDBC driver.
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded.");
			// Step 2: Establish the connection to the database.
			//String url = "jdbc:hsqldb:data/tutorial";

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/topic-mgmt?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
			System.out.println("Got Connection.");

			st = conn.createStatement();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			e.printStackTrace();
//			System.exit(0);
		}
	}
}