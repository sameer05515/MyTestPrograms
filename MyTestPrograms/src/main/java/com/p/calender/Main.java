package com.p.calender;

import java.util.Calendar;

public class Main {
	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-"
				+ now.get(Calendar.YEAR));
		int year = Integer.parseInt("2017");
		int month = Integer.parseInt("10") - 1;
		int date = Integer.parseInt("15");
		now.set(year, month, date);
		System.out.println("Given date : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-"
				+ now.get(Calendar.YEAR));

		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday" };
		// Day_OF_WEEK starts from 1 while array index starts from 0
		System.out.println("Current day is : " + strDays[now.get(Calendar.DAY_OF_WEEK) - 1]);
	}

	public static String getDay(String day, String month, String year) {
		/*
		 * Write your code here.
		 */
		java.util.Calendar now = java.util.Calendar.getInstance();
		int yearr = Integer.parseInt(year);
		int monthh = Integer.parseInt(month) - 1;
		int datee = Integer.parseInt(day);
		now.set(yearr, monthh, datee);
		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday" };

		return strDays[now.get(java.util.Calendar.DAY_OF_WEEK) - 1].toUpperCase();

	}
}
