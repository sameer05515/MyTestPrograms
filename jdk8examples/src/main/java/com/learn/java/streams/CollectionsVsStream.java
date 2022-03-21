package com.learn.java.streams;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CollectionsVsStream {

	public static void main(String[] args) {
		
		ArrayList<String> names=new ArrayList<String>();
		
		names.add("Prem");
		names.add("Prem1");
		names.add("Prem2");
		
		names.remove(0);
		
		System.out.println(names);
		
		Stream<String> nameStream= names.stream();
		
		try {
			nameStream.forEach(System.out::println);
			nameStream.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		names.stream().forEach(System.out::println);
		

	}

}
