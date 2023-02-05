/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.loginlogging.aspects;

import com.commom.logging.loginlogging.model.LoginLoggingBeforeModel;
import com.commom.logging.loginlogging.service.LoginLoggingService;
import com.commom.logging.loginlogging.service.LoginLoggingServiceImpl;

/**
 *
 * @author : Shashi Kant Sharma
 * purpose : For store login logging information into database. This aspect of Spring AspectJ.
 */
public class LoginLoggingAspect {
    
    // Before login advice
    public Integer  loginLoggingBefore(LoginLoggingBeforeModel loginLoggingBeforeModel){
         LoginLoggingService loginLoggingService = new LoginLoggingServiceImpl();
         return loginLoggingService.insertLoginLoggingBeforeInformation(loginLoggingBeforeModel);
    }
    
    //
    public void loginLoggingAfter(){
        
    }
    
    //
    public void loginLoggingAfterThrowing(){
        
    }
    
    
    
}
