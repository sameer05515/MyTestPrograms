package com.prem.java8;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test112 {
    public static void main(String[] args) {
        String name = "premendra";
        Map<Character, Long> charCountMap = name.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
        // Optional: Print result
        System.out.println(charCountMap);
    }
}
