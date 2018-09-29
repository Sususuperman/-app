package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/6/29.
 */

public class EventSupervise implements Parcelable {

    /**
     * MSG : NULL
     * EVENT_TYPE_ID : 3
     * REACH_CODE : PH002
     * EVENT_TYPE_NAME : 遥感监测
     * ENDTIME : 2018-06-27 10:00:00
     * EVENT_NAME : 测试
     * EVENT_CONT : 123
     * DEALTIME : NULL
     * BJBS : 1,1
     * STATE : 2
     * DEAL_IDEA : NULL
     * PER_ID : 1
     * DEAL_ID : fdcb2bf874384ac5a4bb2e29c6b4dbc7
     * DEAL_STATE : 2
     * STARTTIME : 2018-06-26 10:00:00
     * ISBACK : 1
     * REACH_NAME : 东淠河霍山段
     * SEND_PER_ID : 1
     * PER_NM : 超级管理员
     */

    private int SUBTYPES_ID;//类型id
    private String SUBTYPES_NAME;//类型名称

    public int getSUBTYPES_ID() {
        return SUBTYPES_ID;
    }

    public void setSUBTYPES_ID(int SUBTYPES_ID) {
        this.SUBTYPES_ID = SUBTYPES_ID;
    }

    public String getSUBTYPES_NAME() {
        return SUBTYPES_NAME;
    }

    public void setSUBTYPES_NAME(String SUBTYPES_NAME) {
        this.SUBTYPES_NAME = SUBTYPES_NAME;
    }

    /********************************************************************************/
    private String MSG;
    private String EVENT_ID;
    private int EVENT_TYPE_ID;
    private String REACH_CODE;
    private String EVENT_TYPE_NAME;
    private String ENDTIME;
    private String EVENT_NAME;
    private String EVENT_CONT;
    private String DEALTIME;
    private String BJBS;
    private String STATE;
    private String DEAL_IDEA;//0 待办，1,办结，2 在办
    private String PER_ID;
    private String DEAL_ID;
    private String DEAL_STATE;//事件状态
    private String STARTTIME;
    private String ISBACK;
    private String REACH_NAME;
    private String SEND_PER_ID;
    private String PER_NM;
    private List<Deal> deals;
    private List<AttachMent> dealattch;//附件列表
    private List<AttachMent> countattch;//内容附件列表

    public List<AttachMent> getCountattch() {
        return countattch;
    }

    public void setCountattch(List<AttachMent> countattch) {
        this.countattch = countattch;
    }

    public String getEVENT_ID() {
        return EVENT_ID;
    }

    public void setEVENT_ID(String EVENT_ID) {
        this.EVENT_ID = EVENT_ID;
    }

    public List<AttachMent> getDealattch() {
        return dealattch;
    }

    public void setDealattch(List<AttachMent> dealattch) {
        this.dealattch = dealattch;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

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

    public String getEVENT_TYPE_NAME() {
        return EVENT_TYPE_NAME;
    }

    public void setEVENT_TYPE_NAME(String EVENT_TYPE_NAME) {
        this.EVENT_TYPE_NAME = EVENT_TYPE_NAME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String EVENT_NAME) {
        this.EVENT_NAME = EVENT_NAME;
    }

    public String getEVENT_CONT() {
        return EVENT_CONT;
    }

    public void setEVENT_CONT(String EVENT_CONT) {
        this.EVENT_CONT = EVENT_CONT;
    }

    public String getDEALTIME() {
        return DEALTIME;
    }

    public void setDEALTIME(String DEALTIME) {
        this.DEALTIME = DEALTIME;
    }

    public String getBJBS() {
        return BJBS;
    }

    public void setBJBS(String BJBS) {
        this.BJBS = BJBS;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getDEAL_IDEA() {
        return DEAL_IDEA;
    }

    public void setDEAL_IDEA(String DEAL_IDEA) {
        this.DEAL_IDEA = DEAL_IDEA;
    }

    public String getPER_ID() {
        return PER_ID;
    }

    public void setPER_ID(String PER_ID) {
        this.PER_ID = PER_ID;
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

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getISBACK() {
        return ISBACK;
    }

    public void setISBACK(String ISBACK) {
        this.ISBACK = ISBACK;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getSEND_PER_ID() {
        return SEND_PER_ID;
    }

    public void setSEND_PER_ID(String SEND_PER_ID) {
        this.SEND_PER_ID = SEND_PER_ID;
    }

    public String getPER_NM() {
        return PER_NM;
    }

    public void setPER_NM(String PER_NM) {
        this.PER_NM = PER_NM;
    }

    public EventSupervise() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.SUBTYPES_ID);
        dest.writeString(this.SUBTYPES_NAME);
        dest.writeString(this.MSG);
        dest.writeString(this.EVENT_ID);
        dest.writeInt(this.EVENT_TYPE_ID);
        dest.writeString(this.REACH_CODE);
        dest.writeString(this.EVENT_TYPE_NAME);
        dest.writeString(this.ENDTIME);
        dest.writeString(this.EVENT_NAME);
        dest.writeString(this.EVENT_CONT);
        dest.writeString(this.DEALTIME);
        dest.writeString(this.BJBS);
        dest.writeString(this.STATE);
        dest.writeString(this.DEAL_IDEA);
        dest.writeString(this.PER_ID);
        dest.writeString(this.DEAL_ID);
        dest.writeString(this.DEAL_STATE);
        dest.writeString(this.STARTTIME);
        dest.writeString(this.ISBACK);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.SEND_PER_ID);
        dest.writeString(this.PER_NM);
        dest.writeTypedList(this.deals);
        dest.writeTypedList(this.dealattch);
        dest.writeTypedList(this.countattch);
    }

    protected EventSupervise(Parcel in) {
        this.SUBTYPES_ID = in.readInt();
        this.SUBTYPES_NAME = in.readString();
        this.MSG = in.readString();
        this.EVENT_ID = in.readString();
        this.EVENT_TYPE_ID = in.readInt();
        this.REACH_CODE = in.readString();
        this.EVENT_TYPE_NAME = in.readString();
        this.ENDTIME = in.readString();
        this.EVENT_NAME = in.readString();
        this.EVENT_CONT = in.readString();
        this.DEALTIME = in.readString();
        this.BJBS = in.readString();
        this.STATE = in.readString();
        this.DEAL_IDEA = in.readString();
        this.PER_ID = in.readString();
        this.DEAL_ID = in.readString();
        this.DEAL_STATE = in.readString();
        this.STARTTIME = in.readString();
        this.ISBACK = in.readString();
        this.REACH_NAME = in.readString();
        this.SEND_PER_ID = in.readString();
        this.PER_NM = in.readString();
        this.deals = in.createTypedArrayList(Deal.CREATOR);
        this.dealattch = in.createTypedArrayList(AttachMent.CREATOR);
        this.countattch = in.createTypedArrayList(AttachMent.CREATOR);
    }

    public static final Creator<EventSupervise> CREATOR = new Creator<EventSupervise>() {
        @Override
        public EventSupervise createFromParcel(Parcel source) {
            return new EventSupervise(source);
        }

        @Override
        public EventSupervise[] newArray(int size) {
            return new EventSupervise[size];
        }
    };
}
