package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/6/27.
 */

public class RiverCourse implements Parcelable {

    /**
     * Q : 3
     * STCD : 00000002
     * RVNM : 11
     * STNM : 东淠河测站
     * LGTD : 116.274296
     * TM : 2018-06-27 15:00:14
     * Z : 5.6
     * ADCD : 341522000000
     * LTTD : 31.433657
     * ADNM : 霍邱县
     * STLC : 六安市霍邱县
     */

    private int Q;
    private String STCD;//测站编码
    private String RVNM;//河流名称
    private String STNM;
    private String LGTD;
    private String TM;
    private double Z;
    private String ADCD;
    private String LTTD;
    private String ADNM;
    private String STLC;
    private String PEOPLE;//
    private String TEL;
    private String ADMAUTH;//管理机构

    public int getQ() {
        return Q;
    }

    public void setQ(int Q) {
        this.Q = Q;
    }

    public String getSTCD() {
        return STCD;
    }

    public void setSTCD(String STCD) {
        this.STCD = STCD;
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

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double Z) {
        this.Z = Z;
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

    public RiverCourse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Q);
        dest.writeString(this.STCD);
        dest.writeString(this.RVNM);
        dest.writeString(this.STNM);
        dest.writeString(this.LGTD);
        dest.writeString(this.TM);
        dest.writeDouble(this.Z);
        dest.writeString(this.ADCD);
        dest.writeString(this.LTTD);
        dest.writeString(this.ADNM);
        dest.writeString(this.STLC);
        dest.writeString(this.PEOPLE);
        dest.writeString(this.TEL);
        dest.writeString(this.ADMAUTH);
    }

    protected RiverCourse(Parcel in) {
        this.Q = in.readInt();
        this.STCD = in.readString();
        this.RVNM = in.readString();
        this.STNM = in.readString();
        this.LGTD = in.readString();
        this.TM = in.readString();
        this.Z = in.readDouble();
        this.ADCD = in.readString();
        this.LTTD = in.readString();
        this.ADNM = in.readString();
        this.STLC = in.readString();
        this.PEOPLE = in.readString();
        this.TEL = in.readString();
        this.ADMAUTH = in.readString();
    }

    public static final Creator<RiverCourse> CREATOR = new Creator<RiverCourse>() {
        @Override
        public RiverCourse createFromParcel(Parcel source) {
            return new RiverCourse(source);
        }

        @Override
        public RiverCourse[] newArray(int size) {
            return new RiverCourse[size];
        }
    };
}
