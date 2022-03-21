package com.p.event.logging.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Label {

//	Field        Type          Null    Key     Default              Extra   
//	-----------  ------------  ------  ------  -------------------  --------
//	id           varchar(100)  NO      PRI     (NULL)                       
//	label        text          NO      UNI     (NULL)                       
//	description  text          NO              (NULL)                       
//	created_on   datetime      NO              current_timestamp()          
//	updated_on   datetime      NO              current_timestamp()          
//	enabled      tinyint(1)    NO              1   

	private String id;
	private String label;
	private String description;
	private Date createdOn;
	private Date updatedOn;
	private boolean enabled;

	private List<Event> events = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
