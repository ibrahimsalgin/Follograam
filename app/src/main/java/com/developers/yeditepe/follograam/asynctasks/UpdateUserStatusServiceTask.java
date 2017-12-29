package com.developers.yeditepe.follograam.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.developers.yeditepe.follograam.entities.InstagramUsersResponseObject;
import com.developers.yeditepe.follograam.entities.RelationStatusPostObject;
import com.developers.yeditepe.follograam.entities.RelationStatusResponseObject;
import com.developers.yeditepe.follograam.entities.User;
import com.developers.yeditepe.follograam.interfaces.InstagramUserListListener;
import com.developers.yeditepe.follograam.interfaces.UserStatusUpdateListener;
import com.developers.yeditepe.follograam.utils.Constans;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public class UpdateUserStatusServiceTask extends AsyncTask<String, Void, Void> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String action;
    private UserStatusUpdateListener listener;

    public UpdateUserStatusServiceTask(UserStatusUpdateListener listener, String action) {
        this.listener = listener;
        this.action = action;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = Constans.API_URL + params[0];


        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("action", action)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
//            java.lang.reflect.Type type = new TypeToken<RelationStatusResponseObject>() {}.getType();
//            RelationStatusResponseObject postObject = (new Gson()).fromJson(response.body().string(), type);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void o) {
        super.onPostExecute(o);
        this.listener.onUserStatusChangedByService();
    }
}
