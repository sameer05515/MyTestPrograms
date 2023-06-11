package com.p.algo.expert;
import java.io.IOException;

class Node {
    Node left;
    Node right;
    int val;

    Node(int data) {
        val = data;
    }

    public void displayNode() {
        System.out.println("The value of the node is -> " + val);
    }

    @Override
    public String toString() {
        return "Node [left=" + left + ", right=" + right + ", val=" + val + "]";
    }
}

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
                    if (current == null) {
                        System.out.println("Successfully inserted Node with value => " + newNode.val);
                        parent.left = newNode;
                        return;
                    }
                } else {
                    System.out.println(newNode.val + " > " + parent.val);
                    System.out.println("Moving to the RIGHT node");
                    current = current.right;
                    if (current == null) {
                        System.out.println("Successfully inserted Node with value => " + newNode.val);
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "TreeNode [root=" + root + "]";
    }
}

public class TreeAppDriver {
    public static void main(String args[]) throws IOException {
        System.out.println("Executing the main function");
        TreeNode obj = new TreeNode();

        // Test case 1: Add a single node
        obj.addNode(10);
        System.out.println("------------------\n\n");

        // Test case 2: Add multiple nodes in ascending order
        obj.addNode(10);
        obj.addNode(20);
        obj.addNode(30);
        System.out.println("------------------\n\n");

        // Test case 3: Add multiple nodes in descending order
        obj.addNode(30);
        obj.addNode(20);
        obj.addNode(10);
        System.out.println("------------------\n\n");

        // Test case 4: Add nodes with duplicate values
        obj.addNode(10);
        obj.addNode(20);
        obj.addNode(10);
        System.out.println("------------------\n\n");

        // Test case 5: Add nodes with both left and right children
        obj.addNode(10);
        obj.addNode(5);
        obj.addNode(15);
        System.out.println("------------------\n\n");

        // Test case 6: Add nodes with complex branching
        obj.addNode(10);
        obj.addNode(5);
        obj.addNode(15);
        obj.addNode(12);
        obj.addNode(17);
        obj.addNode(3);
        obj.addNode(7);
        System.out.println("------------------\n\n");

        System.out.println(obj);
    }
}
