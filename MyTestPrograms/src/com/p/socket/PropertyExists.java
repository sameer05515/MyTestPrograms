package com.p.socket;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;


public class PropertyExists {
    public static void main(String[] args) {
        Properties p=new Properties();
        String s=p.getProperty("Prem");
        System.out.println("s : "+StringUtils.isBlank(s));
    }

    @Deprecated
    public static void isPropertyExists(){
        Properties p=new Properties();
        p.setProperty("Prem","aaaa");
        System.out.println(p);
        if(p.stringPropertyNames().contains("Prem")){
            System.out.print("value for key is : "+p.get("Prem"));
        }else{
            System.out.println("nahi mila");
        }
    }
}
