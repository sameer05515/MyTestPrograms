/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.report.controller;


import com.commom.logging.report.service.ApplicationLoggingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Shashi Kant Sharma
 */
@Controller
public class ApplicationLoggingReportController {

    @Autowired
    private ApplicationLoggingReportService applicationLoggingReportService;
    
    @RequestMapping(value = "applicationLoggingReport")
    public String viewApplicationLoggingReportController(HttpServletRequest request){
        request.setAttribute("listModule", applicationLoggingReportService.listModulesService(request));
        return "applicationLoggingReport";
    } 
    
    @RequestMapping(value = "listApplicationLoggingReport")
    @ResponseBody
    public List<Map> listViewApplicationLoggingReportController(HttpServletRequest request){
        return applicationLoggingReportService.listApplicationLoggingReportService(request);
    }
    
    @RequestMapping(value = "listApplicationLoggingReportByUsername")
    @ResponseBody
    public List<Map> listApplicationLoggingReportByUsernameController(HttpServletRequest request){
        return applicationLoggingReportService.listApplicationLoggingReportByUsernameService(request);
    }
    
    @RequestMapping(value = "listApplicationLoggingReportByDate")
    @ResponseBody
    public List<Map> listApplicationLoggingReportByDateController(HttpServletRequest request){
        return applicationLoggingReportService.listApplicationLoggingReportByDateService(request);
    }
    
    @RequestMapping(value = "listApplicationLoggingReportByOperation")
    @ResponseBody
    public List<Map> listApplicationLoggingReportByOperationController(HttpServletRequest request){
        return applicationLoggingReportService.listApplicationLoggingReportByOperationService(request);
    }
    
    @RequestMapping(value = "listApplicationLoggingReportByModule")
    @ResponseBody
    public List<Map> listApplicationLoggingReportByModuleController(HttpServletRequest request){
        return applicationLoggingReportService.listApplicationLoggingReportByModuleService(request);
    }
    
    @RequestMapping(value = "listFunctionality")
    @ResponseBody
    public List<Map> listFunctionalityController(HttpServletRequest request){
        return applicationLoggingReportService.listFunctionalityService(request);
    }
    
    @RequestMapping(value = "getApplicatinLoggingReportUpdatedValues")
    @ResponseBody
    public List<Map<String, String>> getApplicatinLoggingReportUpdatedValuesController(HttpServletRequest request){
        return applicationLoggingReportService.getApplicatinLoggingReportUpdatedValuesService(request);
    }
}
