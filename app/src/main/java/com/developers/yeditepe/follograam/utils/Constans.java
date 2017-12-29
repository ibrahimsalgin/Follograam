package com.developers.yeditepe.follograam.utils;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class Constans {

    public static final String CLIENT_ID        =   "057cb123ce5b4572bda6d37b78790d10";
    public static final String CLIENT_SECRET    =   "82e28e37de61405497f728ea8eaa7b5d";
    public static final String API_URL          =   "https://api.instagram.com/v1/";
    public static final String AUTH_URL         =   "https://api.instagram.com/oauth/authorize/";
    public static final String TOKEN_URL        =   "https://api.instagram.com/oauth/access_token/";
    public static final String REDIRECT_URL     =   "http://yeditepedevelopers.blogspot.com.tr";

    public enum ServiceType {
        FOLLOW,
        FOLLOWED_BY;
    }

    public enum UserStatus {
        FOLLOW,
        UNFOLLOW,
        BLOCK;
    }
}
