/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ils.ibatis.logging.report;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Shashi Kant Sharma
 */
public interface ApplicationLoggingReportIbatis {

//    @Select("Select * FROM application_log ${whereCondition}")
//    public List<ApplicationLoggingReportOutputModel> selectApplicationLoggingReportWithoutDateTime(ApplicationLoggingReportModel inputModel);
//
//    @Select("Select * FROM application_log ${whereCondition} OR date_time between #{fromDate} AND #{endDate}")
//    public List<ApplicationLoggingReportOutputModel> selectApplicationLoggingReportWithDateTime1(ApplicationLoggingReportModel inputModel);
//
//    @Select("Select * FROM application_log WHERE date_time between #{fromDate} AND #{endDate}")
//    public List<ApplicationLoggingReportOutputModel> selectApplicationLoggingReportWithDateTime2(ApplicationLoggingReportModel inputModel);
//
//    @Select("select DISTINCT jbp_uid,concat(jbp_users.jbp_givenname,' ',jbp_users.middle_name,' ',jbp_users.jbp_familyname, ' (',AES_DECRYPT(jbp_users.jbp_uname,'usa2010'),' )')  as uname from jbp_users where  AES_DECRYPT(jbp_users.jbp_uname,'usa2010') is not null order by uname")
//    public List<Map> selectAllUsers();
//    select application_log.pk,application_log.local_ip,application_log.global_ip, employee_master.first_name,page_masters.page_name,page_sub_masters.page_sub_name,
//CASE WHEN application_log.action_name=0 then 'Insert Record' when application_log.action_name=1 then 'Update Record' when application_log.action_name=2 then 'Delete Record' END as action_name,
//application_log.before_values
// from application_log 
//INNER JOIN employee_master on application_log.user_id=employee_master.pk 
//INNER JOIN page_masters on page_masters.page_id=application_log.module 
//INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality 
    @Select("select application_log.pk,application_log.local_ip,application_log.global_ip, concat(employee_master.first_name,' ',IFNULL(employee_master.middle_name,' '),' ',employee_master.last_name, '(',employee_master.office_id,')') as name ,page_masters.page_name,page_sub_masters.page_sub_name,\n"
            + " DATE_FORMAT( application_log.date_time,'%e-%b-%Y') as date_time, "
            + " CASE WHEN application_log.action_name=1 then 'Insert Record' when application_log.action_name=2 then 'Update Record' when application_log.action_name=3 then 'Delete Record' END as action_name,\n"
            + " application_log.before_values\n"
            + " from application_log \n"
            + " INNER JOIN employee_master on application_log.user_id=employee_master.pk \n"
            + " INNER JOIN page_masters on page_masters.page_id=application_log.module \n"
            + " INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality where application_log.user_id in(${usernames})")
    public List<Map> listApplicationLoggingReportByUsernameIbatis(Map map);

    @Select("select application_log.pk,application_log.local_ip,application_log.global_ip, concat(employee_master.first_name,' ',IFNULL(employee_master.middle_name,' '),' ',employee_master.last_name, '(',employee_master.office_id,')') as name ,page_masters.page_name,page_sub_masters.page_sub_name,\n"
            + "DATE_FORMAT( application_log.date_time,'%e-%b-%Y') as date_time, "
            + "CASE WHEN application_log.action_name=1 then 'Insert Record' when application_log.action_name=2 then 'Update Record' when application_log.action_name=3 then 'Delete Record' END as action_name,\n"
            + "application_log.before_values\n"
            + " from application_log \n"
            + "INNER JOIN employee_master on application_log.user_id=employee_master.pk \n"
            + "INNER JOIN page_masters on page_masters.page_id=application_log.module \n"
            + "INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality \n"
            + "where application_log.date_time between #{startDate} AND #{endDate}")
    public List<Map> listApplicationLoggingReportByDateIbatis(Map map);

