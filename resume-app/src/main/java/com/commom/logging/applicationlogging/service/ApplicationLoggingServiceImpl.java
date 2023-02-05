/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.service;

import com.commom.logging.applicationlogging.model.ApplicationLoggingException;
import com.commom.logging.applicationlogging.model.ApplicationLoggingModel;
import com.commom.logging.applicationlogging.model.ApplicationLoggingModelTemp;
import com.ils.ibatis.logging.application.ApplicationLoggingIbatis;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Shashi Kant Sharma
 */
public class ApplicationLoggingServiceImpl implements ApplicationLoggingService {

    @Override
    public void applicationLog(ApplicationLoggingModelTemp applicationLoggingModelTemp, int multiple) throws ApplicationLoggingException {

        boolean applicationLoggingExceptionStatus = false;
        String applicationLoggingExceptionMessage = "";

        if (applicationLoggingModelTemp.getOperationalTableName().equals("") || applicationLoggingModelTemp.getOperationalTableName() == null) {
            // Table Name Null
            applicationLoggingExceptionStatus = true;
            applicationLoggingExceptionMessage = "Table name cannot be empty.";

        } else if (applicationLoggingModelTemp.getOperationName().equals("") || applicationLoggingModelTemp.getOperationName() == null) {
            // Operation name Null
            applicationLoggingExceptionStatus = true;
            applicationLoggingExceptionMessage = "Operation name cannot be empty.";
        } else if (applicationLoggingModelTemp.getHttpServletRequest() == null) {
            // HttpServletRequest Null 
            applicationLoggingExceptionStatus = true;
            applicationLoggingExceptionMessage = "HttpServletRequest cannot be empty.";
        } else if (applicationLoggingModelTemp.getSqlSession() == null) {
            // SqlSession Null 
            applicationLoggingExceptionStatus = true;
            applicationLoggingExceptionMessage = "SqlSession cannot be empty.";
        }

        if (applicationLoggingExceptionStatus == true) {
            throw new ApplicationLoggingException(applicationLoggingExceptionMessage);
        }

        if (multiple == 0) {
            if (applicationLoggingModelTemp.getOperationalPKName().equals("") || applicationLoggingModelTemp.getOperationalPKName() == null) {
                // PK Name Null
                applicationLoggingExceptionStatus = true;
                applicationLoggingExceptionMessage = "Primary key name cannot be empty.";
            } else if (applicationLoggingModelTemp.getOperationalPKValue().equals("") || applicationLoggingModelTemp.getOperationalPKValue() == null) {
                // PK Value Null
                applicationLoggingExceptionStatus = true;
                applicationLoggingExceptionMessage = "Primary key cannot be empty.";
            }
        } else if (multiple == 1) {
            if (applicationLoggingModelTemp.getRemainingQuery().trim().equals("")) {
                // Remaining Query Null
                applicationLoggingExceptionStatus = true;
                applicationLoggingExceptionMessage = "Remaining query cannot be empty.";
            }
        }

        SqlSession sqlSession = applicationLoggingModelTemp.getSqlSession();
        ApplicationLoggingIbatis applicationLoggingIbatis = sqlSession.getMapper(ApplicationLoggingIbatis.class);

        HttpServletRequest httpServletRequest = applicationLoggingModelTemp.getHttpServletRequest();
        HttpSession httpSession = httpServletRequest.getSession(false);

        ApplicationLoggingModel applicationLoggingModel = new ApplicationLoggingModel();

        if (applicationLoggingModelTemp.getOperationName().equalsIgnoreCase("1") || applicationLoggingModelTemp.getOperationName().equalsIgnoreCase("2") || applicationLoggingModelTemp.getOperationName().equalsIgnoreCase("3")) {
            try {
                List<Map> beforeActionValuesMap = null;
                if (multiple == 0) {
                    beforeActionValuesMap = applicationLoggingIbatis.getBeforeActionValues(applicationLoggingModelTemp);
                    applicationLoggingModel.setOperationalPKValue(applicationLoggingModelTemp.getOperationalPKValue());
                } else if (multiple == 1) {
                    applicationLoggingModel.setOperationalPKValue(applicationLoggingModelTemp.getRemainingQuery());
                    beforeActionValuesMap = applicationLoggingIbatis.getBeforeActionValuesWithoutPk(applicationLoggingModelTemp);
                }

                java.util.Date currentDate = new java.util.Date();
                java.sql.Timestamp timestampDate = new java.sql.Timestamp(currentDate.getTime());
                String loginCurrentTime = timestampDate.toString(); //
                //(new java.util.Date()).getTime()).toString())

                String localIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
                if (localIpAddress == null) {
                    localIpAddress = httpServletRequest.getRemoteAddr();
                }

                String globalIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
                if (globalIpAddress == null) {
                    globalIpAddress = httpServletRequest.getRemoteAddr();
                }

                applicationLoggingModel.setOperation_based_on_multiple_param_status(multiple); // 0, 1
                applicationLoggingModel.setGlobalIpAddress(localIpAddress);
                applicationLoggingModel.setLocalIpAddress(globalIpAddress);
                applicationLoggingModel.setOperationDateTime(loginCurrentTime);
                applicationLoggingModel.setOperationName(applicationLoggingModelTemp.getOperationName());
                applicationLoggingModel.setOperationalTableName(applicationLoggingModelTemp.getOperationalTableName());
                applicationLoggingModel.setUserId((Long) httpSession.getAttribute("jbp_uid"));
                applicationLoggingModel.setUserRole((String.valueOf(httpSession.getAttribute("roleID"))));//error occures here
                applicationLoggingModel.setSchemaName(applicationLoggingModelTemp.getSchemaName());
                applicationLoggingModel.setModule((Long) httpSession.getAttribute("moduleId"));
                applicationLoggingModel.setFunctionality((Long) httpSession.getAttribute("functionalityId"));
                for (int i = 0; i < beforeActionValuesMap.size(); i++) {
                    applicationLoggingModel.setBeforeActionValues(getJSONFormatOfBeforeValues(beforeActionValuesMap, i));
                    applicationLoggingIbatis.insertApplicationLoggingData(applicationLoggingModel);
                }
            } catch (Exception ex) {
                applicationLoggingExceptionStatus = true;
                applicationLoggingExceptionMessage = "Table name, PK name or PK value is wrong.";
                ex.printStackTrace();
                throw new ApplicationLoggingException(applicationLoggingExceptionMessage);
            }
        } else {
            applicationLoggingExceptionStatus = true;
            applicationLoggingExceptionMessage = "Operation name is wrong.";
            throw new ApplicationLoggingException(applicationLoggingExceptionMessage);
        }
    }

    private String getJSONFormatOfBeforeValues(List<Map> beforeActionValuesMap, int i) {
        int temp = 0;
        StringBuilder jsonFormatSB = new StringBuilder();
        Map valuesMap = beforeActionValuesMap.get(i);
        Iterator iterator = valuesMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry thisEntry = (Entry) iterator.next();
            if (temp == 0) {
                jsonFormatSB.append("{" + thisEntry.getKey().toString() + " : " + "\"" + thisEntry.getValue().toString() + "\"" + ",");
            } else if (temp == (valuesMap.size() - 1)) {
                jsonFormatSB.append(thisEntry.getKey().toString() + " : " + "\"" + thisEntry.getValue().toString() + "\"" + "}");
            } else {
                jsonFormatSB.append(thisEntry.getKey().toString() + " : " + "\"" + thisEntry.getValue().toString() + "\"" + ",");
            }
            temp++;
        }
        return jsonFormatSB.toString();
    }

}
