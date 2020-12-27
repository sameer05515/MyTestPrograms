package com.p.option.generation;

public class StringTest {

	private static String query = "SELECT tc.cat_id,tc.cat_name,tc.creation_date,tc.last_updation_date,tc.rating,\r\n"
			+ "(SELECT COUNT(*) FROM t_catg_ques tcq WHERE tcq.linked_cat_id=tc.cat_id) AS 'totalQuestionsCount'\r\n"
			+ " FROM t_category tc ORDER BY last_updation_date DESC";

	private static String query2 = "select cat_id,cat_name,creation_date,last_updation_date,rating,last_read_date"
			+ ", (SELECT COUNT(*) FROM t_catg_ques tcq WHERE tcq.linked_cat_id=cat_id) AS 'totalQuestionsCount'"
			+ " from t_category" + " where cat_id=?";

	public static void main(String[] args) {

//		System.out.println(query2);

		String[] arr = { "coding_ex_db", "event-logging-db", "interview-mgmt-test", "interview_mgmt",
				"interview_mgmt_sep_27_2019", "interview_mgmt_test_sep_27_2019", "interview_mgmt_testing",
				"jasper-report", "jtrac-mysql", "jtractest", "notes_app", "performance_schema",
				"relation-graph-representation", "relational-db-test", "service-mgmt", "tasksdb", "test", "topic-mgmt",
				"topic-mgmt-sep-27-2019", "topicmgmt27jul18", "tt", "tutorialdb", "word-meaning", "word-meaning-test" };

		for (String s : arr) {
			System.out.printf("echo \"======== DB-DUMP %s =====================\"\r\n"
					+ "$MYSQL_BIN_DIR/mysqldump -u root -p --routines --skip-extended-insert %s > $DB_DUMP_WITH_DATA/%s.sql\r\n"
					+ "$MYSQL_BIN_DIR/mysqldump -u root -p --routines --no-data %s > $DB_DUMP_WITH_DDL_ONLY/%s.sql\r\n\n"
					+ "", s.toUpperCase(), s, s, s, s);
		}
	}

}
