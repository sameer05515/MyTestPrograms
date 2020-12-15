package com.p.serviceimpl;

import org.springframework.stereotype.Component;

import com.p.httpsClient.ConnectHttps;
import com.p.service.NoticeApiService;

@Component
public class NoticeApiServiceImpl implements NoticeApiService {

	@Override
	public String GetToken(String url, String auth_type, String nonce) {
		String resp = ConnectHttps.getResourceCredentials(url, auth_type, nonce);

		return resp;
	}

	@Override
	public String Get_cd_trades_inquiry(String url, String request, String auth_type, String nonce) {
		String resp = ConnectHttps.getResponseCDFromHttps(url, request, auth_type, nonce);
		return resp;
	}

	@Override
	public String Get_cm_trades_inquiry(String url, String request, String auth_type, String nonce) {
		String resp = ConnectHttps.getcmResponseFromHttps(url, request, auth_type, nonce);

		return resp;
	}

	@Override
	public String Get_fo_trades_inquiry(String url, String request, String auth_type, String nonce) {
		String resp = ConnectHttps.getfoResponseFromHttps(url, request, auth_type, nonce);

		return resp;

	}

	@Override
	public String Get_fo_action_inquiry(String url, String request, String auth_type, String nonce) {

		return null;
	}

	@Override
	public String GetTradeInquiry(String url, String request, String auth_type, String nonce) {
		// ConnectHttps.tradeEquery(url, request,auth_type,nonce);
		// ConnectHttps.getResponseFromHttps(url, request,auth_type,nonce);
		return null;
	}

}
