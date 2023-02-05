package com.p.algo.expert;

import java.io.*;

class Node {
	Node left;
	Node right;
	int val;

	Node(int data) {
		val = data;
	}

	public void displayNode() {
		System.out.println("The value of the node is ->" + val);
	}

	@Override
	public String toString() {
		return "Node [left=" + left + ", right=" + right + ", val=" + val + "]";
	}
} // class ends

class TreeNode {
	Node root;

	TreeNode() {
		root = null;
	}

	public void addNode(int data) {
		Node newNode = new Node(data);
		if (root == null) {
			root = newNode;
		} else {
			Node current = root;
			Node parent;
			while (true) {
				parent = current;
				if (newNode.val < parent.val) {
					System.out.println(newNode.val + " < " + parent.val);
					System.out.println("Moving to LEFT node");
					current = current.left;
					if (current == null)
						;
					{

						System.out.println("Successfully inserted Node with value == >" + newNode.val);
						parent.left = newNode;
						return;
					}
				} // if ends
				else {
					System.out.println(newNode.val + " > " + parent.val);
					System.out.println("Moving to the RIGHT node ");
					current = current.right;
					if (current == null) {
						System.out.println("Successfully inserted Node with value == >" + newNode.val);
						parent.right = newNode;
						return;
					}
				} // else ends
			} // while ends
		} // first else ends
	} // function ends

	@Override
	public String toString() {
		return "TreeNode [root=" + root + "]";
	}
}// class ends

public class TreeAppDriver {
	public static void main(String args[]) throws IOException {
		System.out.println("Executing the main function");
		TreeNode obj = new TreeNode();
		obj.addNode(10);
		System.out.println(" ------------------ \n\n");
		obj.addNode(20);
		System.out.println(" ------------------ \n\n");
		obj.addNode(6);
		System.out.println(" ------------------ \n\n");
		obj.addNode(7);
		System.out.println(" ------------------ \n\n");
		obj.addNode(50);
		System.out.println(" ------------------ \n\n");
		obj.addNode(100);
		System.out.println(" ------------------ \n\n");
		obj.addNode(40);
		System.out.println(" ------------------ \n\n");
		System.out.println(obj);
	}
}