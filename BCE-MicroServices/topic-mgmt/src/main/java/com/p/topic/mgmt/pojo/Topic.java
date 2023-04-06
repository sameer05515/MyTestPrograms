package com.p.topic.mgmt.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "topic"/*, catalog = "zettacoaching"*/)
public class Topic {

	private int id;
	
	private String title;
	private String uniqueStrid;
	private String description;
	private Date dateCreated;
	private Date dateLastModified;
	private Date dateLastRead;
	private boolean personal;
	private int rating=1;
	
	public Topic() {
		super();
		//this(0,null,null);
	}
	
	public Topic(int id, String title, String description, String uniqueStrid) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.uniqueStrid= uniqueStrid;
	}
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	@Column(name = "description", nullable = false, columnDefinition="TEXT")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateCreated
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateLastModified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updation_date", nullable = false, length = 19)
	public Date getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified the dateLastModified to set
	 */
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	/**
	 * @return the personal
	 */
	@Column(name = "isprivate", nullable = false)
	public boolean isPersonal() {
		return personal;
	}

	/**
	 * @param personal the personal to set
	 */
	public void setPersonal(boolean personal) {
		this.personal = personal;
	}

	/**
	 * @return the rating
	 */
	@Column(name = "rating", nullable = false)
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the rating
	 */
	@Column(name = "last_read_date", nullable = false)
	public Date getDateLastRead() {
		return dateLastRead;
	}

	public void setDateLastRead(Date dateLastReed) {
		this.dateLastRead = dateLastReed;
	}

	@Column(name = "uniquestrid", nullable = true)
	public String getUniqueStrid() {
		return uniqueStrid;
	}

	public void setUniqueStrid(String uniqueStrid) {
		this.uniqueStrid = uniqueStrid;
	}

}
