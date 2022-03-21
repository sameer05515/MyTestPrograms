package com.bullraider.apps.interceptors;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ClearCacheInterceptor  extends AbstractInterceptor{

	private boolean cleanCache;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context=(ActionContext)invocation.getInvocationContext();
		System.out.println("cleanCache is: "+cleanCache);
		if(cleanCache){
		HttpServletResponse response=(HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		}
		String result=invocation.invoke();
		return result;
	}
	public boolean isCleanCache() {
		return cleanCache;
	}
	public void setCleanCache(boolean cleanCache) {
		this.cleanCache = cleanCache;
	}
	
}
