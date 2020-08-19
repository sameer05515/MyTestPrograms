package com.p.plan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class Plan {
	
	private static int TotalWordMeaning=50001;

	public static void main(String[] args) throws Throwable {
		
		System.out.println("======================");
		System.out.println("TotalWordMeaning : "+TotalWordMeaning);
		int targetInOneDay=100;
		JSONObject targetDet=getTargetDetail(TotalWordMeaning,targetInOneDay);
		System.out.println(targetDet.toString());
		System.out.println("======================");

	}

	private static JSONObject getTargetDetail(int totalWordMeaning, int targetInOneDay) throws ParseException {
		JSONObject targetDet= new JSONObject();
		
		Date currDate=new Date();
		double days=Math.ceil((double)totalWordMeaning/targetInOneDay);
		Date tartgetDate=getDateAdd((int)days);
		
		targetDet.put("TotalWordMeaning", TotalWordMeaning);
		targetDet.put("targetInOneDay", targetInOneDay);
		targetDet.put("totalDaysNeeded", days);
		targetDet.put("targetDate", tartgetDate);
		return targetDet;
	}
	
	private static Date getDateAdd(int days) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date: "+sdf.format(cal.getTime()));
		//Adding 1 Day to the current date
		cal.add(Calendar.DAY_OF_MONTH, days);  
		//Date after adding one day to the current date
		String newDate = sdf.format(cal.getTime());  
		//Displaying the new Date after addition of 1 Day
		//System.out.println("Incremnted current date by one: "+newDate);
		
		return sdf.parse(newDate);
	}

}
