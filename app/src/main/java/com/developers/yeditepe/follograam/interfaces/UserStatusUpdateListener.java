package com.developers.yeditepe.follograam.interfaces;

import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.utils.Constans;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public interface UserStatusUpdateListener {
    void onUserStatusChanged(InstagramUser user, Constans.UserStatus status);

    void onUserStatusChangedByService();
}
