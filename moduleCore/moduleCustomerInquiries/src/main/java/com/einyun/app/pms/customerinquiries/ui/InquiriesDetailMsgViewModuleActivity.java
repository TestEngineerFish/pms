package com.einyun.app.pms.customerinquiries.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.library.resource.workorder.model.ComplainOrderState;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.customerinquiries.BR;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.ActivityInquiriesDetailMsgViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ActivityInquiriesDetailViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ItemInquiriseFeedbackHistoryLayoutBinding;
import com.einyun.app.pms.customerinquiries.model.DealRequest;
import com.einyun.app.pms.customerinquiries.model.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.model.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.model.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.model.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.model.OrderDetailInfoModule;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.viewmodule.InquiriesDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
public class InquiriesDetailMsgViewModuleActivity extends BaseHeadViewModelActivity<ActivityInquiriesDetailMsgViewModuleBinding, InquiriesDetailViewModel> {
    //    @Autowired(name = Constants.INQUIRIES_BEAN);
//    Serializable data;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String mTaskID;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String mProInsId;
    @Autowired(name = RouteKey.FRAGMENT_TAG)
    String fragment;
    private PhotoListAdapter photoListInfoAdapter;
    private PhotoListAdapter forseClosephotoListInfoAdapter;
    private int evaluation = 1;
    private AlertDialog alertDialog;
    private OrderDetailInfoModule orderDetailInfoModule;
    private RVBindingAdapter<ItemInquiriseFeedbackHistoryLayoutBinding, OrderDetailInfoModule.HandleListBean> adapter;
    private boolean isApplyForseClose;
    private String createTime;
    private InquiriesDetailModule.DataBean.CustomerEnquiryModelBean detail;
    private boolean isSendOrder;
    private boolean isResponse;
    private boolean isCall;
    private String state;
    private DealRequest mDealRequest;

