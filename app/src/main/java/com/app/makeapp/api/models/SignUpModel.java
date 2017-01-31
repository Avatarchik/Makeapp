package com.app.makeapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gleb on 23.12.16.
 */

public class SignUpModel {
    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_object_id")
    @Expose
    private Integer userObjectId;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(Integer userObjectId) {
        this.userObjectId = userObjectId;
    }
}
