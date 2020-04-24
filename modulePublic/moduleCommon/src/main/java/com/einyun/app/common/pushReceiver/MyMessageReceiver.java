package com.einyun.app.common.pushReceiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ContentModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PushResultModel;
import com.einyun.app.common.model.UrlxcgdGetInstBOModule;
import com.einyun.app.common.repository.MsgRepository;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseVMFactory;
import com.einyun.app.common.ui.component.blockchoose.viewmodel.BlockChooseViewModel;
import com.einyun.app.common.utils.MsgUtils;
import com.einyun.app.common.viewmodel.BaseWorkOrderHandelViewModel;
import com.einyun.app.common.viewmodel.MsgModelFactory;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

public class MyMessageReceiver extends MessageReceiver{
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
            if (extraMap.containsKey("type") && extraMap.containsKey("subType")&& extraMap.containsKey("content")) {
                String type = extraMap.get("type");
                String subType = extraMap.get("subType");
                String content = extraMap.get("content");
                Log.e(TAG, "onNotification: contrent--== "+content );
                ContentModel contentModel = new Gson().fromJson(content, ContentModel.class);
                Log.e(TAG, "onNotification: getTaskId==="+contentModel.getTaskId() );

                //抢单提醒
                if ("grab".equals(type)) {
                    if ("repair".equals(subType)) {
                        //抢单
                        initVoice(context, R.raw.repair_grap);
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID, contentModel.getTaskId() )
                                .withString(RouteKey.KEY_CATE_NAME, contentModel.getCateName())
                                .withBoolean(RouteKey.KEY_PUSH_JUMP, true)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
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
//        MsgUtils.setBadgeNum(1, CommonApplication.getInstance());
//        MsgUtils.setGoogleBadge(CommonApplication.getInstance(),"1");
//        MsgUtils.setSanXingBadge(CommonApplication.getInstance(),1);
//        MsgUtils.setVivoBadge(CommonApplication.getInstance(),"1");
//        MsgUtils.setXiaoMiBadge(CommonApplication.getInstance(),1);
        MsgUtils.setBadge(CommonApplication.getInstance(),1);
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

        if (repository==null) {
            repository = new MsgRepository();
        }
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
            switch (pushModel.getType()) {
                case "bpmnApproval"://审批提醒
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            repository.getApprovalBasicInfo(pushModel.getContent().getTaskId(), new CallBack<UrlxcgdGetInstBOModule>() {
                                @Override
                                public void call(UrlxcgdGetInstBOModule data) {

                                    Log.e("MyMeessage", "call: ");
                                    ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                            .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                                            .withInt(RouteKey.KEY_TAB_ID, 0)
//                        .withString(RouteKey.KEY_APPROVAL_USER_STATE,data.getUserAuditStatus())
                                            .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                }

                                @Override
                                public void onFaild(Throwable throwable) {
                                    Log.e("MyMeessage", "onFaild: ");
                                    showMsg();

                                }
                            });
                        }
                    },1000);


                    break;
                case "end"://强制关闭
                case "copyto"://抄送
                case "commuFeedBack"://反馈
                    initCopyAndEnd(pushModel);
                    break;

                case "reminder"://新待处理工单提醒
                case "turn"://转单
                case "commuSend"://沟通
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switch (pushModel.getSubType()) {
                                case "audit"://审批消息
                                    ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                                            .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
                                            .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    break;
                                case "dispatch"://派工单消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_ORDER_ID, "")
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_TASK_ID, "")
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkSendOrderTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "plan"://计划工单消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_ORDER_ID, "")
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_TASK_ID, "")
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkPlanOrderTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "inspection"://巡查工单消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_TASK_ID, "")
//                                .withString(RouteKey.KEY_ORDER_ID, "")
//                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkPatrolTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "patrol"://巡更工单消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_TASK_ID,"")
//                                .withString(RouteKey.KEY_ORDER_ID,"")
//                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
//                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
//                                .withString(RouteKey.KEY_PRO_INS_ID,pushModel.getContent().getInstId())
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkPatrol2TaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "complain"://投诉工单消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_ORDER_ID, "")
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_TASK_ID, "")
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkComTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "enquiry"://问询消息
//                        ARouter.getInstance()
//                                .build(RouterUtils.ACTIVITY_INQUIRIES_ORDER_DETAIL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TRANSFERRED_TO)
//                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());
                                    break;
                                case "repair"://报修消息
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
//                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                .withString(RouteKey.KEY_ORDER_ID, "")
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
//                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
//                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                                    checkRepairTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId(),pushModel.getType());

                                    break;
                            }
                        }
                    },1000);


                    break;
            }
