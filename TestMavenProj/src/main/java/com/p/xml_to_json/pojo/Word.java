package com.p.xml_to_json.pojo;

import java.util.ArrayList;
import java.util.List;

public class Word {
	
	private int id;
	private String word;
	private String type;
	
	private List<String> meaningsList=new ArrayList<String>();
	private List<String> examplesList=new ArrayList<String>();
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getMeaningsList() {
		return meaningsList;
	}
	public void setMeaningsList(List<String> meaningsList) {
		this.meaningsList = meaningsList;
	}
	public List<String> getExamplesList() {
		return examplesList;
	}
	public void setExamplesList(List<String> examplesList) {
		this.examplesList = examplesList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
