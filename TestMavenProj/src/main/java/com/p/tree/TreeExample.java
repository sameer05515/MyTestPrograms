package com.p.tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class TreeExample {

	public static void main(String[] args) throws FileNotFoundException {
//		Node<String> root = createTree();

		Node<String> root = createTreeOfGivenSize("ROOT", "Node ", 2);
//		for (int i = 0; i < root.getChildren().size(); i++) {
//
//		}
		JSONObject rootJson = printTreeJson(null, root, " ");
		
		PrintStream ps = new PrintStream(new File("C:\\Users\\premendra.kumar\\Desktop\\treeData.json"));
		ps.print(rootJson.toString(0));

		System.out.println(rootJson.toString(0));
//		System.out.println("=====================================");
		// printULLi(true,rootJson," ");
	}

	private static Node<String> createTreeOfGivenSize(String parentData, String childDataPrefix, int childrenSize) {
		Node<String> parentNode = new Node<>(parentData);
		//System.out.println(parentNode.getData());
//		for (int i = childrenSize; i >=0; i--) {
		for (int i = 0; i <=childrenSize; i++) {
//			Node<String> childNode = new Node<String>(childDataPrefix + (i + 1));
			Node<String> childNode = createTreeOfGivenSize(childDataPrefix + (i + 1), childDataPrefix + (i + 1),
					childrenSize-1);
			parentNode.addChild(childNode);
		}
		return parentNode;
	}
	
//	private static void printULLi(boolean isRoot,JSONObject rootJson,String appender) {
//		if(isRoot) {System.out.println("<ul>");} else {System.out.println("<li>");}
//		System.out.println(appender+rootJson.getString("data"));
//		rootJson.getJSONArray("children").forEach(each -> {
//			if(isRoot) {System.out.println("<li>");} else {System.out.println("<ul>");}
//			//System.out.println(appender+appender+((JSONObject)each).getString("data"));
//			if(isRoot) {System.out.println("</li>");} else {System.out.println("</ul>");}
//		});
//		
//		if(isRoot) {System.out.println("</ul>");} else {System.out.println("</li>");}
//		
//	}

	private static void printULLi(boolean isRoot, JSONObject rootJson, String appender) {
		System.out.println(appender + "<ul>");
		if (isRoot)
			System.out.println(appender + appender + rootJson.getString("data"));
		rootJson.getJSONArray("children").forEach(each -> {
			System.out.println("<li>");
			System.out.println(appender + appender + ((JSONObject) each).getString("data"));
			printULLi(false, ((JSONObject) each), appender + appender);
			System.out.println("</li>");
		});

		System.out.println(appender + "</ul>");

	}

//	private static <T> Node<T> createTreeOfGivenSize(T data,int childrenSize){
//		Node<T> root = new Node<>(data);
//		for(int i=0;i<=childrenSize;i++) {
//			Node<T> node1 = root.addChild(new Node<T>(data.toString()+));
//		}
//		return root;
//	}

	

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

	private static <T> JSONObject printTreeJson(Node<T> parentNode, Node<T> node, String appender) {
//		System.out.println(appender + node.getData());
		JSONObject nodeJson = new JSONObject();
		nodeJson.put("data", node.getData());
		T parentName = (parentNode != null) ? parentNode.getData() : null;
		nodeJson.put("parent", parentName != null ? parentName.toString() : "null");
		JSONArray childrenNodeArrayJson = new JSONArray();
//		for (Iterator<Node<T>> iterator = node.getChildren().iterator(); iterator.hasNext();) {
//			Node<T> each = (Node<T>) iterator.next();
//			JSONObject nodeChildJson = printTreeJson(node, each, appender + appender);
//			childrenNodeArrayJson.put(nodeChildJson);
//		}
		node.getChildren().forEach(each -> {
			JSONObject nodeChildJson = printTreeJson(node, each, appender + appender);
			childrenNodeArrayJson.put(nodeChildJson);
		});
		nodeJson.put("children", childrenNodeArrayJson);
		return nodeJson;
	}
}