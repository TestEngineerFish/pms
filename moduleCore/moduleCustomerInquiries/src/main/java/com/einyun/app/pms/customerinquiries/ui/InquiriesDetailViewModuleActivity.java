package com.einyun.app.pms.customerinquiries.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.databinding.ItemFeedbackHistoryLayoutBinding;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.searchhistory.SingleSearchFragment;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.pms.customerinquiries.BR;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.ActivityCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ActivityInquiriesDetailViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.InquiriesPopwindowItemBinding;
import com.einyun.app.pms.customerinquiries.module.DealRequest;
import com.einyun.app.pms.customerinquiries.module.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.module.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.module.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.module.OrderDetailInfoModule;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.viewmodule.InquiriesDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_INQUIRIES_DETAIL)
public class InquiriesDetailViewModuleActivity extends BaseHeadViewModelActivity<ActivityInquiriesDetailViewModuleBinding, InquiriesDetailViewModel> {
//    @Autowired(name = Constants.INQUIRIES_BEAN);
//    Serializable data;
    @Autowired(name = Constants.INQUIRIES_BEAN)
    Serializable data;
    private InquiriesItemModule inquiriesItemModule;
    private PhotoListAdapter photoListInfoAdapter;
    private int evaluation;
    private AlertDialog alertDialog;
    private OrderDetailInfoModule orderDetailInfoModule;
    private RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, OrderDetailInfoModule.HandleListBean> adapter;
    private boolean isApplyForseClose;


