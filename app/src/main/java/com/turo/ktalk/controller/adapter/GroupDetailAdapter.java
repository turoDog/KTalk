package com.turo.ktalk.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.turo.ktalk.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenzhiyuan on 2017/5/7.
 */

public class GroupDetailAdapter extends BaseAdapter {

    private Context mContext;
    private boolean mIsCanModify;// 是否允许添加和删除群成员
    private List<UserInfo> mUsers = new ArrayList<>();

    public GroupDetailAdapter(Context context, boolean isCanModify) {
        mContext = context;
        mIsCanModify = isCanModify;
    }

    // 刷新数据
    public void refresh(List<UserInfo> users) {

        if (users != null && users.size() >= 0) {
            mUsers.clear();

            // 添加加号和减号
            initUsers();

            mUsers.addAll(0, users);
        }

        notifyDataSetChanged();
    }

    private void initUsers() {
        UserInfo add = new UserInfo("add");
        UserInfo delete = new UserInfo("delete");

        mUsers.add(delete);
        mUsers.add(0, add);
    }

    @Override
    public int getCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        return convertView;
    }
}
