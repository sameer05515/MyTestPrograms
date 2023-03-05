package com.ist.iagent.admin.cti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.PropertyUtil;

public class CTIOSPrimarySecondaryCheckServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger
			.getLogger(CTIOSPrimarySecondaryCheckServlet.class);
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doPost(req, resp);
	
		
		

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,IOException {
		
		log.debug(" CTIos Check request recvd.");
		String primaryUrl="";
		String secondryUrl="";
		CTISettingsRPCHandler ctisettings = new CTISettingsRPCHandler();
		PrintWriter out = response.getWriter();
		ArrayList<PropertyKeyValuePair> ctiSettinsList = ctisettings
				.getCTISettings();

		log.debug("cti properties are:"+ctiSettinsList);
		
		
		for (PropertyKeyValuePair propertyObject : ctiSettinsList) {

			if ("ctios_side_a".equals(propertyObject.getKey())) {
				
				primaryUrl = "http://" + propertyObject.getValue()
						+ "/uccx/isDBMaster";
				
				
				
			}
			if ("ctios_side_b".equals(propertyObject.getKey())) {
				secondryUrl = "http://" + propertyObject.getValue()
						+ "/uccx/isDBMaster";
				
			}

		}

		log.debug("primary url is :"+primaryUrl);
		log.debug("secondryUrl url is :"+secondryUrl);
		
		
		if(checkMasterURL(primaryUrl)){
			log.debug("sending primary true for primary server :"+primaryUrl);
			out.print(1);	
			
		}else if(checkMasterURL(secondryUrl)){
			log.debug("sending primary true for secondery server :"+secondryUrl);
			out.print(2);
		}else{
			
			log.debug("no server is primary now.");
			out.print(0);
		}
		
		
		
	}
	
	

	private boolean checkMasterURL(String urlString) {

		if (urlString == null || "".equals(urlString)) {
			log
					.debug("URL not defined properly.sending false for master property");
			return false;

		}
		URL url;
		try {
			url = new URL(urlString);

			URLConnection urlCon = url.openConnection();
			urlCon.setDoInput(true);
			// u.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			in.close();
			log.trace("Response from UCCX server:" + buffer.toString());

			return (buffer.toString().indexOf("<isMaster>true") > 0);

		} catch (Throwable e) {
			log.error("Error while reading UCCX master-secondary", e);
		}

		// anything goes wrong move to secondary DB.
		return false;
	}

}
