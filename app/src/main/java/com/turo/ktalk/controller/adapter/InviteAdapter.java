package com.turo.ktalk.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.turo.ktalk.R;
import com.turo.ktalk.model.bean.InvationInfo;
import com.turo.ktalk.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YQ950209 on 2017/5/4.
 */
//邀请信息列表页面的适配器
public class InviteAdapter extends BaseAdapter {
    private Context mContext;
    private List<InvationInfo> mInvationInfos = new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    private InvationInfo invationInfo;

    public InviteAdapter(Context context,OnInviteListener onInviteListener) {
        mContext = context;

        mOnInviteListener = onInviteListener;
    }

    //刷新数据的方法
    public void refresh(List<InvationInfo> invationInfos) {
        if (invationInfos != null && invationInfos.size() >= 0) {
            mInvationInfos.clear();//清空邀请信息

            mInvationInfos.addAll(invationInfos);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvationInfos == null ? 0 : mInvationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1、获取或创建一个ViewHolder
        ViewHolder hodler = null;
        if (convertView == null) {
            hodler = new ViewHolder();

            convertView = View.inflate(mContext, R.layout.item_invite, null);

            hodler.name = (TextView) convertView.findViewById(R.id.tv_invite_name);
            hodler.reason = (TextView) convertView.findViewById(R.id.tv_invite_reason);

            hodler.accept = (Button) convertView.findViewById(R.id.bt_invite_accept);
            hodler.reject = (Button) convertView.findViewById(R.id.bt_invite_reject);

            convertView.setTag(hodler);
        } else {
            hodler = (ViewHolder) convertView.getTag();
        }
        // 2 获取当前item数据
        invationInfo = mInvationInfos.get(position);

        // 3 显示当前item数据
        UserInfo user = invationInfo.getUser();

        if (user != null) {// 联系人
            // 名称的展示
            hodler.name.setText(invationInfo.getUser().getName());

            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);

            // 原因
            if (invationInfo.getStatus() == InvationInfo.InvitationStatus.NEW_INVITE) {// 新的邀请

                if (invationInfo.getReason() == null) {
                    hodler.reason.setText("添加好友");
                } else {
                    hodler.reason.setText(invationInfo.getReason());
                }

                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

            } else if (invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT) {// 接受邀请

                if (invationInfo.getReason() == null) {
                    hodler.reason.setText("接受邀请");
                } else {
                    hodler.reason.setText(invationInfo.getReason());
                }
            } else if (invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {// 邀请被接受
                if (invationInfo.getReason() == null) {
                    hodler.reason.setText("邀请被接受");
                } else {
                    hodler.reason.setText(invationInfo.getReason());
                }
            }

            // 按钮的处理
            hodler.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onAccept(invationInfo);
                }
            });

            // 拒绝按钮的点击事件处理
            hodler.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onReject(invationInfo);
                }
            });
        }else{// 群组
            // 显示名称
            hodler.name.setText(invationInfo.getGroup().getInvatePerson());

            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);

            // 显示原因
            switch(invationInfo.getStatus()){
                // 您的群申请请已经被接受
                case GROUP_APPLICATION_ACCEPTED:
                    hodler.reason.setText("您的群申请已经被接受");
                    break;
                //  您的群邀请已经被接收
                case GROUP_INVITE_ACCEPTED:
                    hodler.reason.setText("您接受了群邀请");
                    break;

                // 你的群申请已经被拒绝
                case GROUP_APPLICATION_DECLINED:
                    hodler.reason.setText("你的群申请已经被拒绝");
                    break;

                // 您的群邀请已经被拒绝
                case GROUP_INVITE_DECLINED:
                    hodler.reason.setText("您的群邀请已经被拒绝");
                    break;

                // 您收到了群邀请
                case NEW_GROUP_INVITE:
                    hodler.accept.setVisibility(View.VISIBLE);
                    hodler.reject.setVisibility(View.VISIBLE);

                    // 接受邀请
                    hodler.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteAccept(invationInfo);
                        }
                    });

                    // 拒绝邀请
                    hodler.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteReject(invationInfo);
                        }
                    });

                    hodler.reason.setText("您收到了群邀请");
                    break;

                // 您收到了群申请
                case NEW_GROUP_APPLICATION:
                    hodler.accept.setVisibility(View.VISIBLE);
                    hodler.reject.setVisibility(View.VISIBLE);

                    // 接受申请
                    hodler.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationAccept(invationInfo);
                        }
                    });

                    // 拒绝申请
                    hodler.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationReject(invationInfo);
                        }
                    });

                    hodler.reason.setText("您收到了群申请");
                    break;

                // 你接受了群邀请
                case GROUP_ACCEPT_INVITE:
                    hodler.reason.setText("你接受了群邀请");
                    break;

                // 您批准了群申请
                case GROUP_ACCEPT_APPLICATION:
                    hodler.reason.setText("您批准了群申请");
                    break;

                // 您拒绝了群邀请
                case GROUP_REJECT_INVITE:
                    hodler.reason.setText("您拒绝了群邀请");
                    break;

                // 您拒绝了群申请
                case GROUP_REJECT_APPLICATION:
                    hodler.reason.setText("您拒绝了群申请");
                    break;
            }
        }
            //4、返回view
            return convertView;
    }

    private class ViewHolder{
        private TextView name;
        private TextView reason;

        private Button accept;
        private Button reject;
    }

    public interface OnInviteListener {
        // 联系人接受按钮的点击事件
        void onAccept(InvationInfo invationInfo);

        // 联系人拒绝按钮的点击事件
        void onReject(InvationInfo invationInfo);

        // 接受邀请按钮处理
        void onInviteAccept(InvationInfo invationInfo);
        // 拒绝邀请按钮处理
        void onInviteReject(InvationInfo invationInfo);

        // 接受申请按钮处理
        void onApplicationAccept(InvationInfo invationInfo);
        // 拒绝申请按钮处理
        void onApplicationReject(InvationInfo invationInfo);
    }
}
