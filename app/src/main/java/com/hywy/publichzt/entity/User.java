package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/8/9.
 */

public class User implements Parcelable {
    private String USER_ID;
    private String NAME;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.USER_ID);
        dest.writeString(this.NAME);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.USER_ID = in.readString();
        this.NAME = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
