package com.example.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.spp.demo.utils.math.ArithmeticCalculations;
import org.junit.jupiter.api.Test;

public class ArithmeticCalculationsTest {

  @Test
  public void testAdd() {
    assertEquals(5, ArithmeticCalculations.add(2, 3));
  }

  @Test
  public void testSubtract() {
    assertEquals(1, ArithmeticCalculations.subtract(3, 2));
  }

  @Test
  public void testMultiply() {
    assertEquals(6, ArithmeticCalculations.multiply(2, 3));
  }

  @Test
  public void testDivide() {
    assertEquals(2, ArithmeticCalculations.divide(6, 3));
  }

  @Test
  public void testDivideByZero() {
    assertThrows(IllegalArgumentException.class, () -> ArithmeticCalculations.divide(6, 0));
  }

  @Test
  public void testNegativeInputs() {
    assertThrows(IllegalArgumentException.class, () -> ArithmeticCalculations.add(-1, 5));
  }
}
