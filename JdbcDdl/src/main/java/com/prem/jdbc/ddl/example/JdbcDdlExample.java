package com.prem.jdbc.ddl.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class JdbcDdlExample implements DbQueryConstants {

	// JDBC Driver Name & Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306";

	// JDBC Database Credentials
	static final String JDBC_USER = "root";
	static final String JDBC_PASS = "";

	public final static Logger logger = Logger.getLogger(JdbcDdlExample.class);

	public static void main(String[] args) throws Exception {

		Connection connObj = null;
		Statement stmtOBj = null;
		Savepoint savepoint1 = null;
		try {
			Class.forName(JDBC_DRIVER);
			connObj = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);

			//connObj.setAutoCommit(false);
			//System.out.println("connObj.getAutoCommit() ==>>"+connObj.getAutoCommit());
			
			stmtOBj = connObj.createStatement();
			stmtOBj.executeUpdate("SET autocommit=0;");
			stmtOBj.executeUpdate("START TRANSACTION;");
			
			
			//set a Savepoint
			savepoint1 = connObj.setSavepoint("Savepoint1");

			// DDL Statement 1 - Create Database Schema!
			logger.info("\n=======CREATE " + DATABASE_NAME + " DATABASE=======");			
			stmtOBj.executeUpdate(CREATE_DATABASE_QUERY);
			logger.info("\n=======DATABASE IS SUCCESSFULLY CREATED=======\n");

			logger.info("\n=======USING " + DATABASE_NAME + " DATABASE=======\n");
			stmtOBj.executeUpdate(USE_DATABASE_QUERY);

			// DDL Statement 2 - Create Table!
			logger.info("\n=======CREATE " + TABLE_NAME + " TABLE=======");			
			stmtOBj.executeUpdate(CREATE_TABLE_QUERY);
			logger.info("\n=======TABLE IS SUCCESSFULLY CREATED=======\n");

			logger.info("\n=======SHOW TABLE STRUCTURE=======");
			showDbTableStructure();

			// DDL Statement 3(a) - Alter Table Column!
			logger.info("\n=======ALTER " + TABLE_NAME + " TABLE=======");
			stmtOBj.executeUpdate(ALTER_TABLE_QUERY);
			logger.info("\n=======TABLE IS SUCCESSFULLY ALTERED=======\n");

			logger.info("\n=======SHOW TABLE STRUCTURE=======");
			showDbTableStructure();

			// DDL Statement 3(b) - Alter Table Column Using After Clause!
			logger.info("\n=======ALTER " + TABLE_NAME + " TABLE WITH AFTER CLAUSE=======");
			stmtOBj.executeUpdate(ALTER_TABLE_WITH_AFTER_CLAUSE_QUERY);
			logger.info("\n=======TABLE IS SUCCESSFULLY ALTERED=======\n");

			logger.info("\n=======SHOW TABLE STRUCTURE=======");
			showDbTableStructure();			

			// DDL Statement 4(a) - Drop Table Column!
			logger.info("\n=======DROP COLUMN=======");
			stmtOBj.executeUpdate(DROP_COLUMN);
			logger.info("\n=======COLUMN IS SUCCESSFULLY DROPPED FROM THE TABLE=======\n");

			logger.info("\n=======SHOW TABLE STRUCTURE=======");
			showDbTableStructure();	
			System.out.println(0/0);
			
//			connObj.commit();
			
			stmtOBj.executeUpdate("COMMIT;");
			stmtOBj.executeUpdate("SET autocommit = 1;");

			// DDL Statement 4(b) - Drop Table!
			//logger.info("\n=======DROP TABLE=======");
			//stmtOBj.executeUpdate(DROP_TABLE);
			//logger.info("\n=======TABLE IS SUCCESSFULLY DROPPED FROM THE DATABASE=======\n");

			// DDL Statement 4(c) - Drop Database!
			//logger.info("\n=======DROP DATABASE=======");
			//stmtOBj.executeUpdate(DROP_DATABASE);
			//logger.info("\n=======DATABASE IS SUCCESSFULLY DROPPED=======");
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
			if(connObj != null) {
				//connObj.rollback(savepoint1);
				//connObj.close();	// Close Connection Object
			}
		} finally {
			try {
				if(stmtOBj != null) {
					stmtOBj.close();	// Close Statement Object
				}
				if(connObj != null) {
					//connObj.rollback();
					connObj.close();	// Close Connection Object
				}
			} catch (Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
	}

	// This Method Is Used To Print The Table Structure
	private static void showDbTableStructure() throws SQLException {
		StringBuilder builderObj = new StringBuilder();
		DatabaseMetaData metaObj = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS).getMetaData();
		ResultSet resultSetObj = metaObj.getColumns(DATABASE_NAME, null, TABLE_NAME, "%");

		builderObj.append(TABLE_NAME + " Columns Are?= (");
		while (resultSetObj.next()) {
			String columnName = resultSetObj.getString(4);
			builderObj.append(columnName).append(", ");
		}
		builderObj.deleteCharAt(builderObj.lastIndexOf(",")).deleteCharAt(builderObj.lastIndexOf(" ")).append(")").append("\n");
		logger.info(builderObj.toString());
	}
}