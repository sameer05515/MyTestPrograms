package com.p.appconfiguration;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.p.httpsClient.NoticeAppClient;
import com.p.secure.Secure;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.p")
public class AppConfiguration {

	@Autowired
	Environment env;

	@PostConstruct
	public PropertiesFile getPropertiesFille() {
		PropertiesFile p = null;
		p = new PropertiesFile();

		try {

			PropertiesFile
					.setCons_secret(env.getProperty("cons_secret").isEmpty() ? "" : env.getProperty("cons_secret"));
			PropertiesFile.setCons_key(env.getProperty("cons_key").isEmpty() ? "" : env.getProperty("cons_key"));
			PropertiesFile.setGet_token_url(
					env.getProperty("get_token_url").isEmpty() ? "" : env.getProperty("get_token_url"));
			PropertiesFile.setCd_trades_inquiry(
					env.getProperty("cd_trades_inquiry").isEmpty() ? "" : env.getProperty("cd_trades_inquiry"));
			PropertiesFile.setCm_trades_inquiry(
					env.getProperty("cm_trades_inquiry").isEmpty() ? "" : env.getProperty("cm_trades_inquiry"));
			PropertiesFile.setFo_trades_inquiry(
					env.getProperty("fo_trades_inquiry").isEmpty() ? "" : env.getProperty("fo_trades_inquiry"));
			// PropertiesFile.setFo_action_inquiry(env.getProperty("fo_action_inquiry").isEmpty()?""
			// : env.getProperty("fo_action_inquiry"));
			PropertiesFile
					.setMember_code(env.getProperty("member_code").isEmpty() ? "" : env.getProperty("member_code"));

			PropertiesFile.setDsdriverclassname(
					env.getProperty("dsdriverclassname").isEmpty() ? "" : env.getProperty("dsdriverclassname"));
			PropertiesFile.setDspassword(env.getProperty("dspassword").isEmpty() ? ""
					: Secure.AESDecrypt(env.getProperty("dspassword"), ""));
			PropertiesFile.setDsurl(env.getProperty("dsurl").isEmpty() ? ""
					: "jdbc:oracle:thin:@" + Secure.AESDecrypt(env.getProperty("dsurl"), ""));
			PropertiesFile.setDsusername(env.getProperty("dsusername").isEmpty() ? ""
					: Secure.AESDecrypt(env.getProperty("dsusername"), ""));

			// PropertiesFile.setDspassword(env.getProperty("dspassword").isEmpty() ? ""
			// : env.getProperty("dspassword"));
			// PropertiesFile.setDsurl(
			// env.getProperty("dsurl").isEmpty() ? "" : env.getProperty("dsurl"));
			// PropertiesFile.setDsusername(env.getProperty("dsusername").isEmpty() ? ""
			// : env.getProperty("dsusername"));

			PropertiesFile.setLogPath(env.getProperty("logpath").isEmpty() ? "" : env.getProperty("logpath"));

			PropertiesFile.setSeqLogPath(env.getProperty("notic.logging.seqLogPath").isEmpty() ? ""
					: env.getProperty("notic.logging.seqLogPath"));

			PropertiesFile.setApi_active_time(
					(env.getProperty("api_active_time").isEmpty() ? "" : env.getProperty("api_active_time")));

			/*
			 * PropertiesFile.setIteration_minute((env.getProperty("iteration_minute").
			 * isEmpty()?"" : env.getProperty("iteration_minute")));
			 */

			Log.notice.info("=============memebr ciode: " + PropertiesFile.getMember_code());
			Log.notice.info("seckret: " + PropertiesFile.getCons_secret() + "key: " + PropertiesFile.getCons_key());

			Log.notice.info(
					"PropertiesFile.isEnable_cd_trades_inquiry() : " + PropertiesFile.isEnable_cd_trades_inquiry());
			Log.notice.info(
					"PropertiesFile.isEnable_cm_trades_inquiry() : " + PropertiesFile.isEnable_cm_trades_inquiry());
			Log.notice.info(
					"PropertiesFile.isEnable_fo_action_inquiry() : " + PropertiesFile.isEnable_fo_action_inquiry());
			Log.notice.info(
					"PropertiesFile.isEnable_fo_trades_inquiry() : " + PropertiesFile.isEnable_fo_trades_inquiry());

			Log.notice.info("PropertiesFile.getSeqLogPath() : " + PropertiesFile.getSeqLogPath());

			/// =======

//			Log.notice.info("PropertiesFile.getDsdriverclassname() : " + PropertiesFile.getDsdriverclassname());
//			Log.notice.info("PropertiesFile.setDspassword() : " + PropertiesFile.getDspassword());
//			Log.notice.info("PropertiesFile.getDsurl() : " + PropertiesFile.getDsurl());
//			Log.notice.info("PropertiesFile.getDsusername() : " + PropertiesFile.getDsusername());

			Log.notice.info("Properties loaded successfully........");
		} catch (Exception e) {
			Log.notice.info("Exception occured during loading  Property file........ |Exception: " , e);
		}

		return p;
	}

	@Autowired
	JobLauncher jobLauncher;

	@SuppressWarnings("unused")
	@Autowired
	private JobBuilderFactory jobs;

	@SuppressWarnings("unused")
	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	Job job;

	// @Bean
	// public Step stepOne() {
	// return steps.get("stepOne").tasklet(new FirstTask()).build();
	// }
	//
	// @Bean
	// public Job demoJob() { // here we add the task to perfrom
	// return jobs.get("demoJob").incrementer(new
	// RunIdIncrementer()).start(stepOne()).build();
	// }

	@Autowired
	NoticeAppClient client;

	public static void main(String args[]) {

		SpringApplication.run(AppConfiguration.class, args);
		// ConfigurableApplicationContext applicationContext = new
		// SpringApplicationBuilder(AppConfiguration.class)
		// .properties("spring.config.name:application",
		// "spring.config.location:file:///D:/notice/properties/")
		// .build().run(args);

	}

	// @Scheduled(cron = "0 0 9 ? * SUN,MON,TUE,WED,THU,FRI,SAT")
	@Scheduled(cron = "${notic.cronExpression.resetMaxSeqNoNextDay}")
	public void resetMaxSeqNoNextDay() throws Exception {

		Log.notice.info("Last day sequence numbers : " + " Utility.getMaxCDSequenceNo() : "
				+ Utility.getMaxCDSequenceNo() + " Utility.getMaxCMSequenceNo() : " + Utility.getMaxCMSequenceNo()
				+ " Utility.getMaxFOSequenceNo() : " + Utility.getMaxFOSequenceNo());

		Utility.setMaxCDSequenceNo(0);
		Utility.setMaxCMSequenceNo(0);
		Utility.setMaxFOSequenceNo(0);

		Log.notice.info("Resetting values for current day : " + " Utility.getMaxCDSequenceNo() : "
				+ Utility.getMaxCDSequenceNo() + " Utility.getMaxCMSequenceNo() : " + Utility.getMaxCMSequenceNo()
				+ " Utility.getMaxFOSequenceNo() : " + Utility.getMaxFOSequenceNo());
	}

	@Scheduled(cron = "${notic.cronExpression.jobRerunCron}")
	// @Scheduled(cron = "0 */5 * * * ?") // this is for 5 minute
	public void performtttttt() throws Exception {

		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);

	}

}
