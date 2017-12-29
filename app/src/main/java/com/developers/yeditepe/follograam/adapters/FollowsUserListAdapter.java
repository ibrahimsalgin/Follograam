package com.developers.yeditepe.follograam.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.developers.yeditepe.follograam.R;
import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.interfaces.UserStatusUpdateListener;
import com.developers.yeditepe.follograam.utils.Constans;
import com.developers.yeditepe.follograam.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

public class FollowsUserListAdapter extends BaseAdapter {
    private List<InstagramUser> instagramUserList;
    private List<Drawable> imageList;
    private UserStatusUpdateListener listener;
    private Context context;
    private RequestOptions requestOptions;

    public FollowsUserListAdapter(Context context, UserStatusUpdateListener listener, List<InstagramUser> instagramUserList){
        this.context = context;
        this.listener = listener;
        this.instagramUserList = instagramUserList;
        imageList = new ArrayList<>();

        requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();
        requestOptions.dontAnimate();
        requestOptions.override(150,150);
    }

    @Override
    public int getCount() {
        return instagramUserList.size();
    }

    @Override
    public Object getItem(int i) {
        return instagramUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final InstagramUser user = instagramUserList.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_follows, null);
            viewHolder = new ViewHolder();

            viewHolder.profileImageView = view.findViewById(R.id.image_view_user_profile_picture);
            viewHolder.userNameTextView = view.findViewById(R.id.text_view_username);
            viewHolder.fullNameTextView = view.findViewById(R.id.text_view_user_fullname);
            viewHolder.blockButton = view.findViewById(R.id.button_block);
            viewHolder.unFollowButton = view.findViewById(R.id.button_unfollow);
            view.setTag(viewHolder);  // set tag on view
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        Glide.with(context)
                .load(user.getProfile_picture())
                .apply(requestOptions)
                .into(viewHolder.profileImageView);
//        Utils.LoadImageFromWebOperations(user.getProfile_picture(),viewHolder.profileImageView);
        viewHolder.userNameTextView.setText(user.getUsername());
        viewHolder.fullNameTextView.setText(user.getFull_name());
        viewHolder.blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserStatusChanged(user, Constans.UserStatus.BLOCK);
                viewHolder.blockButton.setEnabled(false);
                viewHolder.unFollowButton.setEnabled(true);
            }
        });
        viewHolder.unFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserStatusChanged(user, Constans.UserStatus.UNFOLLOW);
                viewHolder.unFollowButton.setEnabled(false);
                viewHolder.blockButton.setEnabled(true);
            }
        });
        return view;
    }

    private static class ViewHolder {
        ImageView profileImageView;
        TextView userNameTextView;
        TextView fullNameTextView;
        Button blockButton;
        Button unFollowButton;
    }
}
