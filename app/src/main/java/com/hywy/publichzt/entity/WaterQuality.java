package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 水质监测
 * Created by Superman on 2018/6/26.
 */

public class WaterQuality implements Parcelable {
    /**
     * STCD : 40061
     * CHLA : 37.373
     * RVNM : PH001
     * STNM : 淠河东干渠平桥站
     * LGTD : 116.464914
     * DOX : 1.89
     * SPT : 2018-05-23 09:59:59
     * TURB : 80.214
     * PH : 7.765
     * ADCD : 341503000000
     * LTTD : 31.758412
     * ADNM : 裕安区
     * WT : 25.94
     * COND : 298.355
     * BGA : 1628.509
     * STLC : 六安市裕安区平桥乡
     */

    private String STCD;
    private double CHLA;
    private String RVNM;
    private String STNM;
    private String LGTD;
    private double DOX;//溶解
    private String SPT;//总磷
    private double TURB;//浊度
    private double PH;//ph
    private String ADCD;
    private String LTTD;
    private String ADNM;
    private double WT;
    private double COND;//电导率
    private double BGA;
    private String STLC;
    private String PEOPLE;//
    private String TEL;
    private String ADMAUTH;//管理机构
    private String TP;//总磷
    private String F;//氟化物
    private String CODMN;// 高锰酸钾指数
    private String LEVEL_CODE;//河流类别

    public String getLEVEL_CODE() {
        return LEVEL_CODE;
    }

    public void setLEVEL_CODE(String LEVEL_CODE) {
        this.LEVEL_CODE = LEVEL_CODE;
    }

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getCODMN() {
        return CODMN;
    }

    public void setCODMN(String CODMN) {
        this.CODMN = CODMN;
    }

    public String getSTCD() {
        return STCD;
    }

    public void setSTCD(String STCD) {
        this.STCD = STCD;
    }

    public double getCHLA() {
        return CHLA;
    }

    public void setCHLA(double CHLA) {
        this.CHLA = CHLA;
    }

    public String getRVNM() {
        return RVNM;
    }

    public void setRVNM(String RVNM) {
        this.RVNM = RVNM;
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

    public double getDOX() {
        return DOX;
    }

    public void setDOX(double DOX) {
        this.DOX = DOX;
    }

    public String getSPT() {
        return SPT;
    }

    public void setSPT(String SPT) {
        this.SPT = SPT;
    }

    public double getTURB() {
        return TURB;
    }

    public void setTURB(double TURB) {
        this.TURB = TURB;
    }

    public double getPH() {
        return PH;
    }

    public void setPH(double PH) {
        this.PH = PH;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
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

    public double getWT() {
        return WT;
    }

    public void setWT(double WT) {
        this.WT = WT;
    }

    public double getCOND() {
        return COND;
    }

    public void setCOND(double COND) {
        this.COND = COND;
    }

    public double getBGA() {
        return BGA;
    }

    public void setBGA(double BGA) {
        this.BGA = BGA;
    }

    public String getSTLC() {
        return STLC;
    }

    public void setSTLC(String STLC) {
        this.STLC = STLC;
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

    public String getADMAUTH() {
        return ADMAUTH;
    }

    public void setADMAUTH(String ADMAUTH) {
        this.ADMAUTH = ADMAUTH;
    }

    public WaterQuality() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.STCD);
        dest.writeDouble(this.CHLA);
        dest.writeString(this.RVNM);
        dest.writeString(this.STNM);
        dest.writeString(this.LGTD);
        dest.writeDouble(this.DOX);
        dest.writeString(this.SPT);
        dest.writeDouble(this.TURB);
        dest.writeDouble(this.PH);
        dest.writeString(this.ADCD);
        dest.writeString(this.LTTD);
        dest.writeString(this.ADNM);
        dest.writeDouble(this.WT);
        dest.writeDouble(this.COND);
        dest.writeDouble(this.BGA);
        dest.writeString(this.STLC);
        dest.writeString(this.PEOPLE);
        dest.writeString(this.TEL);
        dest.writeString(this.ADMAUTH);
        dest.writeString(this.TP);
        dest.writeString(this.F);
        dest.writeString(this.CODMN);
        dest.writeString(this.LEVEL_CODE);
    }

    protected WaterQuality(Parcel in) {
        this.STCD = in.readString();
        this.CHLA = in.readDouble();
        this.RVNM = in.readString();
        this.STNM = in.readString();
        this.LGTD = in.readString();
        this.DOX = in.readDouble();
        this.SPT = in.readString();
        this.TURB = in.readDouble();
        this.PH = in.readDouble();
        this.ADCD = in.readString();
        this.LTTD = in.readString();
        this.ADNM = in.readString();
        this.WT = in.readDouble();
        this.COND = in.readDouble();
        this.BGA = in.readDouble();
        this.STLC = in.readString();
        this.PEOPLE = in.readString();
        this.TEL = in.readString();
        this.ADMAUTH = in.readString();
        this.TP = in.readString();
        this.F = in.readString();
        this.CODMN = in.readString();
        this.LEVEL_CODE = in.readString();
    }

    public static final Creator<WaterQuality> CREATOR = new Creator<WaterQuality>() {
        @Override
        public WaterQuality createFromParcel(Parcel source) {
            return new WaterQuality(source);
        }

        @Override
        public WaterQuality[] newArray(int size) {
            return new WaterQuality[size];
        }
    };
}
