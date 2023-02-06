package com.ist.iagent.admin.datasource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.dao.DataSourceDAO;
import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.util.PropertyUtil;

import flex.messaging.io.amf.ASObject;

public class DBQueryServiceRPC {

	private static final Logger log = Logger.getLogger(DBQueryServiceRPC.class);

	/** Regular Expression Strings */
	private static final String callableStmtOutRegEX = "@[a-zA-Z0-9]*";
	private static final String sqlParameterRegEx = "#[a-zA-Z0-9_]*#";

	public static final String QUERY_RESULT_TYPE_VALUE_OBJECT = "Value Object";
	public static final String QUERY_RESULT_TYPE_TABULAR_DATA = "Tabular Data";

	/** Maximum number of query results */
	private String maxQueryServiceResults = PropertyUtil.getInstance()
			.getValueForKey("max_query_service_results");
	private int maxRows = Integer.parseInt(maxQueryServiceResults);

	public boolean dbConnEstablished(DataSource objDataSource) {
		log.debug("checking connection");
		boolean connSuccessful = false;
		Connection conn = null;
		String url = objDataSource.getDbURL();
		String driver = objDataSource.getDbDriverClassName();
		String userName = objDataSource.getDbUsername();
		String password = objDataSource.getDbPassword();
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			connSuccessful = true;
			log.debug("Connected to the database alias :- "
					+ objDataSource.getDbAlias());
			conn.close();
		} catch (Exception e) {
			connSuccessful = false;
			log.error("Error in connection to database alias : - "
					+ objDataSource.getDbAlias(), e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {

			}
		}
		return connSuccessful;
	}

	private Connection getConnectionForDbID(int linkedDbId) throws Exception {
		Connection con = null;
		DataSource ds = new DataSourceDAO().fetchDataSourceForDBid(linkedDbId);
		con = getConnectionForDataSource(ds);
		return con;
	}

	private Connection getConnectionForDataSource(DataSource objDataSource)
			throws Exception {
		Connection conn = null;
		String url = objDataSource.getDbURL();
		String driver = objDataSource.getDbDriverClassName();
		String userName = objDataSource.getDbUsername();
		String password = objDataSource.getDbPassword();

		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(url, userName, password);
		log.debug("Connected to the database alias :- "
				+ objDataSource.getDbAlias());

		return conn;
	}

	public String executeQuery(QueryService objQueryService) throws Exception {
		/**
		 * if given Query is stored procedure then the result will be calculated
		 * using CallableStatement
		 * */
		if (objQueryService.getStoredProc()) {
			return executeStoredProcedure(objQueryService);
		}

		/**
		 * other wise given Query result will be computed in this method
		 * */
		PreparedStatement ps = null;
		String sql = "";

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		ps = conn.prepareStatement(sql);

		/**
		 * checking if query is for update delete or insert
		 * */
		String[] tokens = sql.split(" ");
		if ((tokens[0] != null)
				&& (!tokens[0].trim().equalsIgnoreCase("select"))) {
			boolean isSuccess = ps.executeUpdate() > 0 ? true : false;
			if (isSuccess == true) {
				ps.close();
				return "<table>" + "</table>";
			} else {
				throw new Exception("unable to execute given query");
			}
		}

		ps.setMaxRows(maxRows);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = ps.getMetaData();
		log.trace("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			columnName.add(rsmd.getColumnName(count));
		}

		String resultString = "<table>";
		while (rs.next() /** && (qCount++<=queryResultCount) */
		) {
			resultString += "\n\t<column>";
			for (int count = 1; count <= rsmd.getColumnCount(); count++) {

				resultString += "\n\t\t<" + columnName.get(count - 1) + "> "
						+ rs.getString(count) + " </"
						+ columnName.get(count - 1) + ">";
			}
			resultString += "\n\t</column>";
		}
		resultString += "\n</table>";
		conn.close();
		if (resultString != null) {
			log.trace("Successfully executed Query ");
			log.trace("Sql executed is :" + sql);
			log.trace("Sql Result :-" + resultString);
		}
		return resultString;
	}

	private String executeStoredProcedure(QueryService objQueryService)
			throws Exception {

		CallableStatement cs = null;
		String sql = "";
		ResultSet rs = null;

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		cs = conn.prepareCall("{" + sql + "}");

		List<String> output = fetchOutParameters(sql);
		if (output != null && output.size() > 0) {
			log.debug("Got Output parameters :-" + output);
			String retSqlQuery = "select ";
			int count = 0;
			for (String str : output) {
				retSqlQuery += " " + str;
				if (count++ < (output.size() - 1)) {
					retSqlQuery += " , ";
				} else {
					retSqlQuery += " ; ";
				}
			}
			log.debug("Additional Query going to be executed :-" + retSqlQuery);
			cs.execute(retSqlQuery);
			rs = cs.getResultSet();
		} else {
			cs.setMaxRows(maxRows);
			cs.execute();
			rs = cs.getResultSet();
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		log.debug("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			/**
			 * log.debug("rsmd.getColumnTypeName(count)=="+rsmd.
			 * getColumnTypeName(count));
			 */
			columnName.add(rsmd.getColumnName(count));
		}
		String resultString = "<table>";
		while (rs.next()) {
			resultString += "\n\t<column>";
			for (int count = 1; count <= rsmd.getColumnCount(); count++) {

				resultString += "\n\t\t<" + columnName.get(count - 1) + "> "
						+ rs.getString(count) + " </"
						+ columnName.get(count - 1) + ">";
			}
			resultString += "\n\t</column>";
		}
		resultString += "\n</table>";
		conn.close();

		if (resultString != null) {
			log.debug("Successfully executed Query [" + sql
					+ "] And got a result :- " + resultString);
		}
		return resultString;
	}

	private List<String> fetchOutParameters(String s) {
		Pattern pattern = Pattern.compile(callableStmtOutRegEX);
		List<String> output = new ArrayList<String>();
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			output.add(matcher.group());
		}
		return output;
	}

	private String makeAdditionalQuery(List<String> output) {
		String retSqlQuery = "Select";
		int count = 0;
		for (String str : output) {
			retSqlQuery += " " + str;
			if (count++ < (output.size() - 1)) {
				retSqlQuery += " , ";
			} else {
				retSqlQuery += " ; ";
			}
		}
		return retSqlQuery;
	}

	/**
	 * 
	 * #######################################
	 * 
	 * Additional Method to get result from query execution as an ASObject or
	 * List<ASObject>
	 * 
	 * #######################################
	 * 
	 * */

	// /////////////

	public ASObject executeQueryToGetSingleData(QueryService objQueryService)
			throws Exception {
		/**
		 * if given Query is stored procedure then the result will be calculated
		 * using CallableStatement
		 * */
		if (objQueryService.getStoredProc()) {
			return executeStoredProcedureToGetSingleData(objQueryService);
		}

		/**
		 * other wise given Query result will be computed in this method
		 * */
		PreparedStatement ps = null;
		String sql = "";

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		ps = conn.prepareStatement(sql);

		/**
		 * checking if query is for update delete or insert
		 * */
		String[] tokens = sql.split(" ");
		if ((tokens[0] != null)
				&& (!tokens[0].trim().equalsIgnoreCase("select"))) {
			boolean isSuccess = ps.executeUpdate() > 0 ? true : false;
			if (isSuccess == true) {
				ps.close();
				// return "<table>" + "</table>";
				return new ASObject();
			} else {
				throw new Exception("unable to execute given query");
			}
		}

		ps.setMaxRows(maxRows);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = ps.getMetaData();
		log.debug("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			columnName.add(rsmd.getColumnName(count));
		}

		// String resultString = "<table>";
		// while (rs.next() /** && (qCount++<=queryResultCount) */
		// ) {
		// resultString += "\n\t<column>";
		// for (int count = 1; count <= rsmd.getColumnCount(); count++) {
		//
		// resultString += "\n\t\t<" + columnName.get(count - 1) + "> "
		// + rs.getString(count) + " </"
		// + columnName.get(count - 1) + ">";
		// }
		// resultString += "\n\t</column>";
		// }
		// resultString += "\n</table>";

		ASObject resultObject = new ASObject();

		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			resultObject.put(columnName.get(count - 1), null);
		}
		if (rs.next() /** && (qCount++<=queryResultCount) */
		) {
			resultObject = new ASObject();

			for (int count = 1; count <= rsmd.getColumnCount(); count++) {
				resultObject
						.put(columnName.get(count - 1), rs.getString(count));
			}
		}
		conn.close();
		if (resultObject != null) {
			log.trace("Successfully executed Query ");
			log.trace("Sql executed is :" + sql);
			log.trace("Sql Result :-" + resultObject);
		}
		return resultObject;
	}

	private ASObject executeStoredProcedureToGetSingleData(
			QueryService objQueryService) throws Exception {

		CallableStatement cs = null;
		String sql = "";
		ResultSet rs = null;

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		cs = conn.prepareCall("{" + sql + "}");

		List<String> output = fetchOutParameters(sql);
		if (output != null && output.size() > 0) {
			log.debug("Got Output parameters :-" + output);
			String retSqlQuery = "select ";
			int count = 0;
			for (String str : output) {
				retSqlQuery += " " + str;
				if (count++ < (output.size() - 1)) {
					retSqlQuery += " , ";
				} else {
					retSqlQuery += " ; ";
				}
			}
			log.debug("Additional Query going to be executed :-" + retSqlQuery);
			cs.execute(retSqlQuery);
			rs = cs.getResultSet();
		} else {
			cs.setMaxRows(maxRows);
			cs.execute();
			rs = cs.getResultSet();
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		log.debug("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			/**
			 * log.debug("rsmd.getColumnTypeName(count)=="+rsmd.
			 * getColumnTypeName(count));
			 */
			columnName.add(rsmd.getColumnName(count));
		}
		// String resultString = "<table>";
		// while (rs.next()) {
		// resultString += "\n\t<column>";
		// for (int count = 1; count <= rsmd.getColumnCount(); count++) {
		//
		// resultString += "\n\t\t<" + columnName.get(count - 1) + "> "
		// + rs.getString(count) + " </"
		// + columnName.get(count - 1) + ">";
		// }
		// resultString += "\n\t</column>";
		// }
		// resultString += "\n</table>";

		ASObject resultObject = new ASObject();

		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			resultObject.put(columnName.get(count - 1), null);
		}
		if (rs.next() /** && (qCount++<=queryResultCount) */
		) {
			resultObject = new ASObject();

			for (int count = 1; count <= rsmd.getColumnCount(); count++) {
				resultObject
						.put(columnName.get(count - 1), rs.getString(count));
			}
		}
		conn.close();

		if (resultObject != null) {
			log.trace("Successfully executed Query [" + sql
					+ "] And got a result :- " + resultObject);
		}
		return resultObject;
	}

	// /////////////

	public List<ASObject> executeQueryToGetTabularData(
			QueryService objQueryService) throws Exception {
		/**
		 * if given Query is stored procedure then the result will be calculated
		 * using CallableStatement
		 * */
		if (objQueryService.getStoredProc()) {
			return executeStoredProcedureToGetTabularData(objQueryService);
		}

		/**
		 * other wise given Query result will be computed in this method
		 * */
		PreparedStatement ps = null;
		String sql = "";

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		ps = conn.prepareStatement(sql);

		/**
		 * checking if query is for update delete or insert
		 * */
		String[] tokens = sql.split(" ");
		if ((tokens[0] != null)
				&& (!tokens[0].trim().equalsIgnoreCase("select"))) {
			boolean isSuccess = ps.executeUpdate() > 0 ? true : false;
			if (isSuccess == true) {
				ps.close();
				// return "<table>" + "</table>";
				return new ArrayList<ASObject>();
			} else {
				throw new Exception("unable to execute given query");
			}
		}

		ps.setMaxRows(maxRows);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = ps.getMetaData();
		log.trace("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			columnName.add(rsmd.getColumnName(count));
		}

		List<ASObject> resultList = new ArrayList<ASObject>();
		while (rs.next() /** && (qCount++<=queryResultCount) */
		) {
			ASObject resultObject = new ASObject();
			for (int count = 1; count <= rsmd.getColumnCount(); count++) {
				resultObject
						.put(columnName.get(count - 1), rs.getString(count));
			}
			resultList.add(resultObject);
		}
		conn.close();
		if (resultList != null) {
			log.trace("Successfully executed Query ");
			log.trace("Sql executed is :" + sql);
			log.trace("Sql Result :-" + resultList);
		}
		return resultList;
	}

	private List<ASObject> executeStoredProcedureToGetTabularData(
			QueryService objQueryService) throws Exception {

		CallableStatement cs = null;
		String sql = "";
		ResultSet rs = null;

		Connection conn = getConnectionForDbID(objQueryService.getLinkedDbID());
		sql = objQueryService.getQueryText();
		List<IAgentServiceParameter> inpType = objQueryService.getInputType();

		/**
		 * replace the parameter name(s) with given value(s) in query
		 * */
		Pattern pattern = Pattern.compile(sqlParameterRegEx);
		Matcher matcher = pattern.matcher(sql);
		int mCount = 0;
		while (matcher.find()) {
			sql = sql.replace(matcher.group(),
					((QueryParameter) inpType.get(mCount)).getParamValue());
			mCount++;
		}
		log.debug("Resulting SQL Query Going To Be Executed :-" + sql + "");
		cs = conn.prepareCall("{" + sql + "}");

		List<String> output = fetchOutParameters(sql);
		if (output != null && output.size() > 0) {
			log.debug("Got Output parameters :-" + output);
			String retSqlQuery = "select ";
			int count = 0;
			for (String str : output) {
				retSqlQuery += " " + str;
				if (count++ < (output.size() - 1)) {
					retSqlQuery += " , ";
				} else {
					retSqlQuery += " ; ";
				}
			}
			log.debug("Additional Query going to be executed :-" + retSqlQuery);
			cs.execute(retSqlQuery);
			rs = cs.getResultSet();
		} else {
			cs.setMaxRows(maxRows);
			cs.execute();
			rs = cs.getResultSet();
		}

		ResultSetMetaData rsmd = rs.getMetaData();
		log.debug("Total Column in result : " + rsmd.getColumnCount());
		List<String> columnName = new ArrayList<String>();
		for (int count = 1; count <= rsmd.getColumnCount(); count++) {
			/**
			 * log.debug("rsmd.getColumnTypeName(count)=="+rsmd.
			 * getColumnTypeName(count));
			 */
			columnName.add(rsmd.getColumnName(count));
		}

		List<ASObject> resultList = new ArrayList<ASObject>();
		while (rs.next() /** && (qCount++<=queryResultCount) */
		) {
			ASObject resultObject = new ASObject();
			for (int count = 1; count <= rsmd.getColumnCount(); count++) {
				resultObject
						.put(columnName.get(count - 1), rs.getString(count));
			}
			resultList.add(resultObject);
		}
		conn.close();

		if (resultList != null) {
			log.trace("Successfully executed Query [" + sql
					+ "] And got a result :- " + resultList);
		}
		return resultList;
	}
}
