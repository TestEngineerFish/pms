package com.einyun.app.pms.mine.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.util.LogTime;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.application.ThrowableParser;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.DisqualifiedDetailModel;
import com.einyun.app.common.model.IsCanDealModel;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.UrlxcgdGetInstBOModule;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.NetWorkUtils;
import com.einyun.app.library.core.net.EinyunHttpException;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.PlanInfo;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.pms.mine.BR;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityMessageCenterBinding;
import com.einyun.app.pms.mine.databinding.ItemMessageCenterBinding;
import com.einyun.app.pms.mine.model.MessageCenterModel;
import com.einyun.app.pms.mine.model.MsgExtendVars;
import com.einyun.app.pms.mine.model.MsgModel;
import com.einyun.app.pms.mine.model.RequestPageBean;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.SignSetViewModel;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

@Route(path = RouterUtils.ACTIVITY_MESSAGE_CENTER)
public class MessageCenterActivity extends BaseHeadViewModelActivity<ActivityMessageCenterBinding, SignSetViewModel> implements ItemClickListener<MsgModel> {
    RVPageListAdapter<ItemMessageCenterBinding, MsgModel> adapter;
    private GetNodeIdRequest getNodeIdRequest;
    private boolean isFlag = false;
    private MsgModel msgModel;
    private MsgExtendVars msgExtendVars;

    @Override
    protected SignSetViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SignSetViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_message_center));
        binding.setCallBack(this);
        setRightTxt(R.string.tv_sign_read);
        headBinding.tvRightTitle.setTextColor(getResources().getColor(R.color.blueTextColor));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadPagingData(new RequestPageBean(), "");
    }

    @Override
    protected void initData() {
        super.initData();
        getNodeIdRequest = new GetNodeIdRequest();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            loadPagingData(new RequestPageBean(), "");
        });
        showLoading();
        loadPagingData(new RequestPageBean(), "");
        initAdapter();
//        LiveEventBus.get(LiveDataBusKey.MSG_EMPTY_FRESH, String.class).observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                binding.rlEmpty.setVisibility(View.GONE);
//            }
//        });

        LiveDataBusUtils.getLiveBusData(binding.rlEmpty, LiveDataBusKey.MSGCENTER_EMPTY, this);
    }

    private void loadPagingData(RequestPageBean requestBean, String tag) {
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean, tag).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
            hideLoading();
        });

    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    /**
     * 标记已读
     */
    public void onRightOptionClick(View view) {
        viewModel.allRead("", TimeUtil.getAllTime(System.currentTimeMillis())).observe(this, model -> {
            loadPagingData(new RequestPageBean(), "");
        });
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemMessageCenterBinding, MsgModel>(this, com.einyun.app.pms.mine.BR.messageCenter, mDiffCallback) {
                @Override
                public void onBindItem(ItemMessageCenterBinding itemBinding, MsgModel itemModel) {
                    if (itemModel.isHasRead()) {
                        itemBinding.ivMsgType.setAlpha(0.5f);
                    } else {
                        itemBinding.ivMsgType.setAlpha(1f);

                    }

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_message_center;
                }
            };
        }
        binding.list.setLayoutManager(new LinearLayoutManager(MessageCenterActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.list.setFocusable(false);
        binding.list.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<MsgModel> mDiffCallback = new DiffUtil.ItemCallback<MsgModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MsgModel oldItem, @NonNull MsgModel newItem) {
            return oldItem == newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MsgModel oldItem, @NonNull MsgModel newItem) {
            return oldItem == newItem;
        }
    };

    private static final String TAG = "MessageCenterActivity";

    @Override
    public void onItemClicked(View view, MsgModel msgModel) {
        this.msgModel = msgModel;
        msgExtendVars = new Gson().fromJson(msgModel.getExtendVars(), MsgExtendVars.class);

        if (msgExtendVars == null) {
            return;
        }
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {

            ToastUtil.show(CommonApplication.getInstance(), "请连接网络后，进行处理");
            return;
        }
        switch (msgExtendVars.getType()) {
            case "grab"://抢单

                switch (msgExtendVars.getSubType()) {
                    case "repair"://报修

                        viewModel.isGrap(msgExtendVars.getContent().getTaskId()).observe(this, model2 -> {
                            Log.e(TAG, "onItemClicked: " + model2.isState());

                            isFlag = model2.isState();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isFlag) {
                                        ARouter.getInstance()
                                                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                                                .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                                                .withString(RouteKey.KEY_CATE_NAME, msgExtendVars.getContent().getCateName())
                                                .withBoolean(RouteKey.KEY_PUSH_JUMP, true)
                                                .navigation();
                                    } else {
                                        ToastUtil.show(MessageCenterActivity.this, "该抢单任务已失效");
                                    }
                                }
                            }, 500);
                        });
                        break;
                }

                break;
            case "bpmnApproval"://审批提醒
                viewModel.msgRep.getApprovalBasicInfo(msgExtendVars.getContent().getTaskId(), new CallBack<UrlxcgdGetInstBOModule>() {
                    @Override
                    public void call(UrlxcgdGetInstBOModule data) {
                        hideLoading();
                        Log.e(TAG, "call: ");
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                                .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                                .withInt(RouteKey.KEY_TAB_ID, 0)
//                        .withString(RouteKey.KEY_APPROVAL_USER_STATE,data.getUserAuditStatus())
                                .navigation();
                    }

                    @Override
                    public void onFaild(Throwable throwable) {
                        Log.e(TAG, "onFaild: ");
                        showMsg(throwable);
                        hideLoading();
                    }
                });

                break;
            case "bpmnApprovalEnd"://强制关闭
                ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                        .withInt(RouteKey.KEY_TAB_ID, 1)
