package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRelationCount {

    private int media;
    private int follows;
    private int followed_by;

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowed_by() {
        return followed_by;
    }

    public void setFollowed_by(int followed_by) {
        this.followed_by = followed_by;
    }
}
