package com.test.cron.expression;


import com.test.cron.expression.util.CronExpressions;
import com.test.cron.expression.util.CronUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class CronExpressionApplication {
    public static void main(String[] args) throws ParseException {
        Date d = CronUtil.getNextOccurrence(new Date(), CronExpressions.EVERY_DAY_AT_8_AM, true);
        d = CronUtil.getNextOccurrence(new Date(), CronExpressions.EVERY_ONE_HOUR, true);
        List<Date> dates = CronUtil.getNextNOccurrence(new Date(), CronExpressions.EVERY_FIRST_DAY_OF_MONTH, 5, true);
        dates = CronUtil.getNextNOccurrence(new Date(), CronExpressions.EVERY_ONE_HOUR, 5, true);
        dates = CronUtil.getNextNOccurrence(new Date(), CronExpressions.EVERY_7_SECONDS_START_AT_00_SEC, 5, true);
        dates = CronUtil.getNextNOccurrence(new Date(), CronExpressions.EVERY_5_SECONDS_START_AT_01_SEC, 5, true);
    }
}
