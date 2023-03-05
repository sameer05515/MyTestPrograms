package com.ist.iagent.core.service.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.DBUtil;
import com.ist.iagent.core.service.db.pojo.ServiceLog;


public class ServiceLogDAO {

	private static final Logger log = Logger.getLogger(ServiceLogDAO.class);

	public static String saveServiceLog(String agentID, String callID, String customerID,
			String serviceName, String request, String response, String status,
			int timeInterval) {

		String msg = "";
		ServiceLog objLog = new ServiceLog();
		objLog.setAgentID(agentID);
		objLog.setCallID(callID);
		objLog.setServiceName(serviceName);
		objLog.setRequest(request);
		objLog.setResponse(response);
		objLog.setStatus(status);
		objLog.setTimeInterval(timeInterval);
		objLog.setCustomerID(customerID);
//		long serviceLogTime=System.currentTimeMillis();
//		objLog.setServiceLogTime(serviceLogTime);

		msg = saveServiceLog(objLog);
		return msg;
	}

	public static String saveServiceLog(ServiceLog objLog) {
		Connection con = null;
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			
			String sql = "insert into service_logs (agent_id,call_id,cust_id,service_name,request,response,status,time_interval,time   )" 
				+ " values (?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			
			int j = 1;
			
			ps.setString(j++, objLog.getAgentID());
			ps.setString(j++, objLog.getCallID());
			ps.setString(j++, objLog.getCustomerID());
			ps.setString(j++, objLog.getServiceName());
			ps.setString(j++, objLog.getRequest());
			ps.setString(j++, objLog.getResponse());
			ps.setString(j++, objLog.getStatus());			
			ps.setInt(j++, objLog.getTimeInterval());

			long longTime=System.currentTimeMillis();			
			java.sql.Timestamp serviceLogTime = new java.sql.Timestamp(longTime);//			
			ps.setTimestamp(j++, serviceLogTime);			

			
			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "Service Log added to database ";
			} else {
				msg = "Unable to add Service Log to database ";
				
			}
			
			log.info(msg);
		} catch (SQLException e) {
			log.error("EROOR ON save Service Log to database " + e);
			msg = "Error in saving Service Log to database  " + e.getMessage();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e1) {
				log.warn("EROOR ON save Service Log in database ", e1);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				log.warn(e);
			}
		}
		return msg;
	}

}
