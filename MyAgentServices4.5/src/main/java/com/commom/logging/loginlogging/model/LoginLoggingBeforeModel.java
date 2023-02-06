/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.loginlogging.model;

/**
 *
 * @author shashikantsharma
 */
public class LoginLoggingBeforeModel {
    
    private String loginIPAddressLocal; // 
    private String loginIPAddressGlobal; //
    private String loginUserId;  
    private String loginCurrentTime; // 
    private String loginTime; // 
    private String logoutTime; // 

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }
    private String loginActionStatus; // 0 - Failure, 1 - Success
    private String loginSessionId;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    
    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }
    
    
    
    public String getLoginIPAddressLocal() {
        return loginIPAddressLocal;
    }

    public void setLoginIPAddressLocal(String loginIPAddressLocal) {
        this.loginIPAddressLocal = loginIPAddressLocal;
    }

    public String getLoginIPAddressGlobal() {
        return loginIPAddressGlobal;
    }

    public void setLoginIPAddressGlobal(String loginIPAddressGlobal) {
        this.loginIPAddressGlobal = loginIPAddressGlobal;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginCurrentTime() {
        return loginCurrentTime;
    }

    public void setLoginCurrentTime(String loginCurrentTime) {
        this.loginCurrentTime = loginCurrentTime;
    }

    public String getLoginActionStatus() {
        return loginActionStatus;
    }

    public void setLoginActionStatus(String loginActionStatus) {
        this.loginActionStatus = loginActionStatus;
    }
    
    
    
    
    
    
}
