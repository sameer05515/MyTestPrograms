package com.p.algo.expert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeNodeTest {

    @Test
    public void testAddSingleNode() {
        TreeNode tree = new TreeNode();
        tree.addNode(10);
        String expectedOutput = "TreeNode [root=Node [left=null, right=null, val=10]]";
        Assertions.assertEquals(expectedOutput, tree.toString());
    }

    @Test
    public void testAddMultipleNodesAscending() {
        TreeNode tree = new TreeNode();
        tree.addNode(10);
        tree.addNode(20);
        tree.addNode(30);
        String expectedOutput = "TreeNode [root=Node [left=null, right=Node [left=null, right=Node [left=null, right=null, val=30], val=20], val=10]]";
        Assertions.assertEquals(expectedOutput, tree.toString());
    }

    @Test
    public void testAddMultipleNodesDescending() {
        TreeNode tree = new TreeNode();
        tree.addNode(30);
        tree.addNode(20);
        tree.addNode(10);
        String expectedOutput = "TreeNode [root=Node [left=Node [left=Node [left=null, right=null, val=10], right=null, val=20], right=null, val=30]]";
        System.out.println("Expected: "+expectedOutput);
        System.out.println("Actual: "+ tree.toString());
        Assertions.assertEquals(expectedOutput, tree.toString());
    }

    @Test
    public void testAddNodesWithDuplicates() {
        TreeNode tree = new TreeNode();
        tree.addNode(10);
        tree.addNode(20);
        tree.addNode(10);
        String expectedOutput = "TreeNode [root=Node [left=null, right=Node [left=Node [left=null, right=null, val=10], right=null, val=20], val=10]]";
        Assertions.assertEquals(expectedOutput, tree.toString());
    }

    @Test
    public void testAddNodesWithBothChildren() {
        TreeNode tree = new TreeNode();
        tree.addNode(10);
        tree.addNode(5);
        tree.addNode(15);
        String expectedOutput = "TreeNode [root=Node [left=Node [left=null, right=null, val=5], right=Node [left=null, right=null, val=15], val=10]]";
        Assertions.assertEquals(expectedOutput, tree.toString());
    }

    @Test
    public void testAddNodesWithComplexBranching() {
        TreeNode tree = new TreeNode();
        tree.addNode(10);
        tree.addNode(5);
        tree.addNode(15);
        tree.addNode(12);
        tree.addNode(17);
        tree.addNode(3);
        tree.addNode(7);
        String expectedOutput = "TreeNode [root=Node [left=Node [left=Node [left=null, right=null, val=3], right=Node [left=null, right=null, val=7], val=5], right=Node [left=Node [left=null, right=null, val=12], right=Node [left=null, right=null, val=17], val=15], val=10]]";
        Assertions.assertEquals(expectedOutput, tree.toString());
    }
}
