package com.p.mongo.curd.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(
//        name = "GetFileServlet",
//        description = "Servlet to get document",
//        urlPatterns = {"/get-file"}
//)
public class GetFileServlet  extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {



        String documentId=request.getParameter("documentId");
        ServletOutputStream out = response.getOutputStream();
        System.out.println("provided document id:- "+documentId);
        try{

            if(documentId!=null && documentId.trim().length()>0){
                File file = new File(documentId);
                if(!file.exists()){
                    throw new IOException("File not exists!");
                }
                if(file.isDirectory()){
                    File[] children=file.listFiles();

                    int count=((children!=null)?children.length:0);
                    out.println("<b>Total file found : - "+count+"</b>");
                    out.println("<ul>");
                    for(File child:children){
                        out.println("<li>"+"<a href=\"?documentId="+child.getAbsolutePath().trim().replace("\\", "/")+"\" >"+child.getName()+"</a>"+"</li>");
                    }
                    out.println("</ul>");
                    return;
                }
                response.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                response.setHeader("Content-Disposition", "inline; filename=\"foo.pdf\"");
                Files.copy(file.toPath(), out);
                System.out.println(file);
            }
        }catch(IOException e){
            e.printStackTrace();
            out.println("No File found : "+documentId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
