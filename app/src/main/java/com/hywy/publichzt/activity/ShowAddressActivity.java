package com.hywy.publichzt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.cs.common.base.BaseToolbarActivity;
import com.hywy.amap.PoiItem;
import com.hywy.amap.SingleOverlay;
import com.hywy.publichzt.R;


public class ShowAddressActivity extends BaseToolbarActivity {
    public static final int request_address = 10032;
    private MapView mapView;
    private AMap aMap;
    private SingleOverlay mMyOverlay;//我的位置
    private SingleOverlay mPinOverlay;//大头针
    private Marker mPinMarker;//大头针对应的marker
    private double chooseLat;
    private double chooseLong;

    public static void startAction(Activity context, double lng, double lat) {
        Intent intent = new Intent(context, ShowAddressActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);
        updateTitle();
        init();
        initMap(savedInstanceState);
        initListener();

    }

    public void updateTitle() {
        setTitleBulider(new Bulider().backicon(R.drawable.ic_arrow_back_white_24dp).title("显示地标"));
    }

    private void init() {
        Intent intent = getIntent();
        chooseLat = intent.getDoubleExtra("lat", 0);
        chooseLong = intent.getDoubleExtra("lng", 0);
    }

    /**
     * 初始化地图
     */
    private void initMap(Bundle arg0) {
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(arg0);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mMyOverlay = new SingleOverlay(this, aMap);
        mMyOverlay.setIcon(R.drawable.icon_map_current_location);
        mPinOverlay = new SingleOverlay(this, aMap);
        mPinOverlay.setIcon(R.drawable.ic_map_insure_marker_checked);
    }

    private void initListener() {

        if (chooseLat != 0 && chooseLong != 0) {
            addPoiMrker(chooseLat, chooseLong);
        }
    }

    /**
     * 地图上显示当前用户的位置
     */
    private void addUserMarker(double lat, double lng) {
        PoiItem item = new PoiItem();
        item.setTitle("title");
        item.setSnippet("snippet");
        item.setLatLng(new LatLng(lat, lng));
        mMyOverlay.setPoi(item);
        mMyOverlay.addToMap();//显示我的位置
    }

    /**
     * 地图上显示大头针的位置
     */
    private void addPoiMrker(double lat, double lng) {
        PoiItem item = new PoiItem();
        item.setTitle("title");//title 和 snippet必须添加否则气泡不显示
        item.setSnippet("snippet");
        item.setLatLng(new LatLng(lat, lng));
        mPinOverlay.setPoi(item);//显示大头针位置
        mPinOverlay.addToMap();
        mPinMarker = mPinOverlay.getMyMarker();
        mPinMarker.setAnchor(0.5f, 1.27f);
        mPinOverlay.zoomToSpan();
        hidePoi();
    }

    private void hidePoi() {
        if (isHaveLocal()) {
            mPinOverlay.getMyMarker().setVisible(true);
            if (!mPinMarker.isInfoWindowShown())
                mPinMarker.showInfoWindow();
        }
    }

    /**
     * 判断是否已经定到位置
     *
     * @return
     */
    private boolean isHaveLocal() {
        return mMyOverlay.getMyMarker() != null && mMyOverlay.getMyMarker().getPosition() != null;
    }


    private AnimationDrawable animationDrawable;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        setMapviewDestory();
        super.onDestroy();
    }

    private void setMapviewDestory() {
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
