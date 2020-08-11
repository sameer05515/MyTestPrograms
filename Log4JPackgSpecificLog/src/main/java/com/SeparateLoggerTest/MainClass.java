package com.SeparateLoggerTest;

import org.apache.log4j.Logger;

import com.newpackage.SpecialPackageClass_1;
import com.newpackage.SpecialPackageClass_2;


public class MainClass {
 
 final static Logger logger = Logger.getLogger(MainClass.class);
 
    public static void main( String[] args ) {
     
     logger.info("Hello Logger ::::::::::::: "+new MainClass().getClass());
     
     SpecialPackageClass_1 spc1=new SpecialPackageClass_1();
     SpecialPackageClass_2 spc2=new SpecialPackageClass_2();
        while(true) {
        	spc1.myMethod();
            spc2.myMethod();
        }
    }
}