package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/7/17.
 */

public class ProblemReport implements Parcelable {
    public static final String DATA_NET = "net";
    public static final String DATA_LOCAL = "local";
    private long EVENT_ID;
    private int EVENT_TYPE_ID;
    private String REACH_CODE;
    private String ADCD;
    private String EVENT_NAME;
    private String STARTTIME;
    private String EVENT_CONT;
    private String TYPENAME;//事件类型
    private String ADNM;//所属区划
    private String REACH_NAME;//河段
    private List<AttachMent> PATROL_NOTE;
    private long LOG_ID;
    private String EVENT_LAT;
    private String EVENT_LOGN;

    public long getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(long LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    public String getEVENT_LAT() {
        return EVENT_LAT;
    }

    public void setEVENT_LAT(String EVENT_LAT) {
        this.EVENT_LAT = EVENT_LAT;
    }

    public String getEVENT_LOGN() {
        return EVENT_LOGN;
    }

    public void setEVENT_LOGN(String EVENT_LOGN) {
        this.EVENT_LOGN = EVENT_LOGN;
    }

    private String DATA_TYPE = DATA_NET;//数据类型，本地缓存数据/网络数据

    public int getEVENT_TYPE_ID() {
        return EVENT_TYPE_ID;
    }

    public void setEVENT_TYPE_ID(int EVENT_TYPE_ID) {
        this.EVENT_TYPE_ID = EVENT_TYPE_ID;
    }

    public String getREACH_CODE() {
        return REACH_CODE;
    }

    public void setREACH_CODE(String REACH_CODE) {
        this.REACH_CODE = REACH_CODE;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public long getEVENT_ID() {
        return EVENT_ID;
    }

    public void setEVENT_ID(long EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }

    public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String EVENT_NAME) {
        this.EVENT_NAME = EVENT_NAME;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getEVENT_CONT() {
        return EVENT_CONT;
    }

    public void setEVENT_CONT(String EVENT_CONT) {
        this.EVENT_CONT = EVENT_CONT;
    }

    public String getTYPENAME() {
        return TYPENAME;
    }

    public void setTYPENAME(String TYPENAME) {
        this.TYPENAME = TYPENAME;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public List<AttachMent> getPATROL_NOTE() {
        return PATROL_NOTE;
    }

    public void setPATROL_NOTE(List<AttachMent> PATROL_NOTE) {
        this.PATROL_NOTE = PATROL_NOTE;
    }

    public ProblemReport() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.EVENT_ID);
        dest.writeInt(this.EVENT_TYPE_ID);
        dest.writeString(this.REACH_CODE);
        dest.writeString(this.ADCD);
        dest.writeString(this.EVENT_NAME);
        dest.writeString(this.STARTTIME);
        dest.writeString(this.EVENT_CONT);
        dest.writeString(this.TYPENAME);
        dest.writeString(this.ADNM);
        dest.writeString(this.REACH_NAME);
        dest.writeTypedList(this.PATROL_NOTE);
        dest.writeLong(this.LOG_ID);
        dest.writeString(this.EVENT_LAT);
        dest.writeString(this.EVENT_LOGN);
        dest.writeString(this.DATA_TYPE);
    }

    protected ProblemReport(Parcel in) {
        this.EVENT_ID = in.readLong();
        this.EVENT_TYPE_ID = in.readInt();
        this.REACH_CODE = in.readString();
        this.ADCD = in.readString();
        this.EVENT_NAME = in.readString();
        this.STARTTIME = in.readString();
        this.EVENT_CONT = in.readString();
        this.TYPENAME = in.readString();
        this.ADNM = in.readString();
        this.REACH_NAME = in.readString();
        this.PATROL_NOTE = in.createTypedArrayList(AttachMent.CREATOR);
        this.LOG_ID = in.readLong();
        this.EVENT_LAT = in.readString();
        this.EVENT_LOGN = in.readString();
        this.DATA_TYPE = in.readString();
    }

    public static final Creator<ProblemReport> CREATOR = new Creator<ProblemReport>() {
        @Override
        public ProblemReport createFromParcel(Parcel source) {
            return new ProblemReport(source);
        }

        @Override
        public ProblemReport[] newArray(int size) {
            return new ProblemReport[size];
        }
    };
}
