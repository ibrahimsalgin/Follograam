package com.developers.yeditepe.follograam.entities;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class AccessToken {
    private String access_token;
    private User user;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
