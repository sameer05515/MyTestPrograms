package com.p.batch;

import java.sql.Timestamp;
import java.util.HashMap;

public class ConstantTokens {

	private static String token="not_available";
	private static String tokenv=null;
	//private static String tokenv="f2e20006-db86-4c43-871f-e6da0a595306";
	private static ConstantTokens obj=null;
	private static HashMap<String,Timestamp> map=null;
	
	
	private ConstantTokens(){}

	public static ConstantTokens getInstance()
	{
		if(obj==null)
		{
			synchronized(ConstantTokens.class)
			{
				if(obj==null)
				{
					tokenv=null;
					token="not_available";
					obj=new ConstantTokens();
				}
			}
		}
		return obj;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		ConstantTokens.token = token;
	}

	public static ConstantTokens getObj() {
		return obj;
	}

	public static void setObj(ConstantTokens obj) {
		ConstantTokens.obj = obj;
	}

	public static HashMap<String, Timestamp> getMap() {
		return map;
	}

	public static void setMap(HashMap<String, Timestamp> map) {
		ConstantTokens.map = map;
	}

	public static String getTokenv() {
		return tokenv;
	}

	public static void setTokenv(String tokenv) {
		ConstantTokens.tokenv = tokenv;
	}
	
	
}
