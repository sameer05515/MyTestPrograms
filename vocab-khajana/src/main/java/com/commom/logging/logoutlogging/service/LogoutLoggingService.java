/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.logoutlogging.service;

import com.commom.logging.logoutlogging.model.LogoutLoggingAfterModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingAfterThrowingModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingBeforeModel;

/**
 *
 * @author Shashi Kant Sharma
 */
public interface LogoutLoggingService{
    
    /*===== LogoutLoggingBefore START. =====*/
    public void insertLogoutLoggingBeforeInformation(LogoutLoggingBeforeModel logoutLoggingBeforeModel);
    /* ===== LogoutLoggingBefore END. =====*/
    
    
    /* ===== LogoutLoggingAfter START. =====*/
    public void insertLogoutLoggingAfterInformation(LogoutLoggingAfterModel logoutLoggingAfterModel);
    /* ===== LogoutLoggingAfter END. =====*/
    
    
    /*===== LogoutLoggingAfterThrowing START. =====*/
    public void insertLogoutLoggingAfterThrowingInformation(LogoutLoggingAfterThrowingModel logoutLoggingAfterThrowingModel);
    /*===== LogoutLoggingAfterThrowing END. =====*/
    
    
    
    
}

