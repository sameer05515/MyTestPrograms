package com.ivl.help;
import java.util.Arrays;

class SubArray
{
	// Generic function to get sub-array of a non-primitive array
	// between specified indices
	public static<T> T[] subArray(T[] array, int beg, int end) {
		return Arrays.copyOfRange(array, beg, end + 1);
	}

	public static void main(String[] args)
	{
		String[] arr = { "A", "B", "C", "D", "E", "F", "G", "H" };
		int beg = 0, end = 4;

		String[] subarray = subArray(arr, beg, beg);
		System.out.println(Arrays.toString(subarray));
	}
}