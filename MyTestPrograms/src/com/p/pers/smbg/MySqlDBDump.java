package com.p.pers.smbg;

public class MySqlDBDump {

	private static String[] dbs1 = { "bce-shopping-cart", "event-logging-db", "interview-mgmt-test", "interview_mgmt",
			"interview_mgmt_sep_27_2019", "interview_mgmt_testing", "interview_mgmt_test_sep_27_2019", "jasper-report",
			"jtrac-mysql", "jtractest", "notes_app", "relation-graph-representation", "relational-db-test",
			"service-mgmt", "tasksdb", "topic-mgmt-sep-27-2019", "topic-mgmt", "topicmgmt27jul18", "tt", "tutorialdb",
			"word-meaning-test", "word-meaning" };
	
	private static String[] dbs = { "bce-shopping-cart", "event-logging-db", "interview-mgmt-test", "interview_mgmt",
			"interview_mgmt_sep_27_2019", "interview_mgmt_testing", "interview_mgmt_test_sep_27_2019", "jasper-report",
			"jtrac-mysql", "jtractest", "notes_app", "relation-graph-representation", "relational-db-test",
			"service-mgmt", "tasksdb", "topic-mgmt-sep-27-2019", "topic-mgmt", "topicmgmt27jul18", "tt", "tutorialdb",
			"word-meaning-test", "word-meaning" };

	private static String mysqlBinDir = "D:\\Prem\\CUST-INST\\mariadb-10.3.13-winx64\\bin\\";

	private static String dbBabkupDir = "D:\\Prem\\GIT-PROJ\\db-files\\30-Dec-2019\\";

	public static void main(String[] args) {
		
//		for(String db:dbs) {
//			System.out.println("CREATE DATABASE "+db.replaceAll("-", "_")+";");
//		}
		
		for(int i=0;i<dbs.length;i++) {
			System.out.println(mysqlBinDir + "mysql -u root -p " + dbs1[i].replaceAll("-", "_") + " < " + dbBabkupDir + dbs[i] + ".sql");
		}

//		for (String db : dbs) {
//			System.out.println(mysqlBinDir + "mysql -u root -p " + db + " < " + dbBabkupDir + db + ".sql");
//		}
	}

}
