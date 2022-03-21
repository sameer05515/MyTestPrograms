package com.p.batch;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.p.appconfiguration.Log;
import com.p.appconfiguration.PropertiesFile;
import com.p.httpsClient.NoticeAppClient;
import com.p.model.TokenRes;

public class TradeEnquiryTask implements Tasklet {

	int count = 0;
	static int check = 0;

	static String startt;
	static String endd;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		TradeEnquiryTask.startt = PropertiesFile.getStart();
		TradeEnquiryTask.endd = PropertiesFile.getStop();

		if (inBetween())

		{

			if (isTokenAvailable())

			{

				try {

					/**
					 * ====================
					 * 
					 * 1. FOR CD TRADES ENQUIRY
					 * 
					 * ===================
					 */
					if (PropertiesFile.isEnable_cd_trades_inquiry()) {
						Log.notice.info("cd trade enquiry start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

						Instant start = Instant.now();
						NoticeAppClient p = null;
						p = new NoticeAppClient();
						p.getResponse4cd();
						
						Instant finish = Instant.now();
						
						long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
						Log.notice.info("Total time Elapsed for cd trade enquiry : "+timeElapsed+" milliseconds.");

						Log.notice.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>cd trade enquiry end");
						

					}

					/**
					 * ====================
					 * 
					 * 2. FOR CM TRADES ENQUIRY
					 * 
					 * ===================
					 */

					if (PropertiesFile.isEnable_cm_trades_inquiry()) {

						Log.notice.info("cm trade enquiry start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

						Instant start = Instant.now();
						NoticeAppClient cmp = null;
						cmp = new NoticeAppClient();
						cmp.getResponse4cm();
						
						Instant finish = Instant.now();
						long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
						Log.notice.info("Total time Elapsed for cm trade enquiry : "+timeElapsed+" milliseconds.");
						Log.notice.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>cm trade enquiry end");

					}

					if (PropertiesFile.isEnable_fo_trades_inquiry()) {
						Log.notice.info("fo trade enquery start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

						Instant start = Instant.now();
						NoticeAppClient fop = null;
						fop = new NoticeAppClient();
						fop.getResponse4fo();
						
						Instant finish = Instant.now();
						long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
						Log.notice.info("Total time Elapsed for fo trade enquiry : "+timeElapsed+" milliseconds.");
						Log.notice.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fo trade enquery end");

					}

					Log.notice.info("======================");

				} catch (Exception e) {
					Log.notice.error("problem in generating token", e);
					Log.notice.info(e);
					e.printStackTrace();
				}

				Log.notice.info("MyTaskTwo done..");

			} else {
				Log.notice.info("problem in generating token");
			}
		} else {
			/**
			 * IN THIS SECTION CURSOR COME AFTER 19:55 AS (endd = "19:55"), THIS METHOD
			 * SLEEP THE PROCESS TILL 08:05 AS (startt = "08:05")
			 * 
			 */

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String currentTime = null;

			currentTime = LocalTime.now().format(formatter);
			LocalTime ct = LocalTime.parse(currentTime);
			LocalTime st1 = LocalTime.parse(startt).plusMinutes(1); //
			Log.notice.info("currenttimestamp: " + ct);
			Log.notice.info("till sleep time: " + st1);

			if (ct.getHour() > st1.getHour()) {
				int hourmnts = 24 * 60;// 1440
				int totaminutes = (ct.getHour() * 60) + ct.getMinute();
				int minutestillmrn = st1.getHour() * 60 + st1.getMinute();
				int diffinminutee = (hourmnts - totaminutes) + minutestillmrn;

				try {
					Log.notice.info("sleep start till: " + st1 + "| means awake after: " + diffinminutee + " minutes.");
					TimeUnit.SECONDS.sleep(diffinminutee * 60);
					Log.notice.info("sleep over: |" + LocalDateTime.now());
				} catch (InterruptedException e) {

					Log.notice.info("Exception in sleep");
				}

			} else {

				int thirty = (int) Duration.between(ct, st1).getSeconds();
				try {
					Log.notice.info("sleep start till: " + st1);
					TimeUnit.SECONDS.sleep(thirty);
				} catch (InterruptedException e) {
					Log.notice.info("Exception in sleep");
				}
			}
		}

		return RepeatStatus.FINISHED;

	}

	@SuppressWarnings("unused")
	private static long getDiff(String dateStart, String dateStop) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		/**
		 * 
		 * HH converts hour in 24 hours format (0-23), day calculation
		 * 
		 */

		Date d1 = null;
		Date d2 = null;
		long diff = 0;

		try {

			/**
			 * 
			 * Calculation of time difference in milliseconds
			 * 
			 */
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			if (d1.getTime() > d2.getTime()) {

				diff = d1.getTime() - d2.getTime();
			} else {
				diff = d2.getTime() - d1.getTime();
			}

		} catch (Exception e) {
			Log.notice.info(e);
		}

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		Log.notice.info(
				"process resume after: " + diffHours + " HR| " + diffMinutes + " MN| " + diffSeconds + " seconds");
		return diff;
	}

	private boolean inBetween() {

		Log.notice.info("startt: " + startt);
		Log.notice.info("endd: " + endd);
		boolean status = false;
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		try {
			Date start = parser.parse(startt);
			Date end = parser.parse(endd);

			Date currentTime = new Date(); // current time

			Date currentTimet = parser.parse(parser.format(currentTime));
			if (currentTimet.after(start) && currentTimet.before(end)) {
				status = true;
				Log.notice.info("lie between the time");
			} else {
				Log.notice.info("not lie between the time");
			}
		} catch (Exception e) {
			status = false;
			Log.notice.info("exception occured during checking CURRENT TIME: " + e);
		}

		return status;
	}

	private Boolean isTokenAvailable() {
		Boolean status = false;

		if (ConstantTokens.getToken().equalsIgnoreCase("not_available")) // not_available then send token request
		{
			try {

				status = generateToken();
				Log.notice.info("status form generate token is: " + status);

			} catch (Exception e) {
				status = false;
				Log.notice.info(e);
			}

		} else {

			Date currentTimet = new Timestamp(new Date().getTime());
			if (currentTimet.after(ConstantTokens.getMap().get("gtime"))
					&& currentTimet.before(ConstantTokens.getMap().get("etime"))) {

				Log.notice.info("token is intime/valid: " + new Timestamp(new Date().getTime()));
				status = true;
			} else {
				Log.notice.info("token expired: " + new Timestamp(new Date().getTime())
						+ ">>****************going to re-generate");
				status = generateToken();

			}

		}
		Log.notice.info("status of token is: " + status);
		return status;
	}

	private boolean generateToken() {
		Log.notice.info("process start to generatign token.......");
		boolean status = false;
		try {
			NoticeAppClient app = null;
			app = new NoticeAppClient();

			TokenRes t = app.getResponse1();

			if (t != null) {
				if (t.getAccess_token() != null || t.getAccess_token() != "") {
					Timestamp tkn_generation_time = new Timestamp(new Date().getTime());
					Instant nexttime = tkn_generation_time.toInstant().plusSeconds(t.getExpires_in());
					Timestamp tkn_expiry_time = Timestamp.from(nexttime);
					HashMap<String, Timestamp> hm = new HashMap<String, Timestamp>();
					hm.put("gtime", tkn_generation_time);
					hm.put("etime", tkn_expiry_time);
					ConstantTokens.setToken("available");
					ConstantTokens.setTokenv(t.getAccess_token());
					ConstantTokens.setMap(hm);

					Log.notice.info("token is: " + t.getAccess_token());
					Log.notice.info("token to is valid from: " + tkn_generation_time + " To: " + tkn_expiry_time);
					status = true;
				} else {

					Log.notice.info("token generation failed");
					status = false;
				}
			}
		} catch (Exception e) {

			Log.notice.info("Exception occured in generateToken method.", e);
			status = false;
		}
		return status;
	}

}
