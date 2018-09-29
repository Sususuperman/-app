package com.hywy.amap;

import android.content.Context;


import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charmingsoft on 2016/4/15.
 */
public class SingleOverlay extends PoiOverlay{

    public SingleOverlay(Context context, AMap amap) {
        super(context,amap);
    }
    private Marker marker;
    private BitmapDescriptor icon;
    @Override
    protected BitmapDescriptor getBitmapDescriptor(int index) {
        return icon;
    }


    public void setIcon(int id){
        icon = BitmapDescriptorFactory.fromResource(id);
    }


    public void setPoi(PoiItem item){
        List<PoiItem> list = new ArrayList<PoiItem>();
        list.add(item);
        super.setPois(list);
    }


    /**
     * 获取某个我的marker点
     * @return
     */
    public Marker getMyMarker(){
        if(marker == null && getPoiMarks().size() > 0){
            marker = getPoiMarks().get(0);
        }
        return marker;
    }

}
