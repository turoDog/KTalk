package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.turo.ktalk.R;
import com.turo.ktalk.controller.adapter.GroupListAdapter;

/**
 * 群组列表页面
 */
public class GroupListActivity extends Activity {

    private ListView lv_grouplist;
    private GroupListAdapter groupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        initView();

        initData();
    }

    private void initView() {
        // 获取listview对象
        lv_grouplist = (ListView)findViewById(R.id.lv_grouplist);

        // 添加头布局
        View headerView = View.inflate(this, R.layout.header_grouplist, null);
        lv_grouplist.addHeaderView(headerView);
    }

    private void initData() {
        // 初始化listview
        groupListAdapter = new GroupListAdapter(this);

        lv_grouplist.setAdapter(groupListAdapter);
    }

}
