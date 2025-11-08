package com.tutorialspoint;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

public class RequestURITraceFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        String testParam = config.getInitParameter("test-param");
        System.out.println("Test Param: " + testParam);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();
        System.out.println("RequestURITraceFilter : IP : " + ipAddress + " , request URI : "
                + ((HttpServletRequest) request).getRequestURI() + " , Time : " + new Date());

        chain.doFilter(request, response);

        System.out.println("RequestURITraceFilter work completed : " + ipAddress + " , request URI : "
                + ((HttpServletRequest) request).getRequestURI() + " , Time : " + new Date());
    }

    @Override
    public void destroy() {
        // Called before the Filter instance is removed from service by the web container
    }
}
