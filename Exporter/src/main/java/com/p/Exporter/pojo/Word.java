package com.p.Exporter.pojo;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "t_word")
public class Word {

	@Id
	@GeneratedValue
	int id;
	String unique_name;
	String word;
	String type;
	String details;
	Date created_on;
	Date updated_on;
	Date last_read;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnique_name() {
		return unique_name;
	}

	public void setUnique_name(String unique_name) {
		this.unique_name = unique_name;
	}

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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public Date getLast_read() {
		return last_read;
	}

	public void setLast_read(Date last_read) {
		this.last_read = last_read;
	}

	@Override
	public String toString() {
//		JSONObject obJsonObject=new JSONObject();
//		obJsonObject.put("id", id);
//		obJsonObject.put("unique_name", unique_name);
//		obJsonObject.put("word", word);
//		obJsonObject.put("type", type);
//		obJsonObject.put("details", details);
//		obJsonObject.put("created_on", created_on);
//		obJsonObject.put("updated_on", updated_on);
//		obJsonObject.put("last_read", last_read);
//		
//		return obJsonObject.toJSONString();
		
		
		
		
		return "\n{\"id\":\"" + id + "\", \"unique_name\":\"" + unique_name + "\", \"word\":\"" + word + "\", \"type\":\"" + type
				+ "\", \"details\":\"" + details + "\", \"created_on\":\"" + created_on + "\", \"updated_on\":\"" + updated_on
				+ "\", \"last_read\":\"" + last_read + "\"}";
	}

}
