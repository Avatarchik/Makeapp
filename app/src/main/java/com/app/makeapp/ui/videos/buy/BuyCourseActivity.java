package com.app.makeapp.ui.videos.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.app.makeapp.R;
import com.app.makeapp.ui.BaseActivity;

public class BuyCourseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_course);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
