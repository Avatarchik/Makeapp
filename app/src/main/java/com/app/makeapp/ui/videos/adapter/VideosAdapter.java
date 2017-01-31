package com.app.makeapp.ui.videos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.makeapp.R;
import com.app.makeapp.api.models.VideosModel;
import com.app.makeapp.ui.categories.adapter.task.RetriveVideoFromUrl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 24.12.16.
 */

public class VideosAdapter extends ArrayAdapter<VideosModel> implements RetriveVideoFromUrl.OnVideoUrlRetrivedListener {

    private Context context;
    private OnVideoClickedListener onVideoClickedListener;
    private boolean sold;

    public VideosAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_video, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title.setText(getItem(position).getTitle());
        if(!TextUtils.isEmpty(getItem(position).getImage()))
            Picasso.with(getContext()).load(getItem(position).getImage()).into(viewHolder.image);
        else {
            RetriveVideoFromUrl retriveVideoFromUrl = new RetriveVideoFromUrl(viewHolder);
            retriveVideoFromUrl.setOnVideoUrlRetrivedListener(this);
            retriveVideoFromUrl.execute(getItem(position).getVideo());
        }
        if(sold)
            viewHolder.locked.setVisibility(View.INVISIBLE);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVideoClickedListener.onVideoClicked(getItem(position));
            }
        });
        return convertView;
    }

    @Override
    public void onVideoRetrived(Bitmap bitmap, VideosAdapter.ViewHolder viewHolder) {
        viewHolder.image.setImageBitmap(bitmap);
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public class ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.locked)
        ImageView locked;
        @BindView(R.id.bgImage)
        ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnVideoClickedListener(OnVideoClickedListener onVideoClickedListener) {
        this.onVideoClickedListener = onVideoClickedListener;
    }

    public interface OnVideoClickedListener {
        void onVideoClicked(VideosModel videosModel);
    }
}