//                        .withString(RouteKey.KEY_APPROVAL_USER_STATE,data.getUserAuditStatus())
                        .navigation();
                break;
            case "end"://强制关闭
            case "copyto"://抄送
                initCopyAndEnd(msgModel, msgExtendVars);
                break;
            case "reminder"://新待处理工单提醒
            case "turn"://转单
            case "commuSend"://沟通
            case "commuFeedBack"://反馈
                switch (msgExtendVars.getSubType()) {
                    case "audit"://审批消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                                .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                                .navigation();
                        break;
                    case "dispatch"://派工单消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkSendOrderTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "plan"://计划工单消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkPlanOrderTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "inspection"://巡查工单消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkPatrolTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "patrol"://巡更工单消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkPatrol2TaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "complain"://投诉工单消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkComTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "enquiry"://问询消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }

                                } else {
                                    checkTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "repair"://报修消息
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkRepairTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId(), msgExtendVars.getType());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });

                        break;
                    case "unqualified"://不合格单
                        viewModel.msgRep.checkIsCanDeal(msgExtendVars.getContent().getTaskId(), new CallBack<IsCanDealModel>() {
                            @Override
                            public void call(IsCanDealModel data) {
                                if (!data.isCanTurn()) {
                                    if (data.isShowDetail()) {

                                        initCopyAndEnd(msgModel, msgExtendVars);
                                    } else {
                                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                                    }
                                } else {
                                    checkQualityTaskId(msgExtendVars.getContent().getProcInstId(), msgExtendVars.getContent().getTaskId());
                                }
                            }

                            @Override
                            public void onFaild(Throwable throwable) {
                                ThrowableParser.onFailed(throwable);
                            }
                        });


                        break;
                }
                break;
        }
        viewModel.singleRead(msgModel.getId()).observe(this, model -> {
            msgModel.setHasRead(true);
            adapter.notifyDataSetChanged();
        });

    }

    private void initCopyAndEnd(MsgModel msgModel, MsgExtendVars msgExtendVars) {
        switch (msgExtendVars.getSubType()) {
            case "audit"://审批消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                        .withString(RouteKey.KEY_APPROVAL_USER_STATE, "msgCenter")
                        .navigation();
                break;
            case "dispatch"://派工单消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                        .navigation();
                break;
            case "plan"://计划工单消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                        .navigation();
                break;
            case "inspection"://巡查工单消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .navigation();
                break;
            case "patrol"://巡更工单消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .navigation();
                break;
            case "complain"://投诉工单消息
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                        .withString(RouteKey.KEY_ORDER_ID, "")
                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                        .navigation();
                break;
            case "enquiry"://问询消息
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
                        .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TRANSFERRED_TO)
                        .withString(RouteKey.KEY_TASK_ID, "")
                        .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
                        .navigation();
                break;
            case "repair"://报修消息
                getNodeIdRequest.setDefkey("customer_repair_flow");
//                        getNodeIdRequest.setId("74374815867208710");
                OrderListModel orderListModel = new OrderListModel();
                orderListModel.setID_(msgModel.getId());
                orderListModel.setInstance_id(msgExtendVars.getContent().getProcInstId());
                getNodeId(orderListModel);
//                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
//                                .withString(RouteKey.KEY_ORDER_ID, msgModel.getId())
//                                .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                                .withString(RouteKey.KEY_TASK_ID, "")
//                                .withString(RouteKey.KEY_PRO_INS_ID, msgExtendVars.getContent().getProcInstId())
//                                .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
//                                .navigation();

                break;
        }
    }

    /**
     * 根据id获取nodeId
     */

    private void getNodeId(OrderListModel data) {
        viewModel.getNodeId(getNodeIdRequest, data);
    }

    /**
     * 问询提前判断任务是否失效
     */
    public void checkTaskId(String proInsId, String taskId, String type) {
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
                @Override
                public void call(RepairsDetailModel data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                    case "commuFeedBack"://反馈
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
                                    .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TO_FOLLOW_UP)
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .navigation();
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

                    showMsg(throwable);
                }
            });
        }

    }

    /**
     * 投诉提前判断任务是否失效
     */
    public void checkComTaskId(String proInsId, String taskId, String type) {
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
                @Override
                public void call(RepairsDetailModel data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                        case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                                    .withString(RouteKey.KEY_ORDER_ID, "")
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
                                    .navigation();
                            break;
                        case "commuSend"://沟通
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .navigation();
                            break;
                    }
//                ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
//                        .withString(RouteKey.KEY_ORDER_ID, "")
//                        .withString(RouteKey.KEY_TASK_NODE_ID, "")
//                        .withString(RouteKey.KEY_TASK_ID, taskId)
//                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                        .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
//                        .navigation();
                }

                @Override
                public void onFaild(Throwable throwable) {

                    showMsg(throwable);
                }
            });
        }
    }

    /**
     * 报修提前判断任务是否失效
     */
    public void checkRepairTaskId(String proInsId, String taskId, String type) {
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.workOrderService.getRepairDetail("procInstId=" + proInsId + "&taskId=" + taskId, new CallBack<RepairsDetailModel>() {
                @Override
                public void call(RepairsDetailModel data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                    case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                                    .withString(RouteKey.KEY_ORDER_ID, "")
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW)
                                    .navigation();
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

                    showMsg(throwable);
                }
            });
        }

    }

    /**
     * 不合格单提前判断任务是否失效
     */
    public void checkQualityTaskId(String proInsId, String taskId) {
        /**
         * 获取详情信息
         */
        viewModel.msgRep.getTODODetailInfo(taskId, new CallBack<DisqualifiedDetailModel>() {
            @Override
            public void call(DisqualifiedDetailModel data) {

                if (data == null) {
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    return;
                }
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_DISQUALIFIED_DETAIL)
                        .withString(RouteKey.KEY_TASK_ID, taskId)
                        .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                        .withString(RouteKey.KEY_ID, data.getData().getUnqualified_model().getId_())
                        .withString(RouteKey.FRAGMENT_TAG, RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW)
                        .navigation();
            }

            @Override
            public void onFaild(Throwable throwable) {
                showMsg(throwable);
            }
        });
    }

    /**
     * 派工单提前判断任务是否失效
     */
    public void checkSendOrderTaskId(String proInsId, String taskId, String type) {
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.service.distributeWaitDetial(taskId, new CallBack<DisttributeDetialModel>() {
                @Override
                public void call(DisttributeDetialModel data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                    case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                                    .withString(RouteKey.KEY_ORDER_ID, "")
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                    .navigation();
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
                    showMsg(throwable);
                }
            });
        }

    }

    private void showMsg(Throwable throwable) {
        try {
            if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
                //连接错误
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                ToastUtil.show(CommonApplication.getInstance(), com.einyun.app.common.R.string.toast_error_net);
                Looper.loop();
            } else if (throwable instanceof EinyunHttpException) {
                //API业务错误
                EinyunHttpException exception = (EinyunHttpException) throwable;
                if (exception.getResponse().getCode().equals("34516")) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    Toast.makeText(CommonApplication.getInstance(), "该任务已处理完成", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                    Looper.loop();
                }
            } else {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                Toast.makeText(CommonApplication.getInstance(), "该任务已处理完成", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计划工单提前判断任务是否失效
     */
    public void checkPlanOrderTaskId(String proInsId, String taskId, String type) {
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.service.planOrderDetail(taskId, new CallBack<PlanInfo>() {
                @Override
                public void call(PlanInfo data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
                        case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                    .withString(RouteKey.KEY_ORDER_ID, data.getData().getZyjhgd().getId_())
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
//                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING)
                                    .navigation();
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
                    showMsg(throwable);
                }
            });
        }
    }

    /**
     * 巡查工单提前判断任务是否失效
     */
    public void checkPatrolTaskId(String proInsId, String taskId, String type) {
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskId(taskId);
        viewModel.request.setTaskNodeId("9");
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.service.patrolPendingDetial(viewModel.request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
                @Override
                public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                    case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_ORDER_ID, data.getData().getInfo().getId_())
                                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .navigation();
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

                    showMsg(throwable);

                }
            });
        }
    }

    /**
     * 巡更工单提前判断任务是否失效
     */
    public void checkPatrol2TaskId(String proInsId, String taskId, String type) {
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskId(taskId);
        viewModel.request.setTaskNodeId("UserTask1");
        if ("commuFeedBack".equals(type)) {
            initCopyAndEnd(msgModel, msgExtendVars);
        } else {
            viewModel.service.patrolPendingDetial(viewModel.request, new CallBack<com.einyun.app.library.resource.workorder.model.PatrolInfo>() {
                @Override
                public void call(com.einyun.app.library.resource.workorder.model.PatrolInfo data) {
                    if (data == null) {
                        ToastUtil.show(CommonApplication.getInstance(), "该任务已处理完成");
                        return;
                    }
                    switch (type) {
                        case "reminder"://新待处理工单提醒
                        case "turn"://转单
//                    case "commuFeedBack"://反馈
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                                    .withString(RouteKey.KEY_TASK_ID, taskId)
                                    .withString(RouteKey.KEY_ORDER_ID, data.getData().getInfo().getId_())
                                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.PENDING.getType())
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                    .navigation();
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
                    showMsg(throwable);
                }
            });
        }
    }
}
