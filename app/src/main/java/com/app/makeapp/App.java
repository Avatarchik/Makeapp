package com.app.makeapp;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.app.makeapp.ui.signin.SignInActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by gleb on 20.12.16.
 */

public class App extends Application {

    private static App instance;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(App.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(App.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    public static App getInstance() {
        return instance;
    }
}