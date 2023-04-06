package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class CurrencyFormatterUtilTest {

    private static CurrencyFormatterUtil currencyFormatterUtil;

    @Autowired
    public CurrencyFormatterUtilTest(CurrencyFormatterUtil currencyFormatterUtil) {
        this.currencyFormatterUtil = currencyFormatterUtil;
    }
    @Test
    void testFormatCurrency_DefaultLocale() {
        assertEquals("$1,323.53", currencyFormatterUtil.formatCurrency(1323.526));
    }

    @Test
    void testFormatCurrency_SwedishLocale() {
        Locale swedishLocale = new Locale("sv", "SE");
        assertEquals("kr1 323,53", currencyFormatterUtil.formatCurrency(1323.526, swedishLocale));
    }
}
