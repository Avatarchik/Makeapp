package com.app.makeapp.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.makeapp.BuildConfig;
import com.app.makeapp.R;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.view.ToolbarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 27.12.16.
 */

public class AboutFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    ToolbarView toolbarView;
    @BindView(R.id.about)
    TextView about;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarView.setTitleVisibility(false);
        about.setText(String.format(getString(R.string.about_text), BuildConfig.VERSION_NAME));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
