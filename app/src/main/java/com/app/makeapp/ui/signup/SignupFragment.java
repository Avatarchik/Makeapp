package com.app.makeapp.ui.signup;

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
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.ui.signin.BaseSignInFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 19.12.16.
 */

public class SignupFragment extends BaseSignInFragment implements Callback<SignUpModel> {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.firstName)
    EditText firstName;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.registrButton)
    public void onRegistrClick() {
        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(pass.getText().toString()) && !TextUtils.isEmpty(firstName.getText().toString()) && !TextUtils.isEmpty(lastName.getText().toString())) {
            RestClient.instance().signUp(email.getText().toString(), pass.getText().toString(), firstName.getText().toString(), lastName.getText().toString()).enqueue(this);
        } else
            Toast.makeText(context, getString(R.string.signup_fields_empty), Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

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
}
