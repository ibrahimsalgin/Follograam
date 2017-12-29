package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstagramUsersResponseObject {
    private List<InstagramUser> data;

    public List<InstagramUser> getData() {
        return data;
    }

    public void setData(List<InstagramUser> data) {
        this.data = data;
    }
}
