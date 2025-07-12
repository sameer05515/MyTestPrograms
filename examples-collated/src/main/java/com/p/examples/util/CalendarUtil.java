package com.p.examples.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class CalendarUtil {

    public String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        return formatDate(now);
    }

    public String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return formatDate(calendar);
    }

    public String getDayOfWeek(String day, String month, String year) {
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearInt, monthInt - 1, dayInt);

        String[] daysOfWeek = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return daysOfWeek[dayIndex];
    }

    private static String formatDate(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%02d-%02d-%04d", month, day, year);
    }
}
