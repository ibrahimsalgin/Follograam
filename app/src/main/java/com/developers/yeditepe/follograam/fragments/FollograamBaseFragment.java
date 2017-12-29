package com.developers.yeditepe.follograam.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.developers.yeditepe.follograam.FollograamActivity;
import com.developers.yeditepe.follograam.R;
import com.developers.yeditepe.follograam.application.InstagramSession;
import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.utils.Constans;

import java.util.HashMap;
import java.util.List;

public class FollograamBaseFragment extends Fragment {

    protected static final int HANDLER_ACCESS_TOKEN = 1;
    protected static final int HANDLER_SELF_PROFILE = 2;
    protected static final int HANDLER_PROFILE_IMAGE = 3;

    protected int taskCount = 0;
    protected Object lock = new Object();

    protected InstagramSession session;
    private FollograamActivity myActivity;

    protected ProgressDialog progressDialog;
    protected ActionBar actionBar;

    protected HashMap<InstagramUser, Constans.UserStatus> toBeRefreshedUserList;

    public FollograamBaseFragment() {
        super();
        toBeRefreshedUserList = new HashMap<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = (FollograamActivity) getActivity();
        session = myActivity.getSession();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    protected void goToHomeFragment(){
        myActivity.navigateFragment(new AuthorizationFragment(),"AuthorizationFragment");
    }

    protected void showMessage(String message) {
        Snackbar sb = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).setAction("Action", null);
        sb.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        sb.getView().setMinimumHeight(220);
        sb.show();
    }

    protected List<InstagramUser> getFollowsUserList(){
        return myActivity.getFollowsUserList();
    }

    protected List<InstagramUser> getFollowedByUserList(){
        return myActivity.getFollowedByUserList();
    }

    protected List<InstagramUser> getDifferenceUserList(){
        return myActivity.getDifferenceUserList();
    }

    protected void setFollowUserList(List<InstagramUser> userList){
        myActivity.setFollowsUserList(userList);
    }

    protected void setFollowedByUserList(List<InstagramUser> userList){
        myActivity.setFollowedByUserList(userList);
    }

    protected void setDifferenceUserList(List<InstagramUser> userList){
        myActivity.setDifferenceUserList(userList);
    }

    protected void incrementTaskCount(){
        synchronized (lock){
            taskCount++;
        }
    }

    protected void decrementTaskCount(){
        synchronized (lock){
            taskCount--;
        }
    }

    protected void setShouldRefreshUserList(boolean value){
        myActivity.setShouldRefreshUserList(value);
    }

    protected boolean isShouldRefreshUserList(){
        return myActivity.isShouldRefreshUserList();
    }
}
