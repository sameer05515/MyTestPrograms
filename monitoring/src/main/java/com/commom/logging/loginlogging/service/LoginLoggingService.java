/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.loginlogging.service;

import com.commom.logging.loginlogging.model.LoginLoggingAfterModel;
import com.commom.logging.loginlogging.model.LoginLoggingAfterThrowingModel;
import com.commom.logging.loginlogging.model.LoginLoggingBeforeModel;

/**
 *
 * @author Shashi Kant Sharma
 */
public interface LoginLoggingService{
    
    /*===== LoginLoggingBefore START. =====*/
    public Integer insertLoginLoggingBeforeInformation(LoginLoggingBeforeModel loginLoggingBeforeModel);
    /* ===== LoginLoggingBefore END. =====*/
    
    
    /* ===== LoginLoggingAfter START. =====*/
    public void insertLoginLoggingAfterInformation(LoginLoggingAfterModel loginLoggingAfterModel);
    /* ===== LoginLoggingAfter END. =====*/
    
    
    /*===== LoginLoggingAfterThrowing START. =====*/
    public void insertLoginLoggingAfterThrowingInformation(LoginLoggingAfterThrowingModel loginLoggingAfterThrowingModel);
    /*===== LoginLoggingAfterThrowing END. =====*/
    
    
    
    
}

