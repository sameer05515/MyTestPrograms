//package com.p.aspect;
//
//import java.util.Date;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.context.annotation.Configuration;
//
//import com.p.appconfiguration.Log;
//
//@Aspect
//@Configuration
//public class RestResourceAspect {
//
//	// private Logger logger = LoggerFactory.getLogger(RestResourceAspect.class);
//
//	/**
//	 * What kind of method calls I would intercept
//	 * 
//	 * execution(* PACKAGE.*.*(..))
//	 * 
//	 * Weaving & Weaver
//	 */
//	@Before("execution(* com.p.httpsClient.*.*(..))")
//	public void before(JoinPoint joinPoint) {
//
//		Log.notice.info(" Going to execute resource ");
//		Log.notice.info("Entered into " + joinPoint.getSignature() + " method");
//		Log.notice.info(" Allowed execution for " + joinPoint + "");
//	}
//
//	@AfterReturning(value = "execution(* com.p.httpsClient.*.*(..))", returning = "result")
//	public void afterReturning(JoinPoint joinPoint, Object result) {
//		Log.notice.info(joinPoint + " returned with value " + result + "");
//	}
//
//	@After(value = "execution(* com.p.httpsClient.*.*(..))")
//	public void after(JoinPoint joinPoint) {
//		Log.notice.info("after execution of " + joinPoint);
//	}
//
//	@Around("@annotation(com.p.aspect.TrackTime)")
//	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//		long startTime = System.currentTimeMillis();
//
//		joinPoint.proceed();
//		System.out.println("Tracking time taken : " + new Date());
//
//		long timeTaken = System.currentTimeMillis() - startTime;
//		Log.notice.info("Time Taken by " + joinPoint + " is " + timeTaken + " milliseconds");
//	}
//}