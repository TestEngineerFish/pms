package com.einyun.app.pms.approval.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.fastjson.JSON;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.constants.ApprovalDataKey;
import com.einyun.app.pms.approval.constants.RouteKey;
import com.einyun.app.pms.approval.databinding.ActivityApprovalDetailViewModuleBinding;
import com.einyun.app.pms.approval.databinding.ActivityApprovalViewModuleBinding;
import com.einyun.app.pms.approval.module.ApprovalFormdata;
import com.einyun.app.pms.approval.module.ApprovalItemmodule;
import com.einyun.app.pms.approval.module.ApprovalSumitBean;
import com.einyun.app.pms.approval.module.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.ui.adapter.ApprovalInfoDetailAdapter;
import com.einyun.app.pms.approval.ui.widget.DoubleChoiceDialog;
import com.einyun.app.pms.approval.viewmodule.ApprovalDetailViewModel;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.HashMap;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_APPROVAL_DETAIL)
public class ApprovalDetailViewModuleActivity extends BaseHeadViewModelActivity<ActivityApprovalDetailViewModuleBinding, ApprovalDetailViewModel> {
    @Autowired(name = RouteKey.APPROVAL_ITEM_DATA)
    Serializable data;
    @Autowired(name = RouteKey.APPROVAL_DETAIL_TYPE_VALUE)
    String typeValue;
    private ApprovalItemmodule approvalItemmodule;
    private ApprovalFormdata approvalFormdata;
    private UrlxcgdGetInstBOModule urlxcgdGetInstBOModule;

    @Override
    protected ApprovalDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ApprovalViewModelFactory()).get(ApprovalDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_approval_detail_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_approval_detail));
        approvalItemmodule = (ApprovalItemmodule)data;

    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        binding.listview.setFocusable(false);