    @Override
    protected InquiriesDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new CustomerInquiriesViewModelFactory()).get(InquiriesDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inquiries_detail_view_module;
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
        inquiriesItemModule = (InquiriesItemModule) data;

        switch (inquiriesItemModule.getTaskNodeId()) {
            case Constants.INQUIRIES_STATE_DEALING:
                binding.llReplyContent.setVisibility(View.VISIBLE);
                binding.llPass.setVisibility(View.VISIBLE);
                binding.llForseClose.setVisibility(View.VISIBLE);
                binding.llEvaluation.setVisibility(View.GONE);
                binding.tvDealState.setText(getString(R.string.tv_dealing));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
                break;

            case Constants.INQUIRIES_STATE_RETURN_VISIT:
                binding.llReplyContent.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
                binding.llHistory.setVisibility(View.VISIBLE);
                binding.llEvaluation.setVisibility(View.VISIBLE);
                binding.tvDealState.setText(getString(R.string.tv_for_respone));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.blueTextColor));
                break;
            default:
                binding.tvDealState.setText(getString(R.string.tv_closed));
                binding.tvDealState.setTextColor(getResources().getColor(R.color.greenTextColor));
                binding.llHistory.setVisibility(View.VISIBLE);
                binding.forceCloseInfo.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        photoListInfoAdapter = new PhotoListAdapter(this);
        binding.listPic.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPic.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPic.setAdapter(photoListInfoAdapter);
        /**
         * 获取详情信息
         */
        viewModel.queryInquiriesBasicInfo(inquiriesItemModule.proInsId).observe(this,module->{
            updateUI(module);
        });
        /**
         * 获取工单详情
         */
        viewModel.queryOrderInfo(inquiriesItemModule.proInsId,inquiriesItemModule.taskId==null?"":inquiriesItemModule.taskId).observe(this,module->{
            orderDetailInfoModule = module;
            int c_is_solve = orderDetailInfoModule.getData().getCustomer_repair_model().getC_is_solve();

                if (c_is_solve ==1) {//1 已解决  0 未解决
                    binding.llEvaluationClose.setVisibility(View.VISIBLE);
                    String return_time = (String) orderDetailInfoModule.getData().getCustomer_repair_model().getReturn_time();
                    binding.tvEvaluationTime.setText(return_time);
                }

            initHistoryList(orderDetailInfoModule);
        });
        isCanApplyClose();
    }

    private void initHistoryList(OrderDetailInfoModule orderDetailInfoModule) {
        //一级列表适配器
        adapter = new RVBindingAdapter<ItemFeedbackHistoryLayoutBinding, OrderDetailInfoModule.HandleListBean>(this, BR.module) {
              @Override
              public void onBindItem(ItemFeedbackHistoryLayoutBinding binding, OrderDetailInfoModule.HandleListBean model, int position) {

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
                  return R.layout.item_feedback_history_layout;
              }

          };
//        adapter.setDataList(orderDetailInfoModule.getHandleList());
        binding.listHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.listHistory.setAdapter(adapter);
        List<OrderDetailInfoModule.HandleListBean> handleList = orderDetailInfoModule.getHandleList();
        List<OrderDetailInfoModule.HandleListBean> handleListThreeData=new ArrayList<>();
        if (handleList !=null) {
            binding.llHistory.setVisibility(View.VISIBLE);
            if (handleList.size()>3) {//只展示三条
                binding.rlLoadMore.setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i++) {
                    handleListThreeData.add(handleList.get(i));
                }
                adapter.setDataList(handleListThreeData);
            }else {
                binding.rlLoadMore.setVisibility(View.GONE);
                adapter.setDataList(handleList);
            }
            binding.rlLoadMore.setOnClickListener(view -> {
                adapter.setDataList(handleList);
                binding.rlLoadMore.setVisibility(View.GONE);
            });
        }else {
            binding.llHistory.setVisibility(View.GONE);
        }

    }

    private void updateUI(InquiriesDetailModule inquiriesDetailModule) {
        if (inquiriesDetailModule == null) {
            return;
        }
        binding.setModule(inquiriesDetailModule);
        PicUrlModelConvert convert = new PicUrlModelConvert();
        List<PicUrlModel> modelList = convert.stringToSomeObjectList(inquiriesDetailModule.getData().getCustomer_enquiry_model().getWx_attachment());
        photoListInfoAdapter.updateList(modelList);
    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 是否可以申请强制闭单
     * */
    public  void isCanApplyClose() {
        viewModel.isCanApply(inquiriesItemModule.ID_,"FORCE_CLOSE_ENQUIRY").observe(this,module->{

            isApplyForseClose = module;
            Log.e("isCanApplyClose", "isCanApplyClose: "+isApplyForseClose );
            if (isApplyForseClose) {
//                binding.llForseClose.setVisibility(View.VISIBLE);
            }else {//
//                binding.llForseClose.setVisibility(View.GONE);
                binding.forceCloseInfo.setVisibility(View.VISIBLE);
            }

        });
    }
    /**
     * 处理提交按钮
     * */
    public void onPassClick(){
        if (binding.limitInput.getString().isEmpty()) {
            ToastUtil.show(this,"回复内容不能为空");
            return;
        }
        DealRequest dealRequest = new DealRequest();
        dealRequest.getBizData().setHandle_cont(binding.limitInput.getString());
        dealRequest.getDoNextParam().setTaskId(inquiriesItemModule.taskId);
        viewModel.deal(dealRequest).observe(this,module->{
            if (module) {

                ToastUtil.show(this,"回复成功");
                finish();
            }else {
                ToastUtil.show(this,"回复失败");

            }
        });
    }
    /**
     * 评价
     * */
    public  void onEvaluationClick() {
        String content = binding.etLimitSuggestion.getString();
        if (evaluation==0) {
            if (content.isEmpty()) {
                ToastUtil.show(this,"回复内容不能为空");
                return;
            }
        }
        EvaluationRequest evaluationRequest = new EvaluationRequest();
        evaluationRequest.getBizData().setC_is_solve(evaluation);
        evaluationRequest.getBizData().setReturn_result(content.isEmpty()?"":content);
        evaluationRequest.getDoNextParam().setTaskId(inquiriesItemModule.taskId);
        viewModel.evaluation(evaluationRequest).observe(this,module->{
            if (module) {

                ToastUtil.show(this,"保存成功");
                finish();
            }
        });
    }
    /**
     * 处理保存
     */
    public  void onRejectClick() {
        if (binding.limitInput.getString().isEmpty()) {
            ToastUtil.show(this,getString(R.string.tv_empty_feedback_content));
            return;
        }
        DealSaveRequest dealRequest = new DealSaveRequest();
        dealRequest.setID_(inquiriesItemModule.ID_);
        dealRequest.getBizData().setHandle_cont(binding.limitInput.getString());
        viewModel.dealSave(dealRequest).observe(this,module->{

            if (module) {
                if (alertDialog==null) {
                    alertDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                            .setMsg(getString(R.string.tv_save_suc))
                            .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                finish();
                                }
                            });
                    alertDialog.show();
                }else {
                    if (!alertDialog.isShowing()) {
                        alertDialog.show();
                    }
                }
            }else {
                ToastUtil.show(this, R.string.tv_svae_fail);
            }
        });
    }

    private void initRadioGroup() {
        binding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_solve) {
                    binding.llIsSolved.setVisibility(View.GONE);
                    evaluation = 1;
                }else if (i==R.id.rb_un_solve){
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
}
