package me.iwf.photopicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.iwf.photopicker.R;
import me.iwf.photopicker.entity.Photo;

public class PhotoDialogAdapter extends BaseAdapter {
    private ArrayList<Photo> lstImageItem;

    private Context mContext;

    public PhotoDialogAdapter(Context mContext, ArrayList<Photo> arg0) {
        this.lstImageItem = arg0;
        this.mContext = mContext;
    }

    public int getCount() {
        return lstImageItem.size();
    }

    public Object getItem(int position) {
        return lstImageItem.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.__picker_item_dialog, null);
            viewHolder.tv_dialog = (TextView) view.findViewById(R.id.tv_dialog);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position == 0) {
            viewHolder.tv_dialog.setBackgroundResource(R.drawable.__picker_item_dialog_selector_top);
        } else if (position == getCount() - 1) {
            viewHolder.tv_dialog.setBackgroundResource(R.drawable.__picker_item_dialog_selector_bottom);
        } else {
            viewHolder.tv_dialog.setBackgroundResource(R.drawable.__picker_item_dialog_selector);
        }

        viewHolder.tv_dialog.setText(lstImageItem.get(position).getPath());
        return view;
    }

    class ViewHolder {
        TextView tv_dialog;
    }
}