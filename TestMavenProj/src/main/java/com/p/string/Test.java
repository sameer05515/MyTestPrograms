package com.p.string;

import java.util.StringTokenizer;

public class Test {

	public static void main(String[] args) {
		String[] data = {
				"2617453,1,604206,82638451970289,44139,75,1950,1,1000000011731383,25,5151,1,590123,08756,,2,6001,1260962707,1,,111111111111100,P,08756,NIFTY, ,OPTIDX,1261180800,1220000,CE,,,,,,,,",
				"5|6|7||8|9||",
				"2219,1,2219,82439708184289,8568,4,2675000,2,1000000000015509,2,10725,1,594103,08756,,2,6001,1257930117,1,,333333333333100,P,08756,USDINR, ,OPTCUR,1259280000,715000000,CE,,,,,,,,",
				"2566,1,31250325,82762511250182,47841,75,1211665,2,1100000000003015,25,37359,1,557208,08756,,2,6001,1262855701,1,,111111111111100,P,08756, , , ,0,0, ,,,,,,,,",
				"20437,1,2291,82128275724619,4952,1,719125000,2,1000000000019811,2,14180,1,149593,08756,,2,6001,1253178035,1,,111111111111100,P,08756,JPYINR, ,OPTCUR,1264775400,602500,PE,,,,,,,,",
				"855960,N,2019122000008290,82660590026752,685,200,30500,1,1000000000065680,20,8945,1,574947,08756,1,2,6001,1261299914,1,0,N,,0,1.111111111111E14,4096,N,,,,,,,,",
				"855960,N,2019122000008290,82660590026752,685,200,30500,1,1000000000065680,20,8945,1,574947,08756,1,2,6001,1261299914,1,0,N,,0,111111111111100,4096,N,CHOLAFIN,EQ,CHOLAMANDALAM IN \\u0026 FIN CO,EQ,,,,,,,," };
		Utility.splitData(data[6], ",");
		// Utility.splitData(data[1], "\\|");
		System.out.println("============================================");
		// Utility.splitDataWithStringTokenizer(data[0]);
	}

}

class Utility {
	static void splitData(String data, String splitString) {
		String[] t = data.split(splitString, -1);
		System.out.println("data : " + data + "\nsplitString : \"" + splitString + "\"");
		// String[] t = data.split(splitString);

		System.out.println("t.length() : " + ((t != null) ? t.length : 0));
		int i = 0;
		for (String s : t) {
			System.out.println("t[" + i + "] : " + t[i]);
			i++;
		}
	}

	/**
	 * StringTokenizer has a constructor which takes 3 arguments which the third is
	 * a boolean that controls if the delimiters are to be returned as tokens or
	 * not. You should set it to true.
	 * 
	 * <pre>
	 * new StringTokenizer(yourString, yourDelimiters, true);
	 * </pre>
	 */
	static void splitDataWithStringTokenizer(String data) {
		StringTokenizer stt = null;

		stt = new StringTokenizer(data, ",", true);

		System.out.println("t.length() : " + stt.countTokens());

		int i = 0;
		while (stt.hasMoreTokens()) {
			System.out.println("t[" + i + "] : " + stt.nextToken());
			i++;
		}
	}
}
