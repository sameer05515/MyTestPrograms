package com.learnJava.collections;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HashTag {

    public static void main(String[] args) {
        String input="Hello World Abc";
        System.out.println(hashtag(input));
        System.out.println(Arrays.toString(iii(new int[]{2, 3, 7, 5, 4, 8}, 5)));

    }

    public static String hashtag(String input){

        return Arrays.stream(input.trim().split(" "))
                .map(s-> {
                    String[] part=s.split("");
                    s=part[0].toUpperCase()+s.substring(1);
                    return s;
                }).collect(Collectors.joining("","#",""));
    }

    public static int[] iii(int[] input, int threshold){
        int[] result= new int[input.length];

        int counter=0;
        for(int i=0;i<input.length;i++){
            if(input[i]<threshold){
                result[counter++]=input[i];
            }
        }

        for(int i=0;i<input.length;i++){
            if(input[i]>=threshold){
                result[counter++]=input[i];
            }
        }

        return result;
    }
}
