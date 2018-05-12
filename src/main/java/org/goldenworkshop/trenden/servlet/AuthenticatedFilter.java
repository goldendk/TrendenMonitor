package org.goldenworkshop.trenden.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.model.URIMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticatedFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthenticatedFilter.class);
    private URIMatcher excludeMechanism;
    public static final String INIT_PARAM_NAME_EXCLUDES = "excludes";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.excludeMechanism = new URIMatcher();
        this.excludeMechanism.add(filterConfig.getInitParameter(INIT_PARAM_NAME_EXCLUDES));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AuthContext authContext = AuthContext.get();

        String requestUri = ((HttpServletRequest)request).getRequestURI();
        if(excludeMechanism.matches(requestUri)){
            logger.info("Excluded: " + requestUri);
            chain.doFilter(request, response);
        }
        else if(authContext.isAuthenticated()){
            chain.doFilter(request, response);
        }
        else{
            String redirectUrl = Config.get().getAuthenticationUrl();
            logger.info("Request URI:" + requestUri);
            logger.info("Not authenticated - redirecting to " + redirectUrl);
            ((HttpServletResponse)response).sendRedirect(redirectUrl);
        }
    }

    @Override
    public void destroy() {

    }
}
