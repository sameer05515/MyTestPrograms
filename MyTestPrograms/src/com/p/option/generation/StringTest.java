package com.p.option.generation;

public class StringTest {

	private static String query = "SELECT tc.cat_id,tc.cat_name,tc.creation_date,tc.last_updation_date,tc.rating,\r\n" + 
			"(SELECT COUNT(*) FROM t_catg_ques tcq WHERE tcq.linked_cat_id=tc.cat_id) AS 'totalQuestionsCount'\r\n" + 
			" FROM t_category tc ORDER BY last_updation_date DESC";

	public static void main(String[] args) {

		System.out.println(query);
	}

}
