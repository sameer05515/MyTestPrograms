package com.p.currencyformatter;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatTest {

	public static void main(String[] args) throws Exception {

		double num = 1323.526;

		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
		System.out.println("US: " + defaultFormat.format(num));

		Locale swedish = new Locale("sv", "SE");
		NumberFormat swedishFormat = NumberFormat.getCurrencyInstance(swedish);
		System.out.println("Swedish: " + swedishFormat.format(num));

	}

}