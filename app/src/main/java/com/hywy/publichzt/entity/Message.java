package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/8/1.
 */

public class Message implements Parcelable {

    /**
     * PER_NAME : 汪龙照
     * USER_ID : 1
     * TM : 2018-07-23 18:12:34
     * STATE : 0
     * ID : 5e113de68a424303af6f35022379c61a
     * PER_ID : 10
     * USER_NAME : 超级管理员
     * CONT : 红魔
     */

    private String PER_NAME;
    private String USER_ID;
    private String TM;
    private String STATE;
    private String ID;
    private String PER_ID;
    private String USER_NAME;
    private String CONT;

    public String getPER_NAME() {
        return PER_NAME;
    }

    public void setPER_NAME(String PER_NAME) {
        this.PER_NAME = PER_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getTM() {
        return TM;
    }

    public void setTM(String TM) {
        this.TM = TM;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPER_ID() {
        return PER_ID;
    }

    public void setPER_ID(String PER_ID) {
        this.PER_ID = PER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getCONT() {
        return CONT;
    }

    public void setCONT(String CONT) {
        this.CONT = CONT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PER_NAME);
        dest.writeString(this.USER_ID);
        dest.writeString(this.TM);
        dest.writeString(this.STATE);
        dest.writeString(this.ID);
        dest.writeString(this.PER_ID);
        dest.writeString(this.USER_NAME);
        dest.writeString(this.CONT);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.PER_NAME = in.readString();
        this.USER_ID = in.readString();
        this.TM = in.readString();
        this.STATE = in.readString();
        this.ID = in.readString();
        this.PER_ID = in.readString();
        this.USER_NAME = in.readString();
        this.CONT = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
