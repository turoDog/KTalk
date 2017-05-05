package com.turo.ktalk.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.turo.ktalk.model.bean.UserInfo;
import com.turo.ktalk.model.db.UserAccountDB;

/**
 * Created by YQ950209 on 2017/4/23.
 */
//用户账号数据库的操作类
public class UserAccountDao {

    //有这个对象才能对数据库进行操作
    private final UserAccountDB mHelper;

    public UserAccountDao(Context context) {
        this.mHelper = new UserAccountDB(context);
    }

    //添加用户到数据库
    public void addAccount (UserInfo user){
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID,user.getHxid());
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_NICK,user.getNick());
        values.put(UserAccountTable.COL_PHOTO,user.getPhoto());

        db.replace(UserAccountTable.TAB_NAME,null,values);
    }

    //根据环信ID获取所有用户信息
    public UserInfo getAccountByHxId(String hxId){
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //执行查询语句
        String sql = "select * from " + UserAccountTable.TAB_NAME + " where " + UserAccountTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();

            //封装对象
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }
        //关闭资源
        cursor.close();

        //返回数据
        return userInfo;
    }

}
