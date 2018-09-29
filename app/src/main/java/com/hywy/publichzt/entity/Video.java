package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/8/2.
 */

public class Video implements Parcelable {

    /**
     * REACH_CODE : PH002
     * LGTD : 116.137888
     * IPADDRESS : http://192.168.16.65:81/hzzglxt/video/slsp.mp4
     * VIDCD : 1
     * VIDNM : 站点1视频监控
     * REACH_NAME : 东淠河霍山段
     * VIDLC : 六安市金安区
     * VDSTATE : 1
     * ADCD : 341502000000
     * PEOPLE : 张三
     * TEL : 15712313211
     * LTTD : 31.604731
     * ADNM : 金安区
     */

    private String REACH_CODE;
    private String LGTD;
    private String IPADDRESS;
    private String VIDCD;
    private String VIDNM;
    private String REACH_NAME;
    private String VIDLC;
    private String VDSTATE;
    private String ADCD;
    private String PEOPLE;
    private String TEL;
    private String LTTD;
    private String ADNM;

    public String getREACH_CODE() {
        return REACH_CODE;
    }

    public void setREACH_CODE(String REACH_CODE) {
        this.REACH_CODE = REACH_CODE;
    }

    public String getLGTD() {
        return LGTD;
    }

    public void setLGTD(String LGTD) {
        this.LGTD = LGTD;
    }

    public String getIPADDRESS() {
        return IPADDRESS;
    }

    public void setIPADDRESS(String IPADDRESS) {
        this.IPADDRESS = IPADDRESS;
    }

    public String getVIDCD() {
        return VIDCD;
    }

    public void setVIDCD(String VIDCD) {
        this.VIDCD = VIDCD;
    }

    public String getVIDNM() {
        return VIDNM;
    }

    public void setVIDNM(String VIDNM) {
        this.VIDNM = VIDNM;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getVIDLC() {
        return VIDLC;
    }

    public void setVIDLC(String VIDLC) {
        this.VIDLC = VIDLC;
    }

    public String getVDSTATE() {
        return VDSTATE;
    }

    public void setVDSTATE(String VDSTATE) {
        this.VDSTATE = VDSTATE;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.REACH_CODE);
        dest.writeString(this.LGTD);
        dest.writeString(this.IPADDRESS);
        dest.writeString(this.VIDCD);
        dest.writeString(this.VIDNM);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.VIDLC);
        dest.writeString(this.VDSTATE);
        dest.writeString(this.ADCD);
        dest.writeString(this.PEOPLE);
        dest.writeString(this.TEL);
        dest.writeString(this.LTTD);
        dest.writeString(this.ADNM);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.REACH_CODE = in.readString();
        this.LGTD = in.readString();
        this.IPADDRESS = in.readString();
        this.VIDCD = in.readString();
        this.VIDNM = in.readString();
        this.REACH_NAME = in.readString();
        this.VIDLC = in.readString();
        this.VDSTATE = in.readString();
        this.ADCD = in.readString();
        this.PEOPLE = in.readString();
        this.TEL = in.readString();
        this.LTTD = in.readString();
        this.ADNM = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
