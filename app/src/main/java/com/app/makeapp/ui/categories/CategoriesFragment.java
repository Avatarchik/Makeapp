package com.app.makeapp.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.CategoriesModel;
import com.app.makeapp.etc.Constants;
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.adapter.CategoryAdapter;
import com.app.makeapp.ui.categories.view.ToolbarView;
import com.app.makeapp.ui.videos.VideosActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 19.12.16.
 */

public class CategoriesFragment extends BaseFragment implements Callback<List<CategoriesModel>>,
        CategoryAdapter.OnCategoryItemClickListener {

    @BindView(R.id.listCategories)
    ListView listCategories;
    @BindView(R.id.toolbar)
    ToolbarView toolbarView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestClient.instance().getCategoryList(SharedPrefs.getUserObjectId(context)).enqueue(this);
        toolbarView.setTitleVisibility(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(context);
        categoryAdapter.addAll(response.body());
        categoryAdapter.setOnCategoryItemClickListener(this);
        listCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {
        Log.e(getFragmentManager().toString(), t.getMessage());
    }

    @Override
    public void onCategoryItemClick(CategoriesModel categoriesModel) {
        Intent intent = new Intent(context, VideosActivity.class);
        intent.putExtra(Constants.VIDEO_ID, categoriesModel.getId());
        intent.putExtra(Constants.TITLE, categoriesModel.getTitle());
        intent.putExtra(Constants.SOLD, categoriesModel.getSold());
        startActivity(intent);
    }
}
