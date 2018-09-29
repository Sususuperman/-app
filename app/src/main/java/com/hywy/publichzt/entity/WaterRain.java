package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Superman
 * @date 2018/6/26
 */

public class WaterRain implements Parcelable {

    /**
     * DRP : 1.6
     * STCD : 10001
     * STNM : 润河集雨量站
     * LGTD : 116.092689
     * ADMAUTH : ceshi
     * ADCD : 341500000000
     * PEOPLE : 1
     * TEL : 1
     * LTTD : 32.505224
     * ADNM : 六安市
     */

    private double DRP;
    private String STCD;
    private String STNM;
    private String LGTD;
    private String ADMAUTH;
    private String ADCD;
    private String PEOPLE;
    private String TEL;
    private String LTTD;
    private String ADNM;
    private String RVNM;//河流名称
    private String STLC;//站点地址

    public String getSTLC() {
        return STLC;
    }

    public void setSTLC(String STLC) {
        this.STLC = STLC;
    }

    public String getRVNM() {
        return RVNM;
    }

    public void setRVNM(String RVNM) {
        this.RVNM = RVNM;
    }

    public double getDRP() {
        return DRP;
    }

    public void setDRP(double DRP) {
        this.DRP = DRP;
    }

    public String getSTCD() {
        return STCD;
    }

    public void setSTCD(String STCD) {
        this.STCD = STCD;
    }

    public String getSTNM() {
        return STNM;
    }

    public void setSTNM(String STNM) {
        this.STNM = STNM;
    }

    public String getLGTD() {
        return LGTD;
    }

    public void setLGTD(String LGTD) {
        this.LGTD = LGTD;
    }

    public String getADMAUTH() {
        return ADMAUTH;
    }

    public void setADMAUTH(String ADMAUTH) {
        this.ADMAUTH = ADMAUTH;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public String getPEOPLE() {
        return PEOPLE;
    }

    public void setPEOPLE(String PEOPLE) {
        this.PEOPLE = PEOPLE;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getLTTD() {
        return LTTD;
    }

    public void setLTTD(String LTTD) {
        this.LTTD = LTTD;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

    public WaterRain() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.DRP);
        dest.writeString(this.STCD);
        dest.writeString(this.STNM);
        dest.writeString(this.LGTD);
        dest.writeString(this.ADMAUTH);
        dest.writeString(this.ADCD);
        dest.writeString(this.PEOPLE);
        dest.writeString(this.TEL);
        dest.writeString(this.LTTD);
        dest.writeString(this.ADNM);
        dest.writeString(this.RVNM);
        dest.writeString(this.STLC);
    }

    protected WaterRain(Parcel in) {
        this.DRP = in.readDouble();
        this.STCD = in.readString();
        this.STNM = in.readString();
        this.LGTD = in.readString();
        this.ADMAUTH = in.readString();
        this.ADCD = in.readString();
        this.PEOPLE = in.readString();
        this.TEL = in.readString();
        this.LTTD = in.readString();
        this.ADNM = in.readString();
        this.RVNM = in.readString();
        this.STLC = in.readString();
    }

    public static final Creator<WaterRain> CREATOR = new Creator<WaterRain>() {
        @Override
        public WaterRain createFromParcel(Parcel source) {
            return new WaterRain(source);
        }

        @Override
        public WaterRain[] newArray(int size) {
            return new WaterRain[size];
        }
    };
}
