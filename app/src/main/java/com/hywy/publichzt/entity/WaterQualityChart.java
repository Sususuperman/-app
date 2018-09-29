package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 水质图表
 * <p>
 * Created by Superman on 2018/7/13.
 */

public class WaterQualityChart implements Parcelable {

    /**
     * D01 : 51.30
     * TM : 2018-06
     */

    private String AVG_TURB;//浊度
    private String AVG_PH;//ph
    private String AVG_WT;//温度
    private String AVG_COND;//电导率
    private String AVG_DOX;//溶解氧
    private String AVG_TP;//总磷
    private String AVG_F;//氟化物
    private String AVG_CODMN;//高锰酸盐
    private String SPT;//时间

    public void setAVG_TURB(String AVG_TURB) {
        this.AVG_TURB = AVG_TURB;
    }

    public String getAVG_TP() {
        return AVG_TP;
    }

    public void setAVG_TP(String AVG_TP) {
        this.AVG_TP = AVG_TP;
    }

    public String getAVG_F() {
        return AVG_F;
    }

    public void setAVG_F(String AVG_F) {
        this.AVG_F = AVG_F;
    }

    public String getAVG_CODMN() {
        return AVG_CODMN;
    }

    public void setAVG_CODMN(String AVG_CODMN) {
        this.AVG_CODMN = AVG_CODMN;
    }

    public String getAVG_TURB() {
        return AVG_TURB;
    }

    public void setAVG_TURE(String AVG_TURB) {
        this.AVG_TURB = AVG_TURB;
    }

    public String getAVG_PH() {
        return AVG_PH;
    }

    public void setAVG_PH(String AVG_PH) {
        this.AVG_PH = AVG_PH;
    }

    public String getAVG_WT() {
        return AVG_WT;
    }

    public void setAVG_WT(String AVG_WT) {
        this.AVG_WT = AVG_WT;
    }

    public String getAVG_COND() {
        return AVG_COND;
    }

    public void setAVG_COND(String AVG_COND) {
        this.AVG_COND = AVG_COND;
    }

    public String getAVG_DOX() {
        return AVG_DOX;
    }

    public void setAVG_DOX(String AVG_DOX) {
        this.AVG_DOX = AVG_DOX;
    }


    public String getSPT() {
        return SPT;
    }

    public void setSPT(String SPT) {
        this.SPT = SPT;
    }

    public WaterQualityChart() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AVG_TURB);
        dest.writeString(this.AVG_PH);
        dest.writeString(this.AVG_WT);
        dest.writeString(this.AVG_COND);
        dest.writeString(this.AVG_DOX);
        dest.writeString(this.AVG_TP);
        dest.writeString(this.AVG_F);
        dest.writeString(this.AVG_CODMN);
        dest.writeString(this.SPT);
    }

    protected WaterQualityChart(Parcel in) {
        this.AVG_TURB = in.readString();
        this.AVG_PH = in.readString();
        this.AVG_WT = in.readString();
        this.AVG_COND = in.readString();
        this.AVG_DOX = in.readString();
        this.AVG_TP = in.readString();
        this.AVG_F = in.readString();
        this.AVG_CODMN = in.readString();
        this.SPT = in.readString();
    }

    public static final Creator<WaterQualityChart> CREATOR = new Creator<WaterQualityChart>() {
        @Override
        public WaterQualityChart createFromParcel(Parcel source) {
            return new WaterQualityChart(source);
        }

        @Override
        public WaterQualityChart[] newArray(int size) {
            return new WaterQualityChart[size];
        }
    };
}
