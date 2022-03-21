package com.p.appconfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Struct;
import java.sql.Types;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.p.entity.NoticeLogging;
import com.p.hiber.HibernateUtil;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class DaoUtil {

	int cmcount = 1;
	int cdcount = 1;
	int focount = 1;
	int cmseqresult = 0;
	int cdseqresult = 0;
	int foseqresult = 0;

//	@Deprecated
//	private void insertCM(String[] data) /* throws Exception */
//	{
//
//		Connection conn = ConnectionUtil.getConnection();
//
//		OracleCallableStatement callStmt = null;
//		try {
//			Object[] project1 = null;
//			project1 = data;
//			StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("CM_TRADE_NOTIS_RECORD", conn);
//			STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
//			Struct[] structArrayOfProjects = { structProject1 };
//			ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("CM_TRADE_NOTIS_TAB", conn);
//			ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);
//			callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cm_trade(?,?,?)}");
//
//			String[] result = new String[2];
//			callStmt.setArray(1, arrayOfProjects);
//
//			callStmt.registerOutParameter(2, Types.VARCHAR);
//			callStmt.registerOutParameter(3, Types.VARCHAR);
//			callStmt.execute();
//
//			result[0] = callStmt.getString(2);
//			result[1] = callStmt.getString(3);
//			if (!result[0].equalsIgnoreCase("S")) {
//				// Log.notice.info("failure to logging current data:==> "+ result[1]);
//				Log.notice.info("failure to logging current data:==>  " + result[1]);
//			}
//			// callStmt.execute();
//			conn.commit();
//
//			// Log.notice.info("Committed.");
//		} catch (Exception e) {
//
//			/*
//			 * Log.notice.info("exception occured while logging data:=> "+ e.getMessage()
//			 * +" |ok.");
//			 */
//
//			try {
//				if (e != null) {
//					String ex = e.toString();
//					if (ex.contains("maximum open cursors exceeded")) {
//
//						Log.notice.info("exception occured: maximum open cursors exceeded| this.cmmaxcount-"
//								+ this.cmcount + "- , SO connection trying to re-istablish connection");
//						ConnectionUtil.con = null;
//						Log.notice.info("Connection set to null, Going to insert data");
//						if (this.cmcount < 500) {
//							insertCM(data);
//						} else {
//							throw new RuntimeException();
//						}
//						cmcount++;
//
//					} else {
//						Log.notice.info("exception occured while logging data:=> " + e.getMessage() + " |ok.");
//					}
//
//				}
//			} catch (Exception ee) {
//				Log.notice.info("maximum open cursors exceeded again n again");
//
//			}
//
//		} finally {
//			/*
//			 * callStmt.close(); conn.close();
//			 */
//		}
//
//	}

	// ----------------
	// cd
	// ---------------

	public void insertCDexp(String[] data, Session session) /* throws Exception */
	{

		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) {

				OracleCallableStatement callStmt = null;
				try {
					Object[] project1 = null;
					project1 = data;
					StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("CD_TRADE_NOTIS_RECORD", conn);
					STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
					Struct[] structArrayOfProjects = { structProject1 };
					ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("CD_TRADE_NOTIS_TAB", conn);
					ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);

					
//					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cd_trade(?,?,?)}");
					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cd_trade_new(?,?,?)}");

					String[] result = new String[2];
					callStmt.setArray(1, arrayOfProjects);

					callStmt.registerOutParameter(2, Types.VARCHAR);
					callStmt.registerOutParameter(3, Types.VARCHAR);
					callStmt.execute();

					result[0] = callStmt.getString(2);
					result[1] = callStmt.getString(3);
					if (!result[0].equalsIgnoreCase("S")) {
						// Log.notice.info("failure to logging current data:==> "+ result[1]);
						Log.notice.info("failure to logging current data:==>  " + result[1]);
					}
					// callStmt.execute();
					// conn.commit();

					// Log.notice.info("Committed.");
					callStmt.close();
				} catch (Exception e) {
					// Log.notice.info("exception occured during pushing cd data: "+ e);
					Log.notice.info("exception occured during pushing cd data: ", e);
					// Log.notice.info(
					// "*******************System.exit(0) called IGNORING
					// LOOPING**********************************");
					// System.exit(0);

				}

			}
		});

	}

	/*
	 * public void insertCD(String [] data) {
	 * 
	 * 
	 * Connection conn = ConnectionUtil.getConnectionCD();
	 * 
	 * OracleCallableStatement callStmt = null; try { Object[] project1 =null;
	 * project1=data; StructDescriptor projectTypeDesc =
	 * StructDescriptor.createDescriptor("CD_TRADE_NOTIS_RECORD", conn); STRUCT
	 * structProject1 = new STRUCT(projectTypeDesc, conn, project1); Struct[]
	 * structArrayOfProjects = {structProject1}; ArrayDescriptor
	 * projectTypeArrayDesc = ArrayDescriptor.createDescriptor("CD_TRADE_NOTIS_TAB",
	 * conn); ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn,
	 * structArrayOfProjects); callStmt =
	 * (OracleCallableStatement)conn.prepareCall("{call pkg_notis.cm_trade(?,?,?)}"
	 * );
	 * 
	 * String[] result = new String[2]; callStmt.setArray(1, arrayOfProjects);
	 * 
	 * callStmt.registerOutParameter(2, Types.VARCHAR);
	 * callStmt.registerOutParameter(3, Types.VARCHAR); callStmt.execute();
	 * 
	 * result[0] = callStmt.getString(2); result[1] = callStmt.getString(3);
	 * if(!result[0].equalsIgnoreCase("S")) {
	 * Log.notice.info("failure to logging current data:==>  "+ result[1]); }
	 * 
	 * conn.commit();
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * try{ if(e!=null) { String ex=e.toString();
	 * if(ex.contains("maximum open cursors exceeded")) {
	 * 
	 * 
	 * Log.notice.info("exception occured: maximum open cursors exceeded -" +
	 * this.cdcount +"- , SO connection trying to re-istablish connection");
	 * ConnectionUtil.con1=null;
	 * Log.notice.info("Connection set to null, Going to insert data");
	 * if(this.cdcount<500) { insertCD(data); }else{ throw new RuntimeException(); }
	 * this.cdcount++;
	 * 
	 * }else{ Log.notice.info("exception occured while logging data:=> "+
	 * e.getMessage() +" |ok."); }
	 * 
	 * 
	 * } }catch(Exception ee ){
	 * Log.notice.info("maximum open cursors exceeded again n again");
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * } }
	 */

	// inser fo start

	public void insertFOexp(String[] data, Session session) /* throws Exception */
	{

		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) {
				// Connection conn = ConnectionUtil.getConnectionFO();
				OracleCallableStatement callStmt = null;
				try {
					Object[] project1 = null;
					project1 = data;
					StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("FO_TRADE_NOTIS_RECORD", conn);
					STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
					Struct[] structArrayOfProjects = { structProject1 };
					ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("FO_TRADE_NOTIS_TAB", conn);
					ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);
//					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.fo_trade(?,?,?)}");
					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.fo_trade_new(?,?,?)}");

					String[] result = new String[2];
					callStmt.setArray(1, arrayOfProjects);

					callStmt.registerOutParameter(2, Types.VARCHAR);
					callStmt.registerOutParameter(3, Types.VARCHAR);
					callStmt.execute();

					result[0] = callStmt.getString(2);
					result[1] = callStmt.getString(3);
					if (!result[0].equalsIgnoreCase("S")) {
						// Log.notice.info("failure to logging current data:==> "+ result[1]);
						Log.notice.info("failure to logging current data:==>  " + result[1]);
					}
					// callStmt.execute();
					// conn.commit();

					// Log.notice.info("Committed.");
					callStmt.close();
				} catch (Exception e) {
					Log.notice.info("exception while pushing fo: ", e);
					Log.notice.info("exception occured during pushing fo data: ", e);
					// Log.notice.info(
					// "*******************System.exit(0) called IGNORING
					// LOOPING**********************************");
					// System.exit(0);

				}

			}
		});
	}

