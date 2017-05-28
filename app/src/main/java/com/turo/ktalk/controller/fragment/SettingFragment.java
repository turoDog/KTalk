package com.turo.ktalk.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.turo.ktalk.R;
import com.turo.ktalk.controller.activity.LoginActivity;
import com.turo.ktalk.model.Model;

/**
 * Created by YQ950209 on 2017/4/24.
 */
//设置页面
public class SettingFragment extends Fragment {

    private Button bt_setting_out;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bt_setting_out = (Button) view.findViewById(R.id.bt_setting_out);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {
        //在button上显示当前用户名称
        String string = getResources().getString(R.string.login_out);
        bt_setting_out.setText( string + "(" + EMClient.getInstance().getCurrentUser() + ")");
        //退出登录的逻辑处理
        bt_setting_out.setOnClickListener(v -> Model.getInstance().getGlobalThreadPool().execute(() -> {
            //访问环信服务器退出登录
            EMClient.getInstance().logout(false, new EMCallBack() {
                @Override
                public void onSuccess() {
                    // 关闭DBHelper
                    Model.getInstance().getDbManager().close();
                    getActivity().runOnUiThread(() -> {
                        //更新ui显示
                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                        //回到登录页面
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }

                @Override
                public void onError(int i, String s) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),"退出失败"+ s,Toast.LENGTH_SHORT).show());

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }));
    }
}
