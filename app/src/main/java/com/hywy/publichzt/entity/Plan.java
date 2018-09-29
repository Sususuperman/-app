package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Superman on 2018/7/5.
 */

public class Plan implements Parcelable {

    /**
     * REACH_CODE : PH007
     * PATROL_PLAN_NAME : 淠河霍邱巡查
     * REACH_NAME : 淠河干流霍邱段
     * LENGTH : 3.23
     * PLAN_CONTENT : 2,4
     * PLAN_ID : 1
     * ADCD : 341522000000
     * ADNM : 霍邱县
     * OPTIONS : [{"OPTIONS_NAME":"是否存在各类污水直排口，工业企业、污水处理设备是否有偷排、漏排及超标排放等环境违法行为","OPTIONS_ID":2},{"OPTIONS_NAME":"是否存在河岸垃圾和泥浆渣土、工业固废等废弃物入河现象","OPTIONS_ID":4}]
     */

    private String REACH_CODE;
    private String PATROL_PLAN_NAME;
    private String REACH_NAME;
    private String LENGTH;
    private String PLAN_CONTENT;
    private String PLAN_ID;
    private String ADCD;
    private String ADNM;
    private String PHONE;
    private String NAME;

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    private List<OPTIONSBean> OPTIONS;

    public String getREACH_CODE() {
        return REACH_CODE;
    }

    public void setREACH_CODE(String REACH_CODE) {
        this.REACH_CODE = REACH_CODE;
    }

    public String getPATROL_PLAN_NAME() {
        return PATROL_PLAN_NAME;
    }

    public void setPATROL_PLAN_NAME(String PATROL_PLAN_NAME) {
        this.PATROL_PLAN_NAME = PATROL_PLAN_NAME;
    }

    public String getREACH_NAME() {
        return REACH_NAME;
    }

    public void setREACH_NAME(String REACH_NAME) {
        this.REACH_NAME = REACH_NAME;
    }

    public String getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(String LENGTH) {
        this.LENGTH = LENGTH;
    }

    public String getPLAN_CONTENT() {
        return PLAN_CONTENT;
    }

    public void setPLAN_CONTENT(String PLAN_CONTENT) {
        this.PLAN_CONTENT = PLAN_CONTENT;
    }

    public String getPLAN_ID() {
        return PLAN_ID;
    }

    public void setPLAN_ID(String PLAN_ID) {
        this.PLAN_ID = PLAN_ID;
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

    public List<OPTIONSBean> getOPTIONS() {
        return OPTIONS;
    }

    public void setOPTIONS(List<OPTIONSBean> OPTIONS) {
        this.OPTIONS = OPTIONS;
    }

    public static class OPTIONSBean implements Parcelable {
        /**
         * OPTIONS_NAME : 是否存在各类污水直排口，工业企业、污水处理设备是否有偷排、漏排及超标排放等环境违法行为
         * OPTIONS_ID : 2
         */
        private int status = 0;//用于记录是否完成
        private String OPTIONS_NAME;
        private int OPTIONS_ID;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOPTIONS_NAME() {
            return OPTIONS_NAME;
        }

        public void setOPTIONS_NAME(String OPTIONS_NAME) {
            this.OPTIONS_NAME = OPTIONS_NAME;
        }

        public int getOPTIONS_ID() {
            return OPTIONS_ID;
        }

        public void setOPTIONS_ID(int OPTIONS_ID) {
            this.OPTIONS_ID = OPTIONS_ID;
        }

        public OPTIONSBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeString(this.OPTIONS_NAME);
            dest.writeInt(this.OPTIONS_ID);
        }

        protected OPTIONSBean(Parcel in) {
            this.status = in.readInt();
            this.OPTIONS_NAME = in.readString();
            this.OPTIONS_ID = in.readInt();
        }

        public static final Creator<OPTIONSBean> CREATOR = new Creator<OPTIONSBean>() {
            @Override
            public OPTIONSBean createFromParcel(Parcel source) {
                return new OPTIONSBean(source);
            }

            @Override
            public OPTIONSBean[] newArray(int size) {
                return new OPTIONSBean[size];
            }
        };
    }

    public Plan() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.REACH_CODE);
        dest.writeString(this.PATROL_PLAN_NAME);
        dest.writeString(this.REACH_NAME);
        dest.writeString(this.LENGTH);
        dest.writeString(this.PLAN_CONTENT);
        dest.writeString(this.PLAN_ID);
        dest.writeString(this.ADCD);
        dest.writeString(this.ADNM);
        dest.writeString(this.PHONE);
        dest.writeString(this.NAME);
        dest.writeTypedList(this.OPTIONS);
    }

    protected Plan(Parcel in) {
        this.REACH_CODE = in.readString();
        this.PATROL_PLAN_NAME = in.readString();
        this.REACH_NAME = in.readString();
        this.LENGTH = in.readString();
        this.PLAN_CONTENT = in.readString();
        this.PLAN_ID = in.readString();
        this.ADCD = in.readString();
        this.ADNM = in.readString();
        this.PHONE = in.readString();
        this.NAME = in.readString();
        this.OPTIONS = in.createTypedArrayList(OPTIONSBean.CREATOR);
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel source) {
            return new Plan(source);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };
}
