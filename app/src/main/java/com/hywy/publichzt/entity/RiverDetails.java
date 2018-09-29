package com.hywy.publichzt.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Superman on 2018/7/12.
 */

public class RiverDetails implements Parcelable {

    private List<HDBaseBean> HDBase;
    private List<YHYCListBean> YHYCList;
    private List<SZBaseBean> SZBase;
    private List<XCJHListBean> XCJHList;

    public List<HDBaseBean> getHDBase() {
        return HDBase;
    }

    public void setHDBase(List<HDBaseBean> HDBase) {
        this.HDBase = HDBase;
    }

    public List<YHYCListBean> getYHYCList() {
        return YHYCList;
    }

    public void setYHYCList(List<YHYCListBean> YHYCList) {
        this.YHYCList = YHYCList;
    }

    public List<SZBaseBean> getSZBase() {
        return SZBase;
    }

    public void setSZBase(List<SZBaseBean> SZBase) {
        this.SZBase = SZBase;
    }

    public List<XCJHListBean> getXCJHList() {
        return XCJHList;
    }

    public void setXCJHList(List<XCJHListBean> XCJHList) {
        this.XCJHList = XCJHList;
    }

    public static class HDBaseBean implements Parcelable {
        /**
         *
         *
         * PER_DUTIES : 市委常委、常务副市长
         * RIVER_AREA : 3652
         * REACH_CODE : PH001
         * RIVER_LEN : 57
         * PHONE : 0564-5158564
         * SUP_PER : 孙德士
         * LGTD : 116.52456
         * PER_DUTY : 负责组织领导淠河总干渠水资源保护、水域岸线管理、水污染防治、水环境治理、水生态修复、执法监管等工作，协调解决淠河总干渠管理保护重大问题；牵头组织对淠河总干渠管理范围内的非法侵占河湖水域岸线和航道、围垦河湖、盗采砂石资源、破坏河湖工程设施、违法取水排污、电毒炸鱼及上级交办的突出问题进行依法整治，组织参加淠河总干渠联防联控，对目标任务完成情况进行考核，强化激励问责。
         * MG_SCOPE : 起讫点之间水域、岸线及分干渠或支渠渠首
         * RIVER_SURVEY : 淠河总干渠是建国初期修建淠史杭灌区工程的主要渠道，上游源自佛子岭、磨子潭、响洪甸三大水库，水库出水由东、西淠河汇合后从渠首横排头进水闸行经三里岗进入六安市区，穿过六安市区至九里沟，下游经罗管节制闸过青龙堰入肥西县境，全长104.5公里，我市境内长56.8公里。
         * DT_PER_COMP : 安徽省淠史杭灌区管理总局
         * SUP_PHONE : 0564-51585661780564656612369181056497370564-3393010
         * PER_NAME : 束学龙
         * BOARD_FRONT :
         * ADDRESS :
         * deals : []
         * LTTD : 31.86469
         * MG_TARGET :
         * BOARD_BACK :
         * B_E_LOCATION : 起点横排头渠首，讫点青龙堰
         */
        private String REACH_NAME;

        private String PER_DUTIES;
        private String RIVER_AREA;
        private String REACH_CODE;
        private String RIVER_LEN;
        private String PHONE;
        private String SUP_PER;
        private String LGTD;
        private String PER_DUTY;
        private String MG_SCOPE;
        private String RIVER_SURVEY;
        private String DT_PER_COMP;
        private String SUP_PHONE;
        private String PER_NAME;
        private String BOARD_FRONT;
        private String ADDRESS;
        private String LTTD;
        private String MG_TARGET;
        private String BOARD_BACK;
        private String B_E_LOCATION;
        private List<Deal> deals;

        public String getREACH_NAME() {
            return REACH_NAME;
        }

        public void setREACH_NAME(String REACH_NAME) {
            this.REACH_NAME = REACH_NAME;
        }

        public String getPER_DUTIES() {
            return PER_DUTIES;
        }

        public void setPER_DUTIES(String PER_DUTIES) {
            this.PER_DUTIES = PER_DUTIES;
        }

        public String getRIVER_AREA() {
            return RIVER_AREA;
        }

        public void setRIVER_AREA(String RIVER_AREA) {
            this.RIVER_AREA = RIVER_AREA;
        }

        public String getREACH_CODE() {
            return REACH_CODE;
        }

        public void setREACH_CODE(String REACH_CODE) {
            this.REACH_CODE = REACH_CODE;
        }

        public String getRIVER_LEN() {
            return RIVER_LEN;
        }

