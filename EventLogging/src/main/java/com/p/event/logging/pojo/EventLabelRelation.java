package com.p.event.logging.pojo;

import java.util.Date;

public class EventLabelRelation {
//	Field       Type          Null    Key     Default              Extra   
//	----------  ------------  ------  ------  -------------------  --------
//	id          varchar(100)  NO      PRI     (NULL)                       
//	event_id    varchar(100)  NO      MUL     (NULL)                       
//	label_id    varchar(100)  NO      MUL     (NULL)                       
//	remarks     text          YES             (NULL)                       
//	created_on  datetime      NO              current_timestamp()          
//	updated_on  datetime      NO              current_timestamp()          
//	deleted     tinyint(1)    NO              0    
	
	
	private String id;
	private String eventId;
	private String labelId;
	private String text;
	private Date createdOn;
	private Date updatedOn;
	private boolean deleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

}
