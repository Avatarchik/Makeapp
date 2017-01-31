package com.app.makeapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.InformationModel;
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.ui.information.InformationActivity;
import com.app.makeapp.ui.signin.SignInActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 27.12.16.
 */

public class SplashActivity extends AppCompatActivity implements Runnable {

    private int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this, SPLASH_TIME_OUT);
    }

    @Override
    public void run() {
        if(!TextUtils.isEmpty(SharedPrefs.getToken(this))) {
            RestClient.instance().getInformation().enqueue(new Callback<InformationModel>() {
                @Override
                public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                    if (SharedPrefs.getInformationId(SplashActivity.this) == response.body().getId()) {
                        startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    } else {
                        SharedPrefs.setInformationId(SplashActivity.this, response.body().getId());
                        startActivity(new Intent(SplashActivity.this, InformationActivity.class));
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<InformationModel> call, Throwable t) {
                    Log.e(getFragmentManager().toString(), t.getMessage());
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
        }
    }
}