        public void setRIVER_LEN(String RIVER_LEN) {
            this.RIVER_LEN = RIVER_LEN;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getSUP_PER() {
            return SUP_PER;
        }

        public void setSUP_PER(String SUP_PER) {
            this.SUP_PER = SUP_PER;
        }

        public String getLGTD() {
            return LGTD;
        }

        public void setLGTD(String LGTD) {
            this.LGTD = LGTD;
        }

        public String getPER_DUTY() {
            return PER_DUTY;
        }

        public void setPER_DUTY(String PER_DUTY) {
            this.PER_DUTY = PER_DUTY;
        }

        public String getMG_SCOPE() {
            return MG_SCOPE;
        }

        public void setMG_SCOPE(String MG_SCOPE) {
            this.MG_SCOPE = MG_SCOPE;
        }

        public String getRIVER_SURVEY() {
            return RIVER_SURVEY;
        }

        public void setRIVER_SURVEY(String RIVER_SURVEY) {
            this.RIVER_SURVEY = RIVER_SURVEY;
        }

        public String getDT_PER_COMP() {
            return DT_PER_COMP;
        }

        public void setDT_PER_COMP(String DT_PER_COMP) {
            this.DT_PER_COMP = DT_PER_COMP;
        }

        public String getSUP_PHONE() {
            return SUP_PHONE;
        }

        public void setSUP_PHONE(String SUP_PHONE) {
            this.SUP_PHONE = SUP_PHONE;
        }

        public String getPER_NAME() {
            return PER_NAME;
        }

        public void setPER_NAME(String PER_NAME) {
            this.PER_NAME = PER_NAME;
        }

        public String getBOARD_FRONT() {
            return BOARD_FRONT;
        }

        public void setBOARD_FRONT(String BOARD_FRONT) {
            this.BOARD_FRONT = BOARD_FRONT;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getLTTD() {
            return LTTD;
        }

        public void setLTTD(String LTTD) {
            this.LTTD = LTTD;
        }

        public String getMG_TARGET() {
            return MG_TARGET;
        }

        public void setMG_TARGET(String MG_TARGET) {
            this.MG_TARGET = MG_TARGET;
        }

        public String getBOARD_BACK() {
            return BOARD_BACK;
        }

        public void setBOARD_BACK(String BOARD_BACK) {
            this.BOARD_BACK = BOARD_BACK;
        }

        public String getB_E_LOCATION() {
            return B_E_LOCATION;
        }

        public void setB_E_LOCATION(String B_E_LOCATION) {
            this.B_E_LOCATION = B_E_LOCATION;
        }

        public List<Deal> getDeals() {
            return deals;
        }

        public void setDeals(List<Deal> deals) {
            this.deals = deals;
        }

        public HDBaseBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.REACH_NAME);
            dest.writeString(this.PER_DUTIES);
            dest.writeString(this.RIVER_AREA);
            dest.writeString(this.REACH_CODE);
            dest.writeString(this.RIVER_LEN);
            dest.writeString(this.PHONE);
            dest.writeString(this.SUP_PER);
            dest.writeString(this.LGTD);
            dest.writeString(this.PER_DUTY);
            dest.writeString(this.MG_SCOPE);
            dest.writeString(this.RIVER_SURVEY);
            dest.writeString(this.DT_PER_COMP);
            dest.writeString(this.SUP_PHONE);
            dest.writeString(this.PER_NAME);
            dest.writeString(this.BOARD_FRONT);
            dest.writeString(this.ADDRESS);
            dest.writeString(this.LTTD);
            dest.writeString(this.MG_TARGET);
            dest.writeString(this.BOARD_BACK);
            dest.writeString(this.B_E_LOCATION);
            dest.writeTypedList(this.deals);
        }

        protected HDBaseBean(Parcel in) {
            this.REACH_NAME = in.readString();
            this.PER_DUTIES = in.readString();
            this.RIVER_AREA = in.readString();
            this.REACH_CODE = in.readString();
            this.RIVER_LEN = in.readString();
            this.PHONE = in.readString();
            this.SUP_PER = in.readString();
            this.LGTD = in.readString();
            this.PER_DUTY = in.readString();
            this.MG_SCOPE = in.readString();
            this.RIVER_SURVEY = in.readString();
            this.DT_PER_COMP = in.readString();
            this.SUP_PHONE = in.readString();
            this.PER_NAME = in.readString();
            this.BOARD_FRONT = in.readString();
            this.ADDRESS = in.readString();
            this.LTTD = in.readString();
            this.MG_TARGET = in.readString();
            this.BOARD_BACK = in.readString();
            this.B_E_LOCATION = in.readString();
            this.deals = in.createTypedArrayList(Deal.CREATOR);
        }

