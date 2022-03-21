package com.p.appconfiguration;

import java.util.StringTokenizer;

public class PropertiesFile {
	
	private static String cons_key;
	private static String  cons_secret;
	private static String  get_token_url;
	private static String member_code;
	//private static String  trade_enquery_url;
	private static String  cd_trades_inquiry;
	private static String  cm_trades_inquiry;
	private static String  fo_trades_inquiry;
	private static String  fo_action_inquiry;
	
	private static boolean  enable_cd_trades_inquiry;
	private static boolean  enable_cm_trades_inquiry;
	private static boolean  enable_fo_trades_inquiry;
	private static boolean  enable_fo_action_inquiry;
	
	private static String dsusername;		
	private static String dspassword;		
	private static String dsurl;			
	private static String dsdriverclassname;
	private static String logPath;
	private static String api_active_time;
	private static String start;
	private static String stop;
	
	private static String seqLogPath;
	




	
	
	
	
	
	
	public static String getCons_key() {
		return cons_key;
	}
	public static void setCons_key(String cons_key) {
		PropertiesFile.cons_key = cons_key;
	}
	public static String getCons_secret() {
		return cons_secret;
	}
	public static void setCons_secret(String cons_secret) {
		PropertiesFile.cons_secret = cons_secret;
	}
	public static String getGet_token_url() {
		System.out.println("get_token_url: "+ get_token_url);
		return get_token_url;
	}
	public static void setGet_token_url(String get_token_url) {
		PropertiesFile.get_token_url = get_token_url;
	}
	public static String getCd_trades_inquiry() {
		return cd_trades_inquiry;
	}
	public static void setCd_trades_inquiry(String cd_trades_inquiry) {
		String temp_cd_trades_inquiry=null;
		temp_cd_trades_inquiry=cd_trades_inquiry.trim();		
		StringTokenizer t = new StringTokenizer(temp_cd_trades_inquiry, ",");
		String [] str = new String[2];
		int i=0;
		while(t.hasMoreTokens()){
			String k=t.nextToken();
			str[i]=k;
			i=1;
		}		
		/*System.out.println("=============str0>"+ str[0]);
		System.out.println("=============str1>"+ str[1]);*/
		
		PropertiesFile.cd_trades_inquiry = str[0];
		PropertiesFile.enable_cd_trades_inquiry=Boolean.valueOf(str[1]);
		
		
		
	}
	public static String getCm_trades_inquiry() {
		return cm_trades_inquiry;
	}
	public static void setCm_trades_inquiry(String cm_trades_inquiry) {
		System.out.println("cm_trades_inquiry=> "+cm_trades_inquiry);
		
		String temp_cm_trades_inquiry=null;
		temp_cm_trades_inquiry=cm_trades_inquiry.trim();
		StringTokenizer t = new StringTokenizer(temp_cm_trades_inquiry, ",");
		String [] str = new String[2];
		int i=0;
		while(t.hasMoreTokens()){
			String k=t.nextToken();
			str[i]=k;
			i=1;
		}
		PropertiesFile.cm_trades_inquiry =str[0];
		PropertiesFile.enable_cm_trades_inquiry =Boolean.valueOf(str[1]);
	}
	public static String getFo_trades_inquiry() {
		return fo_trades_inquiry;
	}
	public static void setFo_trades_inquiry(String fo_trades_inquiry) {
		String temp_fo_trades_inquiry=null;
		temp_fo_trades_inquiry=fo_trades_inquiry.trim();		
		StringTokenizer t = new StringTokenizer(temp_fo_trades_inquiry, ",");
		String [] str = new String[2];
		int i=0;
		while(t.hasMoreTokens()){
			String k=t.nextToken();
			str[i]=k;
			i=1;
		}
		PropertiesFile.fo_trades_inquiry =str[0];
		PropertiesFile.enable_fo_trades_inquiry =Boolean.valueOf(str[1]);
		
		
	}
	public static String getFo_action_inquiry() {
		return fo_action_inquiry;
	}
	public static void setFo_action_inquiry(String fo_action_inquiry) {
		String temp_fo_action_inquiry=null;
		temp_fo_action_inquiry=fo_action_inquiry.trim();		
		StringTokenizer t = new StringTokenizer(temp_fo_action_inquiry, ",");
		String [] str = new String[2];
		int i=0;
		while(t.hasMoreTokens()){
			String k=t.nextToken();
			str[i]=k;
			i=1;
		}
		PropertiesFile.fo_action_inquiry =str[0];
		PropertiesFile.enable_fo_action_inquiry =Boolean.valueOf(str[1]);
	}
	//----
	public static boolean isEnable_cd_trades_inquiry() {
		return enable_cd_trades_inquiry;
	}
	public static boolean isEnable_cm_trades_inquiry() {
		return enable_cm_trades_inquiry;
	}
	public static boolean isEnable_fo_trades_inquiry() {
		return enable_fo_trades_inquiry;
	}
	public static boolean isEnable_fo_action_inquiry() {
		return enable_fo_action_inquiry;
	}
	public static String getMember_code() {
		return member_code;
	}
	public static void setMember_code(String member_code) {
		PropertiesFile.member_code = member_code;
	}
	public static String getDsusername() {
		return dsusername;
	}
	public static void setDsusername(String dsusername) {
		PropertiesFile.dsusername = dsusername;
	}
	public static String getDspassword() {
		return dspassword;
	}
	public static void setDspassword(String dspassword) {
		PropertiesFile.dspassword = dspassword;
	}
	public static String getDsurl() {
		return dsurl;
	}
	public static void setDsurl(String dsurl) {
		PropertiesFile.dsurl = dsurl;
	}
	public static String getDsdriverclassname() {
		return dsdriverclassname;
	}
	public static void setDsdriverclassname(String dsdriverclassname) {
		PropertiesFile.dsdriverclassname = dsdriverclassname;
	}
	public static String getLogPath() {
		return logPath;
	}
	public static void setLogPath(String logPath) {
		PropertiesFile.logPath = logPath;
	}
	public static String getApi_active_time() {
		return api_active_time;
	}
	public static void setApi_active_time(String api_active_time) {
		PropertiesFile.api_active_time = api_active_time;
		
		String [] time = api_active_time.split("to");
		PropertiesFile.start= time[0].trim();
		PropertiesFile.stop= time[1].trim();
		
		
	}
	public static String getStart() {
		return start;
	}
	public static void setStart(String start) {
		PropertiesFile.start = start;
	}
	public static String getStop() {
		return stop;
	}
	public static void setStop(String stop) {
		PropertiesFile.stop = stop;
	}
	public static String getSeqLogPath() {
		return seqLogPath;
	}
	public static void setSeqLogPath(String seqLogPath) {
		PropertiesFile.seqLogPath = seqLogPath;
	}
	
	
	
	
	
	
	
	
	
	//======
	
	
	
	
	
	
	
	
	/*public static String getTrade_enquery_url() {
		return trade_enquery_url;
	}
	public static void setTrade_enquery_url(String trade_enquery_url) {
		PropertiesFile.trade_enquery_url = trade_enquery_url;
	}*/
	
	
	
	
	
	
	
	

}
