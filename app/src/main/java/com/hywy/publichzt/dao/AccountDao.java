package com.hywy.publichzt.dao;

import android.content.Context;

import com.cs.android.util.Crypto;
import com.cs.common.basedao.BaseDao;
import com.cs.common.utils.SharedUtils;
import com.google.gson.Gson;
import com.hywy.publichzt.app.App;
import com.hywy.publichzt.entity.Account;


/**
 * 登陆用户的数据库接口
 *
 * @author james
 */
public class AccountDao extends BaseDao {
    String pwd = "0F87D8209B29A5D1239043D4A14E8AC8";
    String tabName = "accountDb";
    String accountName = "account";

    public AccountDao(Context context) {
        super(context);
    }

    /**
     * 存储用户登录数据
     *
     * @param account
     */
    public void replace(Account account) {
        String encrypt = encryptPassword(new Gson().toJson(account), pwd);
        SharedUtils.setString(App.getInstance().getApplicationContext(), tabName, accountName, new Gson().toJson(account));
    }

    /**
     * 存储用户登录数据
     */
    public Account select() {
        String account = SharedUtils.getString(App.getInstance().getApplicationContext(),tabName,accountName,"");
        String decrypt = decryptPassword(account,pwd);
        return new Gson().fromJson(account,Account.class);
    }

    /**
     * 删除登录用户的信息
     */
    public boolean delete() {
        SharedUtils.clear(App.getInstance().getApplicationContext(),tabName);
        return true;
    }


    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password, String userid) {
        return Crypto.encryptString(password, userid.getBytes());
    }

    /**
     * 解密密码
     *
     * @param password
     * @return
     */
    private String decryptPassword(String password, String userid) {
        return Crypto.decryptString(password, userid.getBytes());
    }
}
