package com.turo.ktalk.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.turo.ktalk.R;
import com.turo.ktalk.model.bean.PickContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenzhiyuan on 2017/5/7.
 */
//// 选择联系人的页面适配器
public class PickContactAdapter extends BaseAdapter {

    private Context mContext;
    private List<PickContactInfo> mPicks = new ArrayList<>();
    private List<String> mExistMembers = new ArrayList<>();// 保存群中已经存在的成员集合

    public PickContactAdapter(Context context,List<PickContactInfo> picks, List<String> mExistMembers) {
        this.mContext = context;

        if (picks != null && picks.size() >= 0) {
            mPicks.clear();
            mPicks.addAll(picks);
        }



    }

    @Override
    public int getCount() {
        return mPicks == null ? 0 : mPicks.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 创建或获取viewHolder
        ViewHolder holder  = null;

        if(convertView == null) {
            holder = new ViewHolder();

            convertView = View.inflate(mContext, R.layout.item_pick, null);

            holder.cb = (CheckBox) convertView.findViewById(R.id.cb_pick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_pick_name);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取当前item数据
        PickContactInfo pickContactInfo = mPicks.get(position);

        // 显示数据
        holder.tv_name.setText(pickContactInfo.getUser().getName());
        holder.cb.setChecked(pickContactInfo.isChecked());

        // 判断
        if(mExistMembers.contains(pickContactInfo.getUser().getHxid())) {
            holder.cb.setChecked(true);
            pickContactInfo.setIsChecked(true);
        }
        return convertView;
    }

    private class  ViewHolder{
        private CheckBox cb;
        private TextView tv_name;
    }
}
