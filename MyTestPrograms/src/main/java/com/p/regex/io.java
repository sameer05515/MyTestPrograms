package com.p.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class io {
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		String nextLine=scan.nextLine();
		while(nextLine!=null && !"\\c".equals(nextLine)){
			if ((nextLine != null)) {
				Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
				Matcher match = pt.matcher(nextLine);
				while (match.find()) {
					nextLine = nextLine.replace(Character.toString(nextLine.charAt(match.start())), "");
				}
				System.out.println(nextLine);
				nextLine=scan.nextLine();
			}
		}
	}
}