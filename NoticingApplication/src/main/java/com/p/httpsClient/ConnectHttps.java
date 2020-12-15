package com.p.httpsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.p.appconfiguration.Log;

public class ConnectHttps {

	public static class IBTrustManager implements X509TrustManager {

		public IBTrustManager() {
		}

		public boolean isClientTrusted(X509Certificate cert[]) {
			return true;
		}

		public boolean isServerTrusted(X509Certificate cert[]) {
			return true;
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

	}

	public static class IBHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	/*
	 * public static void checkTo(String noticeUrl,String requestt, String
	 * auth_type,String nonce) { try{ byte[] postData = requestt.getBytes(
	 * StandardCharsets.UTF_8 ); int postDataLength = postData.length; String urll =
	 * noticeUrl; InputStream is = null; URL url = new URL( urll );
	 * HttpURLConnection conn= (HttpURLConnection) url.openConnection();
	 * conn.setDoOutput(true); conn.setInstanceFollowRedirects(false);
	 * conn.setRequestMethod("POST"); conn.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded"); conn.setRequestProperty
	 * ("Authorization", "Basic "+ auth_type); conn.setRequestProperty ("nonce",
	 * nonce); conn.setRequestProperty("charset", "utf-8");
	 * conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
	 * //conn.setUseCaches(false); try(DataOutputStream wr = new
	 * DataOutputStream(conn.getOutputStream())) { wr.write( postData ); }
	 * 
	 * OutputStream os = conn.getOutputStream(); OutputStreamWriter osw = new
	 * OutputStreamWriter(os); osw.flush(); osw.close(); is =conn.getInputStream();
	 * BufferedReader in = new BufferedReader(new InputStreamReader(is));
	 * 
	 * String strTemp; String responseXML="";
	 * Log.notice.info("response code is: "+
	 * conn.getResponseCode()+" and desc: "+ conn.getResponseMessage() ); while
	 * ((strTemp = in.readLine()) != null) { responseXML = responseXML + strTemp; }
	 * is.close(); in.close();
	 * 
	 * Log.notice.info("responseXML: "+ responseXML);
	 * 
	 * 
	 * }catch(Exception e ){ Log.notice.info("exception is: " +e ); }
	 * 
	 * }
	 */

