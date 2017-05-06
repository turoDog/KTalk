package com.turo.ktalk.controller.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.turo.ktalk.R;
import com.turo.ktalk.controller.adapter.InviteAdapter;
import com.turo.ktalk.model.Model;
import com.turo.ktalk.model.bean.InvationInfo;
import com.turo.ktalk.utils.Constant;

import java.util.List;

public class InviteActivity extends Activity {

    private ListView lv_invite;
    private InviteAdapter inviteAdapter;
    private LocalBroadcastManager mLocalBroadcastManager;

    private InviteAdapter.OnInviteListener mOnInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(InvationInfo invationInfo) {
            //通知环信服务器，点击了接受按钮
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().
                                acceptInvitation(invationInfo.getUser().getHxid());

                        //数据库更新
                        Model.getInstance().getDbManager().getInviteTableDao().updateInvitationStatus(
                                InvationInfo.InvitationStatus.INVITE_ACCEPT,invationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //页面发生变化
                                Toast.makeText(InviteActivity.this,"已接受好友邀请",Toast.LENGTH_SHORT).show();

                                //刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }

        @Override
        public void onReject(InvationInfo invationInfo) {
            //通知环信服务器，点击了拒绝按钮
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().
                                declineInvitation(invationInfo.getUser().getHxid());

                        // 数据库变化
                        Model.getInstance().getDbManager().getInviteTableDao().
                                removeInvitation(invationInfo.getUser().getHxid());

                        //页面变化
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "已拒绝好友邀请", Toast.LENGTH_SHORT).show();

                                //刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        initView();

        initData();
    }

    private void initData() {
        //初始化LiatView
        inviteAdapter = new InviteAdapter(this,mOnInviteListener);

        lv_invite.setAdapter(inviteAdapter);
        
        //刷新方法
        refresh();

        // 注册邀请信息变化的广播
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(InviteChangedReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
    }

    //邀请信息变化广播接收器
    private BroadcastReceiver InviteChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //只要接收到邀请信息变化的广播，就刷新页面
            refresh();
        }
    };

    private void refresh() {
        // 获取数据库中的所有邀请信息
        List<InvationInfo> invitations = Model.getInstance().getDbManager().getInviteTableDao().getInVitations();

        // 刷新适配器
        inviteAdapter.refresh(invitations);
    }

    private void initView() {
        lv_invite = (ListView) findViewById(R.id.lv_invite);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(InviteChangedReceiver);
    }
}
