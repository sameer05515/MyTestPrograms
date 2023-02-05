package com.test.cron.expression.util;

import org.apache.log4j.Logger;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CronUtil {

    private static final Logger logger = Logger.getLogger(CronUtil.class);

    private CronUtil(){}

    public static Date getNextOccurrence(Date refDate, CronExpressions cronExpression, boolean showLog) throws ParseException {
        CronTriggerImpl tr = new CronTriggerImpl();
        tr.setCronExpression(cronExpression.getExpression());
        Date nextFireAt = tr.getFireTimeAfter(refDate);
        if (showLog) {
            logger.debug("-------------------------------");
            logger.debug("Cron Expression: '" + cronExpression.getLabel() + "' : '" + cronExpression.getExpression() + "'");
            logger.debug("Reference time: " + refDate);
            logger.debug("Next fire after reference time: " + nextFireAt);
        }
        return nextFireAt;
    }

    public static List<Date> getNextNOccurrence(Date refDate, CronExpressions cronExpression, int loop, boolean showLog) throws ParseException {
        List<Date> nextOccurrences = new ArrayList<>();
        int count = 0;
        Date nextDate = refDate;
        while (count < loop) {
            nextDate = getNextOccurrence(nextDate, cronExpression, false);
            nextOccurrences.add(nextDate);
            count++;
        }
        if (showLog) {
            logger.debug("-------------------------------");
            logger.debug("Cron Expression: '" + cronExpression.getLabel() + "' : '" + cronExpression.getExpression() + "'");
            logger.debug("Reference time: " + refDate);
            logger.debug("Next " + loop +" fires after reference time: " + nextOccurrences);
        }
        return nextOccurrences;
    }
}