	public static String getResponseCDFromHttps(String noticeUrl, String request, String auth_type, String nonce) {

		/*
		 * Log.notice.info("called getResponseCDFromHttps.....");
		 * Log.notice.info("=================");
		 */

		Log.notice.info("REQUESTING CD URL: " + noticeUrl + " TS: " + new Date(System.currentTimeMillis()));
		Log.notice.info("request " + request);
		Log.notice.info("auth_type " + auth_type);
		Log.notice.info("nonce " + nonce);

		Log.notice.info("=================");

		SSLContext sslcontext = null;

		String responseXML = "";

		try {

			sslcontext = SSLContext.getInstance("TLSv1.2");

			sslcontext.init(new KeyManager[0], new TrustManager[] { new IBTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		} catch (KeyManagementException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}

		try {
			// Log.notice.info("request to be send: ==>" + urlParameters );
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;

			String ip = noticeUrl;
			url = new URL(ip);

			// Log.notice.info();

			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			// connection.setRequestProperty("Content-Length", "" +
			// Integer.toString(urlParameters.getBytes().length));

			connection.setRequestProperty("Authorization", auth_type);
			connection.setRequestProperty("nonce", nonce);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new IBHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(request);
			osw.flush();
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String strTemp;
			// Log.notice.info("response code is: "+ connection.getResponseCode()+" and
			// desc: "+ connection.getResponseMessage() );
			// Log.notice.info("cd response code is: "+ connection.getResponseCode()+" and
			// desc: "+ connection.getResponseMessage() );
			while ((strTemp = in.readLine()) != null) {
				// Log.notice.info("hereeeeee");
				responseXML = responseXML + strTemp;
			}
			is.close();
			in.close();
			Log.notice.info("response received CD URL| TS: " + new Date(System.currentTimeMillis()) + " |http-code: "
					+ connection.getResponseCode() + " |http-desc: " + connection.getResponseMessage());
			if (responseXML.length() <= 150) {
				// Log.notice.info("rsponse: "+ responseXML);
				Log.notice.info("rsponse is less than150words: " + responseXML);
				// error
			}
			// Log.notice.info("=======+ "+ responseXML );

		} catch (ConnectException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		} catch (Exception e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}

		// Log.notice.info("response received CD URL| TS: "+ new
		// Date(System.currentTimeMillis()));

		return responseXML;
	}

	// --------------

	public static String getcmResponseFromHttps(String noticeUrl, String request, String auth_type, String nonce) {

		Log.notice.info("REQUESTING CM URL: " + noticeUrl + "TS: " + new Date(System.currentTimeMillis()));

		Log.notice.info("=================");

		Log.notice.info("noticeUrl " + noticeUrl);
		Log.notice.info("request " + request);
		Log.notice.info("auth_type " + auth_type);
		Log.notice.info("nonce " + nonce);

		Log.notice.info("=================");

		SSLContext sslcontext = null;

		String responseXML = "";

		try {

			sslcontext = SSLContext.getInstance("TLSv1.2");

			sslcontext.init(new KeyManager[0], new TrustManager[] { new IBTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "";
		} catch (KeyManagementException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}

		try {
			// Log.notice.info("request to be send: ==>" + urlParameters );
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;

			String ip = noticeUrl;
			url = new URL(ip);

			

			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			// connection.setRequestProperty("Content-Length", "" +
			// Integer.toString(urlParameters.getBytes().length));

			connection.setRequestProperty("Authorization", auth_type);
			connection.setRequestProperty("nonce", nonce);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new IBHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(request);
			osw.flush();
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String strTemp;
			// Log.notice.info("response code is: "+ connection.getResponseCode()+" and
			// desc: "+ connection.getResponseMessage() );
			Log.notice.info("response received Cm URL| TS: " + new Date(System.currentTimeMillis()) + " |http-code: "
					+ connection.getResponseCode() + " |http-desc: " + connection.getResponseMessage());
			while ((strTemp = in.readLine()) != null) {
				Log.notice.info("hereeeeee");
				responseXML = responseXML + strTemp;
			}
			is.close();
			in.close();
			if (responseXML.length() <= 150) {
				// Log.notice.info("rsponse: "+ responseXML);
				Log.notice.info("rsponse is less than150words: " + responseXML);
				// error
			}
			// Log.notice.info("=======+ "+ responseXML );

		} catch (ConnectException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		} catch (Exception e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}
		// Log.notice.info("response received from CM URL| TS: "+ new
		// Date(System.currentTimeMillis()));
		return responseXML;
	}

	// end

	// --------------------------------------

	// private static final Pattern pat =
	// Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
	/*
	 * private static final String clientId = "";//clientId private static final
	 * String clientSecret = "";//client secret private static final String tokenUrl
	 * = "https://api.byu.edu/token"; private static final String auth = clientId +
	 * ":" + clientSecret;
	 */
	// private static final String authentication =
	// Base64.getEncoder().encodeToString(auth.getBytes());

	public static String getResourceCredentials(String noticeUrl, String auth_type, String nonce) {

		Log.notice.info("requesting token: " + noticeUrl);
		Log.notice.info("nonce: " + nonce);
		Log.notice.info("key:secret= " + auth_type);
		String content = "grant_type=client_credentials";
		BufferedReader reader = null;
		HttpsURLConnection connection = null;
		String returnValue = "";

		/*
		 * String tokenUrl=""; String authentication=
		 * "Y2UxM2FjYzJkNjAyNDdhYWJkZmQyODNiY2RiZGI5MTk6ZTczYWU4YjBiNDY2NGI3N2JkYWU0NGVjOWVkMjA4YmE=";
		 */

		try {
			URL url = new URL(noticeUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + auth_type); // authentication => base64encoded of
																					// <cons_key:cons_sec>
			connection.setRequestProperty("nonce", nonce); // nonce => base64enoded of <ddMMyyyyHHmmssSSS:<6-digit
															// random number>>
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			PrintStream os = new PrintStream(connection.getOutputStream());
			os.print(content); // content = grant_type=client_credentials
			os.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();
			returnValue = response;

		} catch (Exception e) {
			returnValue = null;
			// Log.notice.info("Error : " + e.getMessage());
			e.printStackTrace();
			Log.notice.info("Exception occured during calling token api: " , e);

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			connection.disconnect();
		}
		// Log.notice.info("result ======>"+ returnValue);
		return returnValue;
	}

	public static String getResourceCredentialsTemp() {

		String content = "grant_type=client_credentials";
		BufferedReader reader = null;
		HttpsURLConnection connection = null;
		String returnValue = "";

//		String tokenUrl = "";
		String authentication = "Y2UxM2FjYzJkNjAyNDdhYWJkZmQyODNiY2RiZGI5MTk6ZTczYWU4YjBiNDY2NGI3N2JkYWU0NGVjOWVkMjA4YmE=";

		try {
			URL url = new URL("https://www.dev.connect2nse.com/token");
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + authentication); // authentication =>
																						// base64encoded of
																						// <cons_key:cons_sec>
			connection.setRequestProperty("nonce", "MDgwODIwMTkxMDMxMjQ5NjA6OTQ3NzQ0"); // nonce => base64enoded of
																						// <ddMMyyyyHHmmssSSS:<6-digit
																						// random number>>
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			PrintStream os = new PrintStream(connection.getOutputStream());
			os.print(content); // content = grant_type=client_credentials
			os.close();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringWriter out = new StringWriter(
					connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			String response = out.toString();

			/*
			 * Matcher matcher = pat.matcher(response); if (matcher.matches() &&
			 * matcher.groupCount() > 0) { returnValue = matcher.group(1); }
			 */
			Log.notice.info("=====>" + response);

		} catch (Exception e) {
			Log.notice.info("Error : " , e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			connection.disconnect();
		}
		Log.notice.info("result ======>" + returnValue);
		return returnValue;
	}

	// ----------------------- fo start

	public static String getfoResponseFromHttps(String noticeUrl, String request, String auth_type, String nonce) {
		Log.notice.info("REQUESTING FO URL: " + noticeUrl + "TS: " + new Date(System.currentTimeMillis()));

		Log.notice.info("=================");

		Log.notice.info("noticeUrl " + noticeUrl);
		Log.notice.info("request " + request);
		Log.notice.info("auth_type " + auth_type);
		Log.notice.info("nonce " + nonce);

		Log.notice.info("=================");

		SSLContext sslcontext = null;

		String responseXML = "";

		try {

			sslcontext = SSLContext.getInstance("TLSv1.2");

			sslcontext.init(new KeyManager[0], new TrustManager[] { new IBTrustManager() }, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		} catch (KeyManagementException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);
			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}

		try {
			// Log.notice.info("request to be send: ==>" + urlParameters );
			SSLSocketFactory factory = sslcontext.getSocketFactory();
			URL url;
			HttpsURLConnection connection;
			InputStream is = null;

			String ip = noticeUrl;
			url = new URL(ip);

			

			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			// connection.setRequestProperty("Content-Length", "" +
			// Integer.toString(urlParameters.getBytes().length));

			connection.setRequestProperty("Authorization", auth_type);
			connection.setRequestProperty("nonce", nonce);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setSSLSocketFactory(factory);
			connection.setHostnameVerifier(new IBHostnameVerifier());
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(request);
			osw.flush();
			osw.close();
			is = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			String strTemp;
			// Log.notice.info("response code is: "+ connection.getResponseCode()+" and
			// desc: "+ connection.getResponseMessage() );
			Log.notice.info("response received Fo URL| TS: " + new Date(System.currentTimeMillis()) + " |http-code: "
					+ connection.getResponseCode() + " |http-desc: " + connection.getResponseMessage());
			while ((strTemp = in.readLine()) != null) {
				Log.notice.info("hereeeeee");
				responseXML = responseXML + strTemp;
			}
			is.close();
			in.close();
			if (responseXML.length() <= 150) {
				// Log.notice.info("rsponse: "+ responseXML);
				Log.notice.info("rsponse is less than150words: " + responseXML);
				// error
			}
			// Log.notice.info("=======+ "+ responseXML );

		} catch (ConnectException e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		} catch (Exception e) {
			Log.notice.info("Exception in ConnectHttps :" , e);

			return "{ \"status\":\"fail\",\"messages\":{\"code\":\"Exception with NOTICE  connection\" },\"data\":{}}";
		}

		Log.notice.info("resp received from FO URL|  TS: " + new Date(System.currentTimeMillis()));
		return responseXML;
	}

	// ----------------------end

	/*
	 * public static void tradeEquery(String noticeUrl,String request, String
	 * auth_type,String nonce) { BufferedReader reader = null; try { URL url = new
	 * URL(noticeUrl); HttpsURLConnection connection = (HttpsURLConnection)
	 * url.openConnection(); connection.setRequestProperty("Authorization",
	 * auth_type); connection.setRequestProperty("nonce",nonce);
	 * 
	 * connection.setDoOutput(true); connection.setRequestMethod("GET"); reader =
	 * new BufferedReader(new InputStreamReader(connection.getInputStream()));
	 * String line = null; StringWriter out = new
	 * StringWriter(connection.getContentLength() > 0 ? connection while ((line =
	 * reader.readLine()) != null) { out.append(line); } String response =
	 * out.toString(); Log.notice.info(response); } catch (Exception e) { } }
	 */

}