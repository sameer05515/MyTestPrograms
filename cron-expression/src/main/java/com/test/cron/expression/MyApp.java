package com.test.cron.expression;

import com.test.cron.expression.jobs.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

public class MyApp {
    public static void main(String... args) throws SchedulerException, ParseException, InterruptedException {
        try {
            JobDetail job1 = JobBuilder.newJob(Job1.class)
                    .withIdentity("job1", "group1").build();
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);
            JobDetail job2 = JobBuilder.newJob(Job2.class)
                    .withIdentity("job2", "group2").build();
            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger2", "group2")
                    .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0/7 * * * * ?")))
                    .build();
            Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
            scheduler2.start();
            scheduler2.scheduleJob(job2, trigger2);
            JobDetail job3 = JobBuilder.newJob(Job3.class)
                    .withIdentity("job3", "group3").build();
            Trigger trigger3 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger3", "group3").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 46))
                    .build();
            Scheduler scheduler3 = new StdSchedulerFactory().getScheduler();
            scheduler3.start();
            scheduler3.scheduleJob(job3, trigger3);
            JobDetail job4 = JobBuilder.newJob(Job4.class)
                    .withIdentity("job4", "group4").build();
            Trigger trigger4 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger4", "group4").withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(3, 13, 46))
                    .build();
            Scheduler scheduler4 = new StdSchedulerFactory().getScheduler();
            scheduler4.start();
            scheduler4.scheduleJob(job4, trigger4);
            JobDetail job5 = JobBuilder.newJob(Job5.class)
                    .withIdentity("job5", "group5").build();
            Trigger trigger5 = TriggerBuilder
                    .newTrigger().withIdentity("cronTrigger5", "group5")
                    .withSchedule(CronScheduleBuilder.monthlyOnDayAndHourAndMinute(28, 13, 46))
                    .build();
            Scheduler scheduler5 = new StdSchedulerFactory().getScheduler();
            scheduler5.start();
            scheduler5.scheduleJob(job5, trigger5);
            Thread.sleep(100000);
            scheduler1.shutdown();
            scheduler2.shutdown();
            scheduler3.shutdown();
            scheduler4.shutdown();
            scheduler5.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}  