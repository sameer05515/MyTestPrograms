package com.ist.iagent.admin.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.apache.log4j.Logger;

import com.ibm.wsdl.factory.WSDLFactoryImpl;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.util.DBUtil;

public class WSdlDAO {

	private Connection con;
	private static final Logger log = Logger.getLogger(WSdlDAO.class);

	public String saveWSDL(WSdlDTO objWSdlDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			/** First generate next id */
			int wsId = generateNextwsID();
			objWSdlDTO.setWsId(wsId + 1);

			con = DBUtil.getInstance().getConnection();
			String sql = "insert into t_wsdl "
					+ " (ws_id , ws_name , wsdl_url,namespace,useprefix"
					+ ",soap_binding_interface,service_class,ws_package_name) "
					+ " values (?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			int j = 1;
			ps.setInt(j++, objWSdlDTO.getWsId());
			ps.setString(j++, objWSdlDTO.getWsName());
			ps.setString(j++, objWSdlDTO.getWsURL());
			// ps.setString(j++, getNameSpace(objWSdlDTO.getWsURL()));
			// ps.setString(j++, getNameSpace(objWSdlDTO.getUsePrefix()));

			ps.setString(j++, "");
			ps.setString(j++, "");

			ps.setString(j++, objWSdlDTO.getSoapBindingInterface());
			ps.setString(j++, objWSdlDTO.getServiceClass());
			ps.setString(j++, objWSdlDTO.getWsPackageName());

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

	private String getNameSpace(String wsdl) {
		Definition implDef = null;

		/** first get the definition object got the WSDL impl */
		try {
			WSDLFactory factory = new WSDLFactoryImpl();
			WSDLReader reader = factory.newWSDLReader();
			implDef = reader.readWSDL(wsdl);
		} catch (WSDLException e) {
			log.error("Unable to retreice NameSpace for WSDL " + wsdl, e);
		}

		if (implDef == null) {
			log.error("Unable to retreice NameSpace for WSDL "
					+ wsdl
					+ " populating empty string. It will be an extra load on calling service.");
			return "";
		}

		return implDef.getTargetNamespace();

	}

	public int generateNextwsID() {
		int wsId = 0;
		PreparedStatement ps = null;
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select max(ws_id) as max from t_wsdl ";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				wsId = rs.getInt(1);
			} else {
				throw new Exception("unable to generate service id");
			}

		} catch (SQLException e) {
			log.error(
					"EROOR ON unable to generate web service id from database ",
					e);
		} catch (Exception e) {
			log.error(
					"EROOR ON unable to generate web service id from database ",
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
		return wsId;
	}

	public List<WSdlDTO> getWSDLList() {
		List<WSdlDTO> wsdlList = new ArrayList<WSdlDTO>();
		PreparedStatement ps = null;

		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_wsdl";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				WSdlDTO objWSdlDTO = new WSdlDTO();
				objWSdlDTO.setWsId(rs.getInt("ws_id"));
				objWSdlDTO.setWsName(rs.getString("ws_name"));
				objWSdlDTO.setWsURL(rs.getString("wsdl_url"));
				objWSdlDTO.setNameSpace(rs.getString("nameSpace"));
				objWSdlDTO.setUsePrefix(rs.getString("useprefix"));

				objWSdlDTO.setSoapBindingInterface(rs
						.getString("soap_binding_interface"));
				objWSdlDTO.setServiceClass(rs.getString("service_class"));
				objWSdlDTO.setWsPackageName(rs.getString("ws_package_name"));
				wsdlList.add(objWSdlDTO);
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
		return wsdlList;
	}

	public List<WSdlDTO> getWSDLList(Connection externalCon) {
		List<WSdlDTO> wsdlList = new ArrayList<WSdlDTO>();
		PreparedStatement ps = null;

		try {
			String sql = "select * from t_wsdl";
			ps = externalCon.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				WSdlDTO objWSdlDTO = new WSdlDTO();
				objWSdlDTO.setWsId(rs.getInt("ws_id"));
				objWSdlDTO.setWsName(rs.getString("ws_name"));
				objWSdlDTO.setWsURL(rs.getString("wsdl_url"));
				objWSdlDTO.setNameSpace(rs.getString("nameSpace"));
				objWSdlDTO.setUsePrefix(rs.getString("useprefix"));

				objWSdlDTO.setSoapBindingInterface(rs
						.getString("soap_binding_interface"));
				objWSdlDTO.setServiceClass(rs.getString("service_class"));
				objWSdlDTO.setWsPackageName(rs.getString("ws_package_name"));

				wsdlList.add(objWSdlDTO);
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
		return wsdlList;
	}

	/** updateWSDL */
	public String updateWSDL(WSdlDTO objWSdlDTO) {
		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();// +
														// ",soap_binding_interface,service_class,ws_package_name) "
			String sql = "update t_wsdl "
					+ "  set ws_name =? , wsdl_url = ?,namespace=?,useprefix=? "
					+ ",soap_binding_interface=?,service_class=?,ws_package_name=?"
					+ " where ws_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;

			ps.setString(j++, objWSdlDTO.getWsName());
			ps.setString(j++, objWSdlDTO.getWsURL());
			ps.setString(j++, objWSdlDTO.getNameSpace());
			ps.setString(j++, objWSdlDTO.getUsePrefix());

			ps.setString(j++, objWSdlDTO.getSoapBindingInterface());
			ps.setString(j++, objWSdlDTO.getServiceClass());
			ps.setString(j++, objWSdlDTO.getWsPackageName());

			// /where part
			ps.setInt(j++, objWSdlDTO.getWsId());

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

	public String deleteWSDL(int wsId) {

		boolean isSuccess = false;
		String msg = "";
		PreparedStatement ps = null;
		try {
			msg = "";
			con = DBUtil.getInstance().getConnection();
			String sql = "delete from t_wsdl where ws_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, wsId);

			isSuccess = ps.executeUpdate() > 0 ? true : false;

			if (isSuccess) {
				msg = "web service deleted from database ";
			} else {
				msg = "Unable to delete web service from database ";
			}

		} catch (SQLException e) {
			log.error("EROOR ON delete web service from database ", e);
			msg = "Error in deleting web service from database  "
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

	public WSdlDTO fetchWSdlDTOForWSDLid(int wsId) {

		PreparedStatement ps = null;
		WSdlDTO objWSdlDTO = new WSdlDTO();
		try {

			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_wsdl where ws_id=?";
			ps = con.prepareStatement(sql);
			int j = 1;
			ps.setInt(j++, wsId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				objWSdlDTO.setWsId(rs.getInt("ws_id"));
				objWSdlDTO.setWsName(rs.getString("ws_name"));
				objWSdlDTO.setWsURL(rs.getString("wsdl_url"));
				objWSdlDTO.setNameSpace(rs.getString("namespace"));
				objWSdlDTO.setUsePrefix(rs.getString("useprefix"));

				objWSdlDTO.setSoapBindingInterface(rs
						.getString("soap_binding_interface"));
				objWSdlDTO.setServiceClass(rs.getString("service_class"));
				objWSdlDTO.setWsPackageName(rs.getString("ws_package_name"));
			} else {
				throw new SQLException("error in fethching");
			}

		} catch (SQLException e) {
			log.error("EROOR ON fetching wsdlDTO from database ", e);
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
		return objWSdlDTO;
	}

}
