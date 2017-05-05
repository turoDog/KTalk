package com.turo.ktalk.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.turo.ktalk.model.dao.UserAccountTable;

/**
 * Created by YQ950209 on 2017/4/23.
 */

public class UserAccountDB extends SQLiteOpenHelper {

    //构造
    public UserAccountDB(Context context) {
        super(context, "account.db", null, 1);
    }

    //数据库创建得时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表的语句
        db.execSQL(UserAccountTable.CREATE_TAB);
    }

    //数据库更新的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
