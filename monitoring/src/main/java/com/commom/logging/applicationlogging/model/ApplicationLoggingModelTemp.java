/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.model;

import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author shashikantsharma
 */
public class ApplicationLoggingModelTemp {

    public final String INSERT_OPERATION = "Insert";
    public final String UPDATE_OPERATION = "Update";
    public final String DELETE_OPERATION = "Delete";

    private SqlSession sqlSession; // Ibatis session
    private String operationalTableName;
    private String operationalPKValue;
    private String operationalPKName;
    private String operationName;
    private String schemaName;
    
    private String remainingQuery; 
    
    private HttpServletRequest httpServletRequest;

    public String getRemainingQuery() {
        return remainingQuery;
    }

    public void setRemainingQuery(String remainingQuery) {
        this.remainingQuery = remainingQuery;
    }

    
    
    
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    
    
    
    

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public String getOperationalTableName() {
        return operationalTableName;
    }

    public void setOperationalTableName(String operationalTableName) {
        this.operationalTableName = operationalTableName;
    }

    public String getOperationalPKValue() {
        return operationalPKValue;
    }

    public void setOperationalPKValue(String operationalPKValue) {
        this.operationalPKValue = operationalPKValue;
    }

    public String getOperationalPKName() {
        return operationalPKName;
    }

    public void setOperationalPKName(String operationalPKName) {
        this.operationalPKName = operationalPKName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

}
