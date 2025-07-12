package com.prem.java8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Test112 {
    public static void main(String[] args) {
        String name= "premendra";
        Map<Character, Integer> charArr= new HashMap<>();
        Arrays.asList(name.toCharArray()).stream().forEach(c-> {
            if(charArr.containsKey(c)){
                charArr.put(c, charArr.get(c)+1);
            }else{
                charArr.put(c, 0);
            }
        });

    }
}
