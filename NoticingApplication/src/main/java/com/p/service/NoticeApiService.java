package com.p.service;

public interface NoticeApiService {

	String GetToken(String url, String authenticationType, String nonce);

	String Get_cd_trades_inquiry(String url, String request, String auth_type, String nonce);

	String Get_cm_trades_inquiry(String url, String request, String auth_type, String nonce);

	String Get_fo_trades_inquiry(String url, String request, String auth_type, String nonce);

	String Get_fo_action_inquiry(String url, String request, String auth_type, String nonce);

	String GetTradeInquiry(String url, String request, String authenticationType, String nonce);

}
