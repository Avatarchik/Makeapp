package com.app.makeapp.ui.videos.player;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.makeapp.R;
import com.app.makeapp.etc.Constants;
import com.app.makeapp.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 05.01.17.
 */

public class PlayerFragment extends BaseFragment {

    @BindView(R.id.videoView)
    VideoView videoLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String videoUrl = getActivity().getIntent().getExtras().getString(Constants.VIDEO_URL);
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoLayout);
        videoLayout.setMediaController(mediaController);
        videoLayout.setVideoURI(Uri.parse(videoUrl));
        videoLayout.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
