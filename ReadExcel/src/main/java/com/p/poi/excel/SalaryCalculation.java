package com.p.poi.excel;

public class SalaryCalculation {

	private static final String SALARY_PREFIX = " LPA";

	public static void main(String[] args) {
		String[] rangeTitle = { "Minimum", "Average", "Maximum","Extraordinary" };
		double[] range = { 1.25, 1.50, 2, 2.19 };

		double currSalary = 23;
		int iteration = 1;

		// double nextHike = printSalaryHikeCalculations(currSalary, rangeTitle, range,
		// 0);

		for (int returningExpectation = 0; returningExpectation < range.length; returningExpectation++) {
			System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n\t"
					+ "Checking "
					+ rangeTitle[returningExpectation] + " Hikes"+" ("+(range[returningExpectation]-1)*100+" %)"
							+ "\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			
			checkNextHikes(iteration, returningExpectation, currSalary, rangeTitle, range);
		}

	}

	private static double checkNextHikes(int iteration, int returningExpectation, double currSalary,
			String[] rangeTitle, double[] range) {
		System.out.println("=======================================");
		System.out.println("Current Salary : " + currSalary + SALARY_PREFIX);
		for (int i = 0; i < iteration; i++) {
			double nextSalary = currSalary * range[returningExpectation];
			System.out.println(
					"Next salary with " + rangeTitle[returningExpectation] + " hike : " + nextSalary + SALARY_PREFIX);
			currSalary = nextSalary;
		}

		return 0;
	}

	private static double printSalaryHikeCalculations(double currSalary, String[] rangeTitle, double[] range,
			int returningExpectation) {
		System.out.println("=======================================");
		System.out.println("Current Salary : " + currSalary + SALARY_PREFIX);
		for (int i = 0; i < 3; i++) {
			System.out.println("Next salary with " + rangeTitle[i] + " hike : " + (currSalary * range[i]));
		}

		return currSalary * range[returningExpectation];

	}

}
