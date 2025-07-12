package com.p.examples.uncategorized;

import java.util.Scanner;

public class Solution {

	public static int[] arrayLeftRotation(int[] a, int n, int k) {

		int[] temp = new int[n];
		int[] temp1 = new int[n];
		for (int i = 0; i < n; i++) {

			temp[i] = a[i];
			temp1[i] = a[i];
		}

		// for(int i = 0; i < n; i++)
		// System.out.print(((i+1)%n) + " ");
		// System.out.println("##########################");
		// for(int i = 0; i < n; i++)
		// System.out.print(a[i] + " ");
		// System.out.print(" --> ");
		for (int j = 1; j <= k; j++) {
			for (int i = 0; i < n; i++) {
				temp1[i] = temp[(i + 1) % n];
			}
			temp = temp1;
			for (int i = 0; i < n; i++) {
				temp[i] = temp1[i];
				// System.out.print(temp[i] + " ");
			}

			// System.out.print(" --> ");
		}

		return temp;

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int a[] = new int[n];
		for (int a_i = 0; a_i < n; a_i++) {
			a[a_i] = in.nextInt();
		}

		int[] output = new int[n];
		output = arrayLeftRotation(a, n, k);
		for (int i = 0; i < n; i++)
			System.out.print(output[i] + " ");

		System.out.println();

	}
}