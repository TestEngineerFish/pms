package com.einyun.app.common.pushReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PushResultModel;
import com.einyun.app.common.repository.MsgRepository;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.common.viewmodel.MsgModelFactory;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    Gson gson = new Gson();
    public static MediaPlayer player;
    private BaseWorkOrderHandelViewModel viewModel;
    private MsgRepository repository;


    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        super.onNotification(context, title, summary, extraMap);
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        //通知刷新界面
        LiveEventBus.get(LiveDataBusKey.BELL_STATE_FRESH, String.class).post("");
        try {
            if (extraMap.containsKey("type") && extraMap.containsKey("subType")) {
                String type = extraMap.get("type");
                String subType = extraMap.get("subType");

                //抢单提醒
                if ("grab".equals(type)) {
                    if ("repair".equals(subType)) {
                        //抢单
                        initVoice(context, R.raw.repair_grap);
                    }
                }
                //新待处理工单提醒
                if ("reminder".equals(type)) {

                    switch (subType) {
                        case "audit"://审批消息

                            break;
                        case "dispatch"://派工单消息

                            break;
                        case "plan"://计划工单消息

                            break;
                        case "inspection"://巡查工单消息

                            break;
                        case "complain"://投诉工单消息
//                            initVoice(context, R.raw.complaint_response);
                            break;
                        case "enquiry"://问询消息
//                            initVoice(context, R.raw.query_transaction);
                            break;
                        case "repair"://报修消息
//                            initVoice(context, R.raw.repair_grap);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("message", e.getMessage());
        }
    }

    private void initVoice(Context context, int resId) {
        if (player != null) {
            player.release();
            player = null;
            player = MediaPlayer.create(context, resId);
            player.start();
        } else {
            player = MediaPlayer.create(context, resId);
            player.start();
        }
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
            if ("grab".equals(pushModel.getType())) {
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
            if ("reminder".equals(pushModel.getType())||"end".equals(pushModel.getType())||"copyto".equals(pushModel.getType())) {
                switch (pushModel.getSubType()) {
                    case "audit"://审批消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                                .withString(RouteKey.KEY_APPROVAL_USER_STATE, "msgReceiver")
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "dispatch"://派工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "plan"://计划工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "inspection"://巡查工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "patrol"://巡更工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID,"")
                                .withString(RouteKey.KEY_ORDER_ID,"")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
                                .withString(RouteKey.KEY_PRO_INS_ID,pushModel.getContent().getInstId())
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "complain"://投诉工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "enquiry"://问询消息
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_ORDER_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TRANSFERRED_TO)
                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "repair"://报修消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());

                        break;
                }
            }
            if (repository==null) {
                repository = new MsgRepository();
            }
            repository.singleRead(pushModel.getMsgId(), new CallBack<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {

                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });

        } catch (Exception e) {
            Log.e("message", e.getMessage());
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