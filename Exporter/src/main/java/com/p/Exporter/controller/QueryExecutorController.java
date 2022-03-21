package com.p.Exporter.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p.Exporter.config.DataSourceConfig;
import com.p.Exporter.exception.InvalidInputSuppliedException;
import com.p.Exporter.pojo.DBQueryRequestPojo;
import com.p.Exporter.pojo.DataSourcePojo;
import com.p.Exporter.response.DBQueryResponse;
import com.p.Exporter.response.DbQueryRespData;
import com.p.Exporter.response.Response;
import com.p.Exporter.response.ResponseHandler;

@RestController
public class QueryExecutorController {

	@GetMapping("pojoStructure")
	public ResponseEntity<Object> getPojoStructure(
			@RequestParam(value = "name", defaultValue = "DBQueryRequestPojo", required = false) String name) {
		ResponseEntity<Object> response = null;
		Response ir = new Response();
		boolean exceptionOccuredWhileDataProcessing=false;
		try {
			ir.setStatus("Success");
			ir.setMessage("Data recieved");
			switch (name.toLowerCase()) {
			case "dbqueryrequestpojo":
				DBQueryRequestPojo data=new DBQueryRequestPojo();
				data.setDataSourcePojo(new DataSourcePojo());
				data.setQuery("select 1");
				ir.setData(data);
				break;
			default:
				StringBuffer sb = new StringBuffer()
						.append("\"name\" parameter invalid value supplied." + "\nValid values are ");
				sb.append("\n" + "\" DBQueryRequestPojo \"");
				throw new InvalidInputSuppliedException(sb.toString());
			}
		}catch(Exception e) {
			exceptionOccuredWhileDataProcessing = true;
			ir.setStatus("Exception");
			if (e instanceof InvalidInputSuppliedException) {

				ir.setMessage(((InvalidInputSuppliedException) e).getCustomMessage());
				response = ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", ir);
			} else {

				ir.setMessage((e.getMessage()));
				response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail", ir);
			}

			e.printStackTrace();
			return response;
		}

		if (exceptionOccuredWhileDataProcessing) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail", ir);
		} else {
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", ir);
		}
		return response;

	}

	@PostMapping("/excuteQuery")
	public ResponseEntity<Object> executeQuery(@RequestBody DBQueryRequestPojo objDBQueryRequestPojo) {

		ResponseEntity<Object> response = null;
		boolean exceptionOccuredWhileDataProcessing = false;
		DBQueryResponse ir = new DBQueryResponse();
		System.out.println("===>> objDBQueryRequestPojo"+objDBQueryRequestPojo);

		try (Connection con = DataSourceConfig.getConnection(objDBQueryRequestPojo.getDataSourcePojo());) {

			//System.out.println();
			ir.setStatus("Success");
			ir.setMessage("Successfully connected from given Database");
			
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery(objDBQueryRequestPojo.getQuery());
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount=rsmd.getColumnCount();
			System.out.println("========================================================");
			System.out.println("==>>>metadata column count"+colCount);
			for(int i=1;i<=colCount;i++) {
				System.out.print("("+i+")"+rsmd.getColumnLabel(i)+"\t");
			}
			System.out.println("========================================================");
			JSONObject result=new JSONObject();
			JSONArray resultArr=new JSONArray();
			while(rs.next()) {
				JSONObject resultJ=new JSONObject();
				for(int i=1;i<=colCount;i++)
				resultJ.put(rsmd.getColumnLabel(i), rs.getObject(i));
				resultArr.put(resultJ);
			}
			
			result.put("tableData", resultArr);
			result.put("rowCount", resultArr!=null?resultArr.length():0);

			DbQueryRespData data = new DbQueryRespData();
			data.setDataSourcePojo(objDBQueryRequestPojo.getDataSourcePojo());
			data.setQuery(objDBQueryRequestPojo.getQuery());
			data.setResult(result);
			ir.setData(data);

		} catch (Exception e) {

			exceptionOccuredWhileDataProcessing = true;
			ir.setStatus("Exception");
			if (e instanceof InvalidInputSuppliedException) {

				ir.setMessage(((InvalidInputSuppliedException) e).getCustomMessage());
				response = ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, "Fail", ir);
			} else {

				ir.setMessage((e.getMessage()));
				response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail", ir);
			}

			e.printStackTrace();
			return response;
		}

		if (exceptionOccuredWhileDataProcessing) {
			response = ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Fail", ir);
		} else {
			response = ResponseHandler.generateResponse(HttpStatus.OK, false, "Success", ir);
		}
		
		//System.out.println("ir ===>> "+ir);
		return response;
	}

}
