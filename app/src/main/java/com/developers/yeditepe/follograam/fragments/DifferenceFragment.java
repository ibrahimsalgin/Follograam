package com.developers.yeditepe.follograam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.developers.yeditepe.follograam.R;
import com.developers.yeditepe.follograam.adapters.FollowsUserListAdapter;
import com.developers.yeditepe.follograam.asynctasks.UpdateUserStatusServiceTask;
import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.interfaces.UserStatusUpdateListener;
import com.developers.yeditepe.follograam.utils.Constans;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public class DifferenceFragment extends FollograamBaseFragment implements View.OnClickListener, UserStatusUpdateListener {
    private FollowsUserListAdapter listAdapter;

    private View rootView;
    private ListView followUsersListView;
    private TextView followsCountTextView;
    private Button refreshButton;
    private Button cancelButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_follows, container, false);
            followUsersListView = rootView.findViewById(R.id.listview_follow_users);
            followsCountTextView = rootView.findViewById(R.id.text_view_follows_count);
            refreshButton = rootView.findViewById(R.id.button_refresh);
            cancelButton = rootView.findViewById(R.id.button_cancel);
            actionBar.setTitle("Difference");
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followsCountTextView.setText("There are " + getDifferenceUserList().size() + " users dont follow you that you are following");
        listAdapter = new FollowsUserListAdapter(getContext(), this, getDifferenceUserList());
        followUsersListView.setAdapter(listAdapter);
        refreshButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == cancelButton){
            goToHomeFragment();
            return;
        }
        progressDialog.show();
        Iterator it = toBeRefreshedUserList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() == Constans.UserStatus.UNFOLLOW) {
                incrementTaskCount();
                UpdateUserStatusServiceTask task = new UpdateUserStatusServiceTask(this,"unfollow");
                task.execute("users/" + ((InstagramUser) pair.getKey()).getId() + "/relationship?access_token=" + session.getSessionData().getAccessToken());
            } else if (pair.getValue() == Constans.UserStatus.BLOCK) {
                incrementTaskCount();
                UpdateUserStatusServiceTask task = new UpdateUserStatusServiceTask(this,"block");
                task.execute("users/" + ((InstagramUser) pair.getKey()).getId() + "/relationship?access_token=" + session.getSessionData().getAccessToken());
            }
        }
    }

    @Override
    public void onUserStatusChanged(InstagramUser user, Constans.UserStatus status) {
        toBeRefreshedUserList.put(user, status);
    }

    @Override
    public void onUserStatusChangedByService() {
        decrementTaskCount();
        if (taskCount == 0){
//            progressDialog.dismiss();
            setShouldRefreshUserList(true);
            goToHomeFragment();
        }
    }
}
