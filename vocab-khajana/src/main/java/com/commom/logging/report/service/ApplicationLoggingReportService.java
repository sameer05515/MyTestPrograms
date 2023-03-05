/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.report.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shashi Kant Sharma
 */
public interface ApplicationLoggingReportService {
    
    
    public List<Map> listApplicationLoggingReportByUsernameService(HttpServletRequest request);    
   
    public List<Map> listApplicationLoggingReportByDateService(HttpServletRequest request);
    
    public List<Map> listApplicationLoggingReportByOperationService(HttpServletRequest request);
    
    public List<Map> listApplicationLoggingReportByModuleService(HttpServletRequest request);

    public List<Map> listModulesService(HttpServletRequest request);
     public List<Map> listFunctionalityService(HttpServletRequest request);

    public List<Map> listApplicationLoggingReportService(HttpServletRequest request);
    public List<Map<String, String>> getApplicatinLoggingReportUpdatedValuesService(HttpServletRequest request);
    
}
