package com.developers.yeditepe.follograam.entities;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class InstagramData {

    private String oauthCode;
    private String accessToken;
    private User userData;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }
}
