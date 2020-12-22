package com.p.event.logging.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

//	Field       Type          Null    Key     Default              Extra   
//	----------  ------------  ------  ------  -------------------  --------
//	id          varchar(100)  NO      PRI     (NULL)                       
//	title       varchar(500)  NO              (NULL)                       
//	detail      text          NO              (NULL)                       
//	event_date  date          NO              (NULL)                       
//	created_on  datetime      NO              current_timestamp()          
//	updated_on  datetime      NO              current_timestamp()          
//	enabled     tinyint(1)    NO              1 
	
	private String id;
	private String title;
	private String detail;
	private Date eventDate;
	private Date createdOn;
	private Date updatedOn;
	private boolean enabled;

	private List<Label> labels=new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
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
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

}