    @Override
    protected InquiriesDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new CustomerInquiriesViewModelFactory()).get(InquiriesDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inquiries_detail_msg_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_customer_inquiries);
        setRightOption(R.drawable.iv_histroy);
        setRightTxt(R.string.tv_history);
        setRightTxtColor(R.color.blueTextColor);
        initRadioGroup();
        mDealRequest = new DealRequest();
        //选择人员
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.layoutSendOrder.repairSelectedPepple.setText(model.getFullname());
            mDealRequest.getBizData().setPd_assignor(model.getFullname());
            mDealRequest.getBizData().setPd_assignor_id(model.getId());
        });
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
        binding.layoutSendOrder.repairSelectPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPeple();
            }
        });
    }

    /**
     * 选择指派人
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detail.getWx_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detail.getU_project_id())
                .navigation();
    }

    private void initState(String state) {
        switch (state) {
            case RouteKey.LIST_STATUS_SEND_ORDER://待派单
                if (fragment.equals(RouteKey.FRAGMENT_TO_FOLLOW_UP)) {
                    binding.layoutSendOrder.getRoot().setVisibility(View.VISIBLE);
                    binding.llPass.setVisibility(View.VISIBLE);
                    binding.tvSave.setVisibility(View.GONE);
                }
                binding.tvDealState.setText(getString(R.string.text_wait_send));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.repair_detail_evaluate_color));
                break;
            case RouteKey.LIST_STATUS_RESPONSE://待响应 展示 派单信息  响应操作
                if (fragment.equals(RouteKey.FRAGMENT_TO_FOLLOW_UP)) {
                    binding.layoutInquiriesResponse.getRoot().setVisibility(View.VISIBLE);
                    binding.llPass.setVisibility(View.VISIBLE);
                    binding.tvSave.setVisibility(View.GONE);
                }
                if (isSendOrder) {
                    binding.layoutSendOrderInfo.getRoot().setVisibility(View.VISIBLE);
                }
                binding.tvDealState.setText(getString(R.string.text_wait_response));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.repair_detail_response_color));
                break;

            case RouteKey.LIST_STATUS_HANDLE://待处理
                if (fragment.equals(RouteKey.FRAGMENT_TO_FOLLOW_UP)) {
                    binding.llReplyContent.setVisibility(View.VISIBLE);
                    binding.llPass.setVisibility(View.VISIBLE);
//                    binding.llForseClose.setVisibility(View.VISIBLE);
                    binding.llEvaluation.setVisibility(View.GONE);
                }
                if (isSendOrder) {
                    binding.layoutSendOrderInfo.getRoot().setVisibility(View.VISIBLE);
                }
                if (isResponse) {
                    binding.layoutInquiriesResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }
                binding.tvDealState.setText(getString(R.string.tv_dealing));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
                break;

            case RouteKey.LIST_STATUS_EVALUATE://待评价
                if (fragment.equals(RouteKey.FRAGMENT_TO_FOLLOW_UP)) {
                    binding.llReplyContent.setVisibility(View.GONE);
                    binding.llPass.setVisibility(View.GONE);
//                binding.llHistory.setVisibility(View.VISIBLE);
                    binding.llEvaluation.setVisibility(View.VISIBLE);
                }
                if (isSendOrder) {
                    binding.layoutSendOrderInfo.getRoot().setVisibility(View.VISIBLE);
                }
                if (isResponse) {
                    binding.layoutInquiriesResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }
                if (isCall) {
                    binding.llEvaluationCloseCall.setVisibility(View.VISIBLE);
                }
                binding.tvDealState.setText(getString(R.string.tv_for_respone));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.repair_detail_evaluate_color));
                break;
            default://已完成
                if (isSendOrder) {
                    binding.layoutSendOrderInfo.getRoot().setVisibility(View.VISIBLE);
                }
                if (isResponse) {
                    binding.layoutInquiriesResponseInfo.getRoot().setVisibility(View.VISIBLE);
                }
//                if (isCall) {
//                    binding.layoutInquiriesResponseInfo.getRoot().setVisibility(View.VISIBLE);
//                }
                if (isCall) {
                    binding.llEvaluationCloseCall.setVisibility(View.VISIBLE);
                }
                binding.tvDealState.setText("已完成");
                binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
//                binding.llHistory.setVisibility(View.VISIBLE);
//                binding.forceCloseInfo.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        photoListInfoAdapter = new PhotoListAdapter(this);
        forseClosephotoListInfoAdapter = new PhotoListAdapter(this);
        binding.listPic.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPic.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPic.setAdapter(photoListInfoAdapter);
        binding.listApplyPic.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listApplyPic.addItemDecoration(new SpacesItemDecoration(18));
        binding.listApplyPic.setAdapter(forseClosephotoListInfoAdapter);
        /**
         * 获取详情信息
         */
        viewModel.queryInquiriesBasicInfo(mProInsId).observe(this, module -> {
            updateUI(module);
        });
        /**
         * 获取工单详情
         */

        queryOrderInfo();
    }

    private void queryOrderInfo() {
        viewModel.queryOrderInfo(mProInsId, mTaskID == null ? "" : mTaskID).observe(this, module -> {
//            Log.e(TAG, "onResume:proInsId--"+inquiriesItemModule.proInsId+"--taskId--"+inquiriesItemModule.taskId);
            orderDetailInfoModule = module;
            int c_is_solve = orderDetailInfoModule.getData().getCustomer_repair_model().getC_is_solve();
            if (module.getData().getCustomer_repair_model().getPd_time() != null) {
                isSendOrder = true;
            } else {
                isSendOrder = false;
            }
            if (module.getData().getCustomer_repair_model().getResponse_time() != null) {
                isResponse = true;
            } else {
                isResponse = false;
            }
            if (module.getData().getCustomer_repair_model().getReturn_visit_time() != null) {
                isCall = true;
            } else {
                isCall = false;
            }

            if (c_is_solve == 1) {//1 已解决  0 未解决
                if (module.getData().getCustomer_repair_model().getReturn_time() != null) {
                    binding.llEvaluationClose.setVisibility(View.VISIBLE);
                }

                String return_time = (String) orderDetailInfoModule.getData().getCustomer_repair_model().getReturn_time();
                binding.tvEvaluationTime.setText(return_time);
            }


            state = module.getData().getCustomer_repair_model().getState();
            initState(state);
            initHistoryList(orderDetailInfoModule);
            isCanApplyClose(orderDetailInfoModule);
        });
    }

    private static final String TAG = "InquiriesDetailViewModu";

    private void initHistoryList(OrderDetailInfoModule orderDetailInfoModule) {
        //一级列表适配器
        adapter = new RVBindingAdapter<ItemInquiriseFeedbackHistoryLayoutBinding, OrderDetailInfoModule.HandleListBean>(this, BR.module) {
            @Override
            public void onBindItem(ItemInquiriseFeedbackHistoryLayoutBinding binding, OrderDetailInfoModule.HandleListBean model, int position) {
                if (position == 0) {
                    binding.ivFirst.setVisibility(View.INVISIBLE);
                } else {
                    binding.ivFirst.setVisibility(View.VISIBLE);
                }
                if (position == adapter.getDataList().size() - 1) {
                    binding.itemHistroyImg.setVisibility(View.INVISIBLE);
                } else {
                    binding.itemHistroyImg.setVisibility(View.VISIBLE);
                }
                binding.tvContent.setText(model.getHandle_result());
                binding.tvName.setText(model.getHandle_user());
                binding.tvTime.setText(TimeUtil.getAllTime(model.getHandle_time()));
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_inquirise_feedback_history_layout;
            }

        };
//        adapter.setDataList(orderDetailInfoModule.getHandleList());
        binding.listHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.listHistory.setAdapter(adapter);
        List<OrderDetailInfoModule.HandleListBean> handleList = orderDetailInfoModule.getHandleList();
        List<OrderDetailInfoModule.HandleListBean> handleListThreeData = new ArrayList<>();
        if (handleList != null) {
            binding.llHistory.setVisibility(View.VISIBLE);
            if (handleList.size() > 3) {//只展示三条
                binding.rlLoadMore.setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i++) {
                    handleListThreeData.add(handleList.get(i));
                }
                adapter.setDataList(handleListThreeData);
            } else {
                binding.rlLoadMore.setVisibility(View.GONE);
                adapter.setDataList(handleList);
            }
            binding.rlLoadMore.setOnClickListener(view -> {
                adapter.setDataList(handleList);
                binding.rlLoadMore.setVisibility(View.GONE);
            });
        } else {
            binding.llHistory.setVisibility(View.GONE);
        }

    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    private void updateUI(InquiriesDetailModule inquiriesDetailModule) {
        if (inquiriesDetailModule == null) {
            updatePageUIState(PageUIState.LOAD_FAILED.getState());
            return;
        }
        initState(inquiriesDetailModule.getData().getCustomer_enquiry_model().getState());
        //处理时长
        detail = inquiriesDetailModule.getData().getCustomer_enquiry_model();
        updatePageUIState(PageUIState.FILLDATA.getState());
        createTime = detail.getWx_time();
        if (ComplainOrderState.CLOSED.getState().equals(detail.getState())) {
            if (StringUtil.isNullStr(detail.getClose_time()))
                binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getWx_time(), detail.getClose_time()));
        } else {
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detail.getWx_time()));
            runnable.run();
        }
        binding.setModule(inquiriesDetailModule);
        binding.layoutSendOrderInfo.setRepairs(inquiriesDetailModule);
        binding.layoutInquiriesResponseInfo.setRepairs(inquiriesDetailModule);
        PicUrlModelConvert convert = new PicUrlModelConvert();
        List<PicUrlModel> modelList = convert.stringToSomeObjectList(inquiriesDetailModule.getData().getCustomer_enquiry_model().getWx_attachment());
        photoListInfoAdapter.updateList(modelList);
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_HISTORY)
                .withString(RouteKey.KEY_PRO_INS_ID, mProInsId)
                .navigation();
    }

    /**
     * 是否可以申请强制闭单
     *
     * @param orderDetailInfoModule
     */
    public void isCanApplyClose(OrderDetailInfoModule orderDetailInfoModule) {
        viewModel.isCanApply(orderDetailInfoModule.getData().getCustomer_repair_model().getId_(), "FORCE_CLOSE_ENQUIRY").observe(this, module -> {

            isApplyForseClose = module;
            Log.e("isCanApplyClose", "isCanApplyClose: " + isApplyForseClose);
            if (isApplyForseClose) {
                if (fragment.equals(RouteKey.FRAGMENT_TO_FOLLOW_UP)) {
                    if (binding.llEvaluation.isShown()) {
                        binding.llForseClose.setVisibility(View.GONE);
                    } else {
                        binding.llForseClose.setVisibility(View.VISIBLE);
                    }
                }

                OrderDetailInfoModule.ForceCloseInfoBean forceCloseInfo = orderDetailInfoModule.getForceCloseInfo();
                if (forceCloseInfo != null) {
                    binding.forceCloseInfo.setVisibility(View.VISIBLE);
                    binding.tvApprovalResult.setText(forceCloseInfo.getStatusStr());
                    binding.tvApprovalTime.setText(forceCloseInfo.getAuditDate().toString());
                    binding.tvApplyer.setText(forceCloseInfo.getApplyUser());
                    binding.tvApplyTime.setText(forceCloseInfo.getApplyDate());
                    binding.tvApplyReason.setText(forceCloseInfo.getApplyReason());
                    PicUrlModelConvert convert = new PicUrlModelConvert();
                    List<PicUrlModel> modelList = convert.stringToSomeObjectList(forceCloseInfo.getAttachment());
                    forseClosephotoListInfoAdapter.updateList(modelList);
                }

            } else {//强制关闭正在审批中  能操作的都隐藏掉 评价  提交 回复
                binding.llForseClose.setVisibility(View.GONE);

                binding.layoutSendOrder.getRoot().setVisibility(View.GONE);
                binding.layoutInquiriesResponse.getRoot().setVisibility(View.GONE);
                binding.llEvaluation.setVisibility(View.GONE);
                binding.llReplyContent.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);

                binding.forceCloseInfo.setVisibility(View.VISIBLE);
                OrderDetailInfoModule.ForceCloseInfoBean forceCloseInfo = orderDetailInfoModule.getForceCloseInfo();
                if (forceCloseInfo != null) {
                    binding.tvApprovalResult.setText(forceCloseInfo.getStatusStr());
                    binding.tvApprovalTime.setText(forceCloseInfo.getAuditDate().toString());
                    binding.tvApplyer.setText(forceCloseInfo.getApplyUser());
                    binding.tvApplyTime.setText(forceCloseInfo.getApplyDate());
                    binding.tvApplyReason.setText(forceCloseInfo.getApplyReason());
                    PicUrlModelConvert convert = new PicUrlModelConvert();
                    List<PicUrlModel> modelList = convert.stringToSomeObjectList(forceCloseInfo.getAttachment());
                    forseClosephotoListInfoAdapter.updateList(modelList);
                }
            }

        });
    }

    /**
     * 处理提交按钮
     */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {
            switch (state) {
                case RouteKey.LIST_STATUS_SEND_ORDER://待派单
                    //判断指派人
                    if (!StringUtil.isNullStr(mDealRequest.getBizData().getPd_assignor())) {
                        ToastUtil.show(this, "请选择指派人");
                        return;
                    }
                    mDealRequest.getBizData().setPd_remark(binding.layoutSendOrder.repairSendReason.getString());
                    break;
                case RouteKey.LIST_STATUS_RESPONSE://待响应
                    String reasonString = binding.layoutInquiriesResponse.ltResponseReason.getString();
                    if (!StringUtil.isNullStr(reasonString)) {
                        ToastUtil.show(this, "请输入沟通结果");
                        return;
                    }
                    mDealRequest.getBizData().setResponse_result(reasonString);
                    break;

                case RouteKey.LIST_STATUS_HANDLE://待处理
                    if (binding.limitInput.getString().isEmpty()) {
                        ToastUtil.show(this, getString(R.string.tv_empty_feedback_content));
                        return;
                    }
                    mDealRequest.getBizData().setHandle_cont(binding.limitInput.getString());

                    break;
            }
            mDealRequest.getDoNextParam().setTaskId(mTaskID);
            viewModel.deal(mDealRequest).observe(this, module -> {
                if (module) {
                    LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                    ToastUtil.show(this, "提交成功");
                    finish();
                } else {
                    ToastUtil.show(this, "提交失败");

                }
            });

        }
    }

    /**
     * 评价
     */
    public void onEvaluationClick() {
        if (IsFastClick.isFastDoubleClick()) {
            String content = binding.etLimitSuggestion.getString();
            if (evaluation == 0) {
                if (content.isEmpty()) {
                    ToastUtil.show(this, getString(R.string.tv_empty_evaluation));
                    return;
                }
            }
            EvaluationRequest evaluationRequest = new EvaluationRequest();
            evaluationRequest.getBizData().setC_is_solve(evaluation);
            evaluationRequest.getBizData().setReturn_result(content.isEmpty() ? "" : content);
            evaluationRequest.getDoNextParam().setTaskId(mTaskID);
            viewModel.evaluation(evaluationRequest).observe(this, module -> {
                if (module) {
                    LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                    ToastUtil.show(this, getString(R.string.tv_evaluation_suc));
                    finish();
                } else {
                    ToastUtil.show(this, getString(R.string.tv_evaluation_fail));

                }
            });
        }
    }

    /**
     * 申请强制闭单
     */
    public void onForseCloseClick() {
        if (IsFastClick.isFastDoubleClick()) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_CLOSE).withString(RouteKey.KEY_MID_URL, RouteKey.KEY_MID_URL_INQUIRIES)
                    .withString(RouteKey.KEY_TASK_ID, mTaskID)
                    .navigation();
        }
    }

    /**
     * 处理保存
     */
    public void onRejectClick() {
        if (IsFastClick.isFastDoubleClick()) {
            if (binding.limitInput.getString().isEmpty()) {
                ToastUtil.show(this, getString(R.string.tv_empty_save_content));
                return;
            }
            DealSaveRequest dealRequest = new DealSaveRequest();
            dealRequest.setID_(detail.getId_());
            dealRequest.getBizData().setHandle_cont(binding.limitInput.getString());
            viewModel.dealSave(dealRequest).observe(this, module -> {

                if (module) {
                    if (alertDialog == null) {
                        alertDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                                .setMsg(getString(R.string.tv_save_suc))
                                .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        queryOrderInfo();
                                        binding.limitInput.setText("");
//                                finish();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        if (!alertDialog.isShowing()) {
                            alertDialog.show();
                        }
                    }
                } else {
                    ToastUtil.show(this, R.string.tv_svae_fail);
                }
            });
        }
    }

    private void initRadioGroup() {
        binding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_solve) {
                    binding.llIsSolved.setVisibility(View.GONE);
                    evaluation = 1;
                } else if (i == R.id.rb_un_solve) {
                    binding.llIsSolved.setVisibility(View.VISIBLE);
                    evaluation = 0;
                }
            }
        });
    }

    /**
     * 设置图片间隔
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
        }
    }

    //立即调用方法
    Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            //计算时间
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(createTime));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacks(runnable);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
