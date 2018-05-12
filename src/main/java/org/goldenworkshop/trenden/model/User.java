package org.goldenworkshop.trenden.model;

public class User {
    private String username;
    private boolean email;
    private String locale;
    private String pictureUrl;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isEmail() {
        return email;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
