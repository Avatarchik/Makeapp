package com.app.makeapp.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.SignUpModel;
import com.app.makeapp.etc.Constants;
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.ui.categories.CategoriesActivity;
import com.app.makeapp.ui.forgot.ForgotPassActivity;
import com.app.makeapp.ui.signin.helper.fb.FacebookHelper;
import com.app.makeapp.ui.signin.helper.fb.FacebookResponse;
import com.app.makeapp.ui.signin.helper.fb.FacebookUser;
import com.app.makeapp.ui.signin.helper.vk.VKHelper;
import com.app.makeapp.ui.signin.helper.vk.VKResponse;
import com.app.makeapp.ui.signup.SignUpActivity;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 19.12.16.
 */

public class SignInFragment extends BaseSignInFragment implements FacebookResponse, VKResponse {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.pass)
    EditText pass;

    private FacebookHelper mFbHelper;
    private VKHelper vkHelper;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFbHelper = new FacebookHelper(this, "id, name, email, gender, birthday, picture, cover", context);
        vkHelper = new VKHelper(this, new String[] { VKScope.FRIENDS, VKScope.PHOTOS, VKScope.STATUS });
        if(!TextUtils.isEmpty(SharedPrefs.getToken(context))) {
            startActivity(new Intent(context, CategoriesActivity.class));
            context.finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.registrButton)
    public void registOnClick() {
        if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString())) {
            RestClient.instance().signIn(email.getText().toString(), pass.getText().toString()).enqueue(new Callback<SignUpModel>() {
                @Override
                public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                    if (response.isSuccessful()) {
                        SharedPrefs.setToken(context, response.body().getAuthKey(), response.body().getUserId(), response.body().getUserObjectId());
                        logInUser();
                    } else
                        Toast.makeText(context, getString(R.string.signup_problem), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<SignUpModel> call, Throwable t) {
                    Log.e(getFragmentManager().toString(), t.getMessage());
                }
            });
        } else
            Toast.makeText(context, getString(R.string.signup_fields_empty), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fbButton)
    public void onFacebookClick() {
        mFbHelper.performSignIn(this);
    }

    @OnClick(R.id.vkButton)
    public void onVkontakteClick() {
        vkHelper.performSignIn(this);
    }

    @OnClick(R.id.forgotBtn)
    public void onForgotClick() {
        Intent intent = new Intent(context, ForgotPassActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.registrBtn)
    public void onRegistrClick() {
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFbHelper.onActivityResult(requestCode, resultCode, data);
        vkHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFbSignInFail() {
        Toast.makeText(context, getString(R.string.facebook_login_problem), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFbSignInSuccess() { }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
        AccessToken token = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        socialSignIn(facebookUser.email, profile.getFirstName(), profile.getLastName(), token.getUserId(), Constants.FACEBOOK);
    }

    @Override
    public void onVKResult(final VKAccessToken res) {
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(com.vk.sdk.api.VKResponse response) {
                super.onComplete(response);
                VKApiUser user = ((VKList<VKApiUser>)response.parsedModel).get(0);
                socialSignIn(res.email, user.first_name, user.last_name, res.userId, Constants.VKONTAKTE);
            }
        });
    }

    @Override
    public void onVKError(VKError error) {
        Toast.makeText(context, error.errorReason, Toast.LENGTH_LONG).show();
    }

    public void socialSignIn(String email, String firstName, String lastName, String id, String service) {
        RestClient.instance().socialSignIn(email, firstName, lastName, id, service).enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if(response.isSuccessful()) {
                    SharedPrefs.setToken(context, response.body().getAuthKey(), response.body().getUserId(), response.body().getUserObjectId());
                    logInUser();
                } else
                    Toast.makeText(context, getString(R.string.signup_problem), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                Log.e(getFragmentManager().toString(), t.getMessage());
            }
        });
    }
}
