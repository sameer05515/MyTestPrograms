package com.p.option.generation;

public class StringTest {

	private static String query = "SELECT tc.cat_id,tc.cat_name,tc.creation_date,tc.last_updation_date,tc.rating,\r\n" + 
			"(SELECT COUNT(*) FROM t_catg_ques tcq WHERE tcq.linked_cat_id=tc.cat_id) AS 'totalQuestionsCount'\r\n" + 
			" FROM t_category tc ORDER BY last_updation_date DESC";
	
	private static String query2="select cat_id,cat_name,creation_date,last_updation_date,rating,last_read_date" +
			", (SELECT COUNT(*) FROM t_catg_ques tcq WHERE tcq.linked_cat_id=cat_id) AS 'totalQuestionsCount'"
			+ " from t_category" + " where cat_id=?";

	public static void main(String[] args) {

		System.out.println(query2);
	}

}
