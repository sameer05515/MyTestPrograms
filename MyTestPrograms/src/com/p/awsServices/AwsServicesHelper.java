package com.p.awsServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AwsServicesHelper {

	private static final String[] TOKEN_ARRAY = { "===start===",  "===end===" };
	private static String rawFileLocation="/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/aws-services/js/data/raw-services-overview.txt";
	public static void main(String[] args) {
		
		try {
			List<String> rawDataList = getRawList(rawFileLocation);
			
			if (rawDataList != null && rawDataList.size() > 0) {
				for(String line:rawDataList) {
					System.out.println(line);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static List<String> getRawList(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		List<String> rawDataList = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			rawDataList.add(line);
		}
		scanner.close();
		return rawDataList;
	}

}
