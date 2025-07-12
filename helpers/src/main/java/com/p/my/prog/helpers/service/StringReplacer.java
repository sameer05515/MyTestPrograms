package com.p.my.prog.helpers.service;

import org.springframework.stereotype.Service;

@Service
public class StringReplacer {
    public String replaceTopic(String input, String replacement) {
        if(input==null || replacement== null){
            return null;
        }
        return input.replaceAll("#topic#", "\""+replacement+"\"");
    }
}