package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Superman
 * @date 2018/6/29
 */
public class MapClassify implements Parcelable {

    /**
     * id : 18
     * text : 采砂区
     * parent : 16
     * iconSkin : caq
     */

    private String id;
    private String text;
    private String parent;
    private String iconSkin;
    private String LAYERS;
    private String TYPE;//0,请求接口  1，加载图层

//    private List<FeatureLayer> list;//用于存放图层列表

//    public List<FeatureLayer> getList() {
//        return list;
//    }
//
//    public void setList(List<FeatureLayer> list) {
//        this.list = list;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getLAYERS() {
        return LAYERS;
    }

    public void setLAYERS(String LAYERS) {
        this.LAYERS = LAYERS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public MapClassify() {
    }

    @Override
    public boolean equals(Object obj) {
        MapClassify mapClassify = (MapClassify) obj;
        return id.equals(mapClassify.getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeString(this.parent);
        dest.writeString(this.iconSkin);
        dest.writeString(this.LAYERS);
        dest.writeString(this.TYPE);
    }

    protected MapClassify(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        this.parent = in.readString();
        this.iconSkin = in.readString();
        this.LAYERS = in.readString();
        this.TYPE = in.readString();
    }

    public static final Creator<MapClassify> CREATOR = new Creator<MapClassify>() {
        @Override
        public MapClassify createFromParcel(Parcel source) {
            return new MapClassify(source);
        }

        @Override
        public MapClassify[] newArray(int size) {
            return new MapClassify[size];
        }
    };
}
