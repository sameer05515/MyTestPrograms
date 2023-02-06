package com.ist.iagent.core.service.access.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class IAgentPageRequestServlet extends HttpServlet {

	private static final Logger log = Logger
			.getLogger(IAgentPageRequestServlet.class);

	private static final long serialVersionUID = -1731173344718820445L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String requestedType = "client";
		String wrapperFileName = "wrapper_templet";
		String contextPath = getServletContext().getRealPath("/");
		PrintWriter pr = res.getWriter();
		log.info("Application requested by " + req.getRemoteAddr());
		String requestedURI = req.getRequestURI() + "";
		log.info("Requested URI is:" + requestedURI);

		boolean noCtiWraper = ("no".equalsIgnoreCase(req.getParameter("cti")));

		if (requestedURI.contains("/admin")) {

			requestedType = "admin";
			wrapperFileName = "awrapper_templet";
		} else if (noCtiWraper) {

			wrapperFileName = "wrapper_templet_NCTI";

			
		}

		log.debug("Requested type is :" + requestedType);

		String moduleName = req.getParameter("module");

		File f = new File(contextPath + "/templet/" + wrapperFileName);

		if(!f.exists()){
			
			pr.write("<H1><font color=\"#7592AE\"> iSuite</font><h3><font color=\"#7592AE\">iAgent Error:</font>&nbsp<font size=\"2\" color=\"#C52E10\">Wrapper file not found .please contact system administrator.");
			
		}else{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));

		StringBuffer htmlText = new StringBuffer();
		String lineString;

		while ((lineString = br.readLine()) != null) {

			htmlText.append(lineString.replaceAll("--datafile--",
					"application.swf") + "\n");
		}

		br.close();

		

		String templeteText = "";
		if (moduleName != null) {
			templeteText = htmlText.toString().replaceAll("--modulename--",
					moduleName);

		} else {
			templeteText = htmlText.toString().replaceAll("--modulename--", "");
		}

		templeteText = templeteText.replaceAll("--serverdata--",
				"" + System.currentTimeMillis());
		templeteText = templeteText
				.replaceAll("--requestType--", requestedType);

		res.setContentType("text/html");
		pr.write(templeteText);
		}

	}

}
