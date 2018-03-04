package org.goldenworkshop.trenden.servlet;

import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.model.User;

import javax.servlet.http.HttpServletRequest;

public class ServletAuthContext implements AuthContext {
    public static String USER_SESSION_KEY = AuthContextFilter.class.getName() + ".userSessionKey";
    private HttpServletRequest req;

    public ServletAuthContext(HttpServletRequest request) {
        this.req = request;
    }

    @Override
    public User getUser() {
        return (User) req.getSession(true).getAttribute(USER_SESSION_KEY);
    }

    @Override
    public void setUser(User user) {
        if(user == null){
            req.getSession(true).removeAttribute(USER_SESSION_KEY);
        }
        else{
            req.getSession(true).setAttribute(USER_SESSION_KEY, user);
        }
    }

    @Override
    public boolean isAuthenticated() {
        return getUser() != null;
    }


}
