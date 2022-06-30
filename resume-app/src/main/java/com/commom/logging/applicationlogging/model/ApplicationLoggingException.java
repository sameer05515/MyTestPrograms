/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.model;

/**
 *
 * @author shashikantsharma
 */
public class ApplicationLoggingException extends Exception{
    
    public ApplicationLoggingException(String exceptionMessage){
        super(exceptionMessage);
    }
}
