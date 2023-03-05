package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ConfigService;
import com.ist.iagent.admin.db.pojo.ConfigServiceType;
import com.ist.iagent.admin.db.pojo.IAgentServiceChannel;
import com.ist.iagent.admin.util.DBUtil;

public class ConfigServiceDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(ConfigServiceDAO.class);

	public String saveConfigService(ConfigService objWSServiceDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			/** First generate next id */
			int configServiceSrNo = getMaxConfigServiceSrNo();
			objWSServiceDTO.setConfigServiceSrNo(configServiceSrNo + 1);

			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_config_service "
					+ " (config_service_srno , linked_ser_id , ser_type_id , service_channel_id ) "
					+ " values (?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, objWSServiceDTO.getConfigServiceSrNo());
			ps.setInt(j++, objWSServiceDTO.getLinkedServiceId());
			ps.setInt(j++, objWSServiceDTO.getConfigServiceTypeId());

			/** Added new field in database "service_channel_id" */
			if (objWSServiceDTO.getServiceChannel() != null
					&& objWSServiceDTO.getServiceChannel()
							.getServiceChannelID() > 0) {
				ps.setInt(j++, objWSServiceDTO.getServiceChannel()
						.getServiceChannelID());
			} else {
				ps.setInt(j++, 0);
			}

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "Configured Service Details added to database ";

			} else {
				msg = "Unable to add Configured Service Details to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON save Configured Service Details to database ",
					e);
			msg = "Error in saving Configured Service Details to database  "
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

	public int getMaxConfigServiceSrNo() {
		int configServiceSrNo = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(config_service_srno) as max from t_config_service ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				configServiceSrNo = rs.getInt(1);
			} else {
				throw new Exception("unable to generate ConfigServiceSrNo");
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON unable to generate configServiceSrNo from database ",
					e);
		} catch (Exception e) {
			log.error(
					"EROOR ON unable to generate configServiceSrNo from database ",
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
		return configServiceSrNo;
	}

	public List<ConfigService> getConfigServiceListByType(int confServiceTypeId) {
		List<ConfigService> serviceList = new ArrayList<ConfigService>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_config_service where ser_type_id = ?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, confServiceTypeId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ConfigService objQueryService = new ConfigService();
				objQueryService.setLinkedServiceId(rs.getInt("linked_ser_id"));
				objQueryService.setConfigServiceSrNo(rs
						.getInt("config_service_srno"));
				objQueryService
						.setConfigServiceTypeId(rs.getInt("ser_type_id"));

				int serviceChannelID = rs.getInt("service_channel_id");
				IAgentServiceChannel objServiceChannel = new IAgentServiceChannel();
				objServiceChannel.setServiceChannelID(serviceChannelID);

				objQueryService.setServiceChannel(objServiceChannel);

				serviceList.add(objQueryService);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service list from database ", e);
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

	public List<ConfigService> getAllConfigServiceList() {
		List<ConfigService> serviceList = new ArrayList<ConfigService>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_config_service ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ConfigService objQueryService = new ConfigService();
				objQueryService.setLinkedServiceId(rs.getInt("linked_ser_id"));
				objQueryService.setConfigServiceSrNo(rs
						.getInt("config_service_srno"));
				objQueryService
						.setConfigServiceTypeId(rs.getInt("ser_type_id"));

				int serviceChannelID = rs.getInt("service_channel_id");
				IAgentServiceChannel objServiceChannel = new IAgentServiceChannel();
				objServiceChannel.setServiceChannelID(serviceChannelID);

				objQueryService.setServiceChannel(objServiceChannel);

				serviceList.add(objQueryService);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service list from database ", e);
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

	/** update service channel */
	public String updateConfigService(ConfigService objServiceChannel) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update t_config_service "
					+ "  set linked_ser_id = ?, ser_type_id =? , service_channel_id = ?"
					+ " where config_service_srno=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setInt(j++, objServiceChannel.getLinkedServiceId());
			ps.setInt(j++, objServiceChannel.getConfigServiceTypeId());
			if (objServiceChannel.getServiceChannel() != null
					&& objServiceChannel.getServiceChannel()
							.getServiceChannelID() != 0) {
				ps.setInt(j++, objServiceChannel.getServiceChannel()
						.getServiceChannelID());
			} else {
				ps.setInt(j++, 0);
			}

			// /where part
			ps.setInt(j++, objServiceChannel.getConfigServiceSrNo());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "datasource description updated to database ";
			} else {
				msg = "Unable to update web service to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON update datasource description to database ", e);
			msg = "Error in update datasource description to database  "
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

	/** Delete Config Service */
	public String deleteConfigService(int configServiceSrNo) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_config_service where config_service_srno=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, configServiceSrNo);

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

	/** Fetch All Config service Types */
	public List<ConfigServiceType> fetchAllConfigServiceTypes() {
		List<ConfigServiceType> serviceList = new ArrayList<ConfigServiceType>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from conf_service_type ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ConfigServiceType objQueryService = new ConfigServiceType();

				objQueryService.setConfigSerTypeID(rs
						.getInt("conf_ser_type_id"));
				objQueryService.setConfigSerTypeName(rs
						.getString("conf_ser_type_name"));

				int defaultChannelID = rs.getInt("default_channel_id");

				IAgentServiceChannel objServiceChannel = new IAgentServiceChannel();
				if (defaultChannelID > 0) {
					objServiceChannel.setServiceChannelID(defaultChannelID);

				} else {
					objServiceChannel.setServiceChannelID(0);
				}

				objServiceChannel.setServiceChannelName("");

				objQueryService.setDefaultChannel(objServiceChannel);

				serviceList.add(objQueryService);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service list from database ", e);
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

	/** update Config Service Type */
	public String updateConfigServiceType(
			ConfigServiceType objServiceChannelType) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update conf_service_type "
					+ "  set default_channel_id = ?"
					+ " where conf_ser_type_id = ?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setInt(j++, objServiceChannelType.getDefaultChannel()
					.getServiceChannelID());

			// /where part
			ps.setInt(j++, objServiceChannelType.getConfigSerTypeID());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "datasource description updated to database ";
			} else {
				msg = "Unable to update web service to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON update datasource description to database ", e);
			msg = "Error in update datasource description to database  "
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
}
