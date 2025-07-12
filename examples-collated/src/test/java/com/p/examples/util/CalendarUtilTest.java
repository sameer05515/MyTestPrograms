package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalendarUtilTest {

    private final CalendarUtil calendarUtil;

    @Autowired
    public CalendarUtilTest(CalendarUtil calendarUtil) {
        this.calendarUtil = calendarUtil;
    }
    @Test
    void testGetCurrentDate() {
        String currentDate = calendarUtil.getCurrentDate();
        // Assuming the test runs on April 30, 2024
        assertEquals("04-30-2024", currentDate);
    }

    @Test
    void testGetDayOfWeek() {
        assertEquals("Sunday", calendarUtil.getDayOfWeek("30", "04", "2024"));
        assertEquals("Monday", calendarUtil.getDayOfWeek("01", "05", "2024"));
        assertEquals("Tuesday", calendarUtil.getDayOfWeek("02", "04", "2024"));
    }

    @Test
    void testFormatDate() {
        assertEquals("04-15-2017", calendarUtil.formatDate(2017, 10, 15));
    }
}
