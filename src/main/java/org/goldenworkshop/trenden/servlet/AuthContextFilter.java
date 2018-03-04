package org.goldenworkshop.trenden.servlet;


import org.goldenworkshop.trenden.controller.AuthContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AuthContext.set(new ServletAuthContext((HttpServletRequest) request));
        try {
            chain.doFilter(request, response);
        } finally {
            AuthContext.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
