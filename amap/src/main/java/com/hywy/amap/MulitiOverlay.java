package com.hywy.amap;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;


/**
 * Created by charmingsoft on 2016/4/15.
 */
public class MulitiOverlay extends PoiOverlay{
    private BitmapDescriptor icon;
    public MulitiOverlay(Context context, AMap amap) {
        super(context,amap);
    }

    @Override
    protected BitmapDescriptor getBitmapDescriptor(int index) {
        return icon;
    }
    public void setIcon(int id){
        icon = BitmapDescriptorFactory.fromResource(id);
    }
}
