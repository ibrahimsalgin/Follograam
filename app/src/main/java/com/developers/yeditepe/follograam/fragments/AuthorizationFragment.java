package com.developers.yeditepe.follograam.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.developers.yeditepe.follograam.R;
import com.developers.yeditepe.follograam.asynctasks.InstagramSelfUserServiceTask;
import com.developers.yeditepe.follograam.asynctasks.InstagramUserListServiceTask;
import com.developers.yeditepe.follograam.entities.DialogWebView;
import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.entities.User;
import com.developers.yeditepe.follograam.interfaces.AuthenticateListener;
import com.developers.yeditepe.follograam.utils.Constans;
import com.developers.yeditepe.follograam.utils.Utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AuthorizationFragment extends FollograamBaseFragment implements AuthenticateListener {

    private static final String TAG = "AuthorizationFragment: ";

    private View rootView;
    private ImageView selfProfileImageView;
    private TextView selfFullNameTextView;
    private TextView selfBioTextView;
    private TextView mediaCountTextView;
    private TextView followsCountTextView;
    private TextView followersCountTextView;
    private TextView selfWebSiteTextView;
    private RequestOptions requestOptions;
    private AuthenticateListener authenticateListener;

    public AuthorizationFragment() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticateListener = this;
        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();
        requestOptions.dontAnimate();
        requestOptions.override(300,300);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_authorization, container, false);

            selfProfileImageView = rootView.findViewById(R.id.image_view_self_profile_picture);
            selfFullNameTextView = rootView.findViewById(R.id.text_view_self_full_name);
            selfBioTextView = rootView.findViewById(R.id.text_view_self_bio);
            mediaCountTextView = rootView.findViewById(R.id.text_view_media_count);
            followsCountTextView = rootView.findViewById(R.id.text_view_follows_count);
            followersCountTextView = rootView.findViewById(R.id.text_view_followers_count);
            selfWebSiteTextView = rootView.findViewById(R.id.text_view_self_website);
            actionBar.setTitle(getString(R.string.app_name));
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog.show();
        runnable.run();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (session.getSessionData().getAccessToken() != null && !session.getSessionData().getOauthCode().isEmpty()) {
                if (isShouldRefreshUserList()) {
                    getInstagramUsers();
                    setShouldRefreshUserList(false);
                } else {
                    updateUI();
                }
            } else {
                showAuthenticateDialog();
            }
        }
    };

    private void showAuthenticateDialog(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogWebView dialogWebView = new DialogWebView(getContext(), false, null,authenticateListener);
                dialogWebView.show();
                Window window = dialogWebView.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });
    }

    @Override
    public void onAuthenticate(boolean isAuthenticate, String callbackCode) {
        if (!isAuthenticate) {
            Toast.makeText(getContext(), "Bir hata oluştu!!", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            session.getSessionData().setOauthCode(callbackCode);
            getAccessToken(callbackCode);
        }
    }

    private void updateUI() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getContext())
                        .load(session.getSessionData().getUserData().getProfile_picture())
                        .apply(requestOptions)
                        .into(selfProfileImageView);
                selfFullNameTextView.setText(session.getSessionData().getUserData().getFull_name());
                selfBioTextView.setText(session.getSessionData().getUserData().getBio());
                mediaCountTextView.setText(String.valueOf(session.getSessionData().getUserData().getCounts().getMedia()));
                followsCountTextView.setText(String.valueOf(session.getSessionData().getUserData().getCounts().getFollowed_by()));
                followersCountTextView.setText(String.valueOf(session.getSessionData().getUserData().getCounts().getFollows()));
                selfWebSiteTextView.setText(session.getSessionData().getUserData().getWebsite());
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    private Handler mServiceHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_ACCESS_TOKEN:
                    getInstagramUsers();
                    break;
                default:
                    break;
            }
        }
    };

    private void getAccessToken(final String code) {

        new Thread() {
            @Override
            public void run() {
                String mAccessToken = null;
                try {
                    URL url = new URL(Constans.TOKEN_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url
                            .openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    // urlConnection.connect();
                    OutputStreamWriter writer = new OutputStreamWriter(
                            urlConnection.getOutputStream());
                    writer.write("client_id=" + Constans.CLIENT_ID + "&client_secret="
                            + Constans.CLIENT_SECRET + "&grant_type=authorization_code"
                            + "&redirect_uri=" + Constans.REDIRECT_URL + "&code=" + code);
                    writer.flush();
                    String response = Utils.streamToString(urlConnection
                            .getInputStream());
                    Log.i(TAG, "response " + response);
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response)
                            .nextValue();

                    mAccessToken = jsonObj.getString("access_token");
                    Log.i(TAG, "Got access token: " + mAccessToken);

                    session.getSessionData().setAccessToken(mAccessToken);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Message msgObj = mServiceHandler.obtainMessage(HANDLER_ACCESS_TOKEN);
                mServiceHandler.sendMessage(msgObj);

            }
        }.start();

    }

    public void getInstagramUsers() {

        List<InstagramUser> followList = null;
        List<InstagramUser> followedByList = null;
        List<InstagramUser> differenceList = new ArrayList<>();

        //Get user own profile data with async task service call
        InstagramSelfUserServiceTask selfUserServiceTask = new InstagramSelfUserServiceTask(session.getSessionData().getAccessToken());
        try {
            User user = selfUserServiceTask.execute().get();
            session.getSessionData().setUserData(user);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Get user list which i follow with async task service call
        InstagramUserListServiceTask followListServiceTask = new InstagramUserListServiceTask();
        try {
            followList = followListServiceTask.execute("users/self/follows?access_token=" + session.getSessionData().getAccessToken()).get();
            if (followList == null){
                followList = new ArrayList<>();
            }
            setFollowUserList(followList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Get user list which follow me with async task service call
        InstagramUserListServiceTask userListServiceTask = new InstagramUserListServiceTask();
        try {
            followedByList = userListServiceTask.execute("users/self/followed-by?access_token=" + session.getSessionData().getAccessToken()).get();
            if (followedByList == null){
                followedByList = new ArrayList<>();
            }
            setFollowedByUserList(followedByList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //find users that unfllow me which i already follow
        for (InstagramUser followUser : followList) {
            boolean isFind = false;
            for (InstagramUser followedByUser : followedByList) {
                if (followUser.getId().equals(followedByUser.getId())) {
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                differenceList.add(followUser);
            }
        }
        setDifferenceUserList(differenceList);

        updateUI();
    }
}
