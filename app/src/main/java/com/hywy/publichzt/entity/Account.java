package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by weifei on 17/1/9.
 */

public class Account implements Parcelable {

    /**
     * userID : 24880776197767172
     * token : e440f84e80ce398d2877e366bf82f815
     * userName : 常遇春
     * phone : 13910555041
     * status : 1
     * type : 1
     * organID : 24880776197767172
     * organMemberType : 2147483647
     * createdDate : 1483009827
     * changedDate : 1483009827
     */

    private String userId;
    private String userName;//账号
    private String PHONE;
    private String ROLE_NAME;//角色名称
    private String EMAIL;
    private String HOMEROLE;
    private String NAME;//姓名
    private String loginName;
    private String ADCD;
    private String ADNM;
    private String password;
    private String JSESSIONID;
    private long last_login_time;
    private String urlData;//头像

    public String getUrlData() {
        return urlData;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getROLE_NAME() {
        return ROLE_NAME;
    }

    public void setROLE_NAME(String ROLE_NAME) {
        this.ROLE_NAME = ROLE_NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getHOMEROLE() {
        return HOMEROLE;
    }

    public void setHOMEROLE(String HOMEROLE) {
        this.HOMEROLE = HOMEROLE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }


    public long getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(long last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Account() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.PHONE);
        dest.writeString(this.ROLE_NAME);
        dest.writeString(this.EMAIL);
        dest.writeString(this.HOMEROLE);
        dest.writeString(this.NAME);
        dest.writeString(this.loginName);
        dest.writeString(this.ADCD);
        dest.writeString(this.ADNM);
        dest.writeString(this.password);
        dest.writeString(this.JSESSIONID);
        dest.writeLong(this.last_login_time);
        dest.writeString(this.urlData);
    }

    protected Account(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.PHONE = in.readString();
        this.ROLE_NAME = in.readString();
        this.EMAIL = in.readString();
        this.HOMEROLE = in.readString();
        this.NAME = in.readString();
        this.loginName = in.readString();
        this.ADCD = in.readString();
        this.ADNM = in.readString();
        this.password = in.readString();
        this.JSESSIONID = in.readString();
        this.last_login_time = in.readLong();
        this.urlData = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
