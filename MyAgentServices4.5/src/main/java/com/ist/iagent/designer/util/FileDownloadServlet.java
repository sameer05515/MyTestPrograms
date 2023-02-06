package com.ist.iagent.designer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ist.iagent.designer.export.ExportModuleHandler;

public class FileDownloadServlet extends HttpServlet{
	
	private static final Logger log=Logger.getLogger(FileDownloadServlet.class);
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException{
		doPost(req, res);
	}
	
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
		
		FileInputStream in=null;
		ServletOutputStream fout=null;
		
		res.setContentType("application/octet-stream");
		String fileName=req.getParameter("filename");
		
		res.setHeader("Content-Disposition","attachment;filename="+fileName);
		
		try{
		
		if(fileName!=null && fileName.trim().length()>0){
			
			log.debug("file name to download found. "+fileName);
			
			File projectExportFolder=ExportModuleHandler.getProjectStoreFolder();
			
			if(projectExportFolder==null){
				return;
			}
			
			File fileToDownload=new File(projectExportFolder.getAbsolutePath()+"/"+fileName);
			
			if(!fileToDownload.exists() || !fileToDownload.isFile()){
				log.debug("file to download not found at path :"+projectExportFolder.getAbsolutePath());
				return;
			}
			
			log.debug("file to download path  "+fileToDownload.getAbsolutePath());
			
			in=new FileInputStream(fileToDownload);
			fout=res.getOutputStream();
			byte[] arr=new byte[in.available()];
			
			in.read(arr);
			fout.write(arr);
			fout.flush();
			//fout.close();
			//in.close();
		}
		}catch(Exception e){
		log.error("Error in file download servlet. "+e.getMessage());	
		}finally{
			
			if(in!=null){
				in.close();
			}
			
			if(fout!=null){
				fout.flush();
				fout.close();
			}
		}
	}

}
