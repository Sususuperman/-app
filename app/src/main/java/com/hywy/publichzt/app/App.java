package com.hywy.publichzt.app;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.cs.common.baseapp.BaseApp;
import com.cs.common.utils.Logger;
import com.cs.common.utils.SharedUtils;
import com.cs.common.utils.StringUtils;
import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hik.mcrsdk.talk.TalkClientSDK;
import com.hikvision.sdk.VMSNetSDK;
import com.hywy.publichzt.AppConfig;
import com.hywy.publichzt.Const;
import com.hywy.publichzt.dao.AccountDao;
import com.hywy.publichzt.dao.AppMenuDao;
import com.hywy.publichzt.entity.Account;
import com.hywy.publichzt.entity.AppMenu;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 父类已经实现了位置和网络的监控 此类实现访问网络对定时任务
 *
 * @author james
 */
public class App extends BaseApp {
    private String token;// 登录后的令牌
    private static App app;
    private long lastLoginDate = 0;// 最后访问服务器的时间
    private AtomicBoolean isDownMainApk = new AtomicBoolean(false);
    private Account account;
    private String url;
    //    private String url="http://218.22.195.54:7007";//url地址
    private AppMenu menu1, menu2, menu3;

    public static App getInstence() {
        return app;
    }

    public AtomicBoolean getIsDownMainApk() {
        return isDownMainApk;
    }

    /***************读取首页菜单缓存***********************************************************************************/
    private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间
    private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        Logger.d("App", "创建应用:" + System.currentTimeMillis() + "," + this);
        super.onCreate();
        app = this;
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5743b779");
        if (Const.Config.DEVELOPER_MODE
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                    .penaltyDeath().build());
        }


        MCRSDK.init();
        // 初始化RTSP
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);
        // 初始化语音对讲
        TalkClientSDK.initLib();
        // SDK初始化
        VMSNetSDK.init(this);

    }

    public static App getInstance() {
        return (App) getBaseInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        if (token != null) {
            //该token主要用于监听安装监听后，跳转到message时使用
//            SharedUtils.setString(this, Long.toString(loginUser.getUserid()), token);
        }
    }

    public Account getAccount() {
        if (account != null)
            return account;
        account = new AccountDao(this).select();
        return account;
    }

    public AppMenu getMenu1() {
        if (menu1 != null)
            return menu1;
        menu1 = new AppMenuDao(this).selectMenu1();
        return menu1;
    }

    public AppMenu getMenu2() {
        if (menu2 != null)
            return menu2;
        menu2 = new AppMenuDao(this).selectMenu2();
        return menu2;
    }

    public AppMenu getMenu3() {
        if (menu3 != null)
            return menu3;
        menu3 = new AppMenuDao(this).selectMenu3();
        return menu3;
    }

    /**
     * 设置menu类为null
     */
    public void setMenu(AppMenu menu) {
        menu1 = menu;
        menu2 = menu;
        menu3 = menu;
    }

    public String getApiURL() {
//        return "http://192.168.16.101:8080";
        return "http://218.22.195.54:7007";
    }

    public void setApiURL(String url) {
        this.url = url;
        SharedUtils.setString(this, "httpurl", url);
    }

    public void setAccount(Account account) {
        this.account = account;
        lastLoginDate = System.currentTimeMillis();
    }

    /*
     * 判断当前登陆是否有效
     *
     * @return
     */
    public boolean isValidToken() {
        if (this.account == null || this.token == null) {
            return false;
        }

        // 服务器的session的超时时间是30分钟，这里设置如果当前时间大于上次登陆时间28分钟后，则判断登陆失效
        if (System.currentTimeMillis() - this.lastLoginDate > 28 * 60 * 1000) {
            return false;
        }

        return true;
    }

/************************************以下用于读取首页菜单缓存方法*******************************************************************/
    /**
     * 判断缓存数据是否可读
     *
     * @param cachefile
     * @return
     */
    private boolean isReadDataCache(String cachefile) {
        return readObject(cachefile) != null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    private boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 判断缓存是否失效
     *
     * @param cachefile
     * @return
     */
    public boolean isCacheDataFailure(String cachefile) {
        boolean failure = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
            failure = true;
        else if (!data.exists())
            failure = true;
        return failure;
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 将对象保存到内存缓存中
     *
     * @param key
     * @param value
     */
    public void setMemCache(String key, Object value) {
        memCacheRegion.put(key, value);
    }

    /**
     * 从内存缓存中获取对象
     *
     * @param key
     * @return
     */
    public Object getMemCache(String key) {
        return memCacheRegion.get(key);
    }

    /**
     * 保存磁盘缓存
     *
     * @param key
     * @param value
     * @throws IOException
     */
    public void setDiskCache(String key, String value) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.flush();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取磁盘缓存数据
     *
     * @param key
     * @return
     * @throws IOException
     */
    public String getDiskCache(String key) throws IOException {
        FileInputStream fis = null;
        try {
            fis = openFileInput("cache_" + key + ".data");
            byte[] datas = new byte[fis.available()];
            fis.read(datas);
            return new String(datas);
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public void delFileData(String file) {
        File data = getFileStreamPath(file);
        data.delete();
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Serializable readObject(String file) {
        if (!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }


}
