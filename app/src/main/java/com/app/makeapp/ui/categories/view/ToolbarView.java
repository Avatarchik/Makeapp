package com.app.makeapp.ui.categories.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.makeapp.R;
import com.app.makeapp.etc.Constants;
import com.app.makeapp.ui.about.AboutActivity;
import com.app.makeapp.ui.categories.view.adapter.ToolbarPopupAdapter;
import com.app.makeapp.ui.reset.ResetPassActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 24.12.16.
 */

public class ToolbarView extends LinearLayout implements View.OnClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.profile)
    ImageView profile;

    private Context context;
    private PopupWindow popupWindow;

    public ToolbarView(Context context) {
        super(context);
        setUp(context);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    private void setUp(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_toolbar, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
        popupWindow = new PopupWindow(this);

        backArrow.setOnClickListener(this);
        profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                ((Activity) context).finish();
                break;
            case R.id.profile:
                PopupWindow popUp = popupWindowsort();
                popUp.showAsDropDown(this, 0, 0);
                break;
        }
    }

    private PopupWindow popupWindowsort() {
        ArrayList<String> sortList = new ArrayList<String>();
        sortList.add(context.getString(R.string.toolbar_popup_change_pass));
        sortList.add("-");
        sortList.add(context.getString(R.string.toolbar_popup_about));

        ToolbarPopupAdapter adapter = new ToolbarPopupAdapter(context);
        adapter.addAll(sortList);

        ListView listViewSort = new ListView(context);
        listViewSort.setDivider(null);
        listViewSort.setAdapter(adapter);
        listViewSort.setOnItemClickListener(onItemClickListener());
        popupWindow.setFocusable(true);
        popupWindow.setWidth(LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(listViewSort);
        popupWindow.setOnDismissListener(this);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return popupWindow;
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                switch (position) {
                    case Constants.CHANGE_PASSWORD:
                        context.startActivity(new Intent(context, ResetPassActivity.class));
                        break;
                    case Constants.ABOUT:
                        context.startActivity(new Intent(context, AboutActivity.class));
                        break;
                }
                popupWindow.dismiss();
            }
        };
    }

    public void setBackArrowVisibility(boolean status) {
        if(status)
            backArrow.setVisibility(VISIBLE);
        else
            backArrow.setVisibility(INVISIBLE);
    }

    public void setTitleVisibility(boolean status) {
        if(status)
            title.setVisibility(VISIBLE);
        else
            title.setVisibility(INVISIBLE);
    }

    public void setTitleText(String title) {
        this.title.setText(title);
    }

    @Override
    public void onDismiss() {
        popupWindow.dismiss();
    }
}
