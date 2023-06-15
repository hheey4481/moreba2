package com.example.moreba2.mvc.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
@Log4j2

public class UTF8Filter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("utf8 filter...");
        HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }
}