//        binding.tvApprovalState.setOnClickListener(view -> {
//            HashMap<Object, Object> approve = viewModel.approval("sss", urlxcgdGetInstBOModule, approvalItemmodule.getProInsId(), approvalItemmodule.getTaskId(), "sss", approvalFormdata);
//        });
        /*
         * 获取基本信息
         * */
        viewModel.queryApprovalBasicInfo(approvalItemmodule.getProInsId()).observe(this, model -> {
            urlxcgdGetInstBOModule = model;
            String form_data = model.getData().getWorkorder_audit_model().getForm_data();
            approvalFormdata = JSON.parseObject(form_data, ApprovalFormdata.class);
            showBasicInfo(model);
        });
        /*
         * 获取审批信息列表数据
         * */
        viewModel.queryApprovalDetialInfo(approvalItemmodule.getID_()).observe(this, model -> {
            ApprovalInfoDetailAdapter approvalInfoDetailAdapter = new ApprovalInfoDetailAdapter(this,model.getRows());
            binding.listview.setAdapter(approvalInfoDetailAdapter);

        });
    }
    /*
    * 审批通过按钮
    * */
    public void onPassClick(){
        Log.e("sumitApproval", "onPassClick: ");
        sumit(ApprovalDataKey.APPROVAL_SUMIT_AGREE);
    }
    /*
     * 审批不通过按钮
     * */
    public  void onRejectClick(){
//        new DoubleChoiceDialog(this,"test","teast","left","right",
//                view -> {},view -> {}).show();
        sumit(ApprovalDataKey.APPROVAL_SUMIT_REJECT);
    }
    /*
    * 提交审批
    * */
    private void sumit(String actionName) {
        String comment=binding.limitInput.getString().trim();
        if (comment.isEmpty()) {
            ToastUtil.show(getApplicationContext(), R.string.tv_empty_sug);
            return;
        }
        //获取请求参数
        HashMap<Object, Object> approve = viewModel.approval(actionName, urlxcgdGetInstBOModule, approvalItemmodule.getProInsId(), approvalItemmodule.getTaskId(), comment, approvalFormdata);
        /*
         *提交审批
         * */
        viewModel.sumitApproval((ApprovalSumitBean) approve.get("bean"),(String) approve.get("url")).observe(this, model -> {
            Logger.d("sumitApproval-----model="+model);
            if (model) {
//                ToastUtil.show(getApplicationContext(), R.string.tv_had_pass);
                LiveEventBus.get(ApprovalDataKey.APPROVAL_FRAGMENT_REFRESH, Boolean.class).post(true);
                finish();
            }else {
                ToastUtil.show(getApplicationContext(), R.string.tv_no_pass);
                LiveEventBus.get(ApprovalDataKey.APPROVAL_FRAGMENT_REFRESH, Boolean.class).post(true);
            }
        });
    }


    private void showBasicInfo(UrlxcgdGetInstBOModule model) {
        UrlxcgdGetInstBOModule.DataBean.WorkorderAuditModelBean workorder_audit_model = model.getData().getWorkorder_audit_model();
        if (workorder_audit_model.getStatus().equals("submit")) {//待审批
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);

            binding.listview.setVisibility(View.GONE);//
            binding.rlApprovalSug.setVisibility(View.VISIBLE);
            binding.limitInput.setVisibility(View.VISIBLE);
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.tvApprovalState.setText(getString(R.string.tv_wait_approval));
        } else if (workorder_audit_model.getStatus().equals("approve")) {//通过

            binding.listview.setVisibility(View.VISIBLE);//显示审批信息列表
            binding.rlApprovalSug.setVisibility(View.GONE);
            binding.limitInput.setVisibility(View.GONE);
            binding.llPass.setVisibility(View.GONE);
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
            binding.tvApprovalState.setText(getString(R.string.tv_had_approval));
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.greenTextColor));
        } else if (workorder_audit_model.getStatus().equals("reject")) {//驳回

            binding.listview.setVisibility(View.VISIBLE);//
            binding.rlApprovalSug.setVisibility(View.GONE);
            binding.limitInput.setVisibility(View.GONE);
            binding.llPass.setVisibility(View.GONE);

//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
            binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.redTextColor));
        } else if (workorder_audit_model.getStatus().equals("in_approval")) {//审批中
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);
            if (approvalItemmodule.getUserAuditStatus()==null) {//自己没有审批过

                binding.listview.setVisibility(View.GONE);//
                binding.rlApprovalSug.setVisibility(View.VISIBLE);
                binding.limitInput.setVisibility(View.VISIBLE);

                binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                binding.tvApprovalState.setTextColor(getResources().getColor(R.color.blueTextColor));
            }else {//自己审批过 显示审批列表 UserAuditStatus 不为空
                binding.listview.setVisibility(View.VISIBLE);//显示审批信息列表
                binding.rlApprovalSug.setVisibility(View.GONE);
                binding.limitInput.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
                binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                binding.tvApprovalState.setTextColor(getResources().getColor(R.color.blueTextColor));
                if ("approve".equals(approvalItemmodule.getUserAuditStatus())) {
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
//                    binding.tvApprovalState.setText(getString(R.string.tv_had_approval));
//                    binding.tvApprovalState.setTextColor(getResources().getColor(R.color.greenTextColor));
                }else if ("reject".equals(approvalItemmodule.getUserAuditStatus())){

//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
//                    binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
//                    binding.tvApprovalState.setTextColor(getResources().getColor(R.color.redTextColor));
                }

            }
        }
        /*
        * 基本信息
        * */
        binding.tvApprovalNum.setText(workorder_audit_model.getAudit_code());
        binding.tvIntallment.setText(workorder_audit_model.getDivide_name());
        binding.tvApplyPerson.setText(workorder_audit_model.getApply_user());
        binding.tvApplyTime.setText(workorder_audit_model.getApply_date());
        binding.tvApprovalType.setText(typeValue);
        /*
        * 申请信息
        * */
        String type = workorder_audit_model.getAudit_type();
        String subType = workorder_audit_model.getAudit_sub_type();

        if ("INNER_AUDIT_CREATE_PLAN".equals(type) || "INNER_AUDIT_UPDATE_PLAN".equals(type)) {//创建计划，修改计划
            binding.llCreatChangeInfo.setVisibility(View.VISIBLE);
            binding.tvCreateLine.setText(approvalFormdata.getLine());//条线
            binding.tvCreatePlanName.setText(approvalFormdata.getWorkPlanName());//计划名称
            binding.tvCreateResourceClassification.setText(approvalFormdata.getResourceClassificationName());//资源分类
            binding.tvCreateWorkInstruction.setText(approvalFormdata.getWorkGuidanceName());//工作指导
            binding.tvCreateWorkOrderRespone.setText(approvalFormdata.getPrincipal());//工单负责职位
            String effectivePeriod = approvalFormdata.getEffectivePeriod();
            if (effectivePeriod!=null) {
                String[] split = effectivePeriod.split("----");
                binding.tvCreateApplyStartTime.setText(split[0]);//申请开始时间
                binding.tvCreateApplyEndTime.setText(split[1]);//申请结束时间
            }
            if (approvalFormdata.getFrequency()!=null&&approvalFormdata.getFrequency().size()>0) {
                binding.tvCreateExecutionFrequency.setText(approvalFormdata.getFrequency().get(0));//执行频率
            }


        }else if ("INNER_AUDIT_POSTPONED".equals(type)||"INNER_AUDIT_FORCE_CLOSE".equals(type)){//工单延期 强制闭单
            if ("INNER_AUDIT_FORCE_CLOSE".equals(type)) {
                binding.llGoneTime.setVisibility(View.GONE);//强制闭单没有时间信息
            }
            binding.llDelayCloseInfo.setVisibility(View.VISIBLE);
            binding.tvCloseOrderType.setText(approvalFormdata.getFlowType());//工单类型
            binding.tvCloseOrderCode.setText(approvalFormdata.getCode());//工单编码
            binding.tvCloseLine.setText(approvalFormdata.getLine());//条线
            binding.tvCloseDispatchOrderType.setText(approvalFormdata.getDispatchFlowType());//派工单类型
            binding.tvCloseOrderResponer.setText(approvalFormdata.getProcName());//派工单类型
            binding.tvCloseCreateTime.setText(approvalFormdata.getCreationTime());//工单创建时间
            binding.tvCloseEndTime.setText(approvalFormdata.getDeadlineTime());//工单截止时间
            binding.tvCloseDelayDay.setText(approvalFormdata.getExtensionDays());//延期天数
            binding.tvCloseApplyReason.setText(approvalFormdata.getApplicationDescription());//申请原因


        }

    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
