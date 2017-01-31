package com.app.makeapp.ui.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.InformationModel;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.CategoriesActivity;
import com.app.makeapp.ui.categories.view.ToolbarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 25.01.17.
 */

public class InformationFragment extends BaseFragment implements Callback<InformationModel> {

    @BindView(R.id.toolbar)
    ToolbarView toolbarView;
    @BindView(R.id.information)
    TextView information;
    @BindView(R.id.title)
    TextView title;

    private InformationModel informationModel;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarView.setTitleVisibility(false);
        RestClient.instance().getInformation().enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResponse(Call<InformationModel> call, Response<InformationModel> response) {
        this.informationModel = response.body();
        information.setText(response.body().getContent());
        title.setText(response.body().getHeading());
    }

    @Override
    public void onFailure(Call<InformationModel> call, Throwable t) {
        Log.e(getFragmentManager().toString(), t.getMessage());
    }

    @OnClick(R.id.vkButton)
    public void onVKClick() {
        openBrowser(informationModel.getVk());
    }

    @OnClick(R.id.fbButton)
    public void onFBClick() {
        openBrowser(informationModel.getFacebook());
    }

    @OnClick(R.id.instButton)
    public void onInstClick() {
        openBrowser(informationModel.getInstagram());
    }

    @OnClick(R.id.wwwButton)
    public void onWwwClick() {
        openBrowser(informationModel.getSiteLink());
    }

    private void openBrowser(String link) {
        if(isValid(link)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            startActivity(i);
        } else
            Toast.makeText(context, getString(R.string.information_invalid_link), Toast.LENGTH_LONG).show();
    }

    private boolean isValid(String urlString) {
        if(Patterns.WEB_URL.matcher(urlString).matches())
            return true;
        else
            return false;
    }

    @OnClick(R.id.okButton)
    public void onOKButtonClick() {
        startActivity(new Intent(context, CategoriesActivity.class));
        context.finish();
    }
}
