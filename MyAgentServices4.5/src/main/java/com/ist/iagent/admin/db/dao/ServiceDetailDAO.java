package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.util.DBUtil;

public class ServiceDetailDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(ServiceDetailDAO.class);

	public ServiceDetail saveServiceDetail(ServiceDetail objServiceDetail) {
		boolean isSuccess = false;
		PreparedStatement ps = null;
		try {
			int service_id = generateNextServiceID();
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into service_detail "
					+ " (service_id , service_name , service_type , table_name , status) "
					+ " values (?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			int j = 1;

			objServiceDetail.setServiceId(service_id + 1);

			ps.setInt(j++, objServiceDetail.getServiceId());
			ps.setString(j++, objServiceDetail.getServiceName());
			ps.setString(j++, objServiceDetail.getServiceType());
			ps.setString(j++, objServiceDetail.getServiceTableName());
			ps.setBoolean(j++, objServiceDetail.getServiceStatus());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				return objServiceDetail;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service name to database ", e);
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

	public int generateNextServiceID() {
		int service_id = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(service_id) as max from service_detail ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				service_id = rs.getInt(1);
				return service_id;
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

	public ServiceDetail updateServiceDetail(ServiceDetail objServiceDetail) {
		boolean isSuccess = false;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "update service_detail "
					+ "set service_name=?,service_type=?,table_name=?,"
					+ "status=? " + " where service_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, objServiceDetail.getServiceName());
			ps.setString(j++, objServiceDetail.getServiceType());
			ps.setString(j++, objServiceDetail.getServiceTableName());
			ps.setBoolean(j++, objServiceDetail.getServiceStatus());

			// where clause
			ps.setInt(j++, objServiceDetail.getServiceId());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				return objServiceDetail;
			} else {
				throw new SQLException("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON update service ", e);
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

	public List<ServiceDetail> getServiceDetailList() {
		List<ServiceDetail> serviceList = new ArrayList<ServiceDetail>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from service_detail";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ServiceDetail objServiceDTO = new ServiceDetail();
				objServiceDTO.setServiceId(rs.getInt("service_id"));
				objServiceDTO.setServiceName(rs.getString("service_name"));
				objServiceDTO.setServiceType(rs.getString("service_type"));
				objServiceDTO.setServiceTableName(rs.getString("table_name"));
				objServiceDTO.setServiceStatus(rs.getBoolean("status"));
				serviceList.add(objServiceDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service list from database ", e);
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

	public List<ServiceDetail> getServiceDetailsForType(String serviceType) {

		List<ServiceDetail> serviceList = new ArrayList<ServiceDetail>();
		if (serviceType == null) {
			return serviceList;
		}
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from service_detail where service_type = ?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, serviceType);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ServiceDetail objServiceDTO = new ServiceDetail();
				objServiceDTO.setServiceId(rs.getInt("service_id"));
				objServiceDTO.setServiceName(rs.getString("service_name"));
				objServiceDTO.setServiceType(rs.getString("service_type"));
				objServiceDTO.setServiceTableName(rs.getString("table_name"));
				objServiceDTO.setServiceStatus(rs.getBoolean("status"));
				serviceList.add(objServiceDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service list from database ", e);
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

	public List<ServiceDetail> getServiceDetailsForType(String serviceType,
			Connection externalCon) {

		List<ServiceDetail> serviceList = new ArrayList<ServiceDetail>();
		if (serviceType == null) {
			return serviceList;
		}
		PreparedStatement ps = null;

		try {
			// con = DBUtil.getInstance().getConnection();
			String sql = "select * from service_detail where service_type = ?";
			ps = externalCon.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, serviceType);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ServiceDetail objServiceDTO = new ServiceDetail();
				objServiceDTO.setServiceId(rs.getInt("service_id"));
				objServiceDTO.setServiceName(rs.getString("service_name"));
				objServiceDTO.setServiceType(rs.getString("service_type"));
				objServiceDTO.setServiceTableName(rs.getString("table_name"));
				objServiceDTO.setServiceStatus(rs.getBoolean("status"));
				serviceList.add(objServiceDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON save service list from database ", e);
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

	public ServiceDetail getServiceDetailForID(int serviceId) {
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from service_detail where service_id = ?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, serviceId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ServiceDetail objServiceDTO = new ServiceDetail();
				objServiceDTO.setServiceId(rs.getInt("service_id"));
				objServiceDTO.setServiceName(rs.getString("service_name"));
				objServiceDTO.setServiceType(rs.getString("service_type"));
				objServiceDTO.setServiceTableName(rs.getString("table_name"));
				objServiceDTO.setServiceStatus(rs.getBoolean("status"));
				return objServiceDTO;
			} else {
				throw new SQLException(
						"unable to fetch Service Detail for service id : - "
								+ serviceId);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch Service Detail from database ", e);
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

	public String deleteServiceDetail(int serviceId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from service_detail where service_id=?";
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
}
