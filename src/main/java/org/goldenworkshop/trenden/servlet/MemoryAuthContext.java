package org.goldenworkshop.trenden.servlet;

import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.model.User;

public class MemoryAuthContext implements AuthContext {
    private User user;

    public MemoryAuthContext() {

    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean isAuthenticated() {
        return user != null;
    }
}
