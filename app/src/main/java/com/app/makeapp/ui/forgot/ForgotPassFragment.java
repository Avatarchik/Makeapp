package com.app.makeapp.ui.forgot;

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
import com.app.makeapp.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 19.12.16.
 */

public class ForgotPassFragment extends BaseFragment implements Callback<ResponseBody> {

    @BindView(R.id.email)
    EditText email;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.sendButton)
    public void onSendClick() {
        if(!TextUtils.isEmpty(email.getText().toString()))
            RestClient.instance().forgotPass(email.getText().toString()).enqueue(this);
        else
            Toast.makeText(context, getString(R.string.signup_fields_empty), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(call.isExecuted())
            context.finish();
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(getFragmentManager().toString(), t.getMessage());
    }
}
