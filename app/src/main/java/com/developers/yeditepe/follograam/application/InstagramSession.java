package com.developers.yeditepe.follograam.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.developers.yeditepe.follograam.entities.InstagramData;
import com.developers.yeditepe.follograam.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class InstagramSession extends Application {

    private InstagramData sessionData;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static String FOLLOGRAM_DATA = "FollogramData";
    private static String USER_DATA = "UserData";
    private static String ACCESS_TOKEN = "AccessToken";
    private static String OAUTH_CODE = "OAuthCode";

    public InstagramSession() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(FOLLOGRAM_DATA, Context.MODE_PRIVATE);
        sessionData = new InstagramData();
        readSavedData();
    }

    private void readSavedData() {
        Gson gson = new Gson();

        sessionData.setAccessToken(preferences.getString(ACCESS_TOKEN, null));
        sessionData.setOauthCode(preferences.getString(OAUTH_CODE,null));

        String userInfoString = preferences.getString(USER_DATA, null);
        if (userInfoString != null && !userInfoString.isEmpty()) {
            java.lang.reflect.Type type = new TypeToken<User>() {
            }.getType();
            sessionData.setUserData((User) gson.fromJson(userInfoString, type));
        }

    }

    public void storeSavedData() {

        editor = preferences.edit();

        editor.putString(OAUTH_CODE, sessionData.getOauthCode());
        editor.putString(ACCESS_TOKEN, sessionData.getAccessToken());
        editor.putString(USER_DATA, (new Gson()).toJson(sessionData.getUserData()));
        editor.clear();

        editor.commit();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public InstagramData getSessionData() {
        return sessionData;
    }
}
