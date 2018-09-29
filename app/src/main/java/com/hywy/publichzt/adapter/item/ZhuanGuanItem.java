package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Deal;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 专管员item
 *
 * @author Superman
 */

public class ZhuanGuanItem extends AbstractFlexibleItem<ZhuanGuanItem.EntityViewHolder> {
    private Deal deal;

    public Deal getData() {
        return deal;
    }

    public ZhuanGuanItem(Deal deal) {
        this.deal = deal;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_item_zhuanguan;
    }

    @Override
    public ZhuanGuanItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ZhuanGuanItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView user_name;
        TextView user_type;
        ImageView iv_call;


        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            user_name = (TextView) view.findViewById(R.id.name);
            user_type = (TextView) view.findViewById(R.id.user_type);
            iv_call = (ImageView) view.findViewById(R.id.iv_call);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (deal != null) {
            if (StringUtils.hasLength(deal.getNAME())) {
                holder.user_name.setText(deal.getNAME());
            }
            if (deal.getPER_TypeName() != null) {
                holder.user_type.setText(deal.getPER_TypeName());
            }
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
