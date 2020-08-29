package com.p.html.code.generator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public final class HtmlCodeGenerator {

	public static String generateLinksHtmlFiles(String[] linksArr, String directoryPath) {

		StringBuffer sb = new StringBuffer();
		int count = 1;
		for (String link : linksArr) {
			try {
				String padded = String.format("%03d", count++);
				File file = new File(directoryPath + File.separator + padded + "_" + link + ".html");

				if (file.createNewFile())
					sb.append(file.getName() + " : Creation : Success!\n");
				else
					sb.append("Error, file " + file.getName() + " already exists.\n");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	public static String generateLinksHtmlText(String[] linksArr) {

		StringBuffer sb = new StringBuffer();
		int count = 1;
		sb.append("<ul>\n");
		for (String link : linksArr) {
			sb.append("<li>");
			String padded = String.format("%03d", count++);
//			sb.append("<a ").append(" href=").append("\"").append((padded)+"_"+link+".html").append("\"").append(" >");
			sb.append("<a ").append(" href=").append("\"").append(link + ".html").append("\"").append(" >");
			sb.append(link + ".html");
			sb.append("</a>");
			sb.append("</li>\n");
		}
		sb.append("</ul>\n");

		System.out.println(sb.toString());
		return sb.toString();
	}

	public static String generateCanvasJsLinksHtmlText(String[] linksArr) {

		StringBuffer sb = new StringBuffer();
		int index = 0;
		sb.append("<ul>\n");
		for (String link : linksArr) {
//			sb.append("<li>");			
//			sb.append("<a ").append(" href=").append("\"").append(link + ".html").append("\"").append(" >");
//			sb.append(link + ".html");
//			sb.append("</a>");
//			sb.append("</li>\n");
			sb.append("<li><a onclick=\"load(this,"
					+ (index++)
					+ ",'"
					+ "examples/"+link
					+ "')\">"
					+ ""
					+ link
					+ "</a>\r\n" + 
					"                                </li>\n");
		}
		sb.append("</ul>\n");

		System.out.println(sb.toString());
		return sb.toString();
	}

	public static String canvasJsFiles(String exampleDirPath) {
		StringBuffer sb = new StringBuffer();

		File[] exDirSubDirectories = new File(exampleDirPath).listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname != null && pathname.isDirectory()) {
					return true;
				}
				return false;
			}
		});

		for (File file : exDirSubDirectories) {
			if (file != null && file.isDirectory()) {
				File[] htmlFiles = file.listFiles();
				for (File htmlFile : htmlFiles) {
					if (htmlFile != null && htmlFile.isFile()) {
						sb.append("\"");
						sb.append(file.getName() + "/" + htmlFile.getName());
						sb.append("\",");
					}
				}
			}
		}

		System.out.println(sb.toString());
		return sb.toString();
	}

}
