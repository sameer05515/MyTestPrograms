package com.p.string;

import java.util.StringTokenizer;

public class TestStringTokenStrict {

	/**
	 * Strict implementation of StringTokenizer
	 * 
	 * @param str
	 * @param delim
	 * @param strict
	 *            true = include NULL Token
	 * @return
	 */
	static StringTokenizer getStringTokenizerStrict(String str, String delim, boolean strict) {
		StringTokenizer st = new StringTokenizer(str, delim, strict);
		StringBuffer sb = new StringBuffer();

		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (s.equals(delim)) {
				sb.append(" ").append(delim);
			} else {
				sb.append(s).append(delim);
				if (st.hasMoreTokens())
					st.nextToken();
			}
		}
		return (new StringTokenizer(sb.toString(), delim));
	}

	static void altStringTokenizer(StringTokenizer st) {
		while (st.hasMoreTokens()) {
			String type = st.nextToken();
			String one = st.nextToken();
			String two = st.nextToken();
			String three = st.nextToken();
			String four = st.nextToken();
			String five = st.nextToken();

			System.out.println(
					"[" + type + "] [" + one + "] [" + two + "] [" + three + "] [" + four + "] [" + five + "]");
		}
	}

	public static void main(String[] args) {
		String input = "Record|One||Three||Five";
		altStringTokenizer(getStringTokenizerStrict(input, "|", true));
	}
}