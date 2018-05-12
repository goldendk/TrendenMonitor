package org.goldenworkshop.trenden.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goldenworkshop.trenden.Config;
import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter allowing request to authenticate to the application by supplying a "token" and "userId" request parameter.
 */
public class TokenAuthFilter implements Filter {
    private static final String USER_ID_PARAM_NAME = "userId";
    private static final String TOKEN_PARAM_NAME = "token";
    private static Logger logger = LogManager.getLogger(TokenAuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getParameter(TOKEN_PARAM_NAME);
        String userId = request.getParameter(USER_ID_PARAM_NAME);
        if(token != null && userId != null){
            logger.info("UserId authenticating: " + userId);
            final String checkToken = Config.get().getTokenForUser(userId);
            if(StringUtils.equals(checkToken, token)){
                MemoryAuthContext authContext = new MemoryAuthContext();
                authContext.setUser(new User(userId));
                AuthContext.set(authContext);
            }
            else{
                logger.warn("Token was not correct, not continuing!");
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
