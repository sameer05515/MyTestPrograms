/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.loginlogging.service;

import com.basic.common.access.AccessPoint;
import com.commom.logging.loginlogging.model.LoginLoggingAfterModel;
import com.commom.logging.loginlogging.model.LoginLoggingAfterThrowingModel;
import com.commom.logging.loginlogging.model.LoginLoggingBeforeModel;
import com.ils.ibatis.logging.login.LoginLoggingIbatis;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Shashi Kant Sharma
 */
public class LoginLoggingServiceImpl implements LoginLoggingService {

    /*===== LoginLoggingBefore START. =====*/
    @Override
    public Integer insertLoginLoggingBeforeInformation(LoginLoggingBeforeModel loginLoggingBeforeModel) {
       
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        LoginLoggingIbatis loginLoggingIbatis = sqlSession.getMapper(LoginLoggingIbatis.class);
        try {
            loginLoggingIbatis.insertLoginLoggingBeforeInformation(loginLoggingBeforeModel);
            return loginLoggingBeforeModel.getId();
        } catch (Exception ex) {
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return 0;
    }
    /* ===== LoginLoggingBefore END. =====*/

    /* ===== LoginLoggingAfter START. =====*/
    @Override
    public void insertLoginLoggingAfterInformation(LoginLoggingAfterModel loginLoggingAfterModel) {

    }
    /* ===== LoginLoggingAfter END. =====*/

    /*===== LoginLoggingAfterThrowing START. =====*/
    @Override
    public void insertLoginLoggingAfterThrowingInformation(LoginLoggingAfterThrowingModel loginLoggingAfterThrowingModel) {

    }
    /*===== LoginLoggingAfterThrowing END. =====*/

}
