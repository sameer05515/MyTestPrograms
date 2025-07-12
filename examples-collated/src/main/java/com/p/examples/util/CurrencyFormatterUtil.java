package com.p.examples.util;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class CurrencyFormatterUtil {

    public String formatCurrency(double num) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return defaultFormat.format(num);
    }

    public String formatCurrency(double num, Locale locale) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(num);
    }
}