//            if ("end".equals(pushModel.getType())||"copyto".equals(pushModel.getType())||"commuFeedBack".equals(pushModel.getType())) {
//                initCopyAndEnd(pushModel);
//            }else if ("reminder".equals(pushModel.getType())){
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        switch (pushModel.getSubType()) {
//                            case "audit"://审批消息
//                                ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
//                                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                        .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
//                                        .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
//                                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                break;
//                            case "dispatch"://派工单消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_ORDER_ID, "")
////                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                                .withString(RouteKey.KEY_TASK_ID, "")
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkSendOrderTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "plan"://计划工单消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_ORDER_ID, "")
////                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                                .withString(RouteKey.KEY_TASK_ID, "")
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkPlanOrderTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "inspection"://巡查工单消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_TASK_ID, "")
////                                .withString(RouteKey.KEY_ORDER_ID, "")
////                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
////                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkPatrolTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "patrol"://巡更工单消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_TASK_ID,"")
////                                .withString(RouteKey.KEY_ORDER_ID,"")
////                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
////                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
////                                .withString(RouteKey.KEY_PRO_INS_ID,pushModel.getContent().getInstId())
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkPatrol2TaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "complain"://投诉工单消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_ORDER_ID, "")
////                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                                .withString(RouteKey.KEY_TASK_ID, "")
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkComTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "enquiry"://问询消息
////                        ARouter.getInstance()
////                                .build(RouterUtils.ACTIVITY_INQUIRIES_ORDER_DETAIL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TRANSFERRED_TO)
////                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//                                break;
//                            case "repair"://报修消息
////                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
////                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
////                                .withString(RouteKey.KEY_ORDER_ID, "")
////                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                                .withString(RouteKey.KEY_TASK_ID, pushModel.getContent().getTaskId())
////                                .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
////                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
////                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//                                checkRepairTaskId(pushModel.getContent().getInstId(),pushModel.getContent().getTaskId());
//
//                                break;
//                        }
//                    }
//                },1000);
//
//            }
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

    private void showMsg() {

    }

    private void initCopyAndEnd(PushResultModel pushModel) {
        switch (pushModel.getSubType()) {
            case "audit"://审批消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                        .withString(RouteKey.KEY_TASK_ID, "")
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
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
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
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                break;
            case "repair"://报修消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, pushModel.getContent().getInstId())
                        .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());

                break;
        }
    }

    /**
     * 问询提前判断任务是否失效
     */
    public  void checkTaskId(String proInsId,String taskId,String type){
//        repository.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId).observe(this, repairsDetail -> {
//            if (repairsDetail==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance()
////                    .build(RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
////                    .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_TO_FOLLOW_UP)
////                    .withString(RouteKey.KEY_TASK_ID,taskId)
////                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
////                    .navigation();
//            ARouter.getInstance()
//                    .build(RouterUtils.ACTIVITY_INQUIRIES_ORDER_DETAIL)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_TO_FOLLOW_UP)
//                    .withString(RouteKey.KEY_TASK_ID, taskId)
//                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });
        repository.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
//                hideLoading();
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_TO_FOLLOW_UP)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }

            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
//                ThrowableParser.onFailed(throwable);
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

    }
    /**
     * 投诉提前判断任务是否失效
     */
    public  void checkComTaskId(String proInsId,String taskId,String type){
//        repository.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId).observe(this, repairsDetail -> {
//            Log.e(TAG, "checkComTaskId: "+repairsDetail );
//            if (repairsDetail==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
////                    .withString(RouteKey.KEY_ORDER_ID, "")
////                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                    .withString(RouteKey.KEY_TASK_ID, taskId)
////                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
////                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
////                    .navigation();
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.KEY_ORDER_ID, "")
//                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                    .withString(RouteKey.KEY_TASK_ID, taskId)
//                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });

        repository.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
