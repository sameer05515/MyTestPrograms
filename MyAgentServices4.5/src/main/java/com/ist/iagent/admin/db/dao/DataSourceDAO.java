package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.util.DBUtil;

public class DataSourceDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(DataSourceDAO.class);

	public String saveDataSource(DataSource objDataSource) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			/** First generate next id */
			int wsId = generateNextwsID();
			objDataSource.setDbID(wsId + 1);

			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_db_desc "
					+ " (db_id , db_alias , db_user_name , db_password , db_driver_name , db_url ) "
					+ " values (?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, objDataSource.getDbID());
			ps.setString(j++, objDataSource.getDbAlias());
			ps.setString(j++, objDataSource.getDbUsername());
			ps.setString(j++, objDataSource.getDbPassword());
			ps.setString(j++, objDataSource.getDbDriverClassName());
			ps.setString(j++, objDataSource.getDbURL());

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "datasource description added to database ";
			} else {
				msg = "Unable to add datasource description to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON save datasource description to database ", e);
			msg = "Error in saving datasource description to database  "
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

	public int generateNextwsID() {
		int wsId = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(db_id) as max from t_db_desc ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				wsId = rs.getInt(1);
			} else {
				throw new Exception("unable to generate db id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON unable to generate db id from database ", e);
		} catch (Exception e) {
			log.error("EROOR ON unable to generate db id from database ", e);
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
		return wsId;
	}

	public List<DataSource> getDataSourceList() {
		List<DataSource> wsdlList = new ArrayList<DataSource>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_db_desc";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DataSource objWSdlDTO = new DataSource();
				objWSdlDTO.setDbID(rs.getInt("db_id"));
				objWSdlDTO.setDbAlias(rs.getString("db_alias"));
				objWSdlDTO.setDbUsername(rs.getString("db_user_name"));
				objWSdlDTO.setDbPassword(rs.getString("db_password"));
				objWSdlDTO.setDbDriverClassName(rs.getString("db_driver_name"));
				objWSdlDTO.setDbURL(rs.getString("db_url"));
				wsdlList.add(objWSdlDTO);
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON getting datasource description list from database ",
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
		return wsdlList;
	}

	public List<DataSource> getDataSourceList(Connection externalCon) {
		List<DataSource> wsdlList = new ArrayList<DataSource>();
		PreparedStatement ps = null;

		try {
			// con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_db_desc";
			ps = externalCon.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DataSource objWSdlDTO = new DataSource();
				objWSdlDTO.setDbID(rs.getInt("db_id"));
				objWSdlDTO.setDbAlias(rs.getString("db_alias"));
				objWSdlDTO.setDbUsername(rs.getString("db_user_name"));
				objWSdlDTO.setDbPassword(rs.getString("db_password"));
				objWSdlDTO.setDbDriverClassName(rs.getString("db_driver_name"));
				objWSdlDTO.setDbURL(rs.getString("db_url"));
				wsdlList.add(objWSdlDTO);
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON getting datasource description list from database ",
					e);
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
		return wsdlList;
	}

	/** updateDataSource */
	public String updateDataSource(DataSource objDataSource) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "update t_db_desc "
					+ "  set db_alias =? , db_user_name = ?,db_password=?,db_driver_name=? , db_url=? "
					+ " where db_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, objDataSource.getDbAlias());
			ps.setString(j++, objDataSource.getDbUsername());
			ps.setString(j++, objDataSource.getDbPassword());
			ps.setString(j++, objDataSource.getDbDriverClassName());
			ps.setString(j++, objDataSource.getDbURL());
			// /where part
			ps.setInt(j++, objDataSource.getDbID());

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

	public String deleteDataSource(int dbID) {

		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_db_desc where db_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, dbID);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "datasource description deleted from database ";
			} else {
				msg = "Unable to delete datasource description from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete datasource description from database ",
					e);
			msg = "Error in deleting datasource description from database  "
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

	public DataSource fetchDataSourceForDBid(int dbID) {

		PreparedStatement ps = null;
		DataSource objDataSource = new DataSource();
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_db_desc where db_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, dbID);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				objDataSource.setDbID(rs.getInt("db_id"));
				objDataSource.setDbAlias(rs.getString("db_alias"));
				objDataSource.setDbUsername(rs.getString("db_user_name"));
				objDataSource.setDbPassword(rs.getString("db_password"));
				objDataSource.setDbDriverClassName(rs
						.getString("db_driver_name"));
				objDataSource.setDbURL(rs.getString("db_url"));
			} else {
				throw new SQLException("error in fetching");
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetching DataSource details from database ", e);
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
		return objDataSource;
	}

}
