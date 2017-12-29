package com.developers.yeditepe.follograam.asynctasks;

import android.os.AsyncTask;

import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.entities.InstagramUsersResponseObject;
import com.developers.yeditepe.follograam.interfaces.InstagramUserListListener;
import com.developers.yeditepe.follograam.utils.Constans;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public class InstagramUserListServiceTask extends AsyncTask<String, Void, List<InstagramUser>> {
    private InstagramUsersResponseObject responseObject;

    public InstagramUserListServiceTask() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<InstagramUser> doInBackground(String... params) {
        String url = Constans.API_URL +params[0];
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        responseObject = restTemplate.getForObject(url,InstagramUsersResponseObject.class);
        return responseObject.getData();
    }
}
