package com.ist.iagent.admin.db.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.util.DBUtil;
import com.ist.iagent.admin.util.PropertyUtil;

public class JarDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(JarDAO.class);
	private static String destDir = PropertyUtil.getInstance().getValueForKey(
			"serverLibPath");

	public String saveJarName(String jarName) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			/** First generate next id */
			int jarId = generateNextJarID();

			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_jar (jar_id,jar_name) values (?,?);";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, jarId + 1);
			ps.setString(j++, jarName);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "jar name added to database ";
			} else {
				msg = "Unable to add jar name added to database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON save jar name added to database ", e);
			msg = "Error in saving jar name added to database  "
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

	public int generateNextJarID() {
		int jarid = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(jar_id) as max from t_jar";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				jarid = rs.getInt(1);
			} else {
				throw new Exception("unable to generate jar id");
			}

		} catch (SQLException e) {
			log.error("EROOR ON unable to generate jar id from database ", e);
		} catch (Exception e) {
			log.error("EROOR ON unable to generate jar id from database ", e);
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
		return jarid;
	}

	public List<JarDTO> getJarList(Connection externalCon) {
		List<JarDTO> jarList = new ArrayList<JarDTO>();
		PreparedStatement ps = null;

		try {
			// externalCon = DBUtil.getInstance().getConnection();
			String sql = "select * from t_jar";
			ps = externalCon.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				JarDTO objJarDTO = new JarDTO();
				objJarDTO.setJar_id(rs.getInt("jar_id"));
				objJarDTO.setJar_name(rs.getString("jar_name"));
				File f = new File(destDir + rs.getString("jar_name"));
				if (f.exists()) {
					objJarDTO.setJarExistsOnServer(true);
				} else {
					objJarDTO.setJarExistsOnServer(false);
				}
				jarList.add(objJarDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON get jar list from database ", e);
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
		return jarList;
	}

	public List<JarDTO> getJarList() {
		List<JarDTO> jarList = new ArrayList<JarDTO>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_jar";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				JarDTO objJarDTO = new JarDTO();
				objJarDTO.setJar_id(rs.getInt("jar_id"));
				objJarDTO.setJar_name(rs.getString("jar_name"));
				File f = new File(destDir + rs.getString("jar_name"));
				if (f.exists()) {
					objJarDTO.setJarExistsOnServer(true);
				} else {
					objJarDTO.setJarExistsOnServer(false);
				}
				jarList.add(objJarDTO);
			}

		} catch (SQLException e) {
			log.error("EROOR ON get jar list from database ", e);
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
		return jarList;
	}

	public String deleteJarName(int jarId) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_jar where jar_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, jarId);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "jar name deleted from database ";
			} else {
				msg = "Unable to delete jar from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete jar from database ", e);
			msg = "Error in deleting jar from database  " + e.getMessage();
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

	public String fetchJarName(int jarId) {

		String msg = "";
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_jar where jar_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, jarId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("jar_name");
			}

		} catch (SQLException e) {
			log.error("Error in fetching name of jar from database  ", e);
			msg = "Error in fetching name of jar from database  "
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
