package com.einyun.app.pms.mine.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.LoginNavigationCallbackImpl;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
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

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

@Route(path = RouterUtils.ACTIVITY_MESSAGE_CENTER)
public class MessageCenterActivity extends BaseHeadViewModelActivity<ActivityMessageCenterBinding, SignSetViewModel> implements ItemClickListener<MsgModel> {
    RVPageListAdapter<ItemMessageCenterBinding, MsgModel> adapter;
    private GetNodeIdRequest getNodeIdRequest;
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
    protected void initData() {
        super.initData();
        getNodeIdRequest = new GetNodeIdRequest();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            loadPagingData(new RequestPageBean(),"");
        });
        showLoading();
        loadPagingData(new RequestPageBean(),"");
        initAdapter();
        LiveEventBus.get(LiveDataBusKey.MSG_EMPTY_FRESH,String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.rlEmpty.setVisibility(View.GONE);
            }
        });

    }

    private void loadPagingData(RequestPageBean requestBean, String  tag){
//        初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData(requestBean,tag).observe(this, dataBeans ->{
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
    public void onRightOptionClick(View view){
        viewModel.allRead("", TimeUtil.getAllTime(System.currentTimeMillis())).observe(this, model->{
            loadPagingData(new RequestPageBean(),"");
        });
    }
    private void initAdapter() {
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemMessageCenterBinding, MsgModel>(this, com.einyun.app.pms.mine.BR.messageCenter,mDiffCallback){
                @Override
                public void onBindItem(ItemMessageCenterBinding itemBinding, MsgModel itemModel) {
                    if (itemModel.isHasRead()) {
                        itemBinding.ivMsgType.setAlpha(0.5f);
                    }else {
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
            return oldItem==newItem;
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MsgModel oldItem, @NonNull MsgModel newItem) {
            return oldItem==newItem;
        }
    };

    private static final String TAG = "MessageCenterActivity";
    @Override
    public void onItemClicked(View view, MsgModel msgModel) {
        viewModel.singleRead(msgModel.getId()).observe(this,model->{
            msgModel.setHasRead(true);
            adapter.notifyDataSetChanged();
        });
        MsgExtendVars msgExtendVars = new Gson().fromJson(msgModel.getExtendVars(), MsgExtendVars.class);

        if (msgExtendVars==null) {
            return;
        }
        switch (msgExtendVars.getType()) {
            case "grab"://抢单

                switch (msgExtendVars.getSubType()) {
                    case "repair"://报修
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_REPAIRS_PAGING)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .withString(RouteKey.KEY_TASK_ID, msgExtendVars.getContent().getTaskId())
                                .withString(RouteKey.KEY_CATE_NAME, msgExtendVars.getContent().getCateName())
                                .withBoolean(RouteKey.KEY_PUSH_JUMP, true)
                                .navigation(BasicApplication.getInstance(), new LoginNavigationCallbackImpl());
                        break;
                }

                break;
            case "end"://强制关闭
            case "copyto"://抄送
            case "reminder"://新待处理工单提醒
                switch (msgExtendVars.getSubType()) {
                    case "audit"://审批消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_APPROVAL_DETAIL)
                                .withString(RouteKey.KEY_PRO_INS_ID,msgExtendVars.getContent().getProcInstId())
                                .withString(RouteKey.KEY_TASK_ID,msgExtendVars.getContent().getTaskId())
                                .withString(RouteKey.KEY_APPROVAL_USER_STATE,"msgCenter")
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
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                                .withString(RouteKey.KEY_TASK_ID,"")
                                .withString(RouteKey.KEY_ORDER_ID,"")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
                                .withString(RouteKey.KEY_PRO_INS_ID,msgExtendVars.getContent().getProcInstId())
                                .navigation();
                        break;
                    case "patrol"://巡更工单消息
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                                .withString(RouteKey.KEY_TASK_ID,"")
                                .withString(RouteKey.KEY_ORDER_ID,"")
                                .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                .withString(RouteKey.KEY_TASK_NODE_ID,"")
                                .withString(RouteKey.KEY_PRO_INS_ID,msgExtendVars.getContent().getProcInstId())
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
                                .build(RouterUtils.ACTIVITY_INQUIRIES_ORDER_DETAIL)
                                .withString(RouteKey.FRAGMENT_TAG,FRAGMENT_TRANSFERRED_TO)
                                .withString(RouteKey.KEY_TASK_ID,msgExtendVars.getContent().getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID,msgExtendVars.getContent().getProcInstId())
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
                break;
        }

    }
    /**
     * 根据id获取nodeId
     */

    private void getNodeId(OrderListModel data) {
        viewModel.getNodeId(getNodeIdRequest, data);
    }
}
