package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.turo.ktalk.R;
import com.turo.ktalk.controller.adapter.PickContactAdapter;
import com.turo.ktalk.model.Model;
import com.turo.ktalk.model.bean.PickContactInfo;
import com.turo.ktalk.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

//选择联系人页面
public class PickContactActivity extends Activity {

    private TextView tv_pick_save;
    private ListView lv_pick;
    private PickContactAdapter pickContactAdapter;
    private List<PickContactInfo> mPicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);

        initView();

        initData();
    }

    private void initData() {

        // 从本地数据库中获取所有的联系人信息
        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactTableDao().getContacts();

        mPicks = new ArrayList<>();

        if (contacts != null && contacts.size() >= 0) {
            // 转换
            for (UserInfo contact : contacts) {
                PickContactInfo pickContactInfo = new PickContactInfo(contact, false);
                mPicks.add(pickContactInfo);
            }
        }

        // 初始化listview
        pickContactAdapter = new PickContactAdapter(this, mPicks, mExistMembers);

        lv_pick.setAdapter(pickContactAdapter);
    }

    private void initView() {
        tv_pick_save = (TextView) findViewById(R.id.tv_pick_save);
        lv_pick = (ListView) findViewById(R.id.lv_pick);
    }
}
