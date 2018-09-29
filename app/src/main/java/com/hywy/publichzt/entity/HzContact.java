package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 河长通讯录
 * <p>
 *
 * @author Superman
 * @date 2018/7/24
 */

public class HzContact {

    /**
     * NT : 2
     * deals : [{"REACH_CODE":"PH006","PER_NAME":"汪龙照","PER_D":"金安区区县级总河长","PER_TYPE":"区县级总河长","PHONE":"28210089572","USER_ID":"10","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠河金安段","PER_NAME":"霍绍斌","PER_D":"金安区区县级总河长","PER_TYPE":"区县级总河长","PHONE":"28210089572","USER_ID":"11","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠河金安段","PER_NAME":"汪能武","PER_D":"金安区区县级副总河长","PER_TYPE":"区县级副总河长","PHONE":"28210089572","USER_ID":"12","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠河金安段","PER_NAME":"张士银","PER_D":"金安区区县级副总河长","PER_TYPE":"区县级副总河长","PHONE":"28210089572","USER_ID":"13","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠河金安段","PER_NAME":"杜继坤","PER_D":"金安区区县级河长","PER_TYPE":"区县级河长","PHONE":"28210089572","USER_ID":"14","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠河总干渠金安段","PER_NAME":"张敏","PER_D":"金安区区县级河长","PER_TYPE":"区县级河长","PHONE":"28210089572","USER_ID":"15","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"},{"REACH_CODE":"淠东干渠金安段","PER_NAME":"荣维聪","PER_D":"金安区区县级河长","PER_TYPE":"区县级河长","PHONE":"28210089572","USER_ID":"16","IMAGE_URL":"http://192.168.16.65:81/hzzglxt/headImgnull"}]
     * ADCD : 341502000000
     * ADNM : 金安区
     * UPADCD : 341500000000
     */

    private String NT;
    private String ADCD;
    private String ADNM;
    private String UPADCD;
    private List<DealsBean> deals;

    public String getNT() {
        return NT;
    }

    public void setNT(String NT) {
        this.NT = NT;
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

    public String getUPADCD() {
        return UPADCD;
    }

    public void setUPADCD(String UPADCD) {
        this.UPADCD = UPADCD;
    }

    public List<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(List<DealsBean> deals) {
        this.deals = deals;
    }

    public static class DealsBean implements Parcelable {
        /**
         * REACH_CODE : PH006
         * PER_NAME : 汪龙照
         * PER_D : 金安区区县级总河长
         * PER_TYPE : 区县级总河长
         * PHONE : 28210089572
         * USER_ID : 10
         * IMAGE_URL : http://192.168.16.65:81/hzzglxt/headImgnull
         */

        private String REACH_CODE;
        private String PER_NAME;
        private String PER_D;
        private String PER_TYPE;
        private String PHONE;
        private String USER_ID;
        private String IMAGE_URL;

        public String getREACH_CODE() {
            return REACH_CODE;
        }

        public void setREACH_CODE(String REACH_CODE) {
            this.REACH_CODE = REACH_CODE;
        }

        public String getPER_NAME() {
            return PER_NAME;
        }

        public void setPER_NAME(String PER_NAME) {
            this.PER_NAME = PER_NAME;
        }

        public String getPER_D() {
            return PER_D;
        }

        public void setPER_D(String PER_D) {
            this.PER_D = PER_D;
        }

        public String getPER_TYPE() {
            return PER_TYPE;
        }

        public void setPER_TYPE(String PER_TYPE) {
            this.PER_TYPE = PER_TYPE;
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

        public String getIMAGE_URL() {
            return IMAGE_URL;
        }

        public void setIMAGE_URL(String IMAGE_URL) {
            this.IMAGE_URL = IMAGE_URL;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.REACH_CODE);
            dest.writeString(this.PER_NAME);
            dest.writeString(this.PER_D);
            dest.writeString(this.PER_TYPE);
            dest.writeString(this.PHONE);
            dest.writeString(this.USER_ID);
            dest.writeString(this.IMAGE_URL);
        }

        public DealsBean() {
        }

        protected DealsBean(Parcel in) {
            this.REACH_CODE = in.readString();
            this.PER_NAME = in.readString();
            this.PER_D = in.readString();
            this.PER_TYPE = in.readString();
            this.PHONE = in.readString();
            this.USER_ID = in.readString();
            this.IMAGE_URL = in.readString();
        }

        public static final Parcelable.Creator<DealsBean> CREATOR = new Parcelable.Creator<DealsBean>() {
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
}
