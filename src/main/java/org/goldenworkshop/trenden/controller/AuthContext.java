package org.goldenworkshop.trenden.controller;

import org.goldenworkshop.trenden.model.User;

public interface AuthContext {
    ThreadLocal<AuthContext> threadLocal = new ThreadLocal<>();

    static void set(AuthContext user) {
        threadLocal.set(user);
    }

    static void clear() {
        threadLocal.remove();
    }

    static AuthContext get() {
        return threadLocal.get();
    }

    /**
     * Return the user object.
     *
     * @return
     */
    User getUser();

    /**
     * Sets the user object on the authentication context.
     *
     * @param user
     */
    void setUser(User user);

    /**
     * Check if we are authenticated.
     *
     * @return
     */
    boolean isAuthenticated();


}
