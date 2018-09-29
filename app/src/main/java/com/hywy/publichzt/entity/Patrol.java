package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * "OPTIONS_NAME": "是否存在各类污水直排口，工业企业、污水处理设备是否有偷排、漏排及超标排放等环境违法行为",
 * "STATE": "1",
 * "OPTIONS_ID": "2"
 * <p>
 * Created by Superman on 2018/6/25.
 */

public class Patrol implements Parcelable {
    private String OPTIONS_NAME;
    private int STATE;
    private int OPTIONS_ID;

    public String getOPTIONS_NAME() {
        return OPTIONS_NAME;
    }

    public void setOPTIONS_NAME(String OPTIONS_NAME) {
        this.OPTIONS_NAME = OPTIONS_NAME;
    }

    public int getSTATE() {
        return STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public int getOPTIONS_ID() {
        return OPTIONS_ID;
    }

    public void setOPTIONS_ID(int OPTIONS_ID) {
        this.OPTIONS_ID = OPTIONS_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OPTIONS_NAME);
        dest.writeInt(this.STATE);
        dest.writeInt(this.OPTIONS_ID);
    }

    public Patrol() {
    }

    protected Patrol(Parcel in) {
        this.OPTIONS_NAME = in.readString();
        this.STATE = in.readInt();
        this.OPTIONS_ID = in.readInt();
    }

    public static final Parcelable.Creator<Patrol> CREATOR = new Parcelable.Creator<Patrol>() {
        @Override
        public Patrol createFromParcel(Parcel source) {
            return new Patrol(source);
        }

        @Override
        public Patrol[] newArray(int size) {
            return new Patrol[size];
        }
    };
}
