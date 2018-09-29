package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.RiverDetails;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 巡查记录item
 *
 * @author Superman
 */

public class XunChaRecordItem extends AbstractFlexibleItem<XunChaRecordItem.EntityViewHolder> {
    private RiverDetails.XCJHListBean xcjhListBean;

    public RiverDetails.XCJHListBean getData() {
        return xcjhListBean;
    }

    public XunChaRecordItem(RiverDetails.XCJHListBean xcjhListBean) {
        this.xcjhListBean = xcjhListBean;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_item_xcjl;
    }

    @Override
    public XunChaRecordItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new XunChaRecordItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView name;
        TextView date;
        TextView level;


        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_date);
            level = (TextView) view.findViewById(R.id.tv_level);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (xcjhListBean != null) {
            if (StringUtils.hasLength(xcjhListBean.getNAME())) {
                holder.name.setText(xcjhListBean.getNAME());
            }
            if (xcjhListBean.getPATROL_TM() != null) {
                holder.date.setText(xcjhListBean.getPATROL_TM());
            }
            holder.level.setVisibility(View.GONE);
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
