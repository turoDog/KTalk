package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.turo.ktalk.R;
//群详情页面
public class GroupDetailActivity extends Activity {

    private GridView gv_groupdetail;
    private Button bt_groupdetail_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        initView();
    }

    private void initView() {
        gv_groupdetail = (GridView) findViewById(R.id.gv_groupdetail);
        bt_groupdetail_out = (Button) findViewById(R.id.bt_groupdetail_out);
    }
}
