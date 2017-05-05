package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hyphenate.chat.EMClient;
import com.turo.ktalk.R;
import com.turo.ktalk.model.Model;
import com.turo.ktalk.model.bean.UserInfo;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //如果当前activity已经退出，不处理handler中的消息
            if(isFinishing()){
                return;
            }
            
            //判断进入主页面还是登录页面
            toMainOrLoginPager();
        }
    };

    private void toMainOrLoginPager() {
//        new Thread(){
//            @Override
//            public void run() {
//            }
//        }.start();

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否已登录过
                if (EMClient.getInstance().isLoggedInBefore()){//登录过
                    //从环信服务器获取到当前登录信息
                    UserInfo account = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());

                    if (account == null){
                        //跳转到登录页面
                        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{

                        // 登录成功后的方法
                        Model.getInstance().loginSuccess(account);

                        //跳转到主界面
                        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }else{//没登录过
                    //跳转到登录页面
                    Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                //结束当前页面
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //发送2s延时消息
        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁延时消息
        handler.removeCallbacksAndMessages(null);
    }
}
