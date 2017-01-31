package com.app.makeapp.ui.categories.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.makeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 24.12.16.
 */

public class ToolbarPopupAdapter extends ArrayAdapter<String> {

    private Context context;

    public ToolbarPopupAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(getItem(position).equals("-")) {
            convertView = inflater.inflate(R.layout.item_profile_popup_line, parent, false);
        } else {
            convertView = inflater.inflate(R.layout.item_profile_popup, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            viewHolder.itemName.setText(getItem(position));
        }
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.itemName)
        TextView itemName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
