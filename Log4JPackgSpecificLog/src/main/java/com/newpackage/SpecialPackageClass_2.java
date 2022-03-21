package com.newpackage;

import org.apache.log4j.Logger;

public class SpecialPackageClass_2 {
 
 final static Logger logger = Logger.getLogger(SpecialPackageClass_2.class);
 
    public void myMethod() {
     
     logger.info("Hello Logger ::::::::::::: "+new SpecialPackageClass_2().getClass());
     
    }
}