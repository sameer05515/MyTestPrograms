package com.p.examples.util;

import org.springframework.stereotype.Component;

@Component
public class Util {
    public boolean check(String given) {
        boolean result=false;
        if(given!=null&&!given.trim().equals("")) {
            String subString=given.substring(0, given.length()/2);
            char[] carr=given.toCharArray();
            for(int i=0;i<given.length()/2;i++) {
                if(carr[i]==carr[given.length()-1-i]) {
                    result=true;
                }else {
                    result=false;
                    break;
                }
            }
        }
        return result;
    }
}
