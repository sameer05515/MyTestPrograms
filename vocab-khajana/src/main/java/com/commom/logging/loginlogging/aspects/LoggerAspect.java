package com.commom.logging.loginlogging.aspects;//package com.commom.logging.loginlogging.aspects;
//
//import com.basic.common.access.AccessPoint;
//import com.ils.ibatis.common.LoggerAspectIbatis;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.ibatis.session.SqlSession;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Controller
//@Aspect
//public class LoggerAspect {
//
//    private Log log = LogFactory.getLog(this.getClass());
//
//    @Around("execution(* com.*.*.*.*(..))")
//    @Order(1)
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("The method " + joinPoint.getSignature().getName() + "() begins with " + Arrays.toString(joinPoint.getArgs()));
//        try {
//            Object result = joinPoint.proceed();
//            log.info("The method " + joinPoint.getSignature().getName() + "() ends");
//            return result;
//        } catch (IllegalArgumentException e) {
//            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
//            throw e;
//        }
//    }
//
//    @After("execution(* com.*.*.*.*(..))")
//    @Order(2)
//    public void logBefore(JoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        Map<String, Object> sKey = new HashMap<String, Object>();
//        HttpSession session1 = request.getSession(false);
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        sKey.put("headerNames", request.getParameterNames());
//        Enumeration<String> headerNames = request.getParameterNames();
//        try {
//            while (headerNames.hasMoreElements()) {
//                String headerName = headerNames.nextElement();
//                if (request.getParameter(headerName).toLowerCase().contains("update") == true || request.getParameter(headerName).toLowerCase().contains("delete") == true) {
//                    LoggerAspectIbatis mapper = session.getMapper(LoggerAspectIbatis.class);
//                    sKey.put("userid", (Long) session1.getAttribute("userid"));
//                    sKey.put("headerName", headerName);
//                    sKey.put("value", request.getParameter(headerName));
//                    sKey.put("hitedurl", request.getRequestURI());
//                   try{
//                    mapper.saveSuspectDetails(sKey);
//                   }
//                   catch(Exception e){
//                      
//                   }
//                }
//            }
//            session.commit();
//            session.close();
//        } catch (IllegalArgumentException e) {
//            session.rollback();
//            log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in " + joinPoint.getSignature().getName() + "()");
//            throw e;
//        }
//    }
//
//}
