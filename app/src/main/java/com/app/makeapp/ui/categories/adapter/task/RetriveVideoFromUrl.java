package com.app.makeapp.ui.categories.adapter.task;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;

import com.app.makeapp.ui.videos.adapter.VideosAdapter;

import java.util.HashMap;

/**
 * Created by gleb on 24.12.16.
 */

public class RetriveVideoFromUrl extends AsyncTask<String, Integer, Bitmap> {

    private OnVideoUrlRetrivedListener onVideoUrlRetrivedListener;
    private VideosAdapter.ViewHolder viewHolder;

    public RetriveVideoFromUrl(VideosAdapter.ViewHolder viewHolder ) {
        this.viewHolder = viewHolder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(urls[0], new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(urls[0]);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        onVideoUrlRetrivedListener.onVideoRetrived(result, viewHolder);
        super.onPostExecute(result);
    }

    public void setOnVideoUrlRetrivedListener(OnVideoUrlRetrivedListener onVideoUrlRetrivedListener) {
        this.onVideoUrlRetrivedListener = onVideoUrlRetrivedListener;
    }

    public interface OnVideoUrlRetrivedListener {
        void onVideoRetrived(Bitmap bitmap, VideosAdapter.ViewHolder viewHolder);
    }
}