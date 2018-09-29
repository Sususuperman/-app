package com.hywy.publichzt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.adapter.BaseListAdapter;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Plan;


public class PlanListAdapter extends BaseListAdapter<Plan> {

    public PlanListAdapter(Context context) {
        super(context);
    }


    @Override
    protected View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent, final int position) {
        convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.address);
        Plan plan = getItem(position);
        if (plan.getPATROL_PLAN_NAME() != null) {
            name.setText(plan.getPATROL_PLAN_NAME());
        }

        return convertView;
    }
}
