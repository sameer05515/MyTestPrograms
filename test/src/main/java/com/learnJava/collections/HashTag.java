package com.learnJava.collections;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HashTag {

    public static void main(String[] args) {
        String input="Hello World Abc";
        System.out.println(hashtag(input));

    }

    public static String hashtag(String input){

        return Arrays.stream(input.trim().split(" "))
                .map(s-> {
                    String[] part=s.split("");
                    s=part[0].toUpperCase()+s.substring(1);
                    return s;
                }).collect(Collectors.joining("","#",""));
    }
}
