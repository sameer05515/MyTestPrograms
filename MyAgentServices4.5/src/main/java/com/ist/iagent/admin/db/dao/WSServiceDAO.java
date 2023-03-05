package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.util.DBUtil;

public class WSServiceDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(WSServiceDAO.class);

	public String saveWSService(WSService objWSServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_ws_service "
					+ " (service_id , operation_name , linked_ws_id , save_last_record,port_name,param_schema,antilogging ) "
					+ " values (?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setInt(j++, objWSServiceDTO.getServiceId());
			// ps.setString(j++, objWSServiceDTO.getServiceName());
			ps.setString(j++, objWSServiceDTO.getOperationName());
			ps.setInt(j++, objWSServiceDTO.getLinkedWSId());
			ps.setBoolean(j++, objWSServiceDTO.isSaveLastRecord());
			ps.setString(j++, objWSServiceDTO.getPortName());
			ps.setString(j++, objWSServiceDTO.getParameterSchema());
			ps.setString(j++, objWSServiceDTO.loggingAllowed() ? "n" : "y");

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "web service added to database ";

			} else {
				msg = "Unable to add web service added to database ";
			}

		} catch (SQLException e) {
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

	public List<WSService> getWSServiceList(
			List<ServiceDetail> serviceDetailList) {
		List<WSService> serviceList = new ArrayList<WSService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			WSService objWS = fetchWebService(serDetail);
			if (objWS != null) {
				serviceList.add(objWS);
			}
		}
		return serviceList;
	}

	public WSService fetchWebService(ServiceDetail serDetail) {
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
				WSService objWSServiceDTO = new WSService();
				objWSServiceDTO.setServiceId(serDetail.getServiceId());
				objWSServiceDTO.setServiceName(serDetail.getServiceName());
				objWSServiceDTO
						.setOperationName(rs.getString("operation_name"));
				objWSServiceDTO.setLinkedWSId(rs.getInt("linked_ws_id"));
				objWSServiceDTO.setSaveLastRecord(rs
						.getBoolean("save_last_record"));
				objWSServiceDTO.setPortName(rs.getString("port_name"));
				objWSServiceDTO
						.setParameterSchema(rs.getString("param_schema"));
				objWSServiceDTO.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objWSServiceDTO;
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

	public WSService fetchWebService(ServiceDetail serDetail,
			Connection externalCon) {
		PreparedStatement ps = null;

		try {
			String sql = "select * from " + serDetail.getServiceTableName()
					+ " where service_id = ?";
			ps = externalCon.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, serDetail.getServiceId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				WSService objWSServiceDTO = new WSService();
				objWSServiceDTO.setServiceId(serDetail.getServiceId());
				objWSServiceDTO.setServiceName(serDetail.getServiceName());
				objWSServiceDTO
						.setOperationName(rs.getString("operation_name"));
				objWSServiceDTO.setLinkedWSId(rs.getInt("linked_ws_id"));
				objWSServiceDTO.setSaveLastRecord(rs
						.getBoolean("save_last_record"));
				objWSServiceDTO.setPortName(rs.getString("port_name"));
				objWSServiceDTO
						.setParameterSchema(rs.getString("param_schema"));
				objWSServiceDTO.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objWSServiceDTO;
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

	public String updateWSService(WSService objWSServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update t_ws_service "
					+ "  set  operation_name = ? ,"
					+ " linked_ws_id = ? , save_last_record = ? , port_name = ?,param_schema=?,antilogging = ?"
					+ " where service_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			// ps.setString(j++, objWSServiceDTO.getServiceName());
			ps.setString(j++, objWSServiceDTO.getOperationName());
			ps.setInt(j++, objWSServiceDTO.getLinkedWSId());
			ps.setBoolean(j++, objWSServiceDTO.isSaveLastRecord());
			ps.setString(j++, objWSServiceDTO.getPortName());
			ps.setString(j++, objWSServiceDTO.getParameterSchema());
			ps.setString(j++, objWSServiceDTO.loggingAllowed() ? "n" : "y");

			/** where part */
			ps.setInt(j++, objWSServiceDTO.getServiceId());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "web service updated to database ";
			} else {
				msg = "Unable to update web service to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON update web service to database ", e);
			msg = "Error in update web service to database  " + e.getMessage();
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

	public String deleteWSService(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_ws_service where service_id=?";
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

	public List<Integer> fetchAllServiceIDsForWSid(int linkedWSId) {
		List<Integer> serviceIDs = new ArrayList<Integer>();
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select service_id from t_ws_service where linked_ws_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, linkedWSId);

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
	public String saveAllWSParameters(WSService objWSServiceDTO) {
		String msg = "";
		PreparedStatement ps = null;

		try {
			if (objWSServiceDTO.getInputType() != null) {
				for (IAgentServiceParameter param : objWSServiceDTO
						.getInputType()) {

					((ParameterDescDTO) param).setServiceId(objWSServiceDTO
							.getServiceId());
					msg += saveWSParameter(((ParameterDescDTO) param));
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

	public String saveWSParameter(ParameterDescDTO paramDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			int paramId = generateNextWSParamIDForService(paramDTO
					.getServiceId());

			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_ws_param "
					+ " (param_id , ser_id  , p_name , p_type , p_desc ) "
					+ " values (?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;

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

	public int generateNextWSParamIDForService(int serviceId) {
		int paramId = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(param_id) as max from t_ws_param where ser_id=? ";
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

	public List<ParameterDescDTO> getWSServiceParameters(int serviceId,
			Connection externalCon) {
		List<ParameterDescDTO> serviceList = new ArrayList<ParameterDescDTO>();
		PreparedStatement ps = null;

		try {
			// con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_ws_param where ser_id=?";
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

	public List<ParameterDescDTO> getWSServiceParameters(int serviceId) {
		List<ParameterDescDTO> serviceList = new ArrayList<ParameterDescDTO>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_ws_param where ser_id=?";
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

	public String updateAllWSParameters(WSService objWSServiceDTO) {
		String msg = "";
		PreparedStatement ps = null;
		try {
			if (objWSServiceDTO.getInputType() != null) {
				for (IAgentServiceParameter param : objWSServiceDTO
						.getInputType()) {
					msg += updateWSParameter(((ParameterDescDTO) param));
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

	public String updateWSParameter(ParameterDescDTO paramDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {

			con = DBUtil.getInstance().getConnection();

			String sql = "update t_ws_param "
					+ " set  p_name = ? , p_type = ? , p_desc = ? "
					+ " where param_id=? and ser_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, paramDTO.getParameterName());
			ps.setString(j++, paramDTO.getParameterType());
			ps.setString(j++, paramDTO.getParameterDescr());

			/** where part */
			ps.setInt(j++, paramDTO.getParamId());
			ps.setInt(j++, paramDTO.getServiceId());

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

	public String deleteAllWSParameters(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_ws_param where ser_id=?";
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
