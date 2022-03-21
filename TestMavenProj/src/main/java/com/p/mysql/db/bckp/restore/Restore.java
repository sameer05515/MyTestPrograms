package com.p.mysql.db.bckp.restore;

public class Restore {

	private static String[] dbs = { "interview-mgmt-test", "interview_mgmt", "interview_mgmt_testing", "jasper-report",
			"jtrac-mysql", "jtractest", "relational-db-test", "service-mgmt", "tasksdb", "topic-mgmt", "tt",
			"tutorialdb", "word-meaning-test", "word-meaning" };

	public static void main(String[] args) {
		for (String db : dbs) {
			System.out.format(
					"mysql -u root -p %s < C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\30-Dec-2019\\%s.sql\n",
					db, db);
		}

	}

}
