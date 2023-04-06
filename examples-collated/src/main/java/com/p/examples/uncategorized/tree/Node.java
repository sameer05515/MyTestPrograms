package com.p.examples.uncategorized.tree;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

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
	 * @param children the children to set
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
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	private static final BinaryOperator<String> keyValueGenerator = (key, value) -> {
		return "\n" + key + ": \"" + value + "\"";
	};

	private BiFunction<String, List<Node>, String> keyArrayValueGenerator = (key, list) -> {
		String childAppender = levelAppender.apply(JSON_APPENDER, level + 1);
		String arrChildAppender = levelAppender.apply(JSON_APPENDER, level + 2);
		StringJoiner sj = new StringJoiner(",\n", "\n" + arrChildAppender + "[", "\n" + arrChildAppender + "]");

		if (list != null) {
			
			list.forEach(child -> {
				sj.add(child.toSampleString());
			});

		}

		return "\n" + key + ": " + sj.toString();
	};

	private static final String JSON_APPENDER = "->\t";

	private static final BiFunction<String, Integer, String> levelAppender = (appender, level) -> {
		StringJoiner sj = new StringJoiner("");
		IntStream.rangeClosed(0, level).forEach(i -> {
			sj.add(appender);
		});
		return sj.toString();
	};

	public String toSampleString() {
		String appender = levelAppender.apply(JSON_APPENDER, level);
		String childAppender = levelAppender.apply(JSON_APPENDER, level + 1);
		StringJoiner nodeObjSj = new StringJoiner(",", "\n" + appender + "{", "\n" + appender + "}");
		nodeObjSj.add(keyValueGenerator.apply(childAppender + "name", getLabel()));
		nodeObjSj.add(keyValueGenerator.apply(childAppender + "level", getLevel() + ""));
		nodeObjSj.add(keyArrayValueGenerator.apply(childAppender + "children", getChildren()));
		return nodeObjSj.toString();

	}

	private String getSampleChildrenString() {
		if (children != null && children.size() > 0) {
			StringJoiner sj = new StringJoiner("");
			children.forEach(child -> {
				sj.add(child.toSampleString());
			});
			return sj.toString();
		}
		return "";
	}

	public String toString() {
		String str = "|";

		for (int i = 0; i < level; i++) {
			str = str + (isRootNode ? "   " : "   ");
		}
		str = str + (isRootNode ? "|-" : "|-");
		return "\n" + str + "Node name== \"" + label + "\"" + " level == \"" + level + "\"" + " children == "
				+ ((children != null && children.size() > 0) ? children : "no children");
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
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
	 * @param isRootNode the isRootNode to set
	 */
	public void setIsRootNode(boolean isRootNode) {
		this.isRootNode = isRootNode;
	}

}
