package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/7/10.
 */

public class Notify implements Parcelable {
    /**
     * INFOCONTENT : 2018年内完成对东淠河的所有污染治理工作
     * INFOTYPE : 通知公告
     * TM : 2018-03-22 16:40:22
     * ID : 1
     * INFO : 东淠河治理目标
     * INFOMAN : 咸辉
     */

    private String INFOCONTENT;
    private String INFOTYPE;
    private String TM;
    private String ID;
    private String INFO;
    private String INFOMAN;

    public String getINFOCONTENT() {
        return INFOCONTENT;
    }

    public void setINFOCONTENT(String INFOCONTENT) {
        this.INFOCONTENT = INFOCONTENT;
    }

    public String getINFOTYPE() {
        return INFOTYPE;
    }

    public void setINFOTYPE(String INFOTYPE) {
        this.INFOTYPE = INFOTYPE;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getINFOMAN() {
        return INFOMAN;
    }

    public void setINFOMAN(String INFOMAN) {
        this.INFOMAN = INFOMAN;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.INFOCONTENT);
        dest.writeString(this.INFOTYPE);
        dest.writeString(this.TM);
        dest.writeString(this.ID);
        dest.writeString(this.INFO);
        dest.writeString(this.INFOMAN);
    }

    public Notify() {
    }

    protected Notify(Parcel in) {
        this.INFOCONTENT = in.readString();
        this.INFOTYPE = in.readString();
        this.TM = in.readString();
        this.ID = in.readString();
        this.INFO = in.readString();
        this.INFOMAN = in.readString();
    }

    public static final Parcelable.Creator<Notify> CREATOR = new Parcelable.Creator<Notify>() {
        @Override
        public Notify createFromParcel(Parcel source) {
            return new Notify(source);
        }

        @Override
        public Notify[] newArray(int size) {
            return new Notify[size];
        }
    };
}
