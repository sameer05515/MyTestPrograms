/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ils.ibatis.logging.application;

import com.commom.logging.applicationlogging.model.ApplicationLoggingModel;
import com.commom.logging.applicationlogging.model.ApplicationLoggingModelTemp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 *
 * @author shashikantsharma
 */
public interface ApplicationLoggingIbatis {
    
   @Select("select * from ${schemaName}.${operationalTableName} where ${operationalPKName}=#{operationalPKValue}")
   public List<Map> getBeforeActionValues(ApplicationLoggingModelTemp applicationLoggingModelTemp);
    
   @Select("select * from ${schemaName}.${operationalTableName} where ${remainingQuery}")
   public List<Map> getBeforeActionValuesWithoutPk(ApplicationLoggingModelTemp applicationLoggingModelTemp);
    
   
   
    @Insert("insert into application_log "
    + "(local_ip, global_ip, schema_name, user_id, module, functionality, operational_pk, action_name, before_values, table_name, date_time, role_name, operation_based_on_multiple_param_status)"
    + "values(#{localIpAddress},#{globalIpAddress}, #{schemaName}, #{userId}, #{module}, #{functionality}, #{operationalPKValue},#{operationName}, #{beforeActionValues}, #{operationalTableName}, #{operationDateTime}, #{userRole}, #{operation_based_on_multiple_param_status})")
    public void insertApplicationLoggingData(ApplicationLoggingModel applicationLoggingModel);


}

