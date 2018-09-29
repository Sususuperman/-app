package com.hywy.publichzt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.adapter.BaseListAdapter;
import com.cs.common.utils.StringUtils;
import com.cs.common.utils.ViewHolder;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.entity.Video;


/**
 * 视频点gridview adapter
 *
 * @author Superman
 */
public class VideoGridAdapter extends BaseListAdapter<River> {
    private Context context;

    public VideoGridAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent, final int position) {
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.item_video_grid_layout, parent, false);

        TextView tv_name = ViewHolder.get(convertView, R.id.name);
        ImageView iv_status = ViewHolder.get(convertView, R.id.iv_status);
        River river = getItem(position);
        if (river != null) {
            if (StringUtils.hasLength(river.getRV_NAME())) {
                tv_name.setText(river.getRV_NAME());
            }
            if (StringUtils.hasLength(river.getREACH_NAME())) {
                tv_name.setText(river.getREACH_NAME());
            }

            if (StringUtils.hasLength(river.getWATER_TYPE())) {
                switch (river.getWATER_TYPE()) {
                    case "1":
                        iv_status.setImageResource(R.drawable.ic_i);
                        break;
                    case "2":
                        iv_status.setImageResource(R.drawable.ic_ii);
                        break;
                    case "3":
                        iv_status.setImageResource(R.drawable.ic_iii);
                        break;
                    case "4":
                        iv_status.setImageResource(R.drawable.ic_iv);
                        break;
                    case "5":
                        iv_status.setImageResource(R.drawable.ic_v);
                        break;
                    case "6":
                        iv_status.setImageResource(R.drawable.ic_v_);
                        break;
                }
            }
        }
        return convertView;
    }

}
