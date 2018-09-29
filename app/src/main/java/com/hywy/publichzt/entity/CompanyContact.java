package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 协作单位通讯录
 *
 * @author Superman
 * @date 2018/7/24
 */

public class CompanyContact implements Parcelable {

    /**
     * DEPT_NAME : 六安市委宣传部
     * deals : [{"ADDERSS":"六安市","PER_NAME":"张三1","DUTY":"县常委","PHONE":"188888888888","ID":2,"PARENT_ID":"6"}]
     * ADCD : 341500000000
     * ID : 6
     */

    private String DEPT_NAME;
    private String ADCD;
    private int ID;
    private List<DealsBean> deals;

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public String getADCD() {
        return ADCD;
    }

    public void setADCD(String ADCD) {
        this.ADCD = ADCD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(List<DealsBean> deals) {
        this.deals = deals;
    }

    public static class DealsBean implements Parcelable {
        /**
         * ADDERSS : 六安市
         * PER_NAME : 张三1
         * DUTY : 县常委
         * PHONE : 188888888888
         * ID : 2
         * PARENT_ID : 6
         */

        private String ADDERSS;
        private String PER_NAME;
        private String DUTY;
        private String PHONE;
        private int ID;
        private String PARENT_ID;

        public String getADDERSS() {
            return ADDERSS;
        }

        public void setADDERSS(String ADDERSS) {
            this.ADDERSS = ADDERSS;
        }

        public String getPER_NAME() {
            return PER_NAME;
        }

        public void setPER_NAME(String PER_NAME) {
            this.PER_NAME = PER_NAME;
        }

        public String getDUTY() {
            return DUTY;
        }

        public void setDUTY(String DUTY) {
            this.DUTY = DUTY;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getPARENT_ID() {
            return PARENT_ID;
        }

        public void setPARENT_ID(String PARENT_ID) {
            this.PARENT_ID = PARENT_ID;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ADDERSS);
            dest.writeString(this.PER_NAME);
            dest.writeString(this.DUTY);
            dest.writeString(this.PHONE);
            dest.writeInt(this.ID);
            dest.writeString(this.PARENT_ID);
        }

        public DealsBean() {
        }

        protected DealsBean(Parcel in) {
            this.ADDERSS = in.readString();
            this.PER_NAME = in.readString();
            this.DUTY = in.readString();
            this.PHONE = in.readString();
            this.ID = in.readInt();
            this.PARENT_ID = in.readString();
        }

        public static final Creator<DealsBean> CREATOR = new Creator<DealsBean>() {
            @Override
            public DealsBean createFromParcel(Parcel source) {
                return new DealsBean(source);
            }

            @Override
            public DealsBean[] newArray(int size) {
                return new DealsBean[size];
            }
        };
    }

    public CompanyContact() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.DEPT_NAME);
        dest.writeString(this.ADCD);
        dest.writeInt(this.ID);
        dest.writeList(this.deals);
    }

    protected CompanyContact(Parcel in) {
        this.DEPT_NAME = in.readString();
        this.ADCD = in.readString();
        this.ID = in.readInt();
        this.deals = new ArrayList<DealsBean>();
        in.readList(this.deals, DealsBean.class.getClassLoader());
    }

    public static final Creator<CompanyContact> CREATOR = new Creator<CompanyContact>() {
        @Override
        public CompanyContact createFromParcel(Parcel source) {
            return new CompanyContact(source);
        }

        @Override
        public CompanyContact[] newArray(int size) {
            return new CompanyContact[size];
        }
    };
}
