package com.spp.demo;

import java.util.*;
import java.util.stream.*;

public class SecondLargestJava8 {
  public static void main(String[] args) {
    int[] arr = {12, 35, 1, 10, 34, 1};

    Optional<Integer> secondLargest =
        Arrays.stream(arr).boxed().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst();

    secondLargest.ifPresentOrElse(
        val -> System.out.println("Second largest element is: " + val),
        () -> System.out.println("No second largest element found."));
  }
}
