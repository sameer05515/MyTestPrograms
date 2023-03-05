package com.ist.iagent.designer.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.ist.iagent.designer.export.ExportModuleHandler;

public class FileUploadServlet extends HttpServlet{

	private static final Logger log=Logger.getLogger(FileUploadServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		File disk = null;
		FileItem item = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String fileName = "";
		ListIterator<FileItem> iterator = null;
		List items = null;
		File f=ExportModuleHandler.getProjectStoreFolder();
		if(f==null){
			log.error("Project export path not found.");
			return;
		}
			
		try{
		
		String uploadDirectory=f.getAbsolutePath();
		
		ServletFileUpload upload = new ServletFileUpload(factory);
	

	
			out = response.getWriter();
			

			items = upload.parseRequest(request);
			iterator = items.listIterator();

			

			while (iterator.hasNext()) /** Loop over the items in the request. */
			{
				
				item = (FileItem) iterator.next();
				fileName = item.getName();

				/** If the current item is not an HTML form field... */
				if (!item.isFormField()) {
					String newFileName = fileName;

					disk = new File(uploadDirectory +"/"+newFileName);
					/** Instantiate a File object for the file to be written. */

					item.write(disk);
					/** Write the uploaded file to disk. */

				}
			}
			out.close();
		}catch(Exception e){
			log.error("Error in uploading file.",e);
		}
	}
	
}
