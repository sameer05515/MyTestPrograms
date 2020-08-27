package com.p.html.code.generator;

import java.io.File;
import java.io.IOException;

public final class HtmlCodeGenerator {
	
	public static String generateLinksHtmlFiles(String[] linksArr,String directoryPath) {

		StringBuffer sb=new StringBuffer();
		int count=1;
		for(String link:linksArr) {
			try {
				String padded = String.format("%03d" , count++);
		         File file = new File(directoryPath+File.separator+padded+"_"+link+".html");
		         
		         if(file.createNewFile())sb.append(file.getName()+" : Creation : Success!\n");
		         else sb.append ("Error, file "+file.getName()+" already exists.\n");
		      }
		      catch(IOException ioe) {
		         ioe.printStackTrace();
		      }
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String generateLinksHtmlText(String[] linksArr) {
		
		StringBuffer sb=new StringBuffer();
		int count=1;
		sb.append("<ul>\n");
		for(String link:linksArr) {
			sb.append("<li>");
			String padded = String.format("%03d" , count++);
//			sb.append("<a ").append(" href=").append("\"").append((padded)+"_"+link+".html").append("\"").append(" >");
			sb.append("<a ").append(" href=").append("\"").append(link+".html").append("\"").append(" >");
			sb.append(link+".html");			
			sb.append("</a>");
			sb.append("</li>\n");
		}
		sb.append("</ul>\n");
		
		System.out.println(sb.toString());
		return sb.toString();
	}

}
