package com.app.makeapp.ui.videos.buy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.makeapp.R;
import com.app.makeapp.api.models.CategoriesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 05.01.17.
 */

public class BuyCourseAdapter  extends ArrayAdapter<CategoriesModel> {

    private Context context;
    private OnBuyCourseItemClickListener onBuyCourseItemClickListener;

    public BuyCourseAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_buy_course, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title.setText(getItem(position).getTitle() + " - ");
        viewHolder.price.setText("$" + getItem(position).getPrice());
        viewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getItem(position).getSold()) {
                    onBuyCourseItemClickListener.onCourseItemClick(getItem(position).getId());
                } else {
                    Toast.makeText(getContext(), getContext().getString(R.string.buy_toast_already_bought), Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.buyButton)
        Button buyButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public BuyCourseAdapter setOnBuyCourseListener(OnBuyCourseItemClickListener onBuyCourseItemClickListener) {
        this.onBuyCourseItemClickListener = onBuyCourseItemClickListener;
        return this;
    }

    public interface OnBuyCourseItemClickListener {
        void onCourseItemClick(int id);
    }
}
