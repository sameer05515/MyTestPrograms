package com.learn.practice;

import java.util.StringJoiner;

public class MergeAndSortTwoArray {

	public static void main(String[] args) {
		int arr1[] = { 5, 8, 9 };
		int arr2[] = { 4, 7, 8 };
		for(int i:arr1) {
			System.out.print(i);
		}
		System.out.print("\t\t");
		for(int i:arr2) {
			System.out.print(i);
		}
		System.out.println();
//		int arr3[]={4,5,7,8,8,9};

		int arr3[]=mergeAndSort(arr1, arr2);
		StringJoiner sj=new StringJoiner(",", "[", "]");
		
		//Arrays.asList(arr3).forEach(sj.add(i));
		for(int i:arr3) {
			sj.add(i+"");
		}
		System.out.println("\n"+sj.toString());
		

	}
	
	private static  void print(int[] arr3,int j) {
		StringJoiner sj=new StringJoiner(",", "[", "]");
		for(int i:arr3) {
			sj.add(i+"");
		}
		System.out.println(j+"   -     "+sj.toString());

	}

	public static int[] mergeAndSort(int[] arr1, int[] arr2) {

		int[] arr3 = new int[arr1.length + arr2.length];

		for (int i = 0; i < arr1.length; i++) {
			arr3[i] = arr1[i];
			print(arr3, arr1[i]);
		}

		for (int i = 0; i < arr2.length; i++) {

			int greaterVal = arr2[i];
			int temp = 0;

			for (int j = 0; j < arr1.length+i; j++) {
				if (arr3[j] >= greaterVal) {
					temp = arr3[j];
					arr3[j] = greaterVal;
					greaterVal = temp;
				}
			}
			arr3[arr1.length + i] = greaterVal;
			print(arr3, arr2[i]);
		}

		return arr3;

	}

}
