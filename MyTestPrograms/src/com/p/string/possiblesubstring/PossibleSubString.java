package com.p.string.possiblesubstring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PossibleSubString {

	public static void main(String[] args) {
		String[] arr={"hello","abcd"};
		
		
		
		for(String str:arr){
			System.out.println("##########################");
			System.out.println("Given string : "+str);
			String[] subs=possibleSubstring(str);
			System.out.println("Possible substring");
			for(String s:subs){
				System.out.println(s);
			}
		}

	}

	private static String[] possibleSubstring(String str) {
		String[] subs=null;
		if(str==null){
			return null;
		}
		str=str.trim();
		if(str.length()<=0){
			return null;
		}
		
		Set<String> substringSet=new HashSet<String>();
		
		for(int i=1;i<=str.length();i++){
			for(int j=0;j<=str.length()-i;j++){
				String subss=str.substring(j, j+i);
				substringSet.add(subss);
			}
		}
		
		List<String> substringList=new ArrayList<String>();
		for(String s:substringSet){
			substringList.add(s);
		}
		
		Collections.sort(substringList);
		
		subs=(String[]) substringList.toArray(new String[substringList.size()]);
		
		
		
		return subs;
	}

}
