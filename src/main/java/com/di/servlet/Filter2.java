package com.di.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器：Filter2的init()方法");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("过滤器：Filter2的doFilter()方法-before");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        System.out.println("过滤器：Filter2的doFilter()方法-after");

    }

    @Override
    public void destroy() {
        System.out.println("过滤器：Filter2的destroy()方法");

    }
}
