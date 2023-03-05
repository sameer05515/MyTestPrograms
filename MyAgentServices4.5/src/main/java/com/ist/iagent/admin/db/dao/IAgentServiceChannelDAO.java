package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ConfigService;
import com.ist.iagent.admin.db.pojo.IAgentServiceChannel;
import com.ist.iagent.admin.db.pojo.IAgentServiceChannelType;
import com.ist.iagent.admin.util.DBUtil;

public class IAgentServiceChannelDAO {

	private Connection con;
	private static final Logger log = Logger
			.getLogger(IAgentServiceChannelDAO.class);

	/** save service channel */
	public String saveServiceChannel(IAgentServiceChannel objServiceChannel) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			/** First generate next id */
			int configServiceSrNo = getMaxServiceChannelID();
			objServiceChannel.setServiceChannelID(configServiceSrNo + 1);

			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_channel"
					+ " (channel_id , channel_name , request_name , destination , channel_type_id ) "
					+ " values (?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, objServiceChannel.getServiceChannelID());
			ps.setString(j++, objServiceChannel.getServiceChannelName());
			ps.setString(j++, objServiceChannel.getRequestName());
			ps.setString(j++, objServiceChannel.getDestination());
			ps.setInt(j++, objServiceChannel.getChannelType().getId());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "Configured Service Details added to database ";

			} else {
				msg = "Unable to add Configured Service Details to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON save Service Channel Details to database ", e);
			msg = "Error in saving Service Channel Details to database  "
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

	/** generate new service channel id */
	private int getMaxServiceChannelID() {
		int configServiceSrNo = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(channel_id) as max from t_channel ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				configServiceSrNo = rs.getInt(1);
			} else {
				throw new Exception("unable to generate Service Channel id");
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON unable to generate Service Channel id from database ",
					e);
		} catch (Exception e) {
			log.error(
					"EROOR ON unable to generate Service Channel id from database ",
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

	/** update service channel */
	public String updateServiceChannel(IAgentServiceChannel objServiceChannel) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {

			con = DBUtil.getInstance().getConnection();
			String sql = "update t_channel "
					+ "  set channel_name = ?, request_name =? , destination = ?,  channel_type_id =?"
					+ " where channel_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, objServiceChannel.getServiceChannelName());
			ps.setString(j++, objServiceChannel.getRequestName());
			ps.setString(j++, objServiceChannel.getDestination());
			ps.setInt(j++, objServiceChannel.getChannelType().getId());

			// /where part
			ps.setInt(j++, objServiceChannel.getServiceChannelID());

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

	/** fetch all service channels from database */
	public List<IAgentServiceChannel> getAllServiceChannelList() {
		List<IAgentServiceChannel> serviceChannelList = new ArrayList<IAgentServiceChannel>();
		PreparedStatement ps = null;

		List<IAgentServiceChannelType> channelTypes = getAllServiceChannelTypes();

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_channel ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				IAgentServiceChannel objServiceChannel = new IAgentServiceChannel();
				objServiceChannel.setServiceChannelID(rs.getInt("channel_id"));
				objServiceChannel.setServiceChannelName(rs
						.getString("channel_name"));
				objServiceChannel.setRequestName(rs.getString("request_name"));
				objServiceChannel.setDestination(rs.getString("destination"));

				IAgentServiceChannelType objChannelType = new IAgentServiceChannelType();
				objChannelType.setId(rs.getInt("channel_type_id"));
				objServiceChannel.setChannelType(objChannelType);
				for (IAgentServiceChannelType chType : channelTypes) {
					if (chType.getId() == objChannelType.getId()) {
						objChannelType.setName(chType.getName());
					}
				}

				serviceChannelList.add(objServiceChannel);
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetch service channel list from database ", e);
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
		return serviceChannelList;
	}

	/** Delete service channel for given service channel id */
	public String deleteServiceChannel(int serviceChannelID) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_channel where channel_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, serviceChannelID);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "service channel deleted from database ";
			} else {
				msg = "Unable to delete service channel from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete service channel from database ", e);
			msg = "Error in deleting service channel from database  "
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

	public IAgentServiceChannel getServiceChannelForID(int serviceChannelID) {
		IAgentServiceChannel obj = null;

		PreparedStatement ps = null;

		List<IAgentServiceChannelType> channelTypes = getAllServiceChannelTypes();

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_channel where channel_id = ?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, serviceChannelID);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				IAgentServiceChannel objServiceChannel = new IAgentServiceChannel();
				objServiceChannel.setServiceChannelID(rs.getInt("channel_id"));
				objServiceChannel.setServiceChannelName(rs
						.getString("channel_name"));
				objServiceChannel.setRequestName(rs.getString("request_name"));
				objServiceChannel.setDestination(rs.getString("destination"));

				IAgentServiceChannelType objChannelType = new IAgentServiceChannelType();
				objChannelType.setId(rs.getInt("channel_type_id"));
				for (IAgentServiceChannelType chType : channelTypes) {
					if (chType.getId() == objChannelType.getId()) {
						objChannelType.setName(chType.getName());
					}
				}
				objServiceChannel.setChannelType(objChannelType);

				obj = objServiceChannel;
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON fetch service channel type list from database ",
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

		return obj;
	}

	/** fetch all service channel types */
	public List<IAgentServiceChannelType> getAllServiceChannelTypes() {
		List<IAgentServiceChannelType> serviceList = new ArrayList<IAgentServiceChannelType>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_channel_type ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				IAgentServiceChannelType objChannelType = new IAgentServiceChannelType();
				objChannelType.setId(rs.getInt("id"));
				objChannelType.setName(rs.getString("channel_type_name"));
				serviceList.add(objChannelType);
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON fetch service channel type list from database ",
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
		return serviceList;
	}

}
