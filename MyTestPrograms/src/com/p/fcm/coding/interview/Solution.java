/* [2,4,3] - First Linkedlist
* [5,6,6] - second linkedlist
 * output - [7,0,0]
 
 5+2 = 7
 node1 = new ListNode(7);
 node2 = new ListNode(0)
 
 node1.next = node2
 
 node3 = new List(3);
 
 node2.next = node3;
 node3.next = null;
 
 

*/
package com.p.fcm.coding.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}

	public String getElements() {
		StringBuffer sb = new StringBuffer();
		ListNode n = this;
		while (n != null) {
			sb.append(", " + n.val);
			n = n.next;
		}
		return sb.substring(1);
	}

}

public class Solution {

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

		ListNode n1 = l1;
		ListNode n2 = l2;

		int carryOver = 0;
		List<ListNode> vals = new ArrayList<ListNode>();
		while (n1 != null && n2 != null) {
			int sum = n1.val + n2.val + carryOver;
			carryOver = sum / 10;
			ListNode f = new ListNode((sum % 10));
			vals.add(f);
			n1 = n1.next;
			n2 = n2.next;
		}

		Collections.reverse(vals);
		ListNode nxt = null;
		for (ListNode n : vals) {
			n.next = nxt;
			nxt = n;
		}

		return vals.get(vals.size() - 1);
	}

	public static void main(String w[]) {

		Solution solution = new Solution();

		// ===========================================
//		ListNode secondNode = new ListNode(3, null); // last
//		ListNode firstNode = new ListNode(4, secondNode); // middle
//		ListNode rootNode = new ListNode(2, firstNode); // first
//
//		ListNode l2secondNode = new ListNode(4, null);
//		ListNode l2firstNode = new ListNode(6, l2secondNode);
//		ListNode l2rootNode = new ListNode(5, l2firstNode);
//
//		ListNode result = solution.addTwoNumbers(rootNode, l2rootNode);
//
//		System.out.println(rootNode.getElements());
//		System.out.println(l2rootNode.getElements());

		// =====================================================

		int[][] values = { { 2, 8, 5, 7, 5, 6, 7, 8, 9 }, { 5, 5, 6, 6, 9, 8, 7, 6, 5 } };

		ListNode next1 = null; // values[0][0]
		ListNode next2 = null; // values[0][1]
		for (int i = values[0].length - 1; i >= 0; i--) {
			ListNode n1 = new ListNode(values[0][i], next1);
			ListNode n2 = new ListNode(values[1][i], next2);
			next1 = n1;
			next2 = n2;
		}
		ListNode result = solution.addTwoNumbers(next1, next2);
		System.out.println(next1.getElements());
		System.out.println(next2.getElements());

		// =========================================================
		System.out.println(result.getElements());

	}
}