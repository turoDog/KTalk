package com.turo.ktalk.model;

import android.content.Context;

import com.turo.ktalk.model.bean.UserInfo;
import com.turo.ktalk.model.dao.UserAccountDao;
import com.turo.ktalk.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by YQ950209 on 2017/4/23.
 * 数据模型层全局类
 */
public class Model {

    private Context mContext;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;
    private DBManager dbManager;

    //创建对象
    private static Model model = new Model();

    //私有化构造
    public Model() {
    }

    //获取单例对象
    public static Model getInstance(){

        return model;
    }

    //初始化的方法
    public void init(Context context){
        mContext = context;

        //创建用户账号数据库的操作类对象
        userAccountDao = new UserAccountDao(mContext);

        //开启全局监听
        EventListener eventListener = new EventListener(mContext);
    }


    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executorService;
    }

    //用户登录成功后的处理方法
    public void loginSuccess(UserInfo account) {
        // 校验
        if(account == null) {
            return;
        }

        if(dbManager != null) {
            dbManager.close();
        }

        dbManager = new DBManager(mContext, account.getName());
    }

    public DBManager getDbManager(){
        return dbManager;
    }

    //获取用户账号数据库的操作类对象
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
