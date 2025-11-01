package com.p.servlet.ex.filter;

import java.io.IOException;
import java.util.Date;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Get init parameter
        String testParam = config.getInitParameter("test-param");
        // Print the init parameter
        System.out.println("Test Param: " + testParam);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();
        // Log the IP address and current timestamp.
        System.out.println("LogFilter : IP : " + ipAddress + " , request URI : " + ((HttpServletRequest) request).getRequestURI()
                + " , Time : " + new Date().toString());
        // Pass request back down the filter chain
        chain.doFilter(request, response);
        System.out.println("LogFilter work completed : " + ipAddress + " , request URI : "
                + ((HttpServletRequest) request).getRequestURI() + " , Time : " + new Date().toString());
    }

    @Override
    public void destroy() {
        // Called before the Filter instance is removed from service by the web container
    }
}
