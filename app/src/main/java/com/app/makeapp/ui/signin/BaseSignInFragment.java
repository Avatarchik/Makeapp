package com.app.makeapp.ui.signin;

import android.content.Intent;
import android.util.Log;

import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.InformationModel;
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.CategoriesActivity;
import com.app.makeapp.ui.information.InformationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 26.01.17.
 */

public class BaseSignInFragment extends BaseFragment {

    public void logInUser() {
        RestClient.instance().getInformation().enqueue(new Callback<InformationModel>() {
            @Override
            public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
                if(SharedPrefs.getInformationId(context) == response.body().getId()) {
                    startActivity(new Intent(context, CategoriesActivity.class));
                } else {
                    SharedPrefs.setInformationId(context, response.body().getId());
                    startActivity(new Intent(context, InformationActivity.class));
                }
                context.finish();
            }

            @Override
            public void onFailure(Call<InformationModel> call, Throwable t) {
                Log.e(getFragmentManager().toString(), t.getMessage());
            }
        });
    }
}
