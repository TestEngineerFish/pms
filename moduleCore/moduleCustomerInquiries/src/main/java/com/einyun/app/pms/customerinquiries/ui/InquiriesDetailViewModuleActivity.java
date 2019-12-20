package com.einyun.app.pms.customerinquiries.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.component.searchhistory.SingleSearchFragment;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.constants.Constants;
import com.einyun.app.pms.customerinquiries.databinding.ActivityCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.databinding.ActivityInquiriesDetailViewModuleBinding;
import com.einyun.app.pms.customerinquiries.module.DealRequest;
import com.einyun.app.pms.customerinquiries.module.DealSaveRequest;
import com.einyun.app.pms.customerinquiries.module.EvaluationRequest;
import com.einyun.app.pms.customerinquiries.module.InquiriesDetailModule;
import com.einyun.app.pms.customerinquiries.module.InquiriesItemModule;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.einyun.app.pms.customerinquiries.viewmodule.InquiriesDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
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
        switch (inquiriesItemModule.taskNodeId) {
            case Constants.INQUIRIES_STATE_DEALING:
                binding.llReplyContent.setVisibility(View.VISIBLE);
                binding.llPass.setVisibility(View.VISIBLE);
                binding.llForseClose.setVisibility(View.VISIBLE);
                binding.llEvaluation.setVisibility(View.VISIBLE);
                break;

            case Constants.INQUIRIES_STATE_RETURN_VISIT:
                binding.llReplyContent.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
                binding.llHistory.setVisibility(View.VISIBLE);
                binding.llEvaluation.setVisibility(View.VISIBLE);
                break;
            default:
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
        viewModel.Deal(dealRequest).observe(this,module->{
            ToastUtil.show(this,"回复成功");
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
        evaluationRequest.getBizData().setHandle_cont(content.isEmpty()?"":content);
        evaluationRequest.getDoNextParam().setTaskId(inquiriesItemModule.taskId);
        viewModel.Evaluation(evaluationRequest).observe(this,module->{
            ToastUtil.show(this,"保存成功");
        });
    }
    /**
     * 处理保存
     */
    public  void onRejectClick() {
        if (binding.limitInput.getString().isEmpty()) {
            ToastUtil.show(this,"回复内容不能为空");
            return;
        }
        DealSaveRequest dealRequest = new DealSaveRequest();
        dealRequest.setID_(inquiriesItemModule.ID_);
        dealRequest.getBizData().setHandle_cont(binding.limitInput.getString());
        viewModel.DealSave(dealRequest).observe(this,module->{
            ToastUtil.show(this,"保存成功");
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
