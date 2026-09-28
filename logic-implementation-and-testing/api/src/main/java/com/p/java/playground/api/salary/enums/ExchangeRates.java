package com.p.java.playground.api.salary.enums;

public enum ExchangeRates {
    INR(1.0d), 
    UsdToInr(74.0d), 
    EuroToInr(90.0d), 
    AudToInr(55.62d);

    private final double rate;

    ExchangeRates(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
