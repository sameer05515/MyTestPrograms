package com.p.parent.child.relation.topics.ParentChildRelationsTopics.dto;

import java.util.ArrayList;
import java.util.List;

public class TopicNode {
	
	private int id;
	private int parentId;
	private String title;
	private String path;
	private List<TopicNode> children=new ArrayList<>();
	
	
	
	public TopicNode() {
		super();
	}
	
	
	public TopicNode(int id, int parentId, String title) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.title = title;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public String toString() {
		return "TopicNode [id=" + id + ", parentId=" + parentId + ", title=" + title + ", path=" + path + ", children="
				+ children + "]";
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public List<TopicNode> getChildren() {
		return children;
	}


	public void setChildren(List<TopicNode> children) {
		this.children = children;
	}
	
	public TopicNode addChild(TopicNode child) {
		this.children.add(child);
		return this;
	}

}
