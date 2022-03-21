package com.p.pow;

import java.util.Scanner;

public class Power {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(true){
			int a = in.nextInt();
			int b = in.nextInt();

			int pow = 1;
			for (int i = 0; i < b; i++) {
				pow = pow * a;
			}

			System.out.println(a + " to the power " + b + " == " + a + "^" + b + " == " + pow);
		}

	}

}
