package com.p.db.migration;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.hibernate.internal.SessionImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DBDetails {

	public static void main(String[] args) {
		Connection jdbcConnection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/topic-mgmt?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			
			DBDetails objDBDetails=new DBDetails();
			String databaseTables=objDBDetails.getDatabaseTables(jdbcConnection);
			
			System.out.println(databaseTables);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}
	
	
//	@Transactional
//	@RequestMapping(value = { "/getDatabaseTables" }, method = RequestMethod.GET)
	public String getDatabaseTables(Connection con) throws Exception{ 

//	    Connection con = ((SessionImpl) sessionFactory.getCurrentSession()).connection();
	    DatabaseMetaData md = con.getMetaData();
	    ResultSet rs = md.getTables(null, null, "%", null);
	    HashMap<String,List<String>> databaseTables = new HashMap<String,List<String>>();
	    List<String> tables = new ArrayList<String>();
	    String db = "";
	    while (rs.next()) {
	        tables.add(rs.getString(3));
	        db = rs.getString(1);
	    }
	    List<String> database = new ArrayList<String>();
	    database.add(db);
	    databaseTables.put("database", database);
	    Collections.reverse(tables);
	    databaseTables.put("tables", tables);
	    return new ObjectMapper().writeValueAsString(databaseTables);
	}


//	public @ResponseBody String getTableDetails(Connection con,String tablename) throws Exception{ 
//		
//	    System.out.println("...tablename......"+tablename);
////	    Connection con = ((SessionImpl) sessionFactory.getCurrentSession()).connection();       
//	     Statement st = con.createStatement();
//	     String sql = "select * from "+tablename;
//	     ResultSet rs = st.executeQuery(sql);
//	     ResultSetMetaData metaData = rs.getMetaData();
//	     int rowCount = metaData.getColumnCount();    
//	     List<HashMap<String,String>> databaseColumns = new ArrayList<HashMap<String,String>>();
//	     HashMap<String,String> columnDetails = new HashMap<String,String>();
//	     for (int i = 0; i < rowCount; i++) {
//	         columnDetails = new HashMap<String,String>();
//	         Method method = com.mysql.cj.jdbc.ResultSetMetaData.class.getDeclaredMethod("getField", int.class);
//	         method.setAccessible(true);
//	         com.mysql.cj.jdbc.Field field = (com.mysql.cj.jdbc.Field) method.invoke(metaData, i+1);
//	         columnDetails.put("columnName", field.getName());//metaData.getColumnName(i + 1));
//	         columnDetails.put("columnType", metaData.getColumnTypeName(i + 1));
//	         columnDetails.put("columnSize", field.getLength()+"");//metaData.getColumnDisplaySize(i + 1)+"");
//	         columnDetails.put("columnColl", field.getCollation());
//	         columnDetails.put("columnNull", ((metaData.isNullable(i + 1)==0)?"NO":"YES"));
//	         if (field.isPrimaryKey()) {
//	             columnDetails.put("columnKEY", "PRI");
//	         } else if(field.isMultipleKey()) {
//	             columnDetails.put("columnKEY", "MUL");
//	         } else if(field.isUniqueKey()) {
//	             columnDetails.put("columnKEY", "UNI");
//	         } else {
//	             columnDetails.put("columnKEY", "");
//	         }
//	         columnDetails.put("columnAINC", (field.isAutoIncrement()?"AUTO_INC":""));
//	         databaseColumns.add(columnDetails);
//	     }
//	    HashMap<String,List<HashMap<String,String>>> tableColumns = new HashMap<String,List<HashMap<String,String>>>();
//	    Collections.reverse(databaseColumns);
//	    tableColumns.put("columns", databaseColumns);
//	    return new ObjectMapper().writeValueAsString(tableColumns);
//	}

}