        public static final Creator<HDBaseBean> CREATOR = new Creator<HDBaseBean>() {
            @Override
            public HDBaseBean createFromParcel(Parcel source) {
                return new HDBaseBean(source);
            }

            @Override
            public HDBaseBean[] newArray(int size) {
                return new HDBaseBean[size];
            }
        };
    }

    public static class YHYCListBean implements Parcelable {
        /**
         * REACH_CODE : PH001
         * ADCD : 341524000000
         * HTML_Url : http://192.168.16.65:81/hzzglxt/uploadFilesile_zdgcj8186012098j8186015414.html
         */

        private String REACH_CODE;
        private String ADCD;
        private String HTML_Url;

        public String getREACH_CODE() {
            return REACH_CODE;
        }

        public void setREACH_CODE(String REACH_CODE) {
            this.REACH_CODE = REACH_CODE;
        }

        public String getADCD() {
            return ADCD;
        }

        public void setADCD(String ADCD) {
            this.ADCD = ADCD;
        }

        public String getHTML_Url() {
            return HTML_Url;
        }

        public void setHTML_Url(String HTML_Url) {
            this.HTML_Url = HTML_Url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.REACH_CODE);
            dest.writeString(this.ADCD);
            dest.writeString(this.HTML_Url);
        }

        public YHYCListBean() {
        }

        protected YHYCListBean(Parcel in) {
            this.REACH_CODE = in.readString();
            this.ADCD = in.readString();
            this.HTML_Url = in.readString();
        }

