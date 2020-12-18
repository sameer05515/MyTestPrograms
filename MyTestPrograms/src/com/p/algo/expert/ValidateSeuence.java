package com.p.algo.expert;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Validate Subsequence
 * 
 * 
 * Given two non-empty arrays of integers, write a function that determines
 * whether the second array is a subsequence of the first one.
 * 
 * 
 * A subsequence of an array is a set of numbers that aren't necessarily
 * adjacent in the array but that are in the same order as they appear in the
 * array. For instance, the numbers [1, 3, 4] form a subsequence of the array
 * [1, 2, 3, 4] , and so do the numbers . Note that a single number in an array
 * and the array itself are both valid subsequences of the array.
 * 
 */

public class ValidateSeuence {
	public static boolean isValidSubsequence(List<Integer> array, List<Integer> sequence) {
		// Write your code here.

		int arrIndex = 0;
		int seqIndex = 0;
		while (arrIndex < array.size() && seqIndex < sequence.size()) {
			if (array.get(arrIndex).equals(sequence.get(seqIndex))) {
				seqIndex++;
			}
			arrIndex++;
		}
		return seqIndex == sequence.size();
	}

	@Test
	public void TestCase1() {

		assertEquals(true, test("testcase1", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), true));

		assertEquals(true, test("testcase2", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), true));
		assertEquals(true, test("testcase3", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 6, -1, 8, 10), true));
		assertEquals(true,
				test("testcase4", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(22, 25, 6), true));
		assertEquals(true, test("testcase5", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(1, 6, 10), true));
		assertEquals(true,
				test("testcase6", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(5, 1, 22, 10), true));
		assertEquals(true,
				test("testcase7", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(5, -1, 8, 10), true));
		assertEquals(true, test("testcase8", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(25), true));
		assertEquals(true, test("testcase9", Arrays.asList(1, 1, 1, 1, 1), Arrays.asList(1, 1, 1), true));
		assertEquals(true, test("testcase10", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10, 12), false));
		assertEquals(true, test("testcase11", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(4, 5, 1, 22, 25, 6, -1, 8, 10), false));
		assertEquals(true, test("testcase12", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 23, 6, -1, 8, 10), false));
		assertEquals(true, test("testcase13", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 22, 25, 6, -1, 8, 10), false));
		assertEquals(true, test("testcase14", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 22, 6, -1, 8, 10), false));
		assertEquals(true,
				test("testcase15", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(1, 6, -1, -1), false));
		assertEquals(true,
				test("testcase16", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(1, 6, -1, -1, 10), false));
		assertEquals(true,
				test("testcase17", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(1, 6, -1, -2), false));
		assertEquals(true, test("testcase18", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(26), false));
		assertEquals(true, test("testcase19", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 25, 22, 6, -1, 8, 10), false));
		assertEquals(true,
				test("testcase20", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10), Arrays.asList(5, 26, 22, 8), false));
		assertEquals(true, test("testcase21", Arrays.asList(1, 1, 6, 1), Arrays.asList(1, 1, 1, 6), false));
		assertEquals(true, test("testcase22", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(1, 6, -1, 10, 11, 11, 11, 11), false));
		assertEquals(true, test("testcase23", Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10),
				Arrays.asList(5, 1, 22, 25, 6, -1, 8, 10, 10), false));
	}

	private boolean test(String testCaseName, List array, List sequence, boolean expectedResult) {
		boolean testResult = ValidateSeuence.isValidSubsequence(array, sequence);
		boolean pass = testResult == expectedResult;
		System.out.printf("==========%ntestCaseName = %s " + "%n array = %s %nsequence=%s "
				+ "%nexpectedResult=%s testResult=%s " + "%npass = %s %n", testCaseName, array, sequence,
				expectedResult, testResult, pass);
		return pass;
	}

}