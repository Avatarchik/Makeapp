package com.app.makeapp.ui.categories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.makeapp.R;
import com.app.makeapp.api.models.CategoriesModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 23.12.16.
 */

public class CategoryAdapter extends ArrayAdapter<CategoriesModel> {

    private Context context;
    private OnCategoryItemClickListener onCategoryItemClickListener;

    public CategoryAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_category, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title.setText(getItem(position).getTitle());
        viewHolder.description.setText(getItem(position).getDescription());
        Picasso.with(context).load(getItem(position).getImage()).into(viewHolder.image);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryItemClickListener.onCategoryItemClick(getItem(position));
            }
        });
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public CategoryAdapter setOnCategoryItemClickListener(OnCategoryItemClickListener onCategoryItemClickListener) {
        this.onCategoryItemClickListener = onCategoryItemClickListener;
        return this;
    }

    public interface OnCategoryItemClickListener {
        void onCategoryItemClick(CategoriesModel categoriesModel);
    }
}
