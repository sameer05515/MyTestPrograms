/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.aspects;

import com.commom.logging.applicationlogging.model.ApplicationLoggingException;
import com.commom.logging.applicationlogging.model.ApplicationLoggingModelTemp;
import com.commom.logging.applicationlogging.service.ApplicationLoggingService;
import com.commom.logging.applicationlogging.service.ApplicationLoggingServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shashikantsharma
 */
@Service
public class ApplicationLoggingAspect {

    public static void applicationLog(HttpServletRequest httpServletRequest, SqlSession sqlSession, String operationalTableName, String operationalPKName, String operationalPKValue, String operationName) throws ApplicationLoggingException {

        System.out.println("Application Logging With PK and Value");

        HttpSession httpSession = httpServletRequest.getSession(false);
        ApplicationLoggingService applicationLoggingService = new ApplicationLoggingServiceImpl();

        ApplicationLoggingModelTemp applicationLoggingModelTemp = new ApplicationLoggingModelTemp();
        applicationLoggingModelTemp.setOperationName(operationName); // Set Operation Name !!! Use only already defined parameters for operation
        applicationLoggingModelTemp.setHttpServletRequest(httpServletRequest); // Set current HttpServletRequest
        applicationLoggingModelTemp.setOperationalPKName(operationalPKName); // Set the operational PK column name
        applicationLoggingModelTemp.setOperationalPKValue(operationalPKValue); // Set the operational PK value
        applicationLoggingModelTemp.setOperationalTableName(operationalTableName); // Set the operational table name 
        applicationLoggingModelTemp.setSqlSession(sqlSession); // Set Current Ibatis SqlSession object
        applicationLoggingModelTemp.setSchemaName((String) httpSession.getAttribute("skey")); // Set Schema Name from HttpSession

        applicationLoggingService.applicationLog(applicationLoggingModelTemp, 0);

    }

    public static void applicationLog(HttpServletRequest httpServletRequest, SqlSession sqlSession, String operationalTableName, String reaminingQuery, String operationName) throws ApplicationLoggingException {

        System.out.println("Application Logging With Remaining Query");

        HttpSession httpSession = httpServletRequest.getSession(false);

        ApplicationLoggingService applicationLoggingService = new ApplicationLoggingServiceImpl();

        ApplicationLoggingModelTemp applicationLoggingModelTemp = new ApplicationLoggingModelTemp();
        applicationLoggingModelTemp.setOperationName(operationName); // Set Operation Name !!! Use only already defined parameters for operation
        applicationLoggingModelTemp.setHttpServletRequest(httpServletRequest); // Set current HttpServletRequest
        applicationLoggingModelTemp.setRemainingQuery(reaminingQuery); // Set the remaining query
        applicationLoggingModelTemp.setOperationalTableName(operationalTableName); // Set the operational table name 
        applicationLoggingModelTemp.setSqlSession(sqlSession); // Set Current Ibatis SqlSession object
        applicationLoggingModelTemp.setSchemaName((String) httpSession.getAttribute("skey")); // Set Schema Name from HttpSession

        applicationLoggingService.applicationLog(applicationLoggingModelTemp, 1);

    }

    public static void applicationLog(HttpServletRequest httpServletRequest, SqlSession sqlSession, String operationalSchemaName, String operationalTableName, String operationalPKName, String operationalPKValue, String operationName) throws ApplicationLoggingException {

        System.out.println("Application Logging With PK and Value");

        ApplicationLoggingService applicationLoggingService = new ApplicationLoggingServiceImpl();

        ApplicationLoggingModelTemp applicationLoggingModelTemp = new ApplicationLoggingModelTemp();
        applicationLoggingModelTemp.setOperationName(operationName); // Set Operation Name !!! Use only already defined parameters for operation
        applicationLoggingModelTemp.setHttpServletRequest(httpServletRequest); // Set current HttpServletRequest
        applicationLoggingModelTemp.setOperationalPKName(operationalPKName); // Set the operational PK column name
        applicationLoggingModelTemp.setOperationalPKValue(operationalPKValue); // Set the operational PK value
        applicationLoggingModelTemp.setOperationalTableName(operationalTableName); // Set the operational table name 
        applicationLoggingModelTemp.setSqlSession(sqlSession); // Set Current Ibatis SqlSession object
        applicationLoggingModelTemp.setSchemaName(operationalSchemaName); // Set the operational Schema Name

        applicationLoggingService.applicationLog(applicationLoggingModelTemp, 0);

    }
}
