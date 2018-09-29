package com.hywy.amap;


import com.amap.api.maps.model.LatLng;

/**
 * Created by charmingsoft on 2016/3/28.
 */
public class PoiItem {
//    private double lat;//未读
//    private double lng;//经度
//    private double alt;//高度
    public static final int TYPE_NO_CLEAR = -1;//不清除的标志
    private LatLng latLng;
    private int id;
    private Object data;//
    private int type;
    private String title;
    private String snippet;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
