package com.app.makeapp.ui.signin.helper.fb;

import org.json.JSONObject;

/**
 * Created by gleb on 20.12.16.
 */

public class FacebookUser {
    public String name;

    public String email;

    public String facebookID;

    public String gender;

    public String about;

    public String bio;

    public String coverPicUrl;

    public String profilePic;

    /**
     * JSON response received. If you want to parse more fields.
     */
    public JSONObject response;
}
