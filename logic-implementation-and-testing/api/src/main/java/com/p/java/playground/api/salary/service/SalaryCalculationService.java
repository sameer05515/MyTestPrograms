package com.p.java.playground.api.salary.service;

import com.p.java.playground.api.salary.enums.ExchangeRates;
import org.springframework.stereotype.Service;

@Service
public class SalaryCalculationService {

    /**
     * Calculates the hourly freelancer wage.
     *
     * @param salary         The annual salary.
     * @param daysInYear     Total days in a year.
     * @param leavesInYear   Number of leaves in a year.
     * @param workingHour    Working hours in a day.
     * @param coeff          Coefficient multiplier.
     * @return The hourly freelancer wage.
     */
    public double calculateHourlyFreelancerWage(double salary, double daysInYear, double leavesInYear, double workingHour, double coeff) {
        return (salary * coeff) / ((daysInYear - leavesInYear) * workingHour);
    }

    /**
     * Converts a given value to the target currency using the provided exchange rate.
     *
     * @param value          The value to be converted.
     * @param exchangeRates  The exchange rate for conversion.
     * @return The converted value.
     */
    public double convertToCurrency(double value, ExchangeRates exchangeRates) {
        return value / exchangeRates.getRate();
    }
}
