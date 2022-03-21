package com.p.poi.excel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MapSort {

	public static void main(String[] args) {
		Map<String, String> tm = new HashMap<String, String>();
//		tm.put("aa-0004.tiff", "ashish1");
//		tm.put("aa-0001.tiff", "ashish4");
//		tm.put("aa-0002.tiff", "ashish1");
//		tm.put("aa-0003.tiff", "ashish2");

		for (int i = 1; i <=20; i++) {
			tm.put("aa-" + String.format("%04d", 20-i) + ".tiff", "ashish"+ String.format("%04d", 20-i));
		}
		
		Iterator<String> itr = tm.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			System.out.println(/* "Roll no:  " + */ key /* + "     name:   " + tm.get(key) */);
		}

		TreeMap<String, String> tiffAndHOCRFileMap = new TreeMap<String, String>(tm);
//		for(String key:tiffAndHOCRFileMap.keySet()) {
//			
//		}

//		Iterator<String> itr = tiffAndHOCRFileMap.keySet().iterator();
//		while (itr.hasNext()) {
//			String key = itr.next();
//			System.out.println("Roll no:  " + key + "     name:   " + tiffAndHOCRFileMap.get(key));
//		}
	}
}
