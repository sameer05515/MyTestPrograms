package com.p.currencyformatter;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AddPaddingInStringTest {

    @Test
    public void testAdd() {
        String result = AddPaddingInString.add("10", "0");
        assertEquals("10", result);
    }

    @Test
    public void testAddPad() {
        String result = AddPaddingInString.addPad(8, '0', "10");
        assertEquals("00000010", result);
    }
}