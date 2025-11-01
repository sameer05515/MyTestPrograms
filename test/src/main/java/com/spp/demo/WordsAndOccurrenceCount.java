package com.spp.demo;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//  Java 8 का सिंपल और effective प्रोग्राम जो Stream से unique words और उनकी occurrence count
// निकालता है
public class WordsAndOccurrenceCount {
  public static void main(String[] args) {
    System.out.println("Second Main");
    Stream.of(
            "Ram", "Shyam", "Mohan", "Sita", "Radha", "Meera", "Ram", "Shyam", "Mohan", "Sita",
            "Radha", "Meera", "Ram", "Ram", "Ram", "Ram")
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .forEach((k, v) -> System.out.println(k + " : " + v));
  }
}
