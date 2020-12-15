package com.p.appconfiguration;

public class Utility {

	private static int maxCMSequenceNo = 0;
	private static int maxCDSequenceNo = 0;
	private static int maxFOSequenceNo = 0;
	

	public static int getMaxCMSequenceNo() {
		return maxCMSequenceNo;
	}

	public static void setMaxCMSequenceNo(int maxCMSequenceNo) {
		Utility.maxCMSequenceNo = maxCMSequenceNo;
	}

	public static int getMaxCDSequenceNo() {
		return maxCDSequenceNo;
	}

	public static void setMaxCDSequenceNo(int maxCDSequenceNo) {
		Utility.maxCDSequenceNo = maxCDSequenceNo;
	}

	public static int getMaxFOSequenceNo() {
		return maxFOSequenceNo;
	}

	public static void setMaxFOSequenceNo(int maxFOSequenceNo) {
		Utility.maxFOSequenceNo = maxFOSequenceNo;
	}

}
