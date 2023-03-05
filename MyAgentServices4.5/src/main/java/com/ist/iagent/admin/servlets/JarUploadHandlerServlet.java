package com.ist.iagent.admin.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.ist.iagent.admin.jar.JarRPCHandler;
import com.ist.iagent.admin.util.ClassPathLoader;
import com.ist.iagent.admin.util.CustomClassLoader;
import com.ist.iagent.admin.util.PropertyUtil;

public class JarUploadHandlerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=utf-8";
	static Logger log = Logger.getLogger(JarUploadHandlerServlet.class);

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		response.setContentType(CONTENT_TYPE);
		/** Set the servlet's response type to XML. */

		PrintWriter out = null;
		String uploadDirectory = PropertyUtil.getInstance().getValueForKey(
				"serverLibPath");
		/** Get the upload directory from the web.xml file. */

		log.debug(uploadDirectory);
		File disk = null;
		FileItem item = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String jarFileName = "";
		ListIterator iterator = null;
		List items = null;
		ServletFileUpload upload = new ServletFileUpload(factory);
		/** SAX 2.0 ContentHandler. */

		try {
			out = response.getWriter();
			/** SAX XML parsing factory. */

			items = upload.parseRequest(request);
			iterator = items.listIterator();

			AttributesImpl atts = new AttributesImpl();
			/** Declare and instantiate a new attributes object. */

			while (iterator.hasNext()) /** Loop over the items in the request. */
			{
				/** Clear the XML attributes object. */
				atts.clear();
				item = (FileItem) iterator.next();
				jarFileName = item.getName();

				/** If the current item is not an HTML form field... */
				if (!item.isFormField()) {
					String newFileName = jarFileName;

					disk = new File(uploadDirectory + newFileName);
					/** Instantiate a File object for the file to be written. */

					item.write(disk);
					/** Write the uploaded file to disk. */

					ClassPathLoader.addFile(disk);
					/** loads the uploaded file to tomcat classpath */

					/** code written to load all classes */
					List<String> classes = JarRPCHandler.getAllClassName(disk
							.getName());
					if (classes != null && classes.size() != 0) {
						CustomClassLoader custCLoader = new CustomClassLoader();
						ListIterator<String> itr = classes.listIterator();
						while (itr.hasNext()) {
							String s = itr.next();
							Class<?> loadClass = custCLoader
									.getSystemClassLoader().loadClass(s);
						}
					}
				}
			}
			out.close();
			/** Close the output. */

		} catch (TransformerConfigurationException tcException) {
			log.error("TransformerConfigurationException : ", tcException);
		} catch (FileUploadException fileUploadException) {
			log.error("FileUploadException : ", fileUploadException);
		} catch (IOException ioException) {
			log.error("IOException : ", ioException);
		} catch (SAXException saxException) {
			log.error("SAXException : ", saxException);
		} catch (NullPointerException exception) {
			log.error("NullPointerException : ", exception);
		} catch (ClassNotFoundException exception) {
			log.error("ClassNotFoundException : ", exception);
		} catch (Throwable exception) {
			log.error("Error occured : ", exception);
		}
	}

}