        public static final Creator<YHYCListBean> CREATOR = new Creator<YHYCListBean>() {
            @Override
            public YHYCListBean createFromParcel(Parcel source) {
                return new YHYCListBean(source);
            }

            @Override
            public YHYCListBean[] newArray(int size) {
                return new YHYCListBean[size];
            }
        };
    }

    public static class SZBaseBean implements Parcelable {
        /**
         * STCD : 40061
         * CHLA : 71.433
         * SPT : 2018-06-27 09:59:59
         * TURB : 91.115
         * STNM : 淠河东干渠平桥站
         * WATER_TYPE : 3
         * PH : 7.817
         * DOX : 1.942
         * WT : 25.42
         * COND : 348.237
         * BGA : 1736.401
         * STLC : 六安市裕安区平桥乡
         */
        private String REACH_NAME;

        public String getREACH_NAME() {
            return REACH_NAME;
        }

        public void setREACH_NAME(String REACH_NAME) {
            this.REACH_NAME = REACH_NAME;
        }

        private String STCD;
        private String CHLA;
        private String SPT;
        private String TURB;
        private String STNM;
        private String WATER_TYPE;
        private String PH;
        private String DOX;
        private String WT;
        private String COND;
        private String BGA;
        private String STLC;
        private String TP;//总磷
        private String F;//氟化物
        private String CODMN;// 高锰酸钾指数

        public String getTP() {
            return TP;
        }

        public void setTP(String TP) {
            this.TP = TP;
        }

        public String getF() {
            return F;
        }

        public void setF(String f) {
            F = f;
        }

        public String getCODMN() {
            return CODMN;
        }

        public void setCODMN(String CODMN) {
            this.CODMN = CODMN;
        }

        public String getSTCD() {
            return STCD;
        }

        public void setSTCD(String STCD) {
            this.STCD = STCD;
        }

        public String getCHLA() {
            return CHLA;
        }

        public void setCHLA(String CHLA) {
            this.CHLA = CHLA;
        }

        public String getSPT() {
            return SPT;
        }

        public void setSPT(String SPT) {
            this.SPT = SPT;
        }

        public String getTURB() {
            return TURB;
        }

        public void setTURB(String TURB) {
            this.TURB = TURB;
        }

        public String getSTNM() {
            return STNM;
        }

        public void setSTNM(String STNM) {
            this.STNM = STNM;
        }

        public String getWATER_TYPE() {
            return WATER_TYPE;
        }

        public void setWATER_TYPE(String WATER_TYPE) {
            this.WATER_TYPE = WATER_TYPE;
        }

        public String getPH() {
            return PH;
        }

        public void setPH(String PH) {
            this.PH = PH;
        }

        public String getDOX() {
            return DOX;
        }

        public void setDOX(String DOX) {
            this.DOX = DOX;
        }

        public String getWT() {
            return WT;
        }

        public void setWT(String WT) {
            this.WT = WT;
        }

        public String getCOND() {
            return COND;
        }

        public void setCOND(String COND) {
            this.COND = COND;
        }

        public String getBGA() {
            return BGA;
        }

        public void setBGA(String BGA) {
            this.BGA = BGA;
        }

        public String getSTLC() {
            return STLC;
        }

        public void setSTLC(String STLC) {
            this.STLC = STLC;
        }

        public SZBaseBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.REACH_NAME);
            dest.writeString(this.STCD);
            dest.writeString(this.CHLA);
            dest.writeString(this.SPT);
            dest.writeString(this.TURB);
            dest.writeString(this.STNM);
            dest.writeString(this.WATER_TYPE);
            dest.writeString(this.PH);
            dest.writeString(this.DOX);
            dest.writeString(this.WT);
            dest.writeString(this.COND);
            dest.writeString(this.BGA);
            dest.writeString(this.STLC);
            dest.writeString(this.TP);
            dest.writeString(this.F);
            dest.writeString(this.CODMN);
        }

        protected SZBaseBean(Parcel in) {
            this.REACH_NAME = in.readString();
            this.STCD = in.readString();
            this.CHLA = in.readString();
            this.SPT = in.readString();
            this.TURB = in.readString();
            this.STNM = in.readString();
            this.WATER_TYPE = in.readString();
            this.PH = in.readString();
            this.DOX = in.readString();
            this.WT = in.readString();
            this.COND = in.readString();
            this.BGA = in.readString();
            this.STLC = in.readString();
            this.TP = in.readString();
            this.F = in.readString();
            this.CODMN = in.readString();
        }

        public static final Creator<SZBaseBean> CREATOR = new Creator<SZBaseBean>() {
            @Override
            public SZBaseBean createFromParcel(Parcel source) {
                return new SZBaseBean(source);
            }

            @Override
            public SZBaseBean[] newArray(int size) {
                return new SZBaseBean[size];
            }
        };
    }

    public static class XCJHListBean implements Parcelable {
        /**
         * NT : 1
         * PATROL_TM : 2018-03-06 00:00:00
         * LOG_ID : 6
         * NAME : 超级管理员
         */

        private String NT;
        private String PATROL_TM;
        private String LOG_ID;
        private String NAME;

        public String getNT() {
            return NT;
        }

        public void setNT(String NT) {
            this.NT = NT;
        }

        public String getPATROL_TM() {
            return PATROL_TM;
        }

        public void setPATROL_TM(String PATROL_TM) {
            this.PATROL_TM = PATROL_TM;
        }

        public String getLOG_ID() {
            return LOG_ID;
        }

        public void setLOG_ID(String LOG_ID) {
            this.LOG_ID = LOG_ID;
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
            dest.writeString(this.NT);
            dest.writeString(this.PATROL_TM);
            dest.writeString(this.LOG_ID);
            dest.writeString(this.NAME);
        }

        public XCJHListBean() {
        }

        protected XCJHListBean(Parcel in) {
            this.NT = in.readString();
            this.PATROL_TM = in.readString();
            this.LOG_ID = in.readString();
            this.NAME = in.readString();
        }

        public static final Creator<XCJHListBean> CREATOR = new Creator<XCJHListBean>() {
            @Override
            public XCJHListBean createFromParcel(Parcel source) {
                return new XCJHListBean(source);
            }

            @Override
            public XCJHListBean[] newArray(int size) {
                return new XCJHListBean[size];
            }
        };
    }

    public RiverDetails() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.HDBase);
        dest.writeList(this.YHYCList);
        dest.writeList(this.SZBase);
        dest.writeList(this.XCJHList);
    }

    protected RiverDetails(Parcel in) {
        this.HDBase = new ArrayList<HDBaseBean>();
        in.readList(this.HDBase, HDBaseBean.class.getClassLoader());
        this.YHYCList = new ArrayList<YHYCListBean>();
        in.readList(this.YHYCList, YHYCListBean.class.getClassLoader());
        this.SZBase = new ArrayList<SZBaseBean>();
        in.readList(this.SZBase, SZBaseBean.class.getClassLoader());
        this.XCJHList = new ArrayList<XCJHListBean>();
        in.readList(this.XCJHList, XCJHListBean.class.getClassLoader());
    }

    public static final Creator<RiverDetails> CREATOR = new Creator<RiverDetails>() {
        @Override
        public RiverDetails createFromParcel(Parcel source) {
            return new RiverDetails(source);
        }

        @Override
        public RiverDetails[] newArray(int size) {
            return new RiverDetails[size];
        }
    };
}
