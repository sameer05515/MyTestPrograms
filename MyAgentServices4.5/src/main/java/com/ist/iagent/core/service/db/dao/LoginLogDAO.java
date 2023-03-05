package com.ist.iagent.core.service.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.DBUtil;
import com.ist.iagent.core.service.db.pojo.LoginLog;

public class LoginLogDAO {

	private static final Logger log = Logger.getLogger(LoginLogDAO.class);

	public static String saveLoginLog(String agentID, String status,
			String adUserId, String extn, String adStatus, String ctiStatus,
			String reserved_1, String reserved_2, String reserved_3) {

		String msg = "";
		LoginLog objLog = new LoginLog();
		objLog.setAgentID(agentID);
		objLog.setStatus(status);
		objLog.setAdStatus(adStatus);
		objLog.setAdUserId(adUserId);
		objLog.setExtn(extn);
		objLog.setCtiStatus(ctiStatus);
		objLog.setReserved_1(reserved_1);
		objLog.setReserved_2(reserved_2);
		objLog.setReserved_3(reserved_3);

		msg = saveLoginLog(objLog);
		return msg;
	}

	public static String saveLoginLog(LoginLog objLog) {
		Connection con = null;
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into login_logs ( time , agent_id , ad_userid,extn , "
					+ "status , ad_status , cti_status , "
					+ "reserved_1 , reserved_2 , reserved_3)"
					+ " values (?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			int j = 1;

			long longTime = System.currentTimeMillis();
			java.sql.Timestamp loginLogTime = new java.sql.Timestamp(longTime);//
			ps.setTimestamp(j++, loginLogTime);

			ps.setString(j++, objLog.getAgentID());
			ps.setString(j++, objLog.getAdUserId());
			ps.setString(j++, objLog.getExtn());
			ps.setString(j++, objLog.getStatus());
			ps.setString(j++, objLog.getAdStatus());
			ps.setString(j++, objLog.getCtiStatus());
			ps.setString(j++, objLog.getReserved_1());
			ps.setString(j++, objLog.getReserved_2());
			ps.setString(j++, objLog.getReserved_3());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "Login Log added to database ";
			} else {
				msg = "Unable to add Login Log to database ";
				log.info(msg);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save Login Log to database ", e);
			msg = "Error in saving Login Log to database  " + e.getMessage();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e1) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return msg;
	}

}
