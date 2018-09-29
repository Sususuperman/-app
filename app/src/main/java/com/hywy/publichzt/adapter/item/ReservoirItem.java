package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Reservoir;

import java.text.DecimalFormat;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 水库水位监测
 *
 * @author Superman
 */

public class ReservoirItem extends AbstractFlexibleItem<ReservoirItem.EntityViewHolder> {
    private Reservoir reservoir;

    public Reservoir getData() {
        return reservoir;
    }

    public ReservoirItem(Reservoir reservoir) {
        this.reservoir = reservoir;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_reservoir;
    }

    @Override
    public ReservoirItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ReservoirItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title_r;
        TextView tv_address_r;
        TextView tv_water_r;
        TextView tv_stream_r;
        TextView tv_time_r;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title_r = (TextView) view.findViewById(R.id.tv_title_r);
            tv_address_r = (TextView) view.findViewById(R.id.tv_address_r);
            tv_water_r = (TextView) view.findViewById(R.id.water_height);
            tv_stream_r = (TextView) view.findViewById(R.id.stream_day);
            tv_time_r = (TextView) view.findViewById(R.id.tv_time_r);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (reservoir != null) {
            if (StringUtils.hasLength(reservoir.getSTNM())) {
                holder.tv_title_r.setText(reservoir.getSTNM());
            }
            if (reservoir.getSTLC() != null) {
                holder.tv_address_r.setText(reservoir.getSTLC());
            }
            holder.tv_water_r.setText(reservoir.getRZ() + "mm");
            holder.tv_stream_r.setText(reservoir.getOTQ() + "m³/s");
            holder.tv_time_r.setText(reservoir.getTM());

        }
    }

    /**
     * 将数据保留两位小数
     */
    private String getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#########0.00");
        String str = dFormat.format(num);
        return str;
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
