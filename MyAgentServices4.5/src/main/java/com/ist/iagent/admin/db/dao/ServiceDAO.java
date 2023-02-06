package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.util.DBUtil;

public class ServiceDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(ServiceDAO.class);

	public String saveService(JavaService objServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {

			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_js_service "
					+ " (service_id ,  class_name , method_name , save_last_record,linked_jar_id,antilogging) "
					+ " values (?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;

			ps.setInt(j++, objServiceDTO.getServiceId());
			// ps.setString(j++, objServiceDTO.getServiceName());
			ps.setString(j++, objServiceDTO.getClassName());
			ps.setString(j++, objServiceDTO.getMethodName());
			ps.setBoolean(j++, objServiceDTO.getSaveLastRecord());
			ps.setInt(j++, objServiceDTO.getLinkedJarId());
			ps.setString(j++, objServiceDTO.loggingAllowed() ? "n" : "y");

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "service name added to database ";
			} else {
				msg = "Unable to add service name added to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service name to database ", e);
			msg = "Error in saving service name to database  " + e.getMessage();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e1) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return msg;
	}

	public int generateNextServiceID() {
		int service_id = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(service_id) as max from t_js_service ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				service_id = rs.getInt(1);
			} else {
				throw new Exception("unable to generate service id");
			}
		} catch (SQLException e) {
			log.error("EROOR ON unable to generate service id from database ",
					e);
		} catch (Exception e) {
			log.error("EROOR ON unable to generate service id from database ",
					e);
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
		return service_id;
	}

	public List<JavaService> getServiceList(
			List<ServiceDetail> serviceDetailList) {
		List<JavaService> serviceList = new ArrayList<JavaService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			JavaService objJS = fetchJavaService(serDetail);
			if (objJS != null) {
				serviceList.add(objJS);
			}
		}
		return serviceList;
	}

	public JavaService fetchJavaService(ServiceDetail serDetail) {
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from " + serDetail.getServiceTableName()
					+ " where service_id = ?";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, serDetail.getServiceId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				JavaService objServiceDTO = new JavaService();
				objServiceDTO.setServiceId(serDetail.getServiceId());
				objServiceDTO.setServiceName(serDetail.getServiceName());
				objServiceDTO.setMethodName(rs.getString("method_name"));
				objServiceDTO.setClassName(rs.getString("class_name"));
				objServiceDTO.setSaveLastRecord(rs
						.getBoolean("save_last_record"));
				objServiceDTO.setLinkedJarId(rs.getInt("linked_jar_id"));
				objServiceDTO.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objServiceDTO;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service list from database ", e);
			return null;
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
	}

	public JavaService fetchJavaService(ServiceDetail serDetail,
			Connection externalCon) {
		PreparedStatement ps = null;

		try {
			// con = DBUtil.getInstance().getConnection();
			String sql = "select * from " + serDetail.getServiceTableName()
					+ " where service_id = ?";
			ps = externalCon.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, serDetail.getServiceId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				JavaService objServiceDTO = new JavaService();
				objServiceDTO.setServiceId(serDetail.getServiceId());
				objServiceDTO.setServiceName(serDetail.getServiceName());
				objServiceDTO.setMethodName(rs.getString("method_name"));
				objServiceDTO.setClassName(rs.getString("class_name"));
				objServiceDTO.setSaveLastRecord(rs
						.getBoolean("save_last_record"));
				objServiceDTO.setLinkedJarId(rs.getInt("linked_jar_id"));
				objServiceDTO.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objServiceDTO;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service list from database ", e);
			return null;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e1) {
			}
			try {
				if (externalCon != null) {
					externalCon.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public String deleteService(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_js_service where service_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, serviceId);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "service deleted from database ";
			} else {
				msg = "Unable to delete service from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete service from database ", e);
			msg = "Error in deleting service from database  " + e.getMessage();
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

	public String updateService(JavaService objServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update t_js_service "
					+ "set method_name=?,class_name=?,"
					+ "save_last_record=? ,linked_jar_id=? ,antilogging=?"
					+ " where service_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			// ps.setString(j++, objServiceDTO.getServiceName());
			ps.setString(j++, objServiceDTO.getMethodName());
			ps.setString(j++, objServiceDTO.getClassName());
			ps.setBoolean(j++, objServiceDTO.getSaveLastRecord());
			ps.setInt(j++, objServiceDTO.getLinkedJarId());
			ps.setString(j++, objServiceDTO.loggingAllowed() ? "n" : "y");

			// where clause
			ps.setInt(j++, objServiceDTO.getServiceId());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "service updated ";
			} else {
				msg = "Unable to update service ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON update service ", e);
			msg = "Error in update service " + e.getMessage();
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

	public List<Integer> fetchAllServiceIDsForJarID(int linkedJarId) {

		List<Integer> serviceIDs = new ArrayList<Integer>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_js_service where linked_jar_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, linkedJarId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				serviceIDs.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete service from database ", e);
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
		return serviceIDs;
	}

	/** service parameters related member functions */
	public String saveAllParameters(JavaService objServiceDTO) {
		String msg = "";
		PreparedStatement ps = null;
		try {
			if (objServiceDTO.getInputType() != null) {
				for (IAgentServiceParameter param : objServiceDTO
						.getInputType()) {
					((ParameterDescDTO) param).setServiceId(objServiceDTO
							.getServiceId());
					msg += saveParameter(((ParameterDescDTO) param));
				}
			}
		} catch (Exception e) {
			log.error("EROOR ON save web service to database ", e);
			msg = "Error in saving web service to database  " + e.getMessage();
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

	public String saveParameter(ParameterDescDTO paramDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			int paramId = generateNextParamIDForService(paramDTO.getServiceId());
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_js_param "
					+ " (param_id , ser_id  , p_name , p_type , p_desc ) "
					+ " values (?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			int j = 1;
			// int paramId =
			// generateNextParamIDForService(paramDTO.getServiceId());
			paramDTO.setParamId(paramId + 1);

			ps.setInt(j++, paramDTO.getParamId());
			ps.setInt(j++, paramDTO.getServiceId());
			ps.setString(j++, paramDTO.getParameterName());
			ps.setString(j++, paramDTO.getParameterType());
			ps.setString(j++, paramDTO.getParameterDescr());

			// log.debug(ps);
			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = " service parameter added to database ";
			} else {
				msg = "Unable to add service parameter to database ";
				throw new SQLException(msg);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service parameter to database ", e);
			msg = "Error in saving service parameter to database  "
					+ e.getMessage();
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

	public int generateNextParamIDForService(int serviceId) {
		int paramId = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(param_id) as max from t_js_param where ser_id=? ";
			ps = con.prepareStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				paramId = rs.getInt("max");
			} else {
				throw new Exception("unable to generate parameter id");
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON unable to generate parameter id from database ",
					e);
		} catch (Exception e) {
			log.error(
					"EROOR ON unable to generate parameter id from database ",
					e);
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
		return paramId;
	}

	public List<ParameterDescDTO> getServiceParameters(int serviceId) {
		List<ParameterDescDTO> serviceList = new ArrayList<ParameterDescDTO>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_js_param where ser_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ParameterDescDTO objParameterDescDTO = new ParameterDescDTO();
				objParameterDescDTO.setServiceId(rs.getInt("ser_id"));
				objParameterDescDTO.setParamId(rs.getInt("param_id"));
				objParameterDescDTO.setParameterName(rs.getString("p_name"));
				objParameterDescDTO.setParameterType(rs.getString("p_type"));
				objParameterDescDTO.setParameterDescr(rs.getString("p_desc"));
				serviceList.add(objParameterDescDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch parameter list from database ", e);
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
		return serviceList;
	}

	public List<ParameterDescDTO> getServiceParameters(int serviceId,
			Connection externalCon) {
		List<ParameterDescDTO> serviceList = new ArrayList<ParameterDescDTO>();
		PreparedStatement ps = null;

		try {
			String sql = "select * from t_js_param where ser_id=?";
			ps = externalCon.prepareStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ParameterDescDTO objParameterDescDTO = new ParameterDescDTO();
				objParameterDescDTO.setServiceId(rs.getInt("ser_id"));
				objParameterDescDTO.setParamId(rs.getInt("param_id"));
				objParameterDescDTO.setParameterName(rs.getString("p_name"));
				objParameterDescDTO.setParameterType(rs.getString("p_type"));
				objParameterDescDTO.setParameterDescr(rs.getString("p_desc"));
				serviceList.add(objParameterDescDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch parameter list from database ", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e1) {
			}
			try {
				if (externalCon != null) {
					externalCon.close();
				}
			} catch (Exception e) {
			}
		}
		return serviceList;
	}

	public String updateAllParameters(JavaService objServiceDTO) {
		String msg = "";
		PreparedStatement ps = null;
		try {

			if (objServiceDTO.getInputType() != null) {
				for (IAgentServiceParameter param : objServiceDTO
						.getInputType()) {
					msg += updateParameter(((ParameterDescDTO) param));
				}
			}

		} catch (Exception e) {
			log.error("EROOR ON save web service to database ", e);
			msg = "Error in saving web service to database  " + e.getMessage();
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

	public String updateParameter(ParameterDescDTO paramDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();

			String sql = "update t_js_param "
					+ " set  p_name = ? , p_type = ? , p_desc = ? "
					+ " where param_id=? and ser_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, paramDTO.getParameterName());
			ps.setString(j++, paramDTO.getParameterType());
			ps.setString(j++, paramDTO.getParameterDescr());

			// where part
			ps.setInt(j++, paramDTO.getParamId());
			ps.setInt(j++, paramDTO.getServiceId());

			log.debug(ps);
			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = " service parameter added to database ";
			} else {
				msg = "Unable to add service parameter to database ";
				throw new SQLException(msg);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service parameter to database ", e);
			msg = "Error in saving service parameter to database  "
					+ e.getMessage();
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

	public String deleteAllParameters(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_js_param where ser_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, serviceId);
			int rowCount = ps.executeUpdate();

			isSuccess = rowCount >= 0 ? true : false;

			if (isSuccess) {
				msg = rowCount + " services deleted from database ";
			} else {
				msg = "Unable to delete service from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete service from database ", e);
			msg = "Error in deleting service from database  " + e.getMessage();
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
