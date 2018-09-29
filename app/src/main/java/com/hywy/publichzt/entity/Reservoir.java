package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 水库水位监测
 * Created by Superman on 2018/6/26.
 */

public class Reservoir implements Parcelable {

    /**
     * STCD : 00000001
     * STNM : 西淠河测站
     * RZ : 100
     * LGTD : 116.137888
     * INQ : 1
     * OTQ : 100
     * W : 1
     * TM : 2018-05-21 17:55:00
     * ADCD : 341502000000
     * LTTD : 31.604731
     * DTM : 2018-04-09 10:16:51
     * ADNM : 金安区
     * STLC : 六安市金安区
     */

    private String STCD;
    private String STNM;//站名
    private String RZ;
    private String LGTD;
    private String INQ;
    private String OTQ;
    private String W;
    private String TM;//上报时间
    private String ADCD;
    private String LTTD;
    private String DTM;//入库时间
    private String ADNM;
    private String STLC;//行政区带市
    private String RVNM;//河道名称
    private String PEOPLE;//
    private String TEL;
    private String ADMAUTH;//管理机构

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

    public String getRZ() {
        return RZ;
    }

    public void setRZ(String RZ) {
        this.RZ = RZ;
    }

    public String getLGTD() {
        return LGTD;
    }

    public void setLGTD(String LGTD) {
        this.LGTD = LGTD;
    }

    public String getINQ() {
        return INQ;
    }

    public void setINQ(String INQ) {
        this.INQ = INQ;
    }

    public String getOTQ() {
        return OTQ;
    }

    public void setOTQ(String OTQ) {
        this.OTQ = OTQ;
    }

    public String getW() {
        return W;
    }

    public void setW(String W) {
        this.W = W;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
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

    public String getDTM() {
        return DTM;
    }

    public void setDTM(String DTM) {
        this.DTM = DTM;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

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

    public Reservoir() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.STCD);
        dest.writeString(this.STNM);
        dest.writeString(this.RZ);
        dest.writeString(this.LGTD);
        dest.writeString(this.INQ);
        dest.writeString(this.OTQ);
        dest.writeString(this.W);
        dest.writeString(this.TM);
        dest.writeString(this.ADCD);
        dest.writeString(this.LTTD);
        dest.writeString(this.DTM);
        dest.writeString(this.ADNM);
        dest.writeString(this.STLC);
        dest.writeString(this.RVNM);
        dest.writeString(this.PEOPLE);
        dest.writeString(this.TEL);
        dest.writeString(this.ADMAUTH);
    }

    protected Reservoir(Parcel in) {
        this.STCD = in.readString();
        this.STNM = in.readString();
        this.RZ = in.readString();
        this.LGTD = in.readString();
        this.INQ = in.readString();
        this.OTQ = in.readString();
        this.W = in.readString();
        this.TM = in.readString();
        this.ADCD = in.readString();
        this.LTTD = in.readString();
        this.DTM = in.readString();
        this.ADNM = in.readString();
        this.STLC = in.readString();
        this.RVNM = in.readString();
        this.PEOPLE = in.readString();
        this.TEL = in.readString();
        this.ADMAUTH = in.readString();
    }

    public static final Creator<Reservoir> CREATOR = new Creator<Reservoir>() {
        @Override
        public Reservoir createFromParcel(Parcel source) {
            return new Reservoir(source);
        }

        @Override
        public Reservoir[] newArray(int size) {
            return new Reservoir[size];
        }
    };
}
