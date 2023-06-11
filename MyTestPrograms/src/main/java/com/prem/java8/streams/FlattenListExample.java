package com.prem.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlattenListExample {

    public static void main(String[] args) {
        List<List<Integer>> input = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4, 5), Arrays.asList(6, 7, 8, 9));
        List<Integer> output = conventrionalApproach(input);
        System.out.println(output);

        // Flatten the Stream
        List<Integer> flatList = new ArrayList<Integer>();
        flatList = flattenStream(input)
                .collect(Collectors.toList());

        // Print the flattened list
        System.out.println(flatList);


        // Get the lists to be flattened.
        List<Character> a = Arrays.asList('G', 'e', 'e', 'k', 's');
        List<Character> b = Arrays.asList('F', 'o', 'r');
        List<Character> c = Arrays.asList('G', 'e', 'e', 'k', 's');

        List<List<Character> > arr = new ArrayList<List<Character> >();
        arr.add(a);
        arr.add(b);
        arr.add(c);

        // Flatten the Stream
        List<Character> flatListChar = new ArrayList<Character>();
        flatListChar = flattenStream(arr)
                .collect(Collectors.toList());

        // Print the flattened list
        System.out.println(flatListChar);
    }

    private static List<Integer> conventrionalApproach(List<List<Integer>> input) {
        List<Integer> output = new ArrayList<>();
        for (List<Integer> inp : input) {
            output.addAll(inp);
        }
        return output;
    }

    // Function to flatten a Stream of Lists
    public static <T> Stream<T> flattenStream(List<List<T> > lists)
    {

        // Create an empty list to collect the stream
        List<T> finalList = new ArrayList<>();

        // Using forEach loop
        // convert the list into stream
        // and add the stream into list
        for (List<T> list : lists) {
            list.stream()
                    .forEach(finalList::add);
        }

        // Convert the list into Stream and return it
        return finalList.stream();
    }
}