//                hideLoading();
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                        .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
//                ThrowableParser.onFailed(throwable);
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    /**
     * 报修提前判断任务是否失效
     */
    public  void checkRepairTaskId(String proInsId,String taskId,String type){
//        repository.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId).observe(this, repairsDetail -> {
//            Log.e(TAG, "checkComTaskId: "+repairsDetail );
//            if (repairsDetail==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
////                    .withString(RouteKey.KEY_ORDER_ID, "")
////                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                    .withString(RouteKey.KEY_TASK_ID, taskId)
////                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
////                    .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
////                    .navigation();
//
//
//        });
        repository.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
            @Override
            public void call(RepairsDetailModel data) {
//                hideLoading();
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                        .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
//                ThrowableParser.onFailed(throwable);
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    /**
     * 派工单提前判断任务是否失效
     */
    public  void checkSendOrderTaskId(String proInsId,String taskId,String type){
//        repository.pendingDetial(taskId).observe(this, model -> {
//            if (model==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.KEY_ORDER_ID, "")
//                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                    .withString(RouteKey.KEY_TASK_ID, taskId)
//                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
//                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });
        repository.service.distributeWaitDetial(taskId, new CallBack<DisttributeDetialModel>() {
            @Override
            public void call(DisttributeDetialModel data) {
//                workOrderLiveData.postValue(data);
//                hideLoading();
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    Log.e(TAG, "call: data==null" );
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
//                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                Log.e(TAG, "call: " +isMainThread());
            }

            @Override
            public void onFaild(Throwable throwable) {
//                hideLoading();
//                workOrderLiveData.postValue(null);
                Log.e(TAG, "call: data==null===onFaild" );
                Log.e(TAG, "call: data==null===onFaild" +isMainThread());
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
    /**
     * 计划工单提前判断任务是否失效
     */
    public  void checkPlanOrderTaskId(String proInsId,String taskId,String type){
//        repository.loadDetail(proInsId,taskId).observe(this, model -> {
//            if (model==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
////                    .withString(RouteKey.KEY_ORDER_ID, "")
////                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
////                    .withString(RouteKey.KEY_TASK_ID, taskId)
////                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//////                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
////                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
////                    .navigation();
//
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.KEY_ORDER_ID, "")
//                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                    .withString(RouteKey.KEY_TASK_ID, taskId)
//                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });
        repository.service.planOrderDetail(taskId, new CallBack<PlanInfo>() {
            @Override
            public void call(PlanInfo data) {
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_ORDER_ID, data.getData().getZyjhgd().getId_())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                        .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
            }

            @Override
            public void onFaild(Throwable throwable) {
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    /**
     * 巡查工单提前判断任务是否失效
     */
    public  void checkPatrolTaskId(String proInsId,String taskId,String type){
        repository.request.setProInsId(proInsId);
        repository.request.setTaskId(taskId);
        repository.request.setTaskNodeId("UserTask1");

//        repository.loadPendingDetial().observe(this, model -> {
//            if (model==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
////                    .withString(RouteKey.KEY_TASK_ID,taskId)
////                    .withString(RouteKey.KEY_ORDER_ID,"")
////                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
////                    .withString(RouteKey.KEY_TASK_NODE_ID,"")
////                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
////                    .navigation();
//
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.KEY_TASK_ID, taskId)
//                    .withString(RouteKey.KEY_ORDER_ID, "")
//                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });
        repository.service.patrolPendingDetial(repository.request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_ORDER_ID, "")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
            }

            @Override
            public void onFaild(Throwable throwable) {
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
    /**
     * 巡更工单提前判断任务是否失效
     */
    public  void checkPatrol2TaskId(String proInsId,String taskId,String type){
        repository.request.setProInsId(proInsId);
        repository.request.setTaskId(taskId);
        repository.request.setTaskNodeId("UserTask1");

//        repository.loadPendingDetial().observe(this, model -> {
//            if (model==null) {
//                ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
//                return;
//            }
////            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
////                    .withString(RouteKey.KEY_TASK_ID,taskId)
////                    .withString(RouteKey.KEY_ORDER_ID,"")
////                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
////                    .withString(RouteKey.KEY_TASK_NODE_ID,"")
////                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
////                    .navigation();
//
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    .withString(RouteKey.KEY_TASK_ID,taskId)
//                    .withString(RouteKey.KEY_ORDER_ID,"")
//                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                    .withString(RouteKey.KEY_TASK_NODE_ID,"")
//                    .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
//                    .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
//        });
        repository.service.patrolPendingDetial(repository.request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
            @Override
            public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                if (data==null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                switch (type) {
                    case "reminder"://新待处理工单提醒
                    case "turn"://转单
//                    case "commuFeedBack"://反馈
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID,taskId)
                                .withString(RouteKey.KEY_ORDER_ID,"")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
                                .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                    case "commuSend"://沟通
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .navigation();
                        break;
                }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
//                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        .withString(RouteKey.KEY_TASK_ID,taskId)
//                        .withString(RouteKey.KEY_ORDER_ID,"")
//                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
//                        .withString(RouteKey.KEY_TASK_NODE_ID,"")
//                        .withString(RouteKey.KEY_PRO_INS_ID,proInsId)
//                        .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
            }

            @Override
            public void onFaild(Throwable throwable) {
                if (Looper.myLooper()==null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(),"该任务已处理完成",Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
        });
    }
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
        MsgUtils.setBadge(CommonApplication.getInstance(),0);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}