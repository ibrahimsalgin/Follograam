package com.developers.yeditepe.follograam.interfaces;

import com.developers.yeditepe.follograam.entities.InstagramUser;
import com.developers.yeditepe.follograam.utils.Constans;

import java.util.List;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

public interface InstagramUserListListener {
    void onListObtained(List<InstagramUser> instagramUserList, Constans.ServiceType serviceType);
}
