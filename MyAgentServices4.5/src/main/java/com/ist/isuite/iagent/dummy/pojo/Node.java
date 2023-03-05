package com.ist.isuite.iagent.dummy.pojo;

import java.util.List;

public class Node { 

	private String label;

	private List<Node> children;

	private int level;

	private boolean isRootNode;

	public Node(String name, int level) {
		this.label = name;
		this.level = level;
		this.isRootNode = (level == 1) ? true : false;
	}

	/**
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		String str = "|";

		for (int i = 0; i < level; i++) {
			str = str + (isRootNode ? "   " : "   ");
		}
		str = str + (isRootNode ? "|-" : "|-");
		return "\n"
				+ str
				+ "Node name== "
				+ label
				+ " children == "
				+ ((children != null && children.size() > 0) ? children
						: "no children");
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the isRootNode
	 */
	public boolean getIsRootNode() {
		return isRootNode;
	}

	/**
	 * @param isRootNode
	 *            the isRootNode to set
	 */
	public void setIsRootNode(boolean isRootNode) {
		this.isRootNode = isRootNode;
	}

}
