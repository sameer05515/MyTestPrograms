package com.p.java8examples.string.join;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStringExample2 {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("a", "b", "c");

        String result = list.stream().collect(Collectors.joining(","));

        System.out.println(result);

    }

}