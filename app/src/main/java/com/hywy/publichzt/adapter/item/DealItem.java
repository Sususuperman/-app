package com.hywy.publichzt.adapter.item;

import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.ImageLoaderUtils;
import com.cs.common.utils.StringUtils;
import com.hywy.publichzt.R;
import com.hywy.publichzt.activity.ImageGridActivity;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.AttachMent;
import com.hywy.publichzt.entity.Deal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 事件处理过程item
 *
 * @author Superman
 */

public class DealItem extends AbstractFlexibleItem<DealItem.EntityViewHolder> {
    private Deal deal;

    public Deal getData() {
        return deal;
    }

    public DealItem(Deal deal) {
        this.deal = deal;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_deal;
    }

    @Override
    public DealItem.EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new DealItem.EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    static class EntityViewHolder extends FlexibleViewHolder {
        TextView user_name;
        TextView user_type;
        TextView event_time;
        TextView event_content;
        ImageView head_img, time_marker;
        TextView tv_attach;


        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_type = (TextView) view.findViewById(R.id.user_type);
            event_time = (TextView) view.findViewById(R.id.deal_time);
            event_content = (TextView) view.findViewById(R.id.deal_content);
            head_img = (ImageView) view.findViewById(R.id.iv_head);
            time_marker = (ImageView) view.findViewById(R.id.time_marker);
            tv_attach = (TextView) view.findViewById(R.id.tv_attach);
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
            if (deal.getDEAL_IDEA() != null) {
                holder.event_content.setText(deal.getDEAL_IDEA());
            }
            if (deal.getTM() != null) {
                //结束时间
                holder.event_time.setText(deal.getTM());
            }
            if (StringUtils.hasLength(deal.getHTML_URL())) {
                ImageLoaderUtils.displayRound(App.getAppContext(), holder.head_img, deal.getHTML_URL(), R.drawable.ic_head_default);
            } else {
                holder.head_img.setImageResource(R.drawable.ic_head_default);
            }

            if (StringUtils.isNotNullList(deal.getDealattch())) {
                holder.tv_attach.setTextColor(ContextCompat.getColor(holder.tv_attach.getContext(), R.color.bg_text_qing));
                holder.tv_attach.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_attach.getContext(), R.drawable.icon_attach), null, null, null);
                holder.tv_attach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                holder.tv_attach.setText("附件(" + deal.getDealattch().size() + ")");
                holder.tv_attach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageGridActivity.startAction(holder.tv_attach.getContext(), (ArrayList<AttachMent>) deal.getDealattch());
                    }
                });
            } else {
                holder.tv_attach.setTextColor(ContextCompat.getColor(holder.tv_attach.getContext(), R.color.font_gray));
                holder.tv_attach.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(holder.tv_attach.getContext(), R.drawable.icon_attach_gray), null, null, null);
//                holder.tv_attach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                holder.tv_attach.setText("附件(0)");
            }
        }
        if (position == 0) {
            holder.time_marker.setImageResource(R.drawable.icon_time_line_blue);
        } else {
            holder.time_marker.setImageResource(R.drawable.icon_time_line_gray);
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
