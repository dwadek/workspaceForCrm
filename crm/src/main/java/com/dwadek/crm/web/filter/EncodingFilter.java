package com.dwadek.crm.web.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //过滤post请求中文参数乱码
        req.setCharacterEncoding("UTF-8");
        //过滤响应
        resp.setContentType("text/html;charset=UTF-8");

        //将请求放行
        chain.doFilter(req,resp);
    }
}
