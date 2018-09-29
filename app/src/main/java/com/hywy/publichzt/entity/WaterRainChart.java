package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 水雨情图表
 * <p>
 * Created by Superman on 2018/7/13.
 */

public class WaterRainChart implements Parcelable {

    /**
     * D01 : 51.30
     * TM : 2018-06
     */

    private String D01;
    private String TM;

    public String getD01() {
        return D01;
    }

    public void setD01(String D01) {
        this.D01 = D01;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.D01);
        dest.writeString(this.TM);
    }

    public WaterRainChart() {
    }

    protected WaterRainChart(Parcel in) {
        this.D01 = in.readString();
        this.TM = in.readString();
    }

    public static final Parcelable.Creator<WaterRainChart> CREATOR = new Parcelable.Creator<WaterRainChart>() {
        @Override
        public WaterRainChart createFromParcel(Parcel source) {
            return new WaterRainChart(source);
        }

        @Override
        public WaterRainChart[] newArray(int size) {
            return new WaterRainChart[size];
        }
    };
}