//	@Deprecated
//	private void insertFO(String[] data) /* throws Exception */
//	{
//
//		Connection conn = ConnectionUtil.getConnectionFO();
//		OracleCallableStatement callStmt = null;
//		try {
//			Object[] project1 = null;
//			project1 = data;
//			StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("FO_TRADE_NOTIS_RECORD", conn);
//			STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
//			Struct[] structArrayOfProjects = { structProject1 };
//			ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("FO_TRADE_NOTIS_TAB", conn);
//			ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);
//			callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.fo_trade(?,?,?)}");
//
//			String[] result = new String[2];
//			callStmt.setArray(1, arrayOfProjects);
//
//			callStmt.registerOutParameter(2, Types.VARCHAR);
//			callStmt.registerOutParameter(3, Types.VARCHAR);
//			callStmt.execute();
//
//			result[0] = callStmt.getString(2);
//			result[1] = callStmt.getString(3);
//			if (!result[0].equalsIgnoreCase("S")) {
//				Log.notice.info("failure to logging current data:==>  " + result[1]);
//			}
//			// callStmt.execute();
//			conn.commit();
//
//			// Log.notice.info("Committed.");
//		} catch (Exception e) {
//
//			/*
//			 * Log.notice.info("exception occured while logging data:=> "+ e.getMessage()
//			 * +" |ok.");
//			 */
//
//			try {
//				if (e != null) {
//					String ex = e.toString();
//					if (ex.contains("maximum open cursors exceeded")) {
//
//						Log.notice.info("exception occured: maximum open cursors exceeded -" + this.focount
//								+ "- , SO connection trying to re-istablish connection");
//						ConnectionUtil.con2 = null;
//						Log.notice.info("Connection set to null, Going to insert data");
//						if (this.focount < 500) {
//							insertFO(data);
//						} else {
//							throw new RuntimeException();
//						}
//						this.focount++;
//
//					} else {
//						Log.notice.info("exception occured while logging data:=> " + e.getMessage() + " |ok.");
//					}
//
//				}
//			} catch (Exception ee) {
//				Log.notice.info("maximum open cursors exceeded again n again");
//
//			}
//
//		}
//
//	}
	// -------------insert fo end

	// ************fostart

	public int getFOseq() {
		// Session fosession = null;
		// fosession = HibernateUtil.openFoSession();
		Session session = null;
		session = HibernateUtil.openFoSession();
		session.doWork(new Work() {

			@Override
			public void execute(Connection conn) {
				// Connection conn = ConnectionUtil.getConnectionFO();
				boolean isRecord = false;
				try {
					/*
					 * PreparedStatement stmt =
					 * conn.prepareStatement("select * from NOTICELOGGfING");
					 */

					PreparedStatement stmt = conn.prepareStatement(
							"select MAX(e.SEQ_NO) from NOTICELOGGING e where e.req_type like ? and e.inserteddate like trunc(sysdate) group by e.req_type");
					stmt.setString(1, "fo_trades_inquiry");

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						isRecord = true;
						foseqresult = rs.getInt(1);
					}

					if (!isRecord) {
						foseqresult = 0;
					}
					stmt.close();
				} catch (Exception e) {
					// Log.notice.info("Exception occured during getting fo seq===="+ e);
					Log.notice.info("Exception occured during getting fo seq====" + e);

				}
			}
		});
		
		HibernateUtil.closeFoSession();

		Log.notice.info("getFOseq return: " + foseqresult);
		return foseqresult;
	}

	// ************foend

	// fo

	public void getFOSave(NoticeLogging obj) {

		try {
			Session session = null;
			session = HibernateUtil.openFoSession();
			session.getTransaction().begin();

			session.doWork(new Work() {
				@Override
				public void execute(Connection conn) {

					// boolean status = false;
					PreparedStatement ps = null;
					// Connection conn = ConnectionUtil.getConnectionCD();

					try {
						String sql = "insert into  NOTICELOGGING (REQ_MESSAGEID,REQ_TYPE,SEQ_NO,RESP_MESSAGESCODE,RESP_MESSAGEID,STATUS,INSERTEDDATE,COMMENTS) values(?,?,?,?,?,?,?,?)";
						ps = conn.prepareStatement(sql);
						ps.setString(1, obj.getREQ_MESSAGEID());
						ps.setString(2, obj.getREQ_TYPE());
						ps.setInt(3, obj.getSEQ_NO());
						ps.setString(4, obj.getRESP_MESSAGESCODE());
						ps.setString(5, obj.getRESP_MESSAGEID());
						ps.setString(6, obj.getSTATUS());
						ps.setTimestamp(7, obj.getINSERTEDDATE());
						ps.setString(8, obj.getComments());
						ps.execute();
						ps.close();
					} catch (Exception e) {
						Log.notice.info(e);
					}

				}
			});

			session.getTransaction().commit();
			HibernateUtil.closeFoSession();

		} catch (Exception e) {
			Log.notice.info(e);
		}
	}

	// fo end

	public int getCMseq() {

		Session session = null;
		session = HibernateUtil.openCmSession();
		session.doWork(new Work() {

			@Override
			public void execute(Connection conn) {

				// Connection conn = ConnectionUtil.getConnection();
				boolean isRecord = false;

				try {
					/*
					 * PreparedStatement stmt =
					 * conn.prepareStatement("select * from NOTICELOGGfING");
					 */

					PreparedStatement stmt = conn.prepareStatement(
							"select MAX(e.SEQ_NO) from NOTICELOGGING e where e.req_type like ? and e.inserteddate like trunc(sysdate) group by e.req_type");
					stmt.setString(1, "cm_trades_inquiry");

					ResultSet rs = stmt.executeQuery();

					while (rs.next()) {
						isRecord = true;
						cmseqresult = rs.getInt(1);
						// Log.notice.info("cmseqresult: +"+ cmseqresult);
					}

					if (!isRecord) {
						cmseqresult = 0;
					}

					// result= rs.getInt("seq_no");
					stmt.close();
				} catch (Exception e) {
					// Log.notice.info("exception in getCMseq===="+ e);
					Log.notice.info("exception in getCMseq====" + e);

				}
			}
		});
		HibernateUtil.closeCmSession();
		Log.notice.info("getCMseq return====" + cmseqresult);
		return cmseqresult;
	}

	public void getCMSave(NoticeLogging obj) {
		Session session = null;
		session = HibernateUtil.openCmSession();
		session.getTransaction().begin();
		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection conn) {

					PreparedStatement ps = null;
					// Connection conn = ConnectionUtil.getConnection();
					try {
						String sql = "insert into  NOTICELOGGING (REQ_MESSAGEID,REQ_TYPE,SEQ_NO,RESP_MESSAGESCODE,RESP_MESSAGEID,STATUS,INSERTEDDATE,COMMENTS) values(?,?,?,?,?,?,?,?)";
						ps = conn.prepareStatement(sql);
						ps.setString(1, obj.getREQ_MESSAGEID());
						ps.setString(2, obj.getREQ_TYPE());
						ps.setInt(3, obj.getSEQ_NO());
						ps.setString(4, obj.getRESP_MESSAGESCODE());
						ps.setString(5, obj.getRESP_MESSAGEID());
						ps.setString(6, obj.getSTATUS());
						ps.setTimestamp(7, obj.getINSERTEDDATE());
						ps.setString(8, obj.getComments());
						ps.execute();
						ps.close();
					} catch (Exception e) {
						Log.notice.info("exception occured while noticelogging for CM");
					}
				}
			});

		} catch (Exception e) {
			Log.notice.info("Exception in cm save: " + e);
		}

		session.getTransaction().commit();
		HibernateUtil.closeCmSession();

	}

	/*
	 * "REQ_MESSAGEID" VARCHAR2(255 BYTE), "REQ_TYPE" VARCHAR2(255 BYTE), "SEQ_NO"
	 * NUMBER, "RESP_MESSAGESCODE" VARCHAR2(255 BYTE), "RESP_MESSAGEID" VARCHAR2(255
	 * BYTE), "STATUS" VARCHAR2(255 BYTE), "INSERTEDDATE" DATE, "COMMENTS"
	 * VARCHAR2(255 BYTE
	 */

	// ---

	// ---------------------------------CD

	public int getCDseq() {

		Session session = null;
		session = HibernateUtil.openCdSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection conn) {
				// Connection conn = ConnectionUtil.getConnectionCD();
				// Connection conn = ConnectionUtil.getConnectionfor_mainonly(); //only for
				// testing
				boolean isRecord = false;
				try {
					/*
					 * PreparedStatement stmt =
					 * conn.prepareStatement("select * from NOTICELOGGfING");
					 */
					PreparedStatement stmt = conn.prepareStatement(
							"select MAX(e.SEQ_NO) from NOTICELOGGING e where e.req_type like ? and e.inserteddate like trunc(sysdate) group by e.req_type");
					stmt.setString(1, "cd_trades_inquiry");
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						isRecord = true;
						cdseqresult = rs.getInt(1);
					}
					if (!isRecord) {
						cdseqresult = 0;
					}
					stmt.close();
				} catch (Exception e) {
					// Log.notice.info("EXception in getCDseq===="+ e);
					Log.notice.info("EXception in getCDseq====" + e);
				}
			}
		});

		HibernateUtil.closeCdSession();
		Log.notice.info("getCDseq return====" + cdseqresult);
		return cdseqresult;
	}

	public void getCDSave(NoticeLogging obj) {
		Log.notice.info("save notice logging proccessed for CD");

		Session cdsession = null;
		cdsession = HibernateUtil.openCdSession();
		cdsession.getTransaction().begin();
		cdsession.doWork(new Work() {
			@Override
			public void execute(Connection conn) {
				// boolean status = false;
				PreparedStatement ps = null;
				// Connection conn = ConnectionUtil.getConnectionCD();
				try {
					String sql = "insert into  NOTICELOGGING (REQ_MESSAGEID,REQ_TYPE,SEQ_NO,RESP_MESSAGESCODE,RESP_MESSAGEID,STATUS,INSERTEDDATE,COMMENTS) values(?,?,?,?,?,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, obj.getREQ_MESSAGEID());
					ps.setString(2, obj.getREQ_TYPE());
					ps.setInt(3, obj.getSEQ_NO());
					ps.setString(4, obj.getRESP_MESSAGESCODE());
					ps.setString(5, obj.getRESP_MESSAGEID());
					ps.setString(6, obj.getSTATUS());
					ps.setTimestamp(7, obj.getINSERTEDDATE());
					ps.setString(8, obj.getComments());
					ps.execute();
					ps.close();
					/* Log.notice.info("cd===> "+ st); */
				} catch (Exception e) {
					Log.notice.info("exception occured during notice logging for CD: " + e);
				}
			}
		});
		cdsession.getTransaction().commit();
		HibernateUtil.closeCdSession();
		Log.notice.info("save notice logging proccessed for CD finish");

	}

	// -----------------------------------cm experi

	public void insertExp(String[] data, Session cmsession) {

		/*
		 * Session cmsession=null; cmsession=HibernateUtil.openCmSession();
		 * cmsession.getTransaction().begin();
		 */

		cmsession.doWork(new Work() {
			@Override
			public void execute(Connection conn) {
				// do your work using connection

				OracleCallableStatement callStmt = null;
				try {
					Object[] project1 = null;
					project1 = data;
					StructDescriptor projectTypeDesc = StructDescriptor.createDescriptor("CM_TRADE_NOTIS_RECORD", conn);
					STRUCT structProject1 = new STRUCT(projectTypeDesc, conn, project1);
					Struct[] structArrayOfProjects = { structProject1 };
					ArrayDescriptor projectTypeArrayDesc = ArrayDescriptor.createDescriptor("CM_TRADE_NOTIS_TAB", conn);
					ARRAY arrayOfProjects = new ARRAY(projectTypeArrayDesc, conn, structArrayOfProjects);
//					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cm_trade(?,?,?)}");
					callStmt = (OracleCallableStatement) conn.prepareCall("{call pkg_notis.cm_trade_new(?,?,?)}");

					String[] result = new String[2];
					callStmt.setArray(1, arrayOfProjects);

					callStmt.registerOutParameter(2, Types.VARCHAR);
					callStmt.registerOutParameter(3, Types.VARCHAR);
					callStmt.execute();

					result[0] = callStmt.getString(2);
					result[1] = callStmt.getString(3);
					if (!result[0].equalsIgnoreCase("S")) {
						Log.notice.info("failure to logging current data:==>  " + result[1]);
					}
					// Log.notice.info("status to logging current data:==> "+ result[1]);
					// callStmt.execute();
					// conn.commit();

					// Log.notice.info("Committed.");
					// conn.close();
					callStmt.close();

				} catch (Exception e) {

					// Log.notice.info("exception is==0p: "+ e);
					Log.notice.info("exception occured during pushing cm data: ", e);
					// Log.notice.info(
					// "*******************System.exit(0) called IGNORING
					// LOOPING**********************************");
					// System.exit(0);
				}

			}
		});

		/*
		 * cmsession.getTransaction().commit(); HibernateUtil.closeCmSession();
		 */

	}
	// --

	// ------fo

	// ---

	public static void main(String[] args) throws Exception {

		/*
		 * DaoUtil obj = new DaoUtil(); //obj.getCMSave(null); int r= obj.getCDseq();
		 * Log.notice.info("==================> "+ r);
		 * 
		 * // obj.insertCM("");
		 */
	}

}