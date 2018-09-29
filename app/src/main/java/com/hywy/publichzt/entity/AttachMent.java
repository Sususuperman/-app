package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * "ID": 85,
 * "PATROL_ID": "1.52965619E9",
 * "ATTACH_URL": "uploadFiles/patrol/1529656175267.JPEG",
 * "ATTACH_NAME": "1529656175267.JPEG"
 *
 * @author Superman
 * @date 2018/6/25
 */

public class AttachMent implements Parcelable {
    public static final int UPLOAD_ED = 1;//已经上传
    public static final int UPLOAD_UN = 0;//未上传

    private int ID;
    private String PATROL_ID;
    private String ATTACH_URL;
    private String ATTACH_NAME;
    private int status;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPATROL_ID() {
        return PATROL_ID;
    }

    public void setPATROL_ID(String PATROL_ID) {
        this.PATROL_ID = PATROL_ID;
    }

    public String getATTACH_URL() {
        return ATTACH_URL;
    }

    public void setATTACH_URL(String ATTACH_URL) {
        this.ATTACH_URL = ATTACH_URL;
    }

    public String getATTACH_NAME() {
        return ATTACH_NAME;
    }

    public void setATTACH_NAME(String ATTACH_NAME) {
        this.ATTACH_NAME = ATTACH_NAME;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AttachMent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.PATROL_ID);
        dest.writeString(this.ATTACH_URL);
        dest.writeString(this.ATTACH_NAME);
        dest.writeInt(this.status);
    }

    protected AttachMent(Parcel in) {
        this.ID = in.readInt();
        this.PATROL_ID = in.readString();
        this.ATTACH_URL = in.readString();
        this.ATTACH_NAME = in.readString();
        this.status = in.readInt();
    }

    public static final Creator<AttachMent> CREATOR = new Creator<AttachMent>() {
        @Override
        public AttachMent createFromParcel(Parcel source) {
            return new AttachMent(source);
        }

        @Override
        public AttachMent[] newArray(int size) {
            return new AttachMent[size];
        }
    };
}
