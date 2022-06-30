/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.service;

import com.commom.logging.applicationlogging.model.ApplicationLoggingException;
import com.commom.logging.applicationlogging.model.ApplicationLoggingModelTemp;


/**
 *
 * @author shashikantsharma
 */
public interface ApplicationLoggingService {
    

    public abstract void applicationLog(ApplicationLoggingModelTemp applicationLoggingModelTemp, int multiple) throws ApplicationLoggingException;

}
