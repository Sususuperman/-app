package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Superman on 2018/8/21.
 */

public class News implements Parcelable {

    /**
     * IMG_URL : http://39.105.91.190:8083/RMS/uploadFiles/event/1526633734407.jpg
     * TM : 2018-08-13 15:00:47
     * ID : 234252
     * HTML_URL : www.baidu.com
     * CONTENT : 测试
     * NWES_NAME : 百度首页
     */

    private String IMG_URL;
    private String TM;
    private String ID;
    private String HTML_URL;
    private String CONTENT;
    private String NWES_NAME;

    public String getIMG_URL() {
        return IMG_URL;
    }

    public void setIMG_URL(String IMG_URL) {
        this.IMG_URL = IMG_URL;
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

    public String getHTML_URL() {
        return HTML_URL;
    }

    public void setHTML_URL(String HTML_URL) {
        this.HTML_URL = HTML_URL;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getNWES_NAME() {
        return NWES_NAME;
    }

    public void setNWES_NAME(String NWES_NAME) {
        this.NWES_NAME = NWES_NAME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.IMG_URL);
        dest.writeString(this.TM);
        dest.writeString(this.ID);
        dest.writeString(this.HTML_URL);
        dest.writeString(this.CONTENT);
        dest.writeString(this.NWES_NAME);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.IMG_URL = in.readString();
        this.TM = in.readString();
        this.ID = in.readString();
        this.HTML_URL = in.readString();
        this.CONTENT = in.readString();
        this.NWES_NAME = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
