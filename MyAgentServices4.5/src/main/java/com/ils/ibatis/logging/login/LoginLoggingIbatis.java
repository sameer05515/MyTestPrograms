/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ils.ibatis.logging.login;

import com.commom.logging.loginlogging.model.LoginLoggingAfterModel;
import com.commom.logging.loginlogging.model.LoginLoggingAfterThrowingModel;
import com.commom.logging.loginlogging.model.LoginLoggingBeforeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 *
 * @author shashikantsharma
 */
public interface LoginLoggingIbatis {
    
    /*===== LoginLoggingBefore START. =====*/
    String insertLoginLoggingBeforeInformationQuery = "INSERT INTO user_login_log (local_ip,global_ip,user_id,login_time, session_id)" +
                                                      "VALUES (#{loginIPAddressLocal},#{loginIPAddressGlobal},#{loginUserId},#{loginCurrentTime}, #{loginSessionId});";
    @Insert(insertLoginLoggingBeforeInformationQuery)
     @Options(useGeneratedKeys = true, keyProperty = "id")
    public void insertLoginLoggingBeforeInformation(LoginLoggingBeforeModel loginLoggingBeforeModel);
    /* ===== LoginLoggingBefore END. =====*/
    
    /* ===== LoginLoggingAfter START. =====*/
    String insertLoginLoggingAfterInformationQuery = "";
    @Insert(insertLoginLoggingAfterInformationQuery)
    public void insertLoginLoggingAfterInformation(LoginLoggingAfterModel loginLoggingAfterModel);
    /* ===== LoginLoggingAfter END. =====*/
    
    
     /*===== LoginLoggingAfterThrowing START. =====*/
    String insertLoginLoggingAfterThrowingInformationQuery = "";
    @Insert(insertLoginLoggingAfterThrowingInformationQuery)
    public void insertLoginLoggingAfterThrowingInformation(LoginLoggingAfterThrowingModel loginLoggingAfterThrowingModel);
    /*===== LoginLoggingAfterThrowing END. =====*/

    
    
    
}

