package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ibrahimsalgin on 20.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String id;
    private String username;
    private String full_name;
    private String profile_picture;
    private String bio;
    private String website;
    private boolean is_business;
    private UserRelationCount counts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isIs_business() {
        return is_business;
    }

    public void setIs_business(boolean is_business) {
        this.is_business = is_business;
    }

    public UserRelationCount getCounts() {
        return counts;
    }

    public void setCounts(UserRelationCount counts) {
        this.counts = counts;
    }
}
