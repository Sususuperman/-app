package com.hywy.publichzt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.common.adapter.BaseListAdapter;
import com.cs.common.utils.StringUtils;
import com.cs.common.utils.ViewHolder;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.WaterClassify;
import com.hywy.publichzt.view.customer.WaterQualityInfoView;


/**
 * 水质监测项目分类
 *
 * @author Superman
 */
public class WaterClassifyAdapter extends BaseListAdapter<WaterClassify> {
    private Context context;

    public WaterClassifyAdapter(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent, final int position) {
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.layout_item_water_classify, parent, false);

        WaterQualityInfoView waterQualityInfoView = ViewHolder.get(convertView, R.id.view);
        WaterClassify waterClassify = getItem(position);

        if (StringUtils.hasLength(waterClassify.getName())) {
            waterQualityInfoView.setTextView1(waterClassify.getName());
        }

        waterQualityInfoView.setTextView2(waterClassify.getValue() + "");

        if (waterClassify.getColor() != 0) {
            if (waterClassify.getColor() == 1) {
                waterQualityInfoView.setTextBacgound(R.color.bg_type_1);
            } else if (waterClassify.getColor() == 2) {
                waterQualityInfoView.setTextBacgound(R.color.bg_type_2);
            } else if (waterClassify.getColor() == 3) {
                waterQualityInfoView.setTextBacgound(R.color.bg_type_3);
            } else if (waterClassify.getColor() == 4) {
                waterQualityInfoView.setTextBacgound(R.color.bg_type_4);
            } else if (waterClassify.getColor() == 5) {
                waterQualityInfoView.setTextBacgound(R.color.bg_type_5);
            }
        }
        return convertView;
    }


}
