package com.turo.ktalk.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenzhiyuan on 2017/5/7.
 */

public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private List<EMGroup> mGroups = new ArrayList<>();

    public GroupListAdapter(Context context) {
        this.mContext = context;
    }

    // 刷新方法
    public void refresh(List<EMGroup> groups) {

        if (groups != null && groups.size() >= 0) {
            mGroups.clear();

            mGroups.addAll(groups);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
