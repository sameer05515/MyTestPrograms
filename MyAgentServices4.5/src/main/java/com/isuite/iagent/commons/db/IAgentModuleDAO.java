package com.isuite.iagent.commons.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.DBUtil;

public class IAgentModuleDAO {
	private static final Logger log = Logger.getLogger(IAgentModuleDAO.class);

	public List<IAgentModuleVO> getEnabledIAgentModules() {
		PreparedStatement ps = null;
		Connection con = null;
		ArrayList<IAgentModuleVO> modules = new ArrayList<IAgentModuleVO>();
		try {
			con = DBUtil.getInstance().getConnection();
			String sql = "select * from t_iagent_module where is_enabled=1";
			ps = con.prepareStatement(sql); 

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				IAgentModuleVO moduleVO = new IAgentModuleVO();
				moduleVO.setModuleClassName(rs.getString("class_name"));
				moduleVO.setModuleName(rs.getString("module_name"));
				modules.add(moduleVO);
			}
		} catch (SQLException e) {
			log.error("unable to load iagent modules from database ", e);
		} catch (Exception e) {
			log.error("unable to load iagent modules from database ", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e1) {
			}
			try {
				if (con != null) {
					con.close();
				}

			} catch (Exception e) {
			}
		}
		return modules;
	}
}
