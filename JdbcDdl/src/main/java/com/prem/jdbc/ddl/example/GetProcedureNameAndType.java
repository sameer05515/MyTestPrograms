package com.prem.jdbc.ddl.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetProcedureNameAndType {

	public static void main(String[] args) throws Exception {
		Connection conn = getMySqlConnection();
		System.out.println("Got Connection.");
		Statement st = conn.createStatement();
//		st.executeUpdate("drop table survey;");
//		st.executeUpdate("create table survey (id int,name varchar(30));");
//		st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");

		ResultSet rs = null;
		DatabaseMetaData dbMetaData = conn.getMetaData();
		rs = dbMetaData.getProcedures(null, null, "%");

		while (rs.next()) {
			String spName = rs.getString("PROCEDURE_NAME");
			int spType = rs.getInt("PROCEDURE_TYPE");
			System.out.println("Stored Procedure Name: " + spName +"  PROCEDURE_TYPE:  "+spType);
			if (spType == DatabaseMetaData.procedureReturnsResult) {
				System.out.println("procedure Returns Result");
			} else if (spType == DatabaseMetaData.procedureNoResult) {
				System.out.println("procedure No Result");
			} else {
				System.out.println("procedure Result unknown");
			}

		}

		st.close();
		conn.close();
	}

	private static Connection getHSQLConnection() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		System.out.println("Driver Loaded.");
		String url = "jdbc:hsqldb:data/tutorial";
		return DriverManager.getConnection(url, "sa", "");
	}

	public static Connection getMySqlConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/interview_mgmt?useLegacyDatetimeCode=false&serverTimezone=IST";
		String username = "root";
		String password = "";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public static Connection getOracleConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:caspian";
		String username = "mp";
		String password = "mp2";

		Class.forName(driver); // load Oracle driver
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

}
