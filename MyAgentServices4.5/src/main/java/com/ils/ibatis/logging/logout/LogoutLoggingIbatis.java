/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ils.ibatis.logging.logout;

import com.commom.logging.logoutlogging.model.LogoutLoggingAfterModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingAfterThrowingModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingBeforeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author shashikantsharma
 */
public interface LogoutLoggingIbatis {
    
    /*===== LogoutLoggingBefore START. =====*/
    String insertLogoutLoggingBeforeInformationQuery = "UPDATE user_login_log SET logout_time=#{logoutCurrentTime}" +
                                                      "WHERE pk=#{loginLoggingIdPk} AND user_id=#{logoutUserId} AND session_id=#{logoutSessionId} and logout_time is null ;";
    @Update(insertLogoutLoggingBeforeInformationQuery)
    public void insertLogoutLoggingBeforeInformation(LogoutLoggingBeforeModel logoutLoggingBeforeModel);
    /* ===== LogoutLoggingBefore END. =====*/
    
    /* ===== LogoutLoggingAfter START. =====*/
    String insertLogoutLoggingAfterInformationQuery = "";
    @Insert(insertLogoutLoggingAfterInformationQuery)
    public void insertLogoutLoggingAfterInformation(LogoutLoggingAfterModel logoutLoggingAfterModel);
    /* ===== LogoutLoggingAfter END. =====*/
    
    
     /*===== LogoutLoggingAfterThrowing START. =====*/
    String insertLogoutLoggingAfterThrowingInformationQuery = "";
    @Insert(insertLogoutLoggingAfterThrowingInformationQuery)
    public void insertLogoutLoggingAfterThrowingInformation(LogoutLoggingAfterThrowingModel logoutLoggingAfterThrowingModel);
    /*===== LogoutLoggingAfterThrowing END. =====*/

    
    
    
    
}

