package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/8/22.
 */

public class Complain implements Parcelable {
    private String ID;
    private String TYPE;
    /**
     * COMPLAINTXT : 水面杂物太多
     * COMPLAIN : 1
     * LOG : 裕安区
     * PHONE : 18333552188
     * COMID : 002
     * COMPLAINTIME : 1525334232000
     * COMZT : 1
     * IMGSRC : uploadFiles/czimg/001.jpg
     * COMPLAINMAN : 黄峰
     * ADCD : 341503000000
     * OPINION : 123123
     * IMGURL : uploadFiles/czimg/1528367179003.jpg
     * COMPLAINRIVER : PH004
     * COMPLAINTYPE : 水质污染
     */

    private String COMPLAINTXT;
    private String COMPLAIN;
    private String LOG;
    private String PHONE;
    private String COMID;
    private String COMPLAINTIME;
    private String COMZT;//0 未处理，1 已处理
    private String IMGSRC;
    private String COMPLAINMAN;
    private String ADCD;
    private String ADNM;
    private String IMGURL;
    private String COMPLAINRIVER;
    private String COMPLAINTYPE;
    private String REACH_NAME;
    private String LGTD;
    private String LLTD;

    private String SOLVEMAN;//处理人
    private String SOLVE_PHONE;//处理人电话
    private String SOLVETIME;//处理时间
    private String OPINION;//处理意见、结果

    private List<AttachMent> SOLVE_ATTACH;//处理结果图片
    private List<AttachMent> COMPLAIN_ATTACH;//投诉图片

    public String getADNM() {
        return ADNM;
    }

    public void setADNM(String ADNM) {
        this.ADNM = ADNM;
    }

    public List<AttachMent> getSOLVE_ATTACH() {
        return SOLVE_ATTACH;
    }

    public void setSOLVE_ATTACH(List<AttachMent> SOLVE_ATTACH) {
        this.SOLVE_ATTACH = SOLVE_ATTACH;
    }

    public List<AttachMent> getCOMPLAIN_ATTACH() {
        return COMPLAIN_ATTACH;
    }

    public void setCOMPLAIN_ATTACH(List<AttachMent> COMPLAIN_ATTACH) {
        this.COMPLAIN_ATTACH = COMPLAIN_ATTACH;
    }

    public String getSOLVEMAN() {
        return SOLVEMAN;
    }

    public void setSOLVEMAN(String SOLVEMAN) {
        this.SOLVEMAN = SOLVEMAN;
    }

    public String getSOLVE_PHONE() {
        return SOLVE_PHONE;
    }

    public void setSOLVE_PHONE(String SOLVE_PHONE) {
        this.SOLVE_PHONE = SOLVE_PHONE;
    }

    public String getSOLVETIME() {
        return SOLVETIME;
    }

    public void setSOLVETIME(String SOLVETIME) {
        this.SOLVETIME = SOLVETIME;
    }

    public String getLGTD() {
        return LGTD;
    }

    public void setLGTD(String LGTD) {
        this.LGTD = LGTD;
    }

    public String getLLTD() {
        return LLTD;
    }

