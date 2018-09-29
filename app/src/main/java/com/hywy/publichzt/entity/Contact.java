package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织信息~通讯录
 *
 * @author Superman
 * @date 2018/7/23
 */

public class Contact implements Parcelable {

    /**
     * deals : [{"PHONE":"18614022641","USER_ID":"1ec32db2c9a144899210e75ff70e43bd","USERNAME":"szb","NAME":"邵珠波"},{"PHONE":"18614022640","USER_ID":"d4e967a554264deaadb1b1d9643c01b7","USERNAME":"六安河长办","NAME":"六安河长办"}]
     * ROLE_ID : 1187d7101aa64add86f5b0bd4534d09b
     * ROLE_NAME : 市级河长办
     */

    private String ROLE_ID;
    private String ROLE_NAME;
    private List<DealsBean> deals;

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getROLE_NAME() {
        return ROLE_NAME;
    }

    public void setROLE_NAME(String ROLE_NAME) {
        this.ROLE_NAME = ROLE_NAME;
    }

    public List<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(List<DealsBean> deals) {
        this.deals = deals;
    }

    public static class DealsBean implements Parcelable {
        /**
         * PHONE : 18614022641
         * USER_ID : 1ec32db2c9a144899210e75ff70e43bd
         * USERNAME : szb
         * NAME : 邵珠波
         */

        private String PHONE;
        private String USER_ID;
        private String USERNAME;
        private String NAME;
        private String ADMN;
        private String PER_D;//职务
        private String IMAGE_URL;//头像

        public String getADMN() {
            return ADMN;
        }

        public void setADMN(String ADMN) {
            this.ADMN = ADMN;
        }

        public String getPER_D() {
            return PER_D;
        }

        public void setPER_D(String PER_D) {
            this.PER_D = PER_D;
        }

        public String getIMAGE_URL() {
            return IMAGE_URL;
        }

        public void setIMAGE_URL(String IMAGE_URL) {
            this.IMAGE_URL = IMAGE_URL;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public DealsBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.PHONE);
            dest.writeString(this.USER_ID);
            dest.writeString(this.USERNAME);
            dest.writeString(this.NAME);
            dest.writeString(this.ADMN);
            dest.writeString(this.PER_D);
            dest.writeString(this.IMAGE_URL);
        }

        protected DealsBean(Parcel in) {
            this.PHONE = in.readString();
            this.USER_ID = in.readString();
            this.USERNAME = in.readString();
            this.NAME = in.readString();
            this.ADMN = in.readString();
            this.PER_D = in.readString();
            this.IMAGE_URL = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ROLE_ID);
        dest.writeString(this.ROLE_NAME);
        dest.writeList(this.deals);
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        this.ROLE_ID = in.readString();
        this.ROLE_NAME = in.readString();
        this.deals = new ArrayList<DealsBean>();
        in.readList(this.deals, DealsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
