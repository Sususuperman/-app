package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Superman
 * @date 2018/6/21
 * 巡查日志
 */

public class Inspection implements Parcelable {
    private long LOG_ID;//日志ID;
    private String PATROL_LOG_NAME;//日志名称
    private String REACH_NAME;//河段
    private String ADNM;//行政区
    private String STARTTIME;//时间
    private String ENDTIME;//结束时间
    private List<AttachMent> PATROL_NOTE;//附件

    private String NAME;//巡查人名字
    private String PATROL_PLAN_NAME;//巡查计划
    private String OTHER_SITUATION;//其他情况
    private String DISPOSE_SITUATION;//处理情况

    /**********************************/
    private List<PatrolRoute> points;//实际巡查路线
    private List<PatrolRoute> planpoints;//计划巡查路线

    /**********************************/
    private List<Patrol> PATROL_SITUATION;//巡查情况列表

    public long getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(long LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    public String getPATROL_LOG_NAME() {
        return PATROL_LOG_NAME;
    }

    public void setPATROL_LOG_NAME(String PATROL_LOG_NAME) {
        this.PATROL_LOG_NAME = PATROL_LOG_NAME;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public void setSTARTTIME(String STARTTIME) {
        this.STARTTIME = STARTTIME;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(String ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public List<AttachMent> getPATROL_NOTE() {
        return PATROL_NOTE;
    }

    public void setPATROL_NOTE(List<AttachMent> PATROL_NOTE) {
        this.PATROL_NOTE = PATROL_NOTE;
    }

    public List<PatrolRoute> getPoints() {
        return points;
    }

    public void setPoints(List<PatrolRoute> points) {
        this.points = points;
    }

    public List<PatrolRoute> getPlanpoints() {
        return planpoints;
    }

    public void setPlanpoints(List<PatrolRoute> planpoints) {
        this.planpoints = planpoints;
    }

    public String getOTHER_SITUATION() {
        return OTHER_SITUATION;
    }

    public void setOTHER_SITUATION(String OTHER_SITUATION) {
        this.OTHER_SITUATION = OTHER_SITUATION;
    }

    public String getDISPOSE_SITUATION() {
        return DISPOSE_SITUATION;
    }

    public void setDISPOSE_SITUATION(String DISPOSE_SITUATION) {
        this.DISPOSE_SITUATION = DISPOSE_SITUATION;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPATROL_PLAN_NAME() {
        return PATROL_PLAN_NAME;
    }

    public void setPATROL_PLAN_NAME(String PATROL_PLAN_NAME) {
        this.PATROL_PLAN_NAME = PATROL_PLAN_NAME;
    }

    public List<Patrol> getPATROL_SITUATION() {
        return PATROL_SITUATION;
    }

    public void setPATROL_SITUATION(List<Patrol> PATROL_SITUATION) {
        this.PATROL_SITUATION = PATROL_SITUATION;
    }

    public Inspection() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.LOG_ID);
        dest.writeString(this.PATROL_LOG_NAME);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.ADNM);
        dest.writeString(this.STARTTIME);
        dest.writeString(this.ENDTIME);
        dest.writeTypedList(this.PATROL_NOTE);
        dest.writeString(this.NAME);
        dest.writeString(this.PATROL_PLAN_NAME);
        dest.writeString(this.OTHER_SITUATION);
        dest.writeString(this.DISPOSE_SITUATION);
        dest.writeTypedList(this.points);
        dest.writeTypedList(this.planpoints);
        dest.writeTypedList(this.PATROL_SITUATION);
    }

    protected Inspection(Parcel in) {
        this.LOG_ID = in.readLong();
        this.PATROL_LOG_NAME = in.readString();
        this.REACH_NAME = in.readString();
        this.ADNM = in.readString();
        this.STARTTIME = in.readString();
        this.ENDTIME = in.readString();
        this.PATROL_NOTE = in.createTypedArrayList(AttachMent.CREATOR);
        this.NAME = in.readString();
        this.PATROL_PLAN_NAME = in.readString();
        this.OTHER_SITUATION = in.readString();
        this.DISPOSE_SITUATION = in.readString();
        this.points = in.createTypedArrayList(PatrolRoute.CREATOR);
        this.planpoints = in.createTypedArrayList(PatrolRoute.CREATOR);
        this.PATROL_SITUATION = in.createTypedArrayList(Patrol.CREATOR);
    }

    public static final Creator<Inspection> CREATOR = new Creator<Inspection>() {
        @Override
        public Inspection createFromParcel(Parcel source) {
            return new Inspection(source);
        }

        @Override
        public Inspection[] newArray(int size) {
            return new Inspection[size];
        }
    };
}
