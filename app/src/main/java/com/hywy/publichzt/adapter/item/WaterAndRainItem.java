package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.WaterRain;

import java.text.DecimalFormat;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 河湖监测
 *
 * @author Superman
 */

public class WaterAndRainItem extends AbstractFlexibleItem<WaterAndRainItem.EntityViewHolder> {
    private WaterRain waterRain;

    public WaterRain getData() {
        return waterRain;
    }

    public WaterAndRainItem(WaterRain waterRain) {
        this.waterRain = waterRain;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_water_rain;
    }

    @Override
    public WaterAndRainItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new WaterAndRainItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title_r;
        TextView tv_address_r;
        TextView tv_rain_r;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title_r = (TextView) view.findViewById(R.id.tv_title_r);
            tv_address_r = (TextView) view.findViewById(R.id.tv_address_r);
            tv_rain_r = (TextView) view.findViewById(R.id.rain_day);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        if (waterRain != null) {
            if (StringUtils.hasLength(waterRain.getSTNM())) {
                holder.tv_title_r.setText(waterRain.getSTNM());
            }
            if (waterRain.getADNM() != null) {
                holder.tv_address_r.setText(waterRain.getADNM());
            }
            holder.tv_rain_r.setText(getTwoDecimal(waterRain.getDRP()) + "mm");

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
