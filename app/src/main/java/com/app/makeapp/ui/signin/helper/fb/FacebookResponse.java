package com.app.makeapp.ui.signin.helper.fb;

/**
 * Created by gleb on 20.12.16.
 */

public interface FacebookResponse {
    void onFbSignInFail();
    void onFbSignInSuccess();
    void onFbProfileReceived(FacebookUser facebookUser);
}
