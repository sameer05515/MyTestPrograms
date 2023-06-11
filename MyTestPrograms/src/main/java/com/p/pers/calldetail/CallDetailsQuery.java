package com.p.pers.calldetail;

public final class CallDetailsQuery {

	public static String getDistinctNumberWithPulses = "SELECT DISTINCT number, sum(pulses),month FROM t_call_details \n"
			+ "GROUP BY number,month\n" + "ORDER BY month,sum(pulses) DESC";
	
	public static void main(String[] args) {
		System.out.println(getDistinctNumberWithPulses);
	}

}
