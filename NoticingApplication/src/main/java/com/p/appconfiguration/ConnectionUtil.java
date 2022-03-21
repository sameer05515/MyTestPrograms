//package com.p.appconfiguration;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class ConnectionUtil {
//
//	static Connection con = null;
//	static Connection con1 = null;
//	static Connection con2 = null;
//	private static String url = PropertiesFile.getDsurl();
//	private static String driver = PropertiesFile.getDsdriverclassname();
//	private static String uname = PropertiesFile.getDsusername();
//	private static String upass = PropertiesFile.getDspassword();
//
//	private ConnectionUtil() {
//	};
//
//	public static Connection getConnection() {
//		String status = "";
//
//		try {
//			if (con == null) {
//				synchronized (ConnectionUtil.class) {
//					if (con == null) {
//
//						// String
//						// url="jdbc:oracle:thin:@172.25.219.102:1521:OTSUAT2";
//						/* Class.forName("oracle.jdbc.OracleDriver"); */
//						Class.forName(driver);
//						con = DriverManager.getConnection(url, uname, upass);
//						status = "new connection created!!";
//						Log.notice.info("===> new connection created");
//					}
//				}
//			} else {
//				status = "connection availabe!!";
//			}
//
//		} catch (Exception e) {
//			Log.notice.info("exception while creating connection , ConnectionUtil: " + e);
//
//		}
//		 Log.notice.info("PRINT: "+ status);
//
//		return con;
//	}
//
//	
//	//====
//	
//	public static Connection getConnectionCD() {
//		String status = "";
//
//		try {
//			if (con1 == null) {
//				synchronized (ConnectionUtil.class) {
//					if (con1 == null) {
//
//						// String
//						// url="jdbc:oracle:thin:@172.25.219.102:1521:OTSUAT2";
//						/* Class.forName("oracle.jdbc.OracleDriver"); */
//						Class.forName(driver);
//						con1 = DriverManager.getConnection(url, uname, upass);
//						status = "new connection created!!";
//						Log.notice.info("===> new connection created");
//					}
//				}
//			} else {
//				status = "connection availabe!!";
//			}
//
//		} catch (Exception e) {
//			Log.notice.info("exception while creating connection , ConnectionUtil: " + e);
//
//		}
//		 Log.notice.info("PRINT: "+ status);
//
//		return con1;
//	}
//	
//	//===
//	
//	
//	
//	public static Connection getConnectionFO() {
//		String status = "";
//
//		try {
//			if (con2 == null) {
//				synchronized (ConnectionUtil.class) {
//					if (con2 == null) {
//
//						// String
//						// url="jdbc:oracle:thin:@172.25.219.102:1521:OTSUAT2";
//						/* Class.forName("oracle.jdbc.OracleDriver"); */
//						Class.forName(driver);
//						con2 = DriverManager.getConnection(url, uname, upass);
//						status = "new connection created!!";
//						Log.notice.info("===> new connection created");
//					}
//				}
//			} else {
//				status = "connection availabe!!";
//			}
//
//		} catch (Exception e) {
//			Log.notice.info("exception while creating connection , ConnectionUtil: " + e);
//
//		}
//		 Log.notice.info("PRINT: "+ status);
//
//		return con2;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	// -----temp
////	private static Connection getConnectionfor_mainonly() {
////		String status = "";
////
////		try {
////			if (con == null) {
////				synchronized (ConnectionUtil.class) {
////					if (con == null) {
////
////						String url = "jdbc:oracle:thin:@172.25.219.102:1521:OTSUAT2";
////						Class.forName("oracle.jdbc.OracleDriver");
////						con = DriverManager.getConnection(url, "mumbai", "mumbai");
////
////						status = "new connection created!!";
////						Log.notice.info("===> new connection created");
////					}
////				}
////			} else {
////				status = "connection availabe!!";
////			}
////
////		} catch (Exception e) {
////			Log.notice.info("exception while creating connection , ConnectionUtil: " + e);
////
////		}
////		// Log.notice.info("PRINT: "+ status);
////
////		return con;
////	}
//
//}
//
////class Mk {
////
////	public void insertCMData(String data) {
////
////		try {
////			Connection conn = ConnectionUtil.getConnection();
////			// String s="kk"+","+"ddddd";
////
////			/* String array[] = {"one", "two", "three","four"}; */
////			Log.notice.info("=>" + data);
////			String array[] = { data };
////
////			ArrayDescriptor des = ArrayDescriptor.createDescriptor("CM_TRADE_NOTIS_TAB", conn);
////			ARRAY array_to_pass = new ARRAY(des, conn, array);
////
////			CallableStatement st = conn.prepareCall("call pkg_notis.cm_trade(?,?,?)");
////
////			// Passing an array to the procedure -
////			st.setArray(1, array_to_pass);
////
////			st.registerOutParameter(2, Types.INTEGER);
////
////			st.registerOutParameter(3, OracleTypes.ARRAY, "SchemaName.ARRAY_INT");
////
////			st.execute();
////
////			Log.notice.info("size : " + st.getInt(2));
////
////			// Retrieving array from the resultset of the procedure after
////			// execution -
////			ARRAY arr = ((OracleCallableStatement) st).getARRAY(3);
////			BigDecimal[] recievedArray = (BigDecimal[]) (arr.getArray());
////
////			for (int i = 0; i < recievedArray.length; i++)
////				Log.notice.info("element" + i + ":" + recievedArray[i] + "\n");
////		} catch (Exception e) {
////			Log.notice.info(e);
////		}
////
////	}
////
////	// -----------
////
////	public void DataInserted(String data) throws Exception {
////
////		Connection conn = ConnectionUtil.getConnection();
////
////		// OracleConnection conn = (OracleConnection) con;
////
////		Log.notice.info("Got Connection.");
////
////		OracleCallableStatement callStmt = null;
////
////		try {
////
////			Object[] project1 = new Object[] { "null", "null", "null", "null", "null", "null", "null", "null", "null",
////					"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null",
////					"null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null",
////					"null", "null", "null", "null", "null", "null", "null" };
////			// Object[] project1 = new Object[] {data};
////
////			StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("CM_TRADE_NOTIS_RECORD", conn);
////
////			STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
////
////			// each struct is one ProjectType object
////			// Struct structProject1 = conn.createStruct("CM_TRADE_NOTIS_TAB",
////			// project1);
////			// Struct structProject2 = conn.createStruct("PROJECT_TYPE",
////			// project2);
////
////			Struct[] structArrayOfProjects = { structProject1 };
////
////			ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("CM_TRADE_NOTIS_RECORD", conn);
////
////			ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);
////
////			callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cm_trade(?,?,?)}");
////
////			String[] result = new String[2];
////			// Passing an array to the procedure -
////			// callStmt.setArray(1, array_to_pass);
////
////			callStmt.setArray(1, arrayOfProjects);
////
////			callStmt.registerOutParameter(2, Types.VARCHAR);
////			callStmt.registerOutParameter(3, Types.VARCHAR);
////			callStmt.execute();
////
////			result[0] = callStmt.getString(2);
////			result[1] = callStmt.getString(3);
////
////			// callStmt.execute();
////			conn.commit();
////
////			Log.notice.info("Committed.");
////		} catch (Exception e) {
////			Log.notice.info(e);
////			// if (conn != null) try { conn.rollback(); } catch (Exception ex) {
////			// Log.notice.info("Rollback failed."); }
////			throw e;
////		} finally {
////			callStmt.close();
////			conn.close();
////		}
////
////	}
////
////	
////	//----
////	
////	
////	//------
////	
////	
////	
////	
////	
////	
////	
////	
////	
////	
////	
////	
////	public static void main(String[] args) {
////
////	}
////
////}
