package com.bullraider.apps.interceptors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


public class ActionMethodFilterInterceptor extends MethodFilterInterceptor{
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext context=(ActionContext)invocation.getInvocationContext();
		HttpServletResponse response=(HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		System.out.println("Page will not cache");
		String result=invocation.invoke();
		return result;
	}
}
