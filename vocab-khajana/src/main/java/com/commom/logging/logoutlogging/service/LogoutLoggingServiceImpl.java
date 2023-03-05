/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.logoutlogging.service;

import com.basic.common.access.AccessPoint;
import com.commom.logging.logoutlogging.model.LogoutLoggingAfterModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingAfterThrowingModel;
import com.commom.logging.logoutlogging.model.LogoutLoggingBeforeModel;
import com.ils.ibatis.logging.logout.LogoutLoggingIbatis;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Shashi Kant Sharma
 */
public class LogoutLoggingServiceImpl implements LogoutLoggingService {

    /*===== LogoutLoggingBefore START. =====*/
    @Override
    public void insertLogoutLoggingBeforeInformation(LogoutLoggingBeforeModel logoutLoggingBeforeModel) {
        System.out.println("99999999999999999999999999999999999999999999999999999999999999999999999999999999999");
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        LogoutLoggingIbatis logoutLoggingIbatis = sqlSession.getMapper(LogoutLoggingIbatis.class);
        try {
            logoutLoggingIbatis.insertLogoutLoggingBeforeInformation(logoutLoggingBeforeModel);

        } catch (Exception ex) {
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }
    /* ===== LogoutLoggingBefore END. =====*/

    /* ===== LogoutLoggingAfter START. =====*/
    @Override
    public void insertLogoutLoggingAfterInformation(LogoutLoggingAfterModel logoutLoggingAfterModel) {

    }
    /* ===== LogoutLoggingAfter END. =====*/

    /*===== LogoutLoggingAfterThrowing START. =====*/
    @Override
    public void insertLogoutLoggingAfterThrowingInformation(LogoutLoggingAfterThrowingModel logoutLoggingAfterThrowingModel) {

    }
    /*===== LogoutLoggingAfterThrowing END. =====*/

}
