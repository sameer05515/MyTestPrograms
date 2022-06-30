package com.prem.java8.streams;// Java program to remove duplicates from ArrayList

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Program to remove duplicates from a List in Java 8
class RemoveDuplicates {
    public static void main(String[] args) {
        // input list with duplicates
        List<Integer> list = new ArrayList<>(
                Arrays.asList(1, 10, 1, 2, 2, 3, 10, 3, 3, 4, 5, 5));
        // Print the Arraylist
        System.out.println("ArrayList with duplicates: "
                + list);

        // Construct a new list from the set constucted from elements
        // of the original list
        List<Integer> newList = list.stream()
                .distinct()
                .collect(Collectors.toList());

        // Print the ArrayList with duplicates removed
        System.out.println("ArrayList with duplicates removed: "
                + newList);
    }
}
