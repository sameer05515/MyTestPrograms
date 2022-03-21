package com.p.pers.calldetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CallDetail {

	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss"); //  04/02/2011 20:27:05
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //  04/02/2011 20:27:05
			
			List<String> rawDataList = getRawList("/home/premendra/Desktop/1/june-21/v-call-june21.txt");
			
			String name="Vandana Kumari";
			String billCycle="May 2021 bill";
			if (rawDataList != null && rawDataList.size() > 0) {

//				Set<String> numbers = new HashSet<String>();

				for (String line : rawDataList) {
					if (line != null && !line.trim().equalsIgnoreCase("")) {
						String[] tokens = line.split(" ");
						int i = 0;
//						numbers.add(tokens[3]);
						
						////////////////////////
						//2021-06-19 12:56:18
						// 08/APR/2021 13:16:43
						
						
						
				        Date date = sdf.parse(tokens[1]+" "+tokens[2]); // returns date object
//				        System.out.println(date); // outputs: Fri Feb 04 20:27:05 IST 2011
//				        
//
				        System.out.printf("INSERT INTO t_call_details(person, call_time, number, pulses, month) VALUES \n" + 
				        		"('%s', '%s', '%s','%s','%s');\n",name,sdf1.format(date),tokens[3],tokens[5],billCycle);
				        ////////////////////////
						
//						for (String t : tokens) {

//							System.out.print("\t token[" + (i++) + "] == " + t);
//						}
//						System.out.println();
					}
				}
//				for (String n : numbers)
//					System.out.println(n);
//				System.out.println(rawDataList);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
