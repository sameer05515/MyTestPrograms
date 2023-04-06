package com.p.examples.uncategorized;

public class SalaryCalculation {

	private enum ExchangeRates {
		INR(1.0d), UsdToInr(74.0d), EuroToInr(90.0d), AudToInr(55.62d);

		private double rate;

		private ExchangeRates(double rate) {
			this.rate = rate;
		}

		public double getRate() {
			return rate;
		}
	}

	public static void main(String[] args) {
		double salary = 3100000;
		double daysInYear = 365d;
		double leavesInYear = 0d;// 52 * 2d;
		double workingHour = 24d;// 12d;
		double minCoEff = 2d;
		double maxCoEff = 6d;

		double oneHourMinFreelancerWage = onehourFreelancerWage(salary, daysInYear, leavesInYear, workingHour,
				minCoEff);
		double oneHourMaxFreelancerWage = onehourFreelancerWage(salary, daysInYear, leavesInYear, workingHour,
				maxCoEff);

		System.out.printf(
				"=======================%n" + "INR %n" + "=======================%n"
						+ "oneHourMinFreelancerWage = %s %noneHourMaxFreelancerWage = %s %n"
						+ "oneDayMinFreelancerWage = %s %noneDayMaxFreelancerWage = %s %n",
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.INR),
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.INR),
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.INR) * workingHour,
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.INR) * workingHour);
		System.out.printf(
				"=======================%n" + "UsdToInr %n" + "=======================%n"
						+ "oneHourMinFreelancerWage = %s %noneHourMaxFreelancerWage = %s %n"
						+ "oneDayMinFreelancerWage = %s %noneDayMaxFreelancerWage = %s %n",
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.UsdToInr),
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.UsdToInr),
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.UsdToInr) * workingHour,
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.UsdToInr) * workingHour);
		System.out.printf(
				"=======================%n" + "EuroToInr %n" + "=======================%n"
						+ "oneHourMinFreelancerWage = %s %noneHourMaxFreelancerWage = %s %n"
						+ "oneDayMinFreelancerWage = %s %noneDayMaxFreelancerWage = %s %n",
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.EuroToInr),
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.EuroToInr),
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.EuroToInr) * workingHour,
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.EuroToInr) * workingHour);
		System.out.printf(
				"=======================%n" + "AudToInr %n" + "=======================%n"
						+ "oneHourMinFreelancerWage = %s %noneHourMaxFreelancerWage = %s %n"
						+ "oneDayMinFreelancerWage = %s %noneDayMaxFreelancerWage = %s %n",
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.AudToInr),
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.AudToInr),
				getExchangeValue(oneHourMinFreelancerWage, ExchangeRates.AudToInr) * workingHour,
				getExchangeValue(oneHourMaxFreelancerWage, ExchangeRates.AudToInr) * workingHour);
	}

	public static double onehourFreelancerWage(double salary, double daysInYear, double leavesInYear,
			double workingHour, double coeff) {
		return (salary * coeff) / ((daysInYear - leavesInYear) * workingHour);
	}

	public static double getExchangeValue(double value, ExchangeRates exchangeRates) {
		return (value / exchangeRates.getRate());
	}

}
