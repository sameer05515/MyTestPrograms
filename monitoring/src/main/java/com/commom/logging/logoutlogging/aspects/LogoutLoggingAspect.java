/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.logoutlogging.aspects;

import com.commom.logging.logoutlogging.model.LogoutLoggingBeforeModel;
import com.commom.logging.logoutlogging.service.LogoutLoggingService;
import com.commom.logging.logoutlogging.service.LogoutLoggingServiceImpl;

/**
 *
 * @author : Shashi Kant Sharma
 * purpose : For store login logging information into database. This aspect of Spring AspectJ.
 */
public class LogoutLoggingAspect {
    
    // Before logout advice
    public void logoutLoggingBefore(LogoutLoggingBeforeModel logoutLoggingBeforeModel){
         LogoutLoggingService logoutLoggingService = new LogoutLoggingServiceImpl();
         logoutLoggingService.insertLogoutLoggingBeforeInformation(logoutLoggingBeforeModel);
    }
    
    //
    public void logoutLoggingAfter(){
        
    }
    
    //
    public void logoutLoggingAfterThrowing(){
        
    }
    
    
    
}
