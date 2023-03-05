/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.applicationlogging.model;

/**
 *
 * @author shashikantsharma
 */
public class ApplicationLoggingModel {

   
    private String globalIpAddress;
    private String localIpAddress;
    private String operationalTableName;
    private String operationalPKValue;
    private String operationalPKName;
    private String operationName;
    private Long userId;
    private Long module;
    private Long functionality;
    private String beforeActionValues;
    private String operationDateTime;
    private String schemaName;
    private String userRole;
    private Integer operation_based_on_multiple_param_status;

    public Integer getOperation_based_on_multiple_param_status() {
        return operation_based_on_multiple_param_status;
    }

    public void setOperation_based_on_multiple_param_status(Integer operation_based_on_multiple_param_status) {
        this.operation_based_on_multiple_param_status = operation_based_on_multiple_param_status;
    }
    
    
    
    
    
    
    

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    
    

    public String getGlobalIpAddress() {
        return globalIpAddress;
    }

    public void setGlobalIpAddress(String globalIpAddress) {
        this.globalIpAddress = globalIpAddress;
    }

    public String getLocalIpAddress() {
        return localIpAddress;
    }

    public void setLocalIpAddress(String localIpAddress) {
        this.localIpAddress = localIpAddress;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getFunctionality() {
        return functionality;
    }

    public void setFunctionality(Long functionality) {
        this.functionality = functionality;
    }

    public String getBeforeActionValues() {
        return beforeActionValues;
    }

    public void setBeforeActionValues(String beforeActionValues) {
        this.beforeActionValues = beforeActionValues;
    }

    public String getOperationDateTime() {
        return operationDateTime;
    }

    public void setOperationDateTime(String operationDateTime) {
        this.operationDateTime = operationDateTime;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    
    
    
    

    

}