    @Select("select application_log.pk,application_log.local_ip,application_log.global_ip, concat(employee_master.first_name,' ',IFNULL(employee_master.middle_name,' '),' ',employee_master.last_name, '(',employee_master.office_id,')') as name ,page_masters.page_name,page_sub_masters.page_sub_name,\n"
            + " DATE_FORMAT( application_log.date_time,'%e-%b-%Y') as date_time, "
            + " CASE WHEN application_log.action_name=1 then 'Insert Record' when application_log.action_name=2 then 'Update Record' when application_log.action_name=3 then 'Delete Record' END as action_name,\n"
            + " application_log.before_values\n"
            + " from application_log \n"
            + " INNER JOIN employee_master on application_log.user_id=employee_master.pk \n"
            + " INNER JOIN page_masters on page_masters.page_id=application_log.module \n"
            + " INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality \n"
            + " where application_log.action_name in(${operations})")
    public List<Map> listApplicationLoggingReportByOperationIbatis(Map map);

    @Select("select application_log.pk,application_log.local_ip,application_log.global_ip, concat(employee_master.first_name,' ',IFNULL(employee_master.middle_name,' '),' ',employee_master.last_name, '(',employee_master.office_id,')') as name ,page_masters.page_name,page_sub_masters.page_sub_name,\n"
            + " DATE_FORMAT( application_log.date_time,'%e-%b-%Y') as date_time, "
            + " CASE WHEN application_log.action_name=1 then 'Insert Record' when application_log.action_name=2 then 'Update Record' when application_log.action_name=3 then 'Delete Record' END as action_name,\n"
            + " application_log.before_values\n"
            + " from application_log \n"
            + " INNER JOIN employee_master on application_log.user_id=employee_master.pk \n"
            + " INNER JOIN page_masters on page_masters.page_id=application_log.module \n"
            + " INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality \n"
            + " where application_log.module=#{module} && application_log.functionality=#{functionality}")
    public List<Map> listApplicationLoggingReportByModuleIbatis(Map map);

    @Select("Select page_id as id, page_name as value from page_masters")
    public List<Map> listModulesIbatis(Map map);

    @Select("Select page_id, page_name from page_masters")
    public List<Map> listFunctionaliltiesIbatis(Map map);

    @Select("Select  page_sub_id as id, page_sub_name as value  \n"
            + "FROM page_sub_masters\n"
            + "where page_master_pk = #{module};")
    public List<Map> listFunctionalityIbatis(Map map);

    
    @Select("select application_log.pk,application_log.local_ip,application_log.global_ip, concat(employee_master.first_name,' ',IFNULL(employee_master.middle_name,' '),' ',employee_master.last_name, ' ', '(',employee_master.office_id,')') as name ,page_masters.page_name,page_sub_masters.page_sub_name,\n"
            + " DATE_FORMAT( application_log.date_time,'%d-%b-%Y') as date_var, "
            + " DATE_FORMAT( application_log.date_time,'%h:%i %p') as time_var, "
            + " CASE WHEN application_log.action_name=1 then 'Insert Record' when application_log.action_name=2 then 'Update Record' when application_log.action_name=3 then 'Delete Record' END as action_name \n"
            + " from application_log \n"
            + " INNER JOIN employee_master on application_log.user_id=employee_master.pk \n"
            + " INNER JOIN page_masters on page_masters.page_id=application_log.module \n"
            + " INNER JOIN page_sub_masters on page_sub_masters.page_sub_id=application_log.functionality \n"
            + " where ${query}")
    public List<Map> listApplicationLoggingReportIbatis(Map map);

    @Select("select table_name, operational_pk from application_log where pk=#{pk}")
    public Map<String, String> getConfigDataForUpdatedValuesIbatis(Map map);
    
    @Select("select * from ${table_name} where ${table_name}.pk=#{operational_pk}")
    public List<Map> getUpdatedValuesIbatis(Map<String, String> configData);
}
