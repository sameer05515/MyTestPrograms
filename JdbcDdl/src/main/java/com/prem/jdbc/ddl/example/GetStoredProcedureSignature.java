package com.prem.jdbc.ddl.example;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetStoredProcedureSignature {
  public static void main(String[] args) throws Exception {
    Connection conn = getMySqlConnection();
    System.out.println("Got Connection.");
    Statement st = conn.createStatement();
//    st.executeUpdate("drop table survey;");
//    st.executeUpdate("create table survey (id int,name varchar(30));");
//    st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");

    DatabaseMetaData dbMetaData = conn.getMetaData();
    ResultSet rs = dbMetaData.getProcedureColumns(conn.getCatalog(),
                          null,
                          "GetCategoryQuestionWithAnswer",
                          null);
    
    System.out.println("==>>> "+conn.getCatalog());

    while(rs.next()) {
      // get stored procedure metadata
    	System.out.println("=================================");
      String procedureCatalog     = rs.getString(1);
      String procedureSchema      = rs.getString(2);
      String procedureName        = rs.getString(3);
      String columnName           = rs.getString(4);
      short  columnReturn         = rs.getShort(5);
      int    columnDataType       = rs.getInt(6);
      String columnReturnTypeName = rs.getString(7);
      int    columnPrecision      = rs.getInt(8);
      int    columnByteLength     = rs.getInt(9);
      short  columnScale          = rs.getShort(10);
      short  columnRadix          = rs.getShort(11);
      short  columnNullable       = rs.getShort(12);
      String columnRemarks        = rs.getString(13);

      System.out.println("stored Procedure name="+procedureName);
      System.out.println("procedureCatalog=" + procedureCatalog);
      System.out.println("procedureSchema=" + procedureSchema);
      System.out.println("procedureName=" + procedureName);
      System.out.println("columnName=" + columnName);
      System.out.println("columnReturn=" + columnReturn);
      System.out.println("columnDataType=" + columnDataType);
      System.out.println("columnReturnTypeName=" + columnReturnTypeName);
      System.out.println("columnPrecision=" + columnPrecision);
      System.out.println("columnByteLength=" + columnByteLength);
      System.out.println("columnScale=" + columnScale);
      System.out.println("columnRadix=" + columnRadix);
      System.out.println("columnNullable=" + columnNullable);
      System.out.println("columnRemarks=" + columnRemarks);
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