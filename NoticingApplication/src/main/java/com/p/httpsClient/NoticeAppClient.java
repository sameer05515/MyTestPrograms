package com.p.httpsClient;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.p.appconfiguration.DaoUtil;
import com.p.appconfiguration.Log;
import com.p.appconfiguration.Parser;
import com.p.appconfiguration.PropertiesFile;
import com.p.appconfiguration.Utility;
import com.p.batch.ConstantTokens;
import com.p.cd.CdResponse;
import com.p.entity.NoticeLogging;
import com.p.fo.FoResponse;
/*import com.p.entity.NoticeLogging;*/
import com.p.model.Api_Request;
import com.p.model.Api_Version;
import com.p.model.TokenRes;
import com.p.response.cm.CmResponse;
import com.p.serviceimpl.NoticeApiServiceImpl;

@Component
public class NoticeAppClient {

	public TokenRes getResponse1() {
		String cred = null;
		cred = PropertiesFile.getCons_key() + ":" + PropertiesFile.getCons_secret();

		String resp = null;
		Gson g = new Gson();
		NoticeApiServiceImpl obj = null;
		obj = new NoticeApiServiceImpl();
		TokenRes t = null;

		resp = obj.GetToken(PropertiesFile.getGet_token_url(), getBase64Encoded(cred),
				getBase64Encoded(getTimestamp() + ":" + String.valueOf(getRandomNumer())));

		if (resp != null) {
			t = g.fromJson(resp, TokenRes.class);

			Log.notice.info("======>tkn =>" + t.getAccess_token());
		}

		return t;
	}

//	@TrackTime
	public void getResponse4cd() {

		String resp = null;
		CdResponse cdobj = null;
		Api_Request req = null;
		NoticeApiServiceImpl obj = null;
		String msgid = null;
		Gson gson = null;
		String nonce = null;
		gson = new Gson();
		obj = new NoticeApiServiceImpl();
		req = new Api_Request();

		int frmdb;
		int count;
		DaoUtil tt = null;
		tt = new DaoUtil();
		frmdb = tt.getCDseq();

		count = frmdb + 1;

		msgid = PropertiesFile.getMember_code() + getTimestamp1() + getSequenceCD(count);
		req.setMsgId(msgid);
		String tradein = Utility.getMaxCDSequenceNo() + ",ALL,,";
		req.setTradesInquiry(tradein);
		Api_Version v = null;
		v = new Api_Version();
		v.setVersion("1.0");
		v.setRequest(req);
		String request = null;
		request = gson.toJson(v);

		String auth_type = "Bearer " + ConstantTokens.getTokenv();

		nonce = getBase64Encoded(getTimestamp() + ":" + String.valueOf(getRandomNumer()));

		resp = obj.Get_cd_trades_inquiry(PropertiesFile.getCd_trades_inquiry(), request, auth_type, nonce);
		Parser t = new Parser();

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try (PrintStream ps = new PrintStream(
				PropertiesFile.getSeqLogPath() + "\\cd-from-exchg-" + timeStamp + ".txt");) {
			ps.println(resp);
			cdobj = t.getParseCD(resp);
		} catch (Exception e) {

			Log.notice.info("exceprtion during getParseCD method || CD TRADEFILE WRITE", e);
			if (resp != null) {
				if (resp.contains("Exception"))
					Log.notice.info("Exception occured while calling NOTICE api");
				cdobj = new CdResponse();
				cdobj.setStatus(resp);
			}

		}

		try {

			NoticeLogging n = null;

			if (cdobj != null) {
				DaoUtil logging = null;
				logging = new DaoUtil();
				n = new NoticeLogging();
				if (cdobj.getStatus().equalsIgnoreCase("success")) {
					// n.setComments("tradfile written "+ status);
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cd_trades_inquiry");
					n.setRESP_MESSAGESCODE(cdobj.getMessages().getCode());
					n.setRESP_MESSAGEID(cdobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(cdobj.getStatus());
					logging.getCDSave(n);
				} else if (cdobj.getStatus().equalsIgnoreCase("error")) {

					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cd_trades_inquiry");
					n.setRESP_MESSAGESCODE(cdobj.getMessages().getCode());
					n.setRESP_MESSAGEID(cdobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(cdobj.getStatus());
					logging.getCDSave(n);
				} else {

					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cd_trades_inquiry");
					n.setRESP_MESSAGESCODE("NA");
					n.setRESP_MESSAGEID("NA");
					n.setSEQ_NO(count);
					n.setSTATUS(cdobj.getStatus());
					logging.getCDSave(n);
				}
			} else {

				Log.notice.info("Exception occured..d");
			}

		} catch (Exception e) {
			Log.notice.info("Exception occured..during noticelogging", e);
			;
		}

	}

//	@TrackTime
	public void getResponse4cm() {

		String resp = null;
		CmResponse cmobj = null;
		Api_Request req = null;
		NoticeApiServiceImpl obj = null;
		String msgid = null;
		Gson gson = null;
		String nonce = null;
		gson = new Gson();
		obj = new NoticeApiServiceImpl();
		req = new Api_Request();

		int frmdb;
		int count;
		DaoUtil tt = null;
		tt = new DaoUtil();

		frmdb = tt.getCMseq();

		count = frmdb + 1;
		// --

		msgid = PropertiesFile.getMember_code() + getTimestamp1() + getSequenceCM(count);
		req.setMsgId(msgid);
		String tradein = Utility.getMaxCMSequenceNo() + ",ALL,,";
		req.setTradesInquiry(tradein);
		Api_Version v = null;
		v = new Api_Version();
		v.setVersion("1.0");
		v.setRequest(req);
		String request = null;
		request = gson.toJson(v);

		String auth_type = "Bearer " + ConstantTokens.getTokenv();
		nonce = getBase64Encoded(getTimestamp() + ":" + String.valueOf(getRandomNumer()));

		resp = obj.Get_cm_trades_inquiry(PropertiesFile.getCm_trades_inquiry(), request, auth_type, nonce);
		Parser t = new Parser();

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try (PrintStream ps = new PrintStream(
				PropertiesFile.getSeqLogPath() + "\\cm-from-exchg-" + timeStamp + ".txt");) {
			ps.println(resp);

			cmobj = t.getParseCM(resp);

		} catch (Exception e) {

			Log.notice.info("exceprtion during getParseCM method || CM TRADEFILE WRITE", e);

		}

		try {

			NoticeLogging n = null;

			if (cmobj != null) {
				DaoUtil logging = null;
				logging = new DaoUtil();
				n = new NoticeLogging();
				if (cmobj.getStatus().equalsIgnoreCase("success")) {
					// n.setComments("tradfile written "+ status);
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cm_trades_inquiry");
					n.setRESP_MESSAGESCODE(cmobj.getMessages().getCode());
					n.setRESP_MESSAGEID(cmobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(cmobj.getStatus());
					logging.getCMSave(n);
				} else if (cmobj.getStatus().equalsIgnoreCase("error")) {
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cd_trades_inquiry");
					n.setRESP_MESSAGESCODE(cmobj.getMessages().getCode());
					n.setRESP_MESSAGEID(cmobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(cmobj.getStatus());
					logging.getCMSave(n);
				} else {

					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("cm_trades_inquiry");
					n.setRESP_MESSAGESCODE("NA");
					n.setRESP_MESSAGEID("NA");
					n.setSEQ_NO(count);
					n.setSTATUS(cmobj.getStatus());
					logging.getCMSave(n); // tempo comment
				}
			} else {
				Log.notice.info("Exception occured");
			}

		} catch (Exception e) {
			Log.notice.info("Exception occured", e);
		}

	}

//	@TrackTime
	public void getResponse4fo() {

		String foresp = null;
		FoResponse foobj = null;
		Api_Request req = null;
		NoticeApiServiceImpl fo_obj = null;
		String msgid = null;
		Gson gson = null;
		String nonce = null;
		gson = new Gson();
		fo_obj = new NoticeApiServiceImpl();
		req = new Api_Request();

		int fodb;
		int count;
		DaoUtil fott = null;
		fott = new DaoUtil();

		fodb = fott.getFOseq();

		count = fodb + 1;

		msgid = PropertiesFile.getMember_code() + getTimestamp1() + getSequenceFO(count);

		req.setMsgId(msgid);
		String tradein = Utility.getMaxFOSequenceNo() + ",ALL,,";
		req.setTradesInquiry(tradein);
		Api_Version fov = null;

		fov = new Api_Version();
		fov.setVersion("1.0");
		fov.setRequest(req);
		String request = null;
		request = gson.toJson(fov);

		String auth_type = "Bearer " + ConstantTokens.getTokenv();
		nonce = getBase64Encoded(getTimestamp() + ":" + String.valueOf(getRandomNumer()));

		foresp = fo_obj.Get_fo_trades_inquiry(PropertiesFile.getFo_trades_inquiry(), request, auth_type, nonce);

		Parser tc = null;
		tc = new Parser();

		// try {
		// foobj = tc.getParseFO(foresp);
		//
		// } catch (Exception e) {
		//
		// Log.notice.info("exceprtion during getParseFO method || FO TRADEFILE WRITE",
		// e);
		//
		// }

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try (PrintStream ps = new PrintStream(
				PropertiesFile.getSeqLogPath() + "\\fo-from-exchg-" + timeStamp + ".txt");) {
			ps.println(foresp);

			foobj = tc.getParseFO(foresp);

		} catch (Exception e) {

			Log.notice.info("exceprtion during getParseFO method || FO TRADEFILE WRITE", e);

		}

		try {
			NoticeLogging n = null;

			if (foobj != null) {
				DaoUtil logging = null;
				logging = new DaoUtil();
				n = new NoticeLogging();
				if (foobj.getStatus().equalsIgnoreCase("success")) {
					// n.setComments("tradfile written "+ status);
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("fo_trades_inquiry");
					n.setRESP_MESSAGESCODE(foobj.getMessages().getCode());
					n.setRESP_MESSAGEID(foobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(foobj.getStatus());
					logging.getFOSave(n);
				} else if (foobj.getStatus().equalsIgnoreCase("error")) {
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("fo_trades_inquiry");
					n.setRESP_MESSAGESCODE(foobj.getMessages().getCode());
					n.setRESP_MESSAGEID(foobj.getData().getMsgId());
					n.setSEQ_NO(count);
					n.setSTATUS(foobj.getStatus());
					logging.getFOSave(n);
				} else {
					n.setINSERTEDDATE(new Timestamp(System.currentTimeMillis()));
					n.setREQ_MESSAGEID(msgid);
					n.setREQ_TYPE("fo_trades_inquiry");
					n.setRESP_MESSAGESCODE("NA");
					n.setRESP_MESSAGEID("NA");
					n.setSEQ_NO(count);
					n.setSTATUS(foobj.getStatus());
					logging.getFOSave(n);
				}
			} else {

				Log.notice.info("Exception occured");
			}

		} catch (Exception e) {

			Log.notice.info("exception occured during logging fo trade: ", e);
		}

	}

	// -----foend

	// private void getResponse() {
	// String cred = null;
	// String nonce = null;
	// String nonce1 = null;
	// String authType = null;
	//
	// cred = PropertiesFile.getCons_key() + ":" + PropertiesFile.getCons_secret();
	//
	// // authType= "Basic "+getBase64Encoded(cred);
	// //
	// nonce=getBase64Encoded(getTimestamp()+":"+String.valueOf(getRandomNumer()));
	// //
	// // String req="grant_type=client_credentials";
	// // service.GetToken(PropertiesFile.getGet_token_url(), req,authType,nonce);
	//
	// Gson gson = new Gson();
	// Api_Request req = new Api_Request();
	//
	// String msgid = PropertiesFile.getMember_code() + getTimestamp1() +
	// getSequence(0);
	//
	// Log.notice.info("msgid: " + msgid);
	//
	// req.setMsgId(msgid);
	//
	// String tradein = "0,ALL,,";
	// req.setTradesInquiry(tradein);
	// Api_Version v = new Api_Version();
	// v.setVersion("1.0");
	// v.setRequest(req);
	//
	// String request = gson.toJson(v);
	// Log.notice.info("request is: " + request);
	//
	// String access_token = "e1a3d3b1-5d9f-4a32-8bd6-7cfb45de0872";
	//
	// String auth_type = "Bearer " + access_token;
	//
	// nonce1 = getBase64Encoded(getTimestamp() + ":" +
	// String.valueOf(getRandomNumer()));
	//
	// }

	private static String getBase64Encoded(String str1) {
		String resp = null;
		resp = new String(Base64.getEncoder().encode(str1.getBytes()));
		return resp;
	}

	private static String getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
		String timestamp = dateFormat.format(new Date());
		return timestamp;
	}

	private static String getTimestamp1() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String timestamp = dateFormat.format(new Date());

		return timestamp;
	}

	private static int getRandomNumer() {

		Random random = new Random();
		return random.nextInt(999999);
	}

	private String getSequenceCD(int count) {

		return StringUtils.leftPad(String.valueOf(count), 7, "0");
	}

	private String getSequenceCM(int count) {

		return StringUtils.leftPad(String.valueOf(count), 7, "0");
	}

	private String getSequenceFO(int count) {

		return StringUtils.leftPad(String.valueOf(count), 7, "0");
	}

	// private static String getSequence(int frmdb) {
	// int count = 0;
	// count = frmdb + 1;
	// return StringUtils.leftPad(String.valueOf(count), 7, "0");
	// }
	//
	// private static int getMemberCode(int mcode) {
	// return Integer.valueOf(StringUtils.leftPad(String.valueOf(mcode), 5, "0"));
	// }

}
