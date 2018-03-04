package org.goldenworkshop.trenden.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.controller.AuthContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticatedFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthenticatedFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AuthContext authContext = AuthContext.get();

        if(authContext.isAuthenticated()){
            chain.doFilter(request, response);
        }
        else{
            String redirectUrl = Config.get().getAuthenticationUrl();
            logger.info("Not authenticated - redirecting to " + redirectUrl);
            ((HttpServletResponse)response).sendRedirect(redirectUrl);
        }
    }

    @Override
    public void destroy() {

    }
}
