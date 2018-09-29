package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 巡查路线
 *
 * @author Superman
 * @date 2018/6/22
 */

public class PatrolRoute implements Parcelable {
    private int NUM;
    private double LGTD;//纵坐标
    private double LTTD;//横坐标

    public int getNUM() {
        return NUM;
    }

    public void setNUM(int NUM) {
        this.NUM = NUM;
    }

    public double getLGTD() {
        return LGTD;
    }

    public void setLGTD(double LGTD) {
        this.LGTD = LGTD;
    }

    public double getLTTD() {
        return LTTD;
    }

    public void setLTTD(double LTTD) {
        this.LTTD = LTTD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.NUM);
        dest.writeDouble(this.LGTD);
        dest.writeDouble(this.LTTD);
    }

    public PatrolRoute() {
    }

    protected PatrolRoute(Parcel in) {
        this.NUM = in.readInt();
        this.LGTD = in.readDouble();
        this.LTTD = in.readDouble();
    }

    public static final Parcelable.Creator<PatrolRoute> CREATOR = new Parcelable.Creator<PatrolRoute>() {
        @Override
        public PatrolRoute createFromParcel(Parcel source) {
            return new PatrolRoute(source);
        }

        @Override
        public PatrolRoute[] newArray(int size) {
            return new PatrolRoute[size];
        }
    };
}
