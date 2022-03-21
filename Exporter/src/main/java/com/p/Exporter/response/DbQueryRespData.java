package com.p.Exporter.response;

import java.util.Map;

import org.json.JSONObject;

import com.p.Exporter.pojo.DBQueryRequestPojo;
import com.p.Exporter.util.Utility;

public class DbQueryRespData extends DBQueryRequestPojo {
	
	private JSONObject result;
	//private String resultStr;
	private Map<String,Object> resultMap;

//	public JSONObject getResult() {
//		return result;
//	}

	public void setResult(JSONObject result) {
		this.result = result;
		//this.setResultStr(result.toString());
		this.setResultMap(Utility.toMap(result));
	}

//	public String getResultStr() {
//		return resultStr;
//	}
//
//	private void setResultStr(String resultStr) {
//		this.resultStr = resultStr;
//	}

	public Map<String,Object> getResultMap() {
		return resultMap;
	}

	private void setResultMap(Map<String,Object> resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public String toString() {
		return "DbQueryRespData [result=" + result + ", resultMap=" + resultMap + "]";
	}
	

}
