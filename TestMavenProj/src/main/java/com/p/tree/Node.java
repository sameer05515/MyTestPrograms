package com.p.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author w w w. j a v a g i s t s . c o m
 *
 */
public class Node<DataType> {
	
	//private 

	private DataType data = null;

	private List<Node<DataType>> children = new ArrayList<>();

	private Node<DataType> parent = null;

	public Node(DataType data) {
		this.data = data;
	}

	public Node<DataType> addChild(Node<DataType> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public void addChildren(List<Node<DataType>> children) {
		children.forEach(each -> each.setParent(this));
		this.children.addAll(children);
	}

	public List<Node<DataType>> getChildren() {
		return children;
	}

	public DataType getData() {
		return data;
	}

	public void setData(DataType data) {
		this.data = data;
	}

	private void setParent(Node<DataType> parent) {
		this.parent = parent;
	}

	public Node<DataType> getParent() {
		return parent;
	}

}