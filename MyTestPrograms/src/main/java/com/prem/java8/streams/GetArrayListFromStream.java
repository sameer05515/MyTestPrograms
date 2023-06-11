package com.prem.java8.streams;// Java program to convert Stream to ArrayList
// using Collectors.toList() method

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetArrayListFromStream {

    // Function to get ArrayList from Stream
    public static <T> ArrayList<T>
    getArrayListFromStream(Stream<T> stream) {

        // Convert the Stream to List
        List<T>
                list = stream.collect(Collectors.toList());

        // Create an ArrayList of the List
        ArrayList<T>
                arrayList = new ArrayList<T>(list);

        // Return the ArrayList
        return arrayList;
    }

    // Driver code
    public static void main(String args[]) {

        Stream<Integer>
                stream = Stream.of(1, 2, 3, 4, 5);

        // Convert Stream to ArrayList in Java
        ArrayList<Integer>
                arrayList = getArrayListFromStream(stream);

        // Print the arraylist
        System.out.println("ArrayList: " + arrayList);
    }
}
