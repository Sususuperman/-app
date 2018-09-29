package com.hywy.publichzt.adapter.item;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.adapter.WaterClassifyAdapter;
import com.hywy.publichzt.entity.WaterClassify;
import com.hywy.publichzt.entity.WaterQuality;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 水质监测
 *
 * @author Superman
 */

public class WaterQualityItem extends AbstractFlexibleItem<WaterQualityItem.EntityViewHolder> {
    private WaterQuality waterQuality;
    private WaterClassifyAdapter mAdapter;

    public WaterQuality getData() {
        return waterQuality;
    }

    public WaterQualityItem(WaterQuality waterQuality) {
        this.waterQuality = waterQuality;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_water_quality;
    }

    @Override
    public WaterQualityItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new WaterQualityItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView tv_title_r;
        TextView tv_address_r;
        TextView tv_time_r;
        GridView gridView;
        TextView tv_temp_r;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            tv_title_r = (TextView) view.findViewById(R.id.tv_title_r);
            tv_address_r = (TextView) view.findViewById(R.id.tv_address_r);
            tv_time_r = (TextView) view.findViewById(R.id.tv_time_r);
            gridView = (GridView) view.findViewById(R.id.gridview);
            tv_temp_r = (TextView) view.findViewById(R.id.tv_temp_r);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, EntityViewHolder holder, int position, List payloads) {
        mAdapter = new WaterClassifyAdapter(holder.gridView.getContext());
        if (waterQuality != null) {
            if (StringUtils.hasLength(waterQuality.getSTNM())) {
                holder.tv_title_r.setText(waterQuality.getSTNM());
            }

            List<WaterClassify> list = new ArrayList<>();
            WaterClassify waterClassify1 = new WaterClassify(holder.tv_title_r.getContext().getString(R.string.text_zonglin), Double.parseDouble(waterQuality.getTP()), getTPtype(Double.parseDouble(waterQuality.getTP())));
            WaterClassify waterClassify2 = new WaterClassify(holder.tv_title_r.getContext().getString(R.string.text_fuhuawu), Double.parseDouble(waterQuality.getF()), getFtype(Double.parseDouble(waterQuality.getF())));
            WaterClassify waterClassify4 = new WaterClassify(holder.tv_title_r.getContext().getString(R.string.text_gaomeng), Double.parseDouble(waterQuality.getCODMN()), getGmsytype(Double.parseDouble(waterQuality.getCODMN())));
            WaterClassify waterClassify3 = new WaterClassify(holder.tv_title_r.getContext().getString(R.string.text_rongjieyang), waterQuality.getDOX(), getRjytype(waterQuality.getDOX()));
            list.add(waterClassify1);
            list.add(waterClassify2);
            list.add(waterClassify3);
            list.add(waterClassify4);
            mAdapter.setList(list);
            holder.gridView.setAdapter(mAdapter);
            //解决recycleview和gridview的点击冲突问题
            holder.gridView.setClickable(false);
            holder.gridView.setEnabled(false);
            holder.gridView.setPressed(false);

//            if (reservoir.getADCD() != null) {
//                holder.tv_address_r.setText(reservoir.getADCD());
//            }
//            holder.tv_water_r.setText(getTwoDecimal(reservoir.getRZ()) + "m");
//            holder.tv_stream_r.setText(getTwoDecimal(reservoir.getOTQ()) + "m³/s");
            holder.tv_time_r.setText(waterQuality.getSPT());
            holder.tv_address_r.setText(waterQuality.getSTLC());
            holder.tv_temp_r.setText(waterQuality.getWT() + "℃");

            if (StringUtils.hasLength(waterQuality.getLEVEL_CODE())) {

                if (waterQuality.getLEVEL_CODE().equals("1")) {
                    holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_title_r.getContext(), R.drawable.ic_i), null, null, null);
                } else if (waterQuality.getLEVEL_CODE().equals("2")) {
                    holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_title_r.getContext(), R.drawable.ic_ii), null, null, null);
                } else if (waterQuality.getLEVEL_CODE().equals("3")) {
                    holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_title_r.getContext(), R.drawable.ic_iii), null, null, null);
                } else if (waterQuality.getLEVEL_CODE().equals("4")) {
                    holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_title_r.getContext(), R.drawable.ic_iv), null, null, null);
                } else if (waterQuality.getLEVEL_CODE().equals("5")) {
                    holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_title_r.getContext(), R.drawable.ic_v), null, null, null);
                }

            } else {
                holder.tv_title_r.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
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

    //总磷类别
    public int getTPtype(double d) {
        if (d <= 0.02) {
            return 1;
        } else if (d > 0.02 && d <= 0.1) {
            return 2;
        } else if (d > 0.1 && d <= 0.2) {
            return 3;
        } else if (d > 0.2 && d <= 0.3) {
            return 4;
        } else if (d > 0.3 && d <= 0.4) {
            return 5;
        }
        return 5;
    }

    //氟化物类别
    public int getFtype(double d) {
        if (d <= 1.0) {
            return 1;
        } else if (d > 1.0 && d <= 1.5) {
            return 4;
        }
        return 5;
    }

    //溶解氧类别
    public int getRjytype(double d) {
        if (d >= 7.5) {
            return 1;
        } else if (d >= 6.0 && d < 7.5) {
            return 2;
        } else if (d >= 5.0 && d < 6.0) {
            return 3;
        } else if (d >= 3.0 && d < 5.0) {
            return 4;
        } else if (d >= 2.0 && d < 3.0) {
            return 5;
        }
        return 5;
    }

    //高锰酸盐类别
    public int getGmsytype(double d) {
        if (d <= 2.0) {
            return 1;
        } else if (d > 2.0 && d <= 4.0) {
            return 2;
        } else if (d > 4.0 && d <= 6.0) {
            return 3;
        } else if (d > 6.0 && d <= 10.0) {
            return 4;
        } else if (d > 10.0 && d <= 15.0) {
            return 5;
        }
        return 5;
    }
}
