package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.util.DBUtil;

public class QueryServiceDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(QueryServiceDAO.class);

	public String saveQueryService(QueryService objWSServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {

			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_query_service "
					+ " (service_id , linked_db_id , result_type , stored_proc , query_text , antilogging ) "
					+ " values (?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, objWSServiceDTO.getServiceId());
			ps.setInt(j++, objWSServiceDTO.getLinkedDbID());
			ps.setString(j++, objWSServiceDTO.getQueryResultType());

			ps.setString(j++, objWSServiceDTO.getStoredProc() ? "y" : "n");
			ps.setString(j++, objWSServiceDTO.getQueryText());
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

	public List<QueryService> getQueryServiceList(
			List<ServiceDetail> serviceDetailList) {
		List<QueryService> serviceList = new ArrayList<QueryService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			QueryService objJS = fetchQueryService(serDetail);
			if (objJS != null) {
				serviceList.add(objJS);
			}
		}
		return serviceList;
	}

	public QueryService fetchQueryService(ServiceDetail serDetail) {
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
				QueryService objQueryService = new QueryService();
				objQueryService.setServiceId(serDetail.getServiceId());
				objQueryService.setServiceName(serDetail.getServiceName());
				objQueryService.setLinkedDbID(rs.getInt("linked_db_id"));
				objQueryService.setStoredProc("y".equalsIgnoreCase(rs
						.getString("stored_proc")));
				objQueryService.setQueryText(rs.getString("query_text"));
				objQueryService.setQueryResultType(rs.getString("result_type"));
				objQueryService.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objQueryService;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service list from database ", e);
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

	public QueryService fetchQueryService(ServiceDetail serDetail,
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
				QueryService objQueryService = new QueryService();
				objQueryService.setServiceId(serDetail.getServiceId());
				objQueryService.setServiceName(serDetail.getServiceName());
				objQueryService.setLinkedDbID(rs.getInt("linked_db_id"));
				objQueryService.setStoredProc("y".equalsIgnoreCase(rs
						.getString("stored_proc")));
				objQueryService.setQueryText(rs.getString("query_text"));
				objQueryService.setQueryResultType(rs.getString("result_type"));
				objQueryService.setLoggingAllowed(!"y".equalsIgnoreCase(rs
						.getString("antilogging")));
				return objQueryService;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service list from database ", e);
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

	public String updateQueryService(QueryService objWSServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update t_query_service "
					+ "  set  result_type = ? ,"
					+ " linked_db_id = ? , stored_proc = ? , query_text = ?,antilogging = ?"
					+ " where service_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, objWSServiceDTO.getQueryResultType());
			ps.setInt(j++, objWSServiceDTO.getLinkedDbID());
			ps.setString(j++, objWSServiceDTO.getStoredProc() ? "y" : "n");
			ps.setString(j++, objWSServiceDTO.getQueryText());
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

	public String deleteQueryService(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_query_service where service_id=?";
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

	public List<Integer> fetchAllServiceIDsForDsID(int linkedDbId) {
		List<Integer> serviceIDs = new ArrayList<Integer>();
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select service_id from t_query_service where linked_db_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, linkedDbId);

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
	public String saveAllQueryParameters(QueryService objServiceDTO) {
		String msg = "";
		PreparedStatement ps = null;

		try {
			if (objServiceDTO.getInputType() != null) {
				for (IAgentServiceParameter param : objServiceDTO
						.getInputType()) {
					((QueryParameter) param).setServiceId(objServiceDTO
							.getServiceId());
					msg += saveQueryParameter(((QueryParameter) param));
				}
			}
		} catch (Exception e) {
			log.error("EROOR ON save query service to database ", e);
			msg = "Error in saving query service to database  "
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

	public String saveQueryParameter(QueryParameter paramDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_query_param "
					+ " (param_id , ser_id  , p_name , p_data_type , p_result_type , p_blank_allowed , p_desc ) "
					+ " values (?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			int j = 1;
			int paramId = getMaxParamIDForService(paramDTO.getServiceId());
			paramDTO.setParamId(paramId + 1);

			ps.setInt(j++, paramDTO.getParamId());
			ps.setInt(j++, paramDTO.getServiceId());
			ps.setString(j++, paramDTO.getParameterName());
			ps.setString(j++, paramDTO.getParameterType());
			ps.setString(j++, paramDTO.getParameterResultType());
			ps.setString(j++, paramDTO.getBlankAllowed() ? "y" : "n");
			ps.setString(j++, paramDTO.getParameterDescr());

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

	public int getMaxParamIDForService(int serviceId) {
		int paramId = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(param_id) as max from t_query_param where ser_id=? ";
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

	public List<QueryParameter> getQueryServiceParameters(int serviceId) {
		List<QueryParameter> serviceList = new ArrayList<QueryParameter>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_query_param where ser_id=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				QueryParameter objQueryParameter = new QueryParameter();
				objQueryParameter.setServiceId(rs.getInt("ser_id"));
				objQueryParameter.setParamId(rs.getInt("param_id"));
				objQueryParameter.setParameterName(rs.getString("p_name"));
				objQueryParameter.setParameterType(rs.getString("p_data_type"));
				objQueryParameter.setParameterResultType(rs
						.getString("p_result_type"));
				objQueryParameter.setBlankAllowed("y".equalsIgnoreCase(rs
						.getString("p_blank_allowed")));
				objQueryParameter.setParameterDescr(rs.getString("p_desc"));
				serviceList.add(objQueryParameter);
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

	public List<QueryParameter> getQueryServiceParameters(int serviceId,
			Connection externalCon) {
		List<QueryParameter> serviceList = new ArrayList<QueryParameter>();
		PreparedStatement ps = null;

		try {
			String sql = "select * from t_query_param where ser_id=?";
			ps = externalCon.prepareStatement(sql);
			ps.setInt(1, serviceId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				QueryParameter objQueryParameter = new QueryParameter();
				objQueryParameter.setServiceId(rs.getInt("ser_id"));
				objQueryParameter.setParamId(rs.getInt("param_id"));
				objQueryParameter.setParameterName(rs.getString("p_name"));
				objQueryParameter.setParameterType(rs.getString("p_data_type"));
				objQueryParameter.setParameterResultType(rs
						.getString("p_result_type"));
				objQueryParameter.setBlankAllowed("y".equalsIgnoreCase(rs
						.getString("p_blank_allowed")));
				objQueryParameter.setParameterDescr(rs.getString("p_desc"));
				serviceList.add(objQueryParameter);
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

	public String updateAllQueryParameters(QueryService objQueryService) {
		String msg = "";
		PreparedStatement ps = null;
		try {
			if (objQueryService.getInputType() != null) {
				for (IAgentServiceParameter param : objQueryService
						.getInputType()) {
					msg += updateQueryParameter((QueryParameter) param);
				}
			}
		} catch (Exception e) {
			log.error("EROOR ON update QueryService to database ", e);
			msg = "Error in update QueryService to database  " + e.getMessage();
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

	public String updateQueryParameter(QueryParameter param) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();

			String sql = "update t_query_param "
					+ " set  p_name = ? , p_data_type = ? , p_result_type = ? , p_blank_allowed =? , p_desc = ?  "
					+ " where param_id=? and ser_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, param.getParameterName());
			ps.setString(j++, param.getParameterType());
			ps.setString(j++, param.getParameterResultType());
			ps.setString(j++, param.getBlankAllowed() ? "y" : "n");
			ps.setString(j++, param.getParameterDescr());

			/** where part */
			ps.setInt(j++, param.getParamId());
			ps.setInt(j++, param.getServiceId());

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
				if (ps != null)
					ps.close();
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

	public String deleteAllQueryParameters(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_query_param where ser_id=?";
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
