package com.hywy.publichzt.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.common.adapter.BaseListAdapter;
import com.hywy.publichzt.R;
import com.hywy.publichzt.entity.Adnm;
import com.hywy.publichzt.entity.Complain;
import com.hywy.publichzt.entity.EventSupervise;
import com.hywy.publichzt.entity.River;
import com.hywy.publichzt.entity.User;

/**
 * @author Superman
 * @date 2018/7/9
 */

public class SpinnerListAdapter extends BaseListAdapter {
    public SpinnerListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getView(LayoutInflater layoutInflater, View convertView, ViewGroup parent, int position) {
        convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.address);
        name.setTextSize(15);
        name.setTextColor(ContextCompat.getColor(mContext, R.color.font_1));
        Object object = getItem(position);
        if (object instanceof Adnm) {
            Adnm adnm = (Adnm) object;
            if (adnm.getADNM() != null) {
                name.setText(adnm.getADNM());
            }
        } else if (object instanceof River) {
            River river = (River) object;
            if (river.getREACH_NAME() != null) {
                name.setText(river.getREACH_NAME());
            }
        } else if (object instanceof User) {
            User user = (User) object;
            if (user.getNAME() != null) {
                name.setText(user.getNAME());
            }
        } else if(object instanceof EventSupervise){
            EventSupervise event = (EventSupervise) object;
            if (event.getEVENT_TYPE_NAME() != null) {
                name.setText(event.getEVENT_TYPE_NAME());
            }
        }else{
            Complain complain = (Complain) object;
            if (complain.getTYPE() != null) {
                name.setText(complain.getTYPE());
            }
        }

        return convertView;
    }
}
