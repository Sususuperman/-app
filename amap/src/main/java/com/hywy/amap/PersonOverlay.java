package com.hywy.amap;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;


/**
 * Created by charmingsoft on 2016/4/15.
 */
public class PersonOverlay extends PoiOverlay{

    public PersonOverlay(Context context, AMap amap) {
        super(context,amap);
    }

    @Override
    protected BitmapDescriptor getBitmapDescriptor(int index) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_person_pin_circle_black_24dp));
        return icon;
    }

}
