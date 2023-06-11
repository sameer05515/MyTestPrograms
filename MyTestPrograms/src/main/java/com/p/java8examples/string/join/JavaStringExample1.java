package com.p.java8examples.string.join;
import java.util.Arrays;
import java.util.List;

public class JavaStringExample1 {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("a","b","c");
        String result = String.join(",", list);

        System.out.println(result);

    }

}