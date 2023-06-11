package com.p.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class TestRPC {
	
	

	public static void main(String[] args) {
		
		

//		int[] count = { 2, 3, 4, 1, 1, 4, 1 };
		int[] count = { 2};
		List<Node> list = new TestRPC().getTreeData(count);		
		for (Node node : list) {
			System.out.print(node);
		}
		System.out.println("\n=====================================");
		
		List<Node> jdk8list = new TestRPC().getTreeDataJdk8Way(count);
		System.out.println("[");
		jdk8list.forEach(node -> {
			System.out.println(node.toSampleString());
		});
		System.out.println("\n]");
	}

	public List<Node> getTreeDataJdk8Way(int... count) {
		List<Node> list = new ArrayList<Node>();
		if (count != null && count.length > 0) {
			int level = 1;
			IntStream.rangeClosed(0, count.length - 1).forEach(i -> {
				Node parent = new Node("node [" + (i + 1) + "]", level);
				List<Node> children = createChildrens(parent, count[i], 1);
				parent.setChildren(children);
				list.add(parent);
			});
		}

		return list;
	}

	public List<Node> getTreeData(int... count) {
//		int[] count = { 2, 3, 4, 1, 1, 4, 1 };
		List<Node> list = new ArrayList<Node>();
		int level = 1;
		for (int i = 0; i <= count.length - 1; i++) {
			Node parent = new Node("node [" + (i + 1) + "]", level);
			List<Node> children = createChildrens(parent, count[i], 1);
			parent.setChildren(children);
			list.add(parent);
		}
		return list;
	}

	private List<Node> createChildrens(Node parentNode, int countOfChildrens, int level) {
		List<Node> children = null;
		for (int j = 0; j < countOfChildrens; j++) {
			if (children == null) {
				children = new ArrayList<Node>();
			}
			Node child = new Node(parentNode.getLabel() + "[" + (j + 1) + "]", parentNode.getLevel() + 1);
			List<Node> childChildren = createChildrens(child, countOfChildrens - 1, parentNode.getLevel() + 1);
			child.setChildren(childChildren);
			children.add(child);
		}
		return children;
	}
}
