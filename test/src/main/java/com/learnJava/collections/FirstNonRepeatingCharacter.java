package com.learnJava.collections;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FirstNonRepeatingCharacter {
    public static void main(String[] args) {
        int[] arr = {4, 5, 1, 2, 1, 5, 4, 6, 2};
        System.out.println(firstNonRepeating(arr));

        String s = "programming";
        System.out.println(firstNonRepeating(s));
    }

    public static char firstNonRepeating(String s) {
        Map<Character, Long> frequency =
                s.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.groupingBy(
                                c -> c,
                                LinkedHashMap::new,
                                Collectors.counting()
                        ));

        return frequency.entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }

    public static int firstNonRepeating(int[] arr) {
        Map<Integer, Long> frequency =
                Arrays.stream(arr)
                        .boxed()
                        .collect(Collectors.groupingBy(
                                n -> n,
                                LinkedHashMap::new,
                                Collectors.counting()
                        ));

        return frequency.entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }
}
