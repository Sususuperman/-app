package com.hywy.publichzt.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.ProblemReport;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * 上报item
 *
 * @author Superman
 */
public class ProblemReportItem extends AbstractFlexibleItem<ProblemReportItem.EntityViewHolder> {
    private ProblemReport problemReport;

    public ProblemReportItem(ProblemReport problemReport) {
        this.problemReport = problemReport;
    }

    public ProblemReport getData() {
        return problemReport;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_yujing_layout;
    }

    @Override
    public EntityViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        ProblemReportItem.EntityViewHolder viewHolder = new EntityViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
        return viewHolder;
    }


    static class EntityViewHolder extends FlexibleViewHolder {
        TextView title, name, content, time, type;

        public EntityViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            title = (TextView) view.findViewById(R.id.title);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            type = (TextView) view.findViewById(R.id.type);
        }
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProblemReportItem.EntityViewHolder holder, int position, List payloads) {
        holder.title.setText(problemReport.getEVENT_NAME());
        holder.name.setText(problemReport.getEVENT_CONT());
        holder.time.setText(problemReport.getSTARTTIME());
        holder.content.setVisibility(View.GONE);
    }

}
