package com.p.pers.jsontoobj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyJSONReader {

	public static void main(String[] args) {
		try(PrintStream myPS=new PrintStream(new File("/home/premendra/git/file-bckp/15-Aug-21/Resume/git-proj.html"))) {
			String chapterArrJsonFilePath = "/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/my-wishes/sameer05515.git.repos.json";
//			String rawListFilePath = "D:\\Prem\\GIT-PROJ\\file-bckp\\smbg\\verseDetails\\temp.txt";
			JsonArray chapterArr = getJsonArrayFromFile(chapterArrJsonFilePath);
//		List<String> rawDataList = getRawList(rawListFilePath);
			
			
			
			myPS.println("<!DOCTYPE html>\n" + 
					"<html lang=\"en\">\n" + 
					"\n" + 
					"<head>\n" + 
					"    <meta charset=\"UTF-8\">\n" + 
					"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
					"    <title>My Resume</title>\n" + 
					"    <link rel=\"stylesheet\" href=\"lib/style.css\">\n" + 
					"    <script src=\"lib/jquery-3.6.0.js\"></script>\n" + 
					"    \n" + 
					"</head>\n" + 
					"\n" + 
					"<body>\n"+
					"       <div>\n" + 
					"            <table style=\"border-collapse: collapse; width: 100%;\" border=\"1\" id=\"mytable\">\n" + 
					"                <thead>\n" + 
					"                    <tr>\n" + 
					"                        <th style=\"width: 5%;\" id=\"sl\">#</th>\n" + 
					"                        <th style=\"width: 25%;\" id=\"comp\"><b>Name</b></th>\n" + 
					"                        <th style=\"width: 25%;\"><b>Description</b></th>\n" + 
					"                        <th style=\"width: 15%;\"><b>URL</b></th>\n" + 
					"                        <th style=\"width: 15%;\"><b>GIT URL</b></th>\n" + 
					"                        <th style=\"width: 15%;\"><b>SSH URL</b></th>\n" + 
					"                    </tr>\n" + 
					"                </thead>\n" + 
					"                <tbody>\n");
			int counter=0;
			for (JsonElement ec : chapterArr) {
				JsonObject chapterObj = (JsonObject) ec;
				String name = getValue(chapterObj,"name");
				String description = getValue(chapterObj,"description");
				String url = getValue(chapterObj,"url");
				String git_url=getValue(chapterObj, "git_url");
				String ssh_url=getValue(chapterObj, "ssh_url");
//				System.out.println("========================");
//				System.out.printf("Name : %s \nDescription : %s \nURL : %s"+
//				"\n", name,description,url);
//				System.out.println("========================");
				
				myPS.println("           <tr>\n" + 
						"                        <td style=\"width: 5%;\"> "+ ++counter +"</td>"+
						"                        <td style=\"width: 25%;\"> "+name +"</td>\n" +
						"                        <td style=\"width: 25%;\"> "+description +"</td>\n" + 
						"                        <td style=\"width: 15%;\"> "+url +"</td>\n" +
						"                        <td style=\"width: 15%;\"> "+git_url +"</td>\n" + 
						"                        <td style=\"width: 15%;\"> "+ssh_url +"</td>\n" +
						"           </tr>");
			}
			System.out.println(chapterArr.size());
			myPS.println("                </tbody>\n" + 
					"            </table>\n" + 
					"        </div>\n" + 
					"    </div>\n"+
					
					"    <div>\n" + 
					"        <pre>\n" + 
					"            to get all repos in github below is API url\n" + 
					"            <code>https://api.github.com/users/sameer05515/repos?per_page=100&page=3</code>\n" + 
					"        </pre>\n" + 
					"    </div>"+
					
			"   <script>\n" + 
					"        $(function () {\n" + 
					"            var includes = $('[data-include]')\n" + 
					"            $.each(includes, function () {\n" + 
					"                var file = 'header-footer/' + $(this).data('include') + '.html'\n" + 
					"                $(this).load(file)\n" + 
					"            })\n" + 
					"        })\n" + 
					"    </script>\n" + 
					"    <script src=\"lib/table-sort.js\"></script>\n" + 
					"    <script src=\"lib/custom.js\"></script>\n" + 
					"\n" + 
					"</body>\n" + 
					"\n" + 
					"</html>");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(UnsupportedOperationException e) {
			e.printStackTrace();
		}

	}
	
	public static String getValue(JsonObject obj,String key) {
		JsonElement o=obj.get(key);
		if(o!=null && !o.isJsonNull()) {
			String s= o.getAsString();
			if(s!=null) {
				return s.trim();
			}
		}
		
		return "&nbsp;";
	}

	@SuppressWarnings("deprecation")
	public static JsonArray getJsonArrayFromFile(String JSON_PATH) throws FileNotFoundException {
		JsonArray arr = new JsonArray();
		BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
		JsonParser parser = new JsonParser();
		arr = parser.parse(br).getAsJsonArray();
		return arr;
	}

}
