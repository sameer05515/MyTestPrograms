package com.p.tree;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * w w w . j a v a g i s t s . c o m
 *
 */
public class TreeExample {

	public static void main(String[] args) {
		Node<String> root = createTree();
		JSONObject rootJson = printTreeJson(null, root, " ");
		System.out.println(rootJson.toString(2));
	}

	private static Node<String> createTree() {
		Node<String> root = new Node<>("root");

		Node<String> node1 = root.addChild(new Node<String>("node 1"));

		Node<String> node11 = node1.addChild(new Node<String>("node 11"));
		Node<String> node111 = node11.addChild(new Node<String>("node 111"));
		Node<String> node112 = node11.addChild(new Node<String>("node 112"));

		Node<String> node12 = node1.addChild(new Node<String>("node 12"));

		Node<String> node2 = root.addChild(new Node<String>("node 2"));

		Node<String> node21 = node2.addChild(new Node<String>("node 21"));
		Node<String> node211 = node2.addChild(new Node<String>("node 22"));
		return root;
	}

	private static <T> void printTree(Node<T> node, String appender) {
		System.out.println(appender + node.getData());
		node.getChildren().forEach(each -> printTree(each, appender + appender));
	}

	private static <T> JSONObject printTreeJson(Node<T> parentNode, Node<T> node, 
			String appender) {
		System.out.println(appender + node.getData());
		JSONObject nodeJson = new JSONObject();
		nodeJson.put("data", node.getData());
		T parentName=(parentNode!=null)?parentNode.getData():null;
		nodeJson.put("parent", parentName!=null?parentName.toString():"null");
		JSONArray childrenNodeArrayJson=new JSONArray();
		for (Iterator<Node<T>> iterator = node.getChildren().iterator(); iterator.hasNext();) {
			Node<T> each = (Node<T>) iterator.next();
			JSONObject nodeChildJson=printTreeJson(node, each, appender+ appender);
			childrenNodeArrayJson.put(nodeChildJson);
		}
		nodeJson.put("children", childrenNodeArrayJson);
		return nodeJson;
	}
}