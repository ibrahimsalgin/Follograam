package com.developers.yeditepe.follograam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.developers.yeditepe.follograam.application.InstagramSession;
import com.developers.yeditepe.follograam.asynctasks.InstagramUserListServiceTask;
import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.fragments.AuthorizationFragment;
import com.developers.yeditepe.follograam.fragments.DifferenceFragment;
import com.developers.yeditepe.follograam.fragments.FollograamBaseFragment;
import com.developers.yeditepe.follograam.fragments.FollowedByFragment;
import com.developers.yeditepe.follograam.fragments.FollowsFragment;
import com.developers.yeditepe.follograam.interfaces.InstagramUserListListener;
import com.developers.yeditepe.follograam.utils.BottomNavigationViewHelper;
import com.developers.yeditepe.follograam.utils.Constans;
import com.developers.yeditepe.follograam.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FollograamActivity extends AppCompatActivity {

    private int previouslySelectedItemId;

    private InstagramSession session;
    private BottomNavigationView navigationView;
    private List<InstagramUser> followsUserList;
    private List<InstagramUser> followedByUserList;
    private List<InstagramUser> differenceUserList;


    private boolean shouldRefreshUserList = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == previouslySelectedItemId) {
                return false;
            }

            previouslySelectedItemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navigateFragment(new AuthorizationFragment(), "AuthorizationFragment");
                    return true;
                case R.id.navigation_follows:
                    navigateFragment(new FollowsFragment(), "FollowsFragment");
                    return true;
                case R.id.navigation_followers:
                    navigateFragment(new FollowedByFragment(), "FollowedByFragment");
                    return true;
                case R.id.navigation_difference:
                    navigateFragment(new DifferenceFragment(), "DifferenceFragment");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follograam);
        differenceUserList = new ArrayList<>();
        session = (InstagramSession) getApplicationContext();
        navigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //navigateFragment(new AuthorizationFragment(), "AuthorizationFragment");

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onStop() {
        super.onStop();
        session.storeSavedData();
    }

    public void navigateFragment(FollograamBaseFragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).commit();
    }

    public InstagramSession getSession() {
        return session;
    }

    public List<InstagramUser> getFollowsUserList() {
        return followsUserList;
    }

    public List<InstagramUser> getFollowedByUserList() {
        return followedByUserList;
    }

    public List<InstagramUser> getDifferenceUserList() {
        return differenceUserList;
    }

    public void setFollowsUserList(List<InstagramUser> followsUserList) {
        this.followsUserList = followsUserList;
    }

    public void setFollowedByUserList(List<InstagramUser> followedByUserList) {
        this.followedByUserList = followedByUserList;
    }

    public void setDifferenceUserList(List<InstagramUser> differenceUserList) {
        this.differenceUserList = differenceUserList;
    }

    public boolean isShouldRefreshUserList() {
        return shouldRefreshUserList;
    }

    public void setShouldRefreshUserList(boolean shouldRefreshUserList) {
        this.shouldRefreshUserList = shouldRefreshUserList;
    }
}
