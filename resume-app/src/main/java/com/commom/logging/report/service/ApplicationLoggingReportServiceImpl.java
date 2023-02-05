/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commom.logging.report.service;

import com.basic.common.access.AccessPoint;
import com.ils.ibatis.logging.report.ApplicationLoggingReportIbatis;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Shashi Kant Sharma
 */
@Service
public class ApplicationLoggingReportServiceImpl implements ApplicationLoggingReportService {

    @Override
    public List<Map> listApplicationLoggingReportByUsernameService(HttpServletRequest request) {
        List<Map> listApplicationLoggingReportModel = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            String usernames[] = request.getParameterValues("usernames[]");

            StringBuilder sbTemp = new StringBuilder();
            for (int i = 0; i < usernames.length; i++) {
                if (i < usernames.length - 1) {
                    sbTemp.append(usernames[i]).append(",");
                } else {
                    sbTemp.append(usernames[i]);
                }
            }
            map.put("usernames", sbTemp.toString());

            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listApplicationLoggingReportModel = applicationLoggingReportIbatis.listApplicationLoggingReportByUsernameIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listApplicationLoggingReportModel;
        }
    }

    @Override
    public List<Map> listApplicationLoggingReportByDateService(HttpServletRequest request) {
        List<Map> listApplicationLoggingReportModel = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            map.put("startDate", sf.format(df.parse(request.getParameter("start_date"))));
            map.put("endDate", sf.format(df.parse(request.getParameter("end_date"))));

            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listApplicationLoggingReportModel = applicationLoggingReportIbatis.listApplicationLoggingReportByDateIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listApplicationLoggingReportModel;
        }
    }

    @Override
    public List<Map> listApplicationLoggingReportByOperationService(HttpServletRequest request) {
        List<Map> listApplicationLoggingReportModel = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            map.put("operations", request.getParameter("operations"));

            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listApplicationLoggingReportModel = applicationLoggingReportIbatis.listApplicationLoggingReportByOperationIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listApplicationLoggingReportModel;
        }
    }

    @Override
    public List<Map> listApplicationLoggingReportByModuleService(HttpServletRequest request) {
        List<Map> listApplicationLoggingReportModel = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            map.put("module", request.getParameter("module"));
            map.put("functionality", request.getParameter("functionality"));
            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listApplicationLoggingReportModel = applicationLoggingReportIbatis.listApplicationLoggingReportByModuleIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listApplicationLoggingReportModel;
        }
    }

    @Override
    public List<Map> listModulesService(HttpServletRequest request) {
        List<Map> listModule = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listModule = applicationLoggingReportIbatis.listModulesIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listModule;
        }
    }

    @Override
    public List<Map> listFunctionalityService(HttpServletRequest request) {
        List<Map> listFunctionality = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            map.put("module", request.getParameter("module"));
            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listFunctionality = applicationLoggingReportIbatis.listFunctionalityIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listFunctionality;
        }
    }

    @Override
    public List<Map> listApplicationLoggingReportService(HttpServletRequest request) {
        List<Map> listApplicationLoggingReport = null;
        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            StringBuilder sb = new StringBuilder();
            Map map = new HashMap();            
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

            sb.append(" application_log.date_time between '").append(sf.format(df.parse(request.getParameter("start_date")))).append(" 00:00:00' AND '").append(sf.format(df.parse(request.getParameter("end_date")))).append(" 23:59:59'");
            String usernames[] = request.getParameterValues("usernames[]");

            if (usernames != null) {
                StringBuilder sbTemp = new StringBuilder();
                for (int i = 0; i < usernames.length; i++) {
                    if (i < usernames.length - 1) {
                        sbTemp.append(usernames[i]).append(",");
                    } else {
                        sbTemp.append(usernames[i]);
                    }
                }
                sb.append(" and application_log.user_id in(").append(sbTemp.toString()).append(")");
            }

            if (request.getParameter("module") != null && request.getParameter("functionality") != null) {
                if (!(request.getParameter("module").trim().equals("")) && !(request.getParameter("functionality").trim().equals(""))) {
                    sb.append(" and application_log.module=").append(request.getParameter("module")).append(" && application_log.functionality=").append(request.getParameter("functionality"));
                }
            }

            if (request.getParameter("operations") != null) {
                if (!(request.getParameter("operations").trim().equals(""))) {
                    sb.append(" and application_log.action_name in(").append(request.getParameter("operations")).append(")");
                }
            }
            
            map.put("query", sb.toString());

            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            listApplicationLoggingReport = applicationLoggingReportIbatis.listApplicationLoggingReportIbatis(map);
        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return listApplicationLoggingReport;
        }
    }

    @Override
    public List<Map<String, String>> getApplicatinLoggingReportUpdatedValuesService(HttpServletRequest request) {
        List<Map> updatedValues = null;
        List<Map<String, String>> updatedValuesTemp = new ArrayList<Map<String, String>>();

        SqlSession sqlSession = AccessPoint.getDBTemplate().openSession();
        try {
            Map map = new HashMap();
            map.put("pk", request.getParameter("id"));
            ApplicationLoggingReportIbatis applicationLoggingReportIbatis = sqlSession.getMapper(ApplicationLoggingReportIbatis.class);
            Map<String, String> configData = applicationLoggingReportIbatis.getConfigDataForUpdatedValuesIbatis(map);
            updatedValues = applicationLoggingReportIbatis.getUpdatedValuesIbatis(configData);

            if (updatedValues != null) {
                if (updatedValues.size() > 0) {
                    Map map1 = updatedValues.get(0);
                    Map<String, String> map2 = new HashMap<String, String>();

                    Iterator<Entry> iterator = map1.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry entry = (Entry) iterator.next();
                        map2.put(entry.getKey().toString(), new StringBuilder().append("--;;--::--").append(entry.getValue().toString()).toString());
                    }

                    updatedValues.remove(0);
                    updatedValuesTemp.add(map2);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.commit();
            sqlSession.close();
            return updatedValuesTemp;
        }
    }
}
