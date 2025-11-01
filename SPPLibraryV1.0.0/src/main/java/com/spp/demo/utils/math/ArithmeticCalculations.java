package com.spp.demo.utils.math;

public class ArithmeticCalculations {

  public static int add(int a, int b) {
    validateInputs(a, b);
    return a + b;
  }

  public static int subtract(int a, int b) {
    validateInputs(a, b);
    return a - b;
  }

  public static int multiply(int a, int b) {
    validateInputs(a, b);
    return a * b;
  }

  public static int divide(int a, int b) {
    validateInputs(a, b);
    if (b == 0) {
      throw new IllegalArgumentException("Division by zero is not allowed.");
    }
    return a / b;
  }

  private static void validateInputs(int a, int b) {
    if (a < 0 || b < 0) {
      throw new IllegalArgumentException("Inputs must be non-negative integers.");
    }
  }
}