    public void setLLTD(String LLTD) {
        this.LLTD = LLTD;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Complain() {
    }

    public String getCOMPLAINTXT() {
        return COMPLAINTXT;
    }

    public void setCOMPLAINTXT(String COMPLAINTXT) {
        this.COMPLAINTXT = COMPLAINTXT;
    }

    public String getCOMPLAIN() {
        return COMPLAIN;
    }

    public void setCOMPLAIN(String COMPLAIN) {
        this.COMPLAIN = COMPLAIN;
    }

    public String getLOG() {
        return LOG;
    }

    public void setLOG(String LOG) {
        this.LOG = LOG;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getCOMID() {
        return COMID;
    }

    public void setCOMID(String COMID) {
        this.COMID = COMID;
    }

    public String getCOMPLAINTIME() {
        return COMPLAINTIME;
    }

    public void setCOMPLAINTIME(String COMPLAINTIME) {
        this.COMPLAINTIME = COMPLAINTIME;
    }

    public String getCOMZT() {
        return COMZT;
    }

    public void setCOMZT(String COMZT) {
        this.COMZT = COMZT;
    }

    public String getIMGSRC() {
        return IMGSRC;
    }

    public void setIMGSRC(String IMGSRC) {
        this.IMGSRC = IMGSRC;
    }

    public String getCOMPLAINMAN() {
        return COMPLAINMAN;
    }

    public void setCOMPLAINMAN(String COMPLAINMAN) {
        this.COMPLAINMAN = COMPLAINMAN;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public String getOPINION() {
        return OPINION;
    }

    public void setOPINION(String OPINION) {
        this.OPINION = OPINION;
    }

    public String getIMGURL() {
        return IMGURL;
    }

    public void setIMGURL(String IMGURL) {
        this.IMGURL = IMGURL;
    }

    public String getCOMPLAINRIVER() {
        return COMPLAINRIVER;
    }

    public void setCOMPLAINRIVER(String COMPLAINRIVER) {
        this.COMPLAINRIVER = COMPLAINRIVER;
    }

    public String getCOMPLAINTYPE() {
        return COMPLAINTYPE;
    }

    public void setCOMPLAINTYPE(String COMPLAINTYPE) {
        this.COMPLAINTYPE = COMPLAINTYPE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.TYPE);
        dest.writeString(this.COMPLAINTXT);
        dest.writeString(this.COMPLAIN);
        dest.writeString(this.LOG);
        dest.writeString(this.PHONE);
        dest.writeString(this.COMID);
        dest.writeString(this.COMPLAINTIME);
        dest.writeString(this.COMZT);
        dest.writeString(this.IMGSRC);
        dest.writeString(this.COMPLAINMAN);
        dest.writeString(this.ADCD);
        dest.writeString(this.ADNM);
        dest.writeString(this.IMGURL);
        dest.writeString(this.COMPLAINRIVER);
        dest.writeString(this.COMPLAINTYPE);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.LGTD);
        dest.writeString(this.LLTD);
        dest.writeString(this.SOLVEMAN);
        dest.writeString(this.SOLVE_PHONE);
        dest.writeString(this.SOLVETIME);
        dest.writeString(this.OPINION);
        dest.writeTypedList(this.SOLVE_ATTACH);
        dest.writeTypedList(this.COMPLAIN_ATTACH);
    }

    protected Complain(Parcel in) {
        this.ID = in.readString();
        this.TYPE = in.readString();
        this.COMPLAINTXT = in.readString();
        this.COMPLAIN = in.readString();
        this.LOG = in.readString();
        this.PHONE = in.readString();
        this.COMID = in.readString();
        this.COMPLAINTIME = in.readString();
        this.COMZT = in.readString();
        this.IMGSRC = in.readString();
        this.COMPLAINMAN = in.readString();
        this.ADCD = in.readString();
        this.ADNM = in.readString();
        this.IMGURL = in.readString();
        this.COMPLAINRIVER = in.readString();
        this.COMPLAINTYPE = in.readString();
        this.REACH_NAME = in.readString();
        this.LGTD = in.readString();
        this.LLTD = in.readString();
        this.SOLVEMAN = in.readString();
        this.SOLVE_PHONE = in.readString();
        this.SOLVETIME = in.readString();
        this.OPINION = in.readString();
        this.SOLVE_ATTACH = in.createTypedArrayList(AttachMent.CREATOR);
        this.COMPLAIN_ATTACH = in.createTypedArrayList(AttachMent.CREATOR);
    }

    public static final Creator<Complain> CREATOR = new Creator<Complain>() {
        @Override
        public Complain createFromParcel(Parcel source) {
            return new Complain(source);
        }

        @Override
        public Complain[] newArray(int size) {
            return new Complain[size];
        }
    };
}
