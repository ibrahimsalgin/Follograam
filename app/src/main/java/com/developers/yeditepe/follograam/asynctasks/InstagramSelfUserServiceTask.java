package com.developers.yeditepe.follograam.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.developers.yeditepe.follograam.entities.User;
import com.developers.yeditepe.follograam.entities.UserResponseObject;
import com.developers.yeditepe.follograam.utils.Constans;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public class InstagramSelfUserServiceTask extends AsyncTask<Void, Void, User> {

    private String accessToken;

    public InstagramSelfUserServiceTask(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void... aVoid) {
        UserResponseObject userInfo = null;
        String url = Constans.API_URL + "users/self/?access_token=" + accessToken;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            userInfo = restTemplate.getForObject(url, UserResponseObject.class);

            if (userInfo != null){
                return userInfo.getData();
            }

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }
}
