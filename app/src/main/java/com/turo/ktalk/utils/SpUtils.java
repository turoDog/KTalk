package com.turo.ktalk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.turo.ktalk.InitApplication;

/**
 * Created by YQ950209 on 2017/5/3.
 */
//保存，获取数据
public class SpUtils {
    public static final String IS_NEW_INVITE = "is_new_invite";// 新的邀请标记
    private static SpUtils instance = new SpUtils();
    private static SharedPreferences mSharedPreferences;

    private SpUtils(){

    }

    //单例
    public static SpUtils getInstance(){

        if (mSharedPreferences == null){
            mSharedPreferences = InitApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }

        return instance;
    }

    //保存
    public void save(String key, Object value){

        if (value instanceof String){
            mSharedPreferences.edit().putString(key, (String) value).commit();
        }else if (value instanceof Boolean){
            mSharedPreferences.edit().putBoolean(key, (Boolean) value).commit();
        }else if (value instanceof Integer){
            mSharedPreferences.edit().putInt(key, (Integer) value).commit();
        }
    }

    //获取数据的方法
    public String getString(String key, String defValue){
        return mSharedPreferences.getString(key,defValue);
    }

    //获取boolean数据
    public boolean getBoolean(String key, boolean defValue){
        return mSharedPreferences.getBoolean(key,defValue);
    }

    //获取int数据
    public int getInt(String key, int defValue){
        return mSharedPreferences.getInt(key,defValue);
    }

}
