package com.p.palindrom;

public class PalindromCheck {

	public static void main(String[] args) {
		String given[] = {"123321","12332",null,""};
		for(String g:given) {
			System.out.println("Given str '" + g + "' is palindrom:" + check(g));
		}
	}

	private static boolean check(String given) {
		boolean result = false;
		if (given != null && !given.trim().equals("")) {
			char[] carr = given.toCharArray();
			for (int i = 0; i < given.length() / 2; i++) {
				if (carr[i] == carr[given.length() - 1 - i]) {
					result = true;
				} else {
					result = false;
					break;
				}
			}
		}
		return result;
	}

}
