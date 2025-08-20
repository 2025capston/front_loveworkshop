package com.example.romancesample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CloneAdapter extends BaseAdapter {
    private Context context;
    private List<CloneItem> cloneList;

    public CloneAdapter(Context context, List<CloneItem> cloneList) {
        this.context = context;
        this.cloneList = cloneList;
    }

    @Override
    public int getCount() {
        return cloneList.size();
    }

    @Override
    public Object getItem(int position) {
        return cloneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView iv_img;
        TextView tv_name;
        TextView tv_contents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false);
            holder = new ViewHolder();
            holder.iv_img = convertView.findViewById(R.id.profileimg);
            holder.tv_name = convertView.findViewById(R.id.height1);
            holder.tv_contents = convertView.findViewById(R.id.live1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CloneItem item = cloneList.get(position);
        holder.iv_img.setImageResource(item.imageResId);
        holder.tv_name.setText(item.name);
        holder.tv_contents.setText(item.contents);

        return convertView;
    }
}
