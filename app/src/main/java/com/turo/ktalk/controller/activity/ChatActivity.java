package com.turo.ktalk.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.turo.ktalk.R;
import com.turo.ktalk.utils.Constant;

//会话详情页面
public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment easeChatFragment;
    private LocalBroadcastManager mLocalBroadcastManager;
    private int mChatType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intiData();

        initListener();
    }

    private void initListener() {
        easeChatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {
                Intent intent = new Intent(ChatActivity.this, GroupDetailActivity.class);
                // 群id
                intent.putExtra(Constant.GROUP_ID, mHxid);
                startActivity(intent);
            }

            @Override
            public void onAvatarClick(String username) {

            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });

        // 如果当前类型为群聊
        if(mChatType == EaseConstant.CHATTYPE_GROUP) {

            // 注册退群广播
            BroadcastReceiver ExitGroupReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if(mHxid.equals(intent.getStringExtra(Constant.GROUP_ID))) {
                        // 结束当前页面
                        finish();
                    }
                }
            };

            mLocalBroadcastManager.registerReceiver(ExitGroupReceiver, new IntentFilter(Constant.EXIT_GROUP));
        }
    }

    private void intiData() {
        // 创建一个会话的fragment
        easeChatFragment = new EaseChatFragment();

        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        // 获取聊天类型
        mChatType = getIntent().getExtras().getInt(EaseConstant.EXTRA_CHAT_TYPE);

        easeChatFragment.setArguments(getIntent().getExtras());

        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, easeChatFragment).commit();

        // 获取发送广播的管理者
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(ChatActivity.this);
    }
}
