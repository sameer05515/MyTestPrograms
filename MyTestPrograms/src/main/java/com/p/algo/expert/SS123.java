package com.p.algo.expert;

import java.util.HashMap;

public class SS123 {
	
	private String key;
	private HashMap<String, Integer> keyCountMap;
	
	public SS123() {
		super();
	}
	
	public SS123(String key, HashMap<String, Integer> keyCountMap) {
		super();
		this.key = key;
		this.keyCountMap = keyCountMap;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public HashMap<String, Integer> getKeyCountMap() {
		return keyCountMap;
	}
	public void setKeyCountMap(HashMap<String, Integer> keyCountMap) {
		this.keyCountMap = keyCountMap;
	}

}
