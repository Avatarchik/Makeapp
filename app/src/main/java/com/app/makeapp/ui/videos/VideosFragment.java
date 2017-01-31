package com.app.makeapp.ui.videos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.VideosModel;
import com.app.makeapp.etc.Constants;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.view.ToolbarView;
import com.app.makeapp.ui.videos.adapter.VideosAdapter;
import com.app.makeapp.ui.videos.buy.BuyCourseActivity;
import com.app.makeapp.ui.videos.player.PlayerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 19.12.16.
 */

public class VideosFragment extends BaseFragment implements Callback<List<VideosModel>>,
        VideosAdapter.OnVideoClickedListener {

    @BindView(R.id.listVideos)
    ListView listVideos;
    @BindView(R.id.toolbar)
    ToolbarView toolbarView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarView.setTitleText(getActivity().getIntent().getExtras().getString(Constants.TITLE));
        RestClient.instance().getVideosById(getActivity().getIntent().getExtras().getInt(Constants.VIDEO_ID)).enqueue(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResponse(Call<List<VideosModel>> call, Response<List<VideosModel>> response) {
        VideosAdapter videosAdapter = new VideosAdapter(context);
        videosAdapter.addAll(response.body());
        videosAdapter.setOnVideoClickedListener(this);
        videosAdapter.setSold(getActivity().getIntent().getExtras().getBoolean(Constants.SOLD));
        listVideos.setAdapter(videosAdapter);
    }

    @Override
    public void onFailure(Call<List<VideosModel>> call, Throwable t) {
        Log.e(getFragmentManager().toString(), t.getMessage());
    }

    @Override
    public void onVideoClicked(VideosModel videosModel) {
        if(getActivity().getIntent().getExtras().getBoolean(Constants.SOLD)) {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra(Constants.VIDEO_URL, videosModel.getVideo());
            startActivity(intent);
        } else {
            startActivity(new Intent(context, BuyCourseActivity.class));
        }
    }
}
