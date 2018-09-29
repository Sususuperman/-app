package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 河道水文图表
 * <p>
 * Created by Superman on 2018/7/13.
 */

public class RiverCourseChart implements Parcelable {

    /**
     * D01 : 51.30
     * TM : 2018-06
     */

    private String Z03;
    private String Q03;
    private String TM;

    public String getZ03() {
        return Z03;
    }

    public void setZ03(String z03) {
        Z03 = z03;
    }

    public String getQ03() {
        return Q03;
    }

    public void setQ03(String q03) {
        Q03 = q03;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public RiverCourseChart() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Z03);
        dest.writeString(this.Q03);
        dest.writeString(this.TM);
    }

    protected RiverCourseChart(Parcel in) {
        this.Z03 = in.readString();
        this.Q03 = in.readString();
        this.TM = in.readString();
    }

    public static final Creator<RiverCourseChart> CREATOR = new Creator<RiverCourseChart>() {
        @Override
        public RiverCourseChart createFromParcel(Parcel source) {
            return new RiverCourseChart(source);
        }

        @Override
        public RiverCourseChart[] newArray(int size) {
            return new RiverCourseChart[size];
        }
    };
}
