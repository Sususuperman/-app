package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.RiverCourse;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 河道水文
 *
 * @author Superman
 */

public class RiverCourseItem extends AbstractFlexibleItem<RiverCourseItem.EntityViewHolder> {
    private RiverCourse riverCourse;

    public RiverCourse getData() {
        return riverCourse;
    }

    public RiverCourseItem(RiverCourse riverCourse) {
        this.riverCourse = riverCourse;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_river_course;
    }

    @Override
    public RiverCourseItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new RiverCourseItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title_r;
        TextView tv_address_r;
        TextView tv_time_r;
        TextView tv_wt_r;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title_r = (TextView) view.findViewById(R.id.tv_title_r);
            tv_address_r = (TextView) view.findViewById(R.id.tv_address_r);
            tv_time_r = (TextView) view.findViewById(R.id.tv_time_r);
            tv_wt_r = (TextView) view.findViewById(R.id.water_height);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (riverCourse != null) {
            if (StringUtils.hasLength(riverCourse.getSTNM())) {
                holder.tv_title_r.setText(riverCourse.getSTNM());
            }

            holder.tv_time_r.setText(riverCourse.getTM());
            holder.tv_address_r.setText(riverCourse.getSTLC());
            holder.tv_wt_r.setText(riverCourse.getZ() + "mm");

        }
    }


    /**
     */
    @Override
    public boolean equals(Object o) {
//        if (o instanceof WaterAndRainItem) {
//            WaterAndRainItem odata = (WaterAndRainItem) o;
//            return waterRain.getOrganID() == odata.getData().getOrganID();
//        }

        return false;
    }
}
