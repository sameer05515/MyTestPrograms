package com.newpackage;

import org.apache.log4j.Logger;

public class SpecialPackageClass_1 {
 
 final static Logger logger = Logger.getLogger(SpecialPackageClass_1.class);
 
    public void myMethod() {
     
     logger.info("Hello Logger ::::::::::::: "+new SpecialPackageClass_1().getClass());
     
    }
}