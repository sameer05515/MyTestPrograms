package com.p.matrixrotationalgorithm;

public class Solution {

	public static void main(String[] args) {
		int[] mnr={4,4,1};
		int[][] mn={{11,12,13,14},{21,22,23,24},{31,32,33,34},{41,42,43,44}};
		
		
		int m=mnr[0];
		int n=mnr[1];
		int r=mnr[2];
		
		int[][] matrixAfterRotation=matrixAfterRotation(m,n,r,mn);



	}

	private static int[][] matrixAfterRotation(int m, int n, int r, int[][] mn) {
		
		int[][] tempMN=new int[mn.length][];
		int i=0;
		for(int[] tmp:tempMN){
			tmp=new int[mn[i++].length];
		}
		
		return null;
	}

}
