package com.p.algo.expert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UniqueValueMatcher {

	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(new File("C:\\Users\\premendra\\Desktop\\SREIndexes.txt"));
			List<String> rawDataList = new ArrayList<>();
			Set<String> rawSet=new HashSet<String>();
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				rawDataList.add(line);
				rawSet.add(line.toLowerCase());
			}
			scanner.close();
			System.out.println(rawDataList.size());
			System.out.println(rawSet.size());
		} catch (Exception e) {
		}

	}

}
