package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/7/3.
 */

public class River implements Parcelable {

    /**
     * RV_CODE : 001
     * REACH_CODE : PH001
     * REACH_NAME : 西淠河金寨段
     * WATER_TYPE : Ⅲ类
     * ADCD : 341524000000
     * child : []
     */
    private String RV_NAME;//河流名称
    private String RV_CODE;
    private String REACH_CODE;
    private String REACH_NAME;
    private String WATER_TYPE;
    private String ADCD;
    private List<River> child;

    public String getRV_NAME() {
        return RV_NAME;
    }

    public void setRV_NAME(String RV_NAME) {
        this.RV_NAME = RV_NAME;
    }

    public String getRV_CODE() {
        return RV_CODE;
    }

    public void setRV_CODE(String RV_CODE) {
        this.RV_CODE = RV_CODE;
    }

    public String getREACH_CODE() {
        return REACH_CODE;
    }

    public void setREACH_CODE(String REACH_CODE) {
        this.REACH_CODE = REACH_CODE;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getWATER_TYPE() {
        return WATER_TYPE;
    }

    public void setWATER_TYPE(String WATER_TYPE) {
        this.WATER_TYPE = WATER_TYPE;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public List<River> getChild() {
        return child;
    }

    public void setChild(List<River> child) {
        this.child = child;
    }

    public River() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.RV_NAME);
        dest.writeString(this.RV_CODE);
        dest.writeString(this.REACH_CODE);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.WATER_TYPE);
        dest.writeString(this.ADCD);
        dest.writeTypedList(this.child);
    }

    protected River(Parcel in) {
        this.RV_NAME = in.readString();
        this.RV_CODE = in.readString();
        this.REACH_CODE = in.readString();
        this.REACH_NAME = in.readString();
        this.WATER_TYPE = in.readString();
        this.ADCD = in.readString();
        this.child = in.createTypedArrayList(River.CREATOR);
    }

    public static final Creator<River> CREATOR = new Creator<River>() {
        @Override
        public River createFromParcel(Parcel source) {
            return new River(source);
        }

        @Override
        public River[] newArray(int size) {
            return new River[size];
        }
    };
}
