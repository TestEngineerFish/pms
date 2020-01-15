package com.einyun.app.common.pushReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PushResultModel;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    Gson gson = new Gson();

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        super.onNotification(context, title, summary, extraMap);
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        //抢单
//        ARouter.getInstance()
//                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
//                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                .withString(RouteKey.KEY_TASK_ID, "1231")
//                .withString(RouteKey.KEY_CATE_NAME, "321")
//                .withBoolean(RouteKey.KEY_PUSH_JUMP, true)
//                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
        try {
            PushResultModel pushModel = gson.fromJson(extraMap, new TypeToken<PushResultModel>() {
            }.getType());
            //抢单提醒
            if ("grad".equals(pushModel.getType())) {
                if ("repair".equals(pushModel.getSubType())) {
                    //抢单
                    ARouter.getInstance()
                            .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                            .withString(RouteKey.KEY_CATE_NAME, pushModel.getContent().getCateName())
                            .withBoolean(RouteKey.KEY_PUSH_JUMP, true)
                            .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                }
            }
            //新待处理工单提醒
            if ("reminder".equals(pushModel.getType())) {

            }
        }catch (Exception e){

        }


    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}