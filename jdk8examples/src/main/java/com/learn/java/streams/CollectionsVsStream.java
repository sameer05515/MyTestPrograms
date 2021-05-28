package com.learn.java.streams;

import java.util.ArrayList;

public class CollectionsVsStream {

	public static void main(String[] args) {
		
		ArrayList<String> names=new ArrayList<String>();
		
		names.add("Prem");
		names.add("Prem1");
		names.add("Prem2");
		
		names.remove(0);
		
		System.out.println(names);
		
		

	}

}
