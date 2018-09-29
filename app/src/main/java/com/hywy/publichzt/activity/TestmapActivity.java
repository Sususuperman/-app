package com.hywy.publichzt.activity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.cs.common.base.BaseActivity;
import com.hywy.publichzt.R;

import butterknife.Bind;

public class TestmapActivity extends BaseActivity {
    @Bind(R.id.mapView)
    MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testmap);
        initMap(savedInstanceState);
    }

    /**
     * 初始化地图
     */
    private void initMap(Bundle arg0) {

        mapView.onCreate(arg0);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
//        //设置地图的放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        //地图缩放按钮,true为显示，false为隐藏
        aMap.getUiSettings().setZoomControlsEnabled(false);
    }

}
