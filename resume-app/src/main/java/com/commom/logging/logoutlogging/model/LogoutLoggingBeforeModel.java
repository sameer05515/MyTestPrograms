/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.logoutlogging.model;

/**
 *
 * @author shashikantsharma
 */
public class LogoutLoggingBeforeModel {
    
    
    private String logoutUserId;  
    private String logoutCurrentTime; // 
    private String logoutSessionId;
    private String loginLoggingIdPk;

    public String getLoginLoggingIdPk() {
        return loginLoggingIdPk;
    }

    public void setLoginLoggingIdPk(String loginLoggingIdPk) {
        this.loginLoggingIdPk = loginLoggingIdPk;
    }
    
    

    public String getLogoutUserId() {
        return logoutUserId;
    }

    public void setLogoutUserId(String logoutUserId) {
        this.logoutUserId = logoutUserId;
    }

    public String getLogoutCurrentTime() {
        return logoutCurrentTime;
    }

    public void setLogoutCurrentTime(String logoutCurrentTime) {
        this.logoutCurrentTime = logoutCurrentTime;
    }

    public String getLogoutSessionId() {
        return logoutSessionId;
    }

    public void setLogoutSessionId(String logoutSessionId) {
        this.logoutSessionId = logoutSessionId;
    }

    
    
   
    
    
    
    
}
