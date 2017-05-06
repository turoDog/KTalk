package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.turo.ktalk.R;
//选择联系人页面
public class PickContactActivity extends Activity {

    private TextView tv_pick_save;
    private ListView lv_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);

        initView();
    }

    private void initView() {
        tv_pick_save = (TextView) findViewById(R.id.tv_pick_save);
        lv_pick = (ListView) findViewById(R.id.lv_pick);
    }
}
