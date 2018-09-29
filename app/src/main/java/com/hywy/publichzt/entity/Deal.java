package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/7/2.
 */

public class Deal implements Parcelable {
    /**
     * DEAL_IDEA :
     * STM : 2018-06-26 16:39:12
     * NAME : 超级管理员
     * DEAL_ID : 08dba00bc8004de68384e185427055f7
     * DEAL_STATE : 1
     * TM : 2018-06-29 16:59:05
     * EVENT_ID : 040816
     * DEAL_PER_ID : 1
     * PER_LEV : 市级总河长
     * HTML_URL : http://192.168.16.65:81/hzzglxt/headImg/1530258506586.JPEG
     * ADNM : 六安市
     * PER_TypeName : 市级总河长
     * IMAGE_URL : 1530258506586.JPEG
     */

    private String DEAL_IDEA;
    private String STM;
    private String NAME;
    private String DEAL_ID;
    private String DEAL_STATE;
    private String TM;
    private String EVENT_ID;
    private String DEAL_PER_ID;
    private String PER_LEV;
    private String HTML_URL;
    private String ADNM;
    private String PER_TypeName;
    private String IMAGE_URL;
    private List<AttachMent> dealattch;

    public List<AttachMent> getDealattch() {
        return dealattch;
    }

    public void setDealattch(List<AttachMent> dealattch) {
        this.dealattch = dealattch;
    }

    public String getDEAL_IDEA() {
        return DEAL_IDEA;
    }

    public void setDEAL_IDEA(String DEAL_IDEA) {
        this.DEAL_IDEA = DEAL_IDEA;
    }

    public String getSTM() {
        return STM;
    }

    public void setSTM(String STM) {
        this.STM = STM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDEAL_ID() {
        return DEAL_ID;
    }

    public void setDEAL_ID(String DEAL_ID) {
        this.DEAL_ID = DEAL_ID;
    }

    public String getDEAL_STATE() {
        return DEAL_STATE;
    }

    public void setDEAL_STATE(String DEAL_STATE) {
        this.DEAL_STATE = DEAL_STATE;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public String getEVENT_ID() {
        return EVENT_ID;
    }

    public void setEVENT_ID(String EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }

    public String getDEAL_PER_ID() {
        return DEAL_PER_ID;
    }

    public void setDEAL_PER_ID(String DEAL_PER_ID) {
        this.DEAL_PER_ID = DEAL_PER_ID;
    }

    public String getPER_LEV() {
        return PER_LEV;
    }

    public void setPER_LEV(String PER_LEV) {
        this.PER_LEV = PER_LEV;
    }

    public String getHTML_URL() {
        return HTML_URL;
    }

    public void setHTML_URL(String HTML_URL) {
        this.HTML_URL = HTML_URL;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

    public String getPER_TypeName() {
        return PER_TypeName;
    }

    public void setPER_TypeName(String PER_TypeName) {
        this.PER_TypeName = PER_TypeName;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public Deal() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.DEAL_IDEA);
        dest.writeString(this.STM);
        dest.writeString(this.NAME);
        dest.writeString(this.DEAL_ID);
        dest.writeString(this.DEAL_STATE);
        dest.writeString(this.TM);
        dest.writeString(this.EVENT_ID);
        dest.writeString(this.DEAL_PER_ID);
        dest.writeString(this.PER_LEV);
        dest.writeString(this.HTML_URL);
        dest.writeString(this.ADNM);
        dest.writeString(this.PER_TypeName);
        dest.writeString(this.IMAGE_URL);
        dest.writeTypedList(this.dealattch);
    }

    protected Deal(Parcel in) {
        this.DEAL_IDEA = in.readString();
        this.STM = in.readString();
        this.NAME = in.readString();
        this.DEAL_ID = in.readString();
        this.DEAL_STATE = in.readString();
        this.TM = in.readString();
        this.EVENT_ID = in.readString();
        this.DEAL_PER_ID = in.readString();
        this.PER_LEV = in.readString();
        this.HTML_URL = in.readString();
        this.ADNM = in.readString();
        this.PER_TypeName = in.readString();
        this.IMAGE_URL = in.readString();
        this.dealattch = in.createTypedArrayList(AttachMent.CREATOR);
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel source) {
            return new Deal(source);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };
}
