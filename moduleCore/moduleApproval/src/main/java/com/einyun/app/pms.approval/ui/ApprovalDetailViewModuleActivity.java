package com.einyun.app.pms.approval.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.widget.SpacesItemDecoration;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.pms.approval.R;
import com.einyun.app.pms.approval.constants.ApprovalDataKey;
import com.einyun.app.pms.approval.databinding.ActivityApprovalDetailViewModuleBinding;
import com.einyun.app.pms.approval.model.ApprovalDetailInfoBean;
import com.einyun.app.pms.approval.model.ApprovalFormdata;
import com.einyun.app.pms.approval.model.ApprovalItemmodule;
import com.einyun.app.pms.approval.model.ApprovalSumitBean;
import com.einyun.app.pms.approval.model.UrlxcgdGetInstBOModule;
import com.einyun.app.pms.approval.ui.adapter.ApprovalInfoDetailAdapter;
import com.einyun.app.pms.approval.viewmodule.ApprovalDetailViewModel;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_APPROVAL_DETAIL)
public class ApprovalDetailViewModuleActivity extends BaseHeadViewModelActivity<ActivityApprovalDetailViewModuleBinding, ApprovalDetailViewModel> {
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String mProinsId;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String mTaskId;
    @Autowired(name = RouteKey.KEY_APPROVAL_USER_STATE)
    String userAudioState;
    @Autowired(name = RouteKey.KEY_TAB_ID)
    int tabId;
    private ApprovalFormdata approvalFormdata;
    private UrlxcgdGetInstBOModule urlxcgdGetInstBOModule;
    private PhotoListAdapter photoOrderInfoAdapter;

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
        setHeadTitle(getString(R.string.tv_approval));

        /**
         * ??????
         */
        photoOrderInfoAdapter = new PhotoListAdapter(this);
        binding.listPicOrderInfo.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.listPicOrderInfo.addItemDecoration(new SpacesItemDecoration(18));
        binding.listPicOrderInfo.setAdapter(photoOrderInfoAdapter);
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
         * ??????????????????
         * */
        viewModel.queryApprovalBasicInfo(mProinsId).observe(this, model -> {
            urlxcgdGetInstBOModule = model;
            if (urlxcgdGetInstBOModule == null || model.getData().getWorkorder_audit_model() == null) {
                return;
            }

            String form_data = model.getData().getWorkorder_audit_model().getForm_data();
            approvalFormdata = JSON.parseObject(form_data, ApprovalFormdata.class);
            showBasicInfo(model);
            /*
             * ??????????????????????????????
             * */
            viewModel.queryApprovalDetialInfo(model.getData().getWorkorder_audit_model().getId_()).observe(this, model2 -> {
                List<ApprovalDetailInfoBean.RowsBean> rows = model2.getRows();
                if (rows != null) {
                    ApprovalDetailInfoBean.RowsBean rowsBean = new ApprovalDetailInfoBean.RowsBean();
                    rowsBean.setAuditor(urlxcgdGetInstBOModule.getData().getWorkorder_audit_model().getApply_user());
                    rowsBean.setApprovalRole(getString(R.string.tv_for_apply));
                    rowsBean.setComment("");
                    rowsBean.setAudit_date(TimeUtil.getTimeMillis(model.getData().getWorkorder_audit_model().getApply_date()));
                    rows.add(rows.size(), rowsBean);
                    ApprovalInfoDetailAdapter approvalInfoDetailAdapter = new ApprovalInfoDetailAdapter(this, rows);
                    binding.listview.setAdapter(approvalInfoDetailAdapter);
                }

            });
        });

    }

    /*
     * ??????????????????
     * */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {
            Log.e("sumitApproval", "onPassClick: ");
            sumit(ApprovalDataKey.APPROVAL_SUMIT_AGREE);
        }
    }

    /**
     * ????????????????????????????????????
     */
    public void goToOrderDetailClick() {
        if (IsFastClick.isFastDoubleClick()) {
            //????????????????????????????????????????????????????????????
            UrlxcgdGetInstBOModule.DataBean.WorkorderAuditModelBean workorder_audit_model = urlxcgdGetInstBOModule.getData().getWorkorder_audit_model();
            switch (workorder_audit_model.getAudit_sub_type()) {
                case ApprovalDataKey.CREATE_WORK_PLAN://????????????
                case ApprovalDataKey.UPDATE_WORK_PLAN:
                case ApprovalDataKey.POSTPONED_PLAN:
                case ApprovalDataKey.FORCE_CLOSE_PLAN:
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                            .withString(RouteKey.KEY_ORDER_ID, workorder_audit_model.getId_())
                            .withString(RouteKey.KEY_TASK_NODE_ID, "")
                            .withString(RouteKey.KEY_TASK_ID, "")
                            .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                            .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                            .navigation();
                    break;
                case ApprovalDataKey.POSTPONED_PATROL://????????????
                case ApprovalDataKey.CREATE_PATROL_PLAN:
                case ApprovalDataKey.FORCE_CLOSE_PATROL:
                case ApprovalDataKey.UPDATE_PATROL_PLAN:
                    viewModel.getPatrolType(workorder_audit_model.getApply_instance_id()).observe(this, model -> {

                        if (model.getData().getZyxcgd().getF_patrol_line_id() == null) {//??????
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                                    .withString(RouteKey.KEY_TASK_ID, "")
                                    .withString(RouteKey.KEY_ORDER_ID, model.getData().getZyxcgd().getId_())
                                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                                    .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                                    .navigation();
                        } else {//??????
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
                                    .withString(RouteKey.KEY_TASK_ID, "")
                                    .withString(RouteKey.KEY_ORDER_ID, model.getData().getZyxcgd().getId_())
                                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                                    .withString(RouteKey.KEY_TASK_NODE_ID, "UserTask1")
                                    .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                                    .navigation();

                        }

                    });

                    break;
                case ApprovalDataKey.FORCE_CLOSE_ALLOCATE://?????????
                case ApprovalDataKey.POSTPONED_ALLOCATE:
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                            .withString(RouteKey.KEY_ORDER_ID, "")
                            .withString(RouteKey.KEY_TASK_NODE_ID, "")
                            .withString(RouteKey.KEY_TASK_ID, "")
                            .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                            .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                            .navigation();
                    break;
                case ApprovalDataKey.FORCE_CLOSE_ENQUIRY://??????
                    ARouter.getInstance()
                            .build(RouterUtils.ACTIVITY_INQUIRIES_MSG_DETAIL)
                            .withString(RouteKey.FRAGMENT_TAG, FRAGMENT_TRANSFERRED_TO)
                            .withString(RouteKey.KEY_TASK_ID, "")
                            .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                            .navigation();
                    break;
                case ApprovalDataKey.FORCE_CLOSE_COMPLAIN://??????
                case ApprovalDataKey.POSTPONED_COMPLAIN:
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                            .withString(RouteKey.KEY_ORDER_ID, workorder_audit_model.getId_())
                            .withString(RouteKey.KEY_TASK_NODE_ID, "")
                            .withString(RouteKey.KEY_TASK_ID, "")
                            .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                            .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                            .navigation();
                    break;
                case ApprovalDataKey.FORCE_CLOSE_REPAIR://??????
                case ApprovalDataKey.POSTPONED_REPAIR:
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                            .withString(RouteKey.KEY_ORDER_ID, workorder_audit_model.getId_())
                            .withString(RouteKey.KEY_TASK_NODE_ID, "")
                            .withString(RouteKey.KEY_TASK_ID, "")
                            .withString(RouteKey.KEY_PRO_INS_ID, workorder_audit_model.getApply_instance_id())
                            .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                            .navigation();
                    break;

            }

        }
    }

    /*
     * ?????????????????????
     * */
    public void onRejectClick() {
//        new DoubleChoiceDialog(this,"test","teast","left","right",
//                view -> {},view -> {}).show();
        if (IsFastClick.isFastDoubleClick()) {

            sumit(ApprovalDataKey.APPROVAL_SUMIT_REJECT);
        }
    }

    /**
     * ????????????
     */
    private void sumit(String actionName) {

        String comment = binding.limitInput.getString().trim();
        if (ApprovalDataKey.APPROVAL_SUMIT_REJECT.equals(actionName)) {
            if (comment.isEmpty()) {
                ToastUtil.show(getApplicationContext(), R.string.tv_empty_sug);
                return;
            }
        }
        if (approvalFormdata == null) {
            return;
        }
        //??????????????????
        HashMap<Object, Object> approve = viewModel.approval(actionName, urlxcgdGetInstBOModule, mProinsId, mTaskId, comment, approvalFormdata);
        /**
         *????????????
         * */
        viewModel.sumitApproval((ApprovalSumitBean) approve.get(ApprovalDataKey.APPROVAL_SUMIT_PARMS), (String) approve.get(ApprovalDataKey.APPROVAL_SUMIT_URL)).observe(this, model -> {
            Logger.d("sumitApproval-----model=" + model);
            if (model) {
//                ToastUtil.show(getApplicationContext(), R.string.tv_had_pass);
                LiveEventBus.get(ApprovalDataKey.APPROVAL_FRAGMENT_REFRESH, Boolean.class).post(true);
                finish();
            } else {
                ToastUtil.show(getApplicationContext(), R.string.tv_no_pass);
                LiveEventBus.get(ApprovalDataKey.APPROVAL_FRAGMENT_REFRESH, Boolean.class).post(true);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("user_name", UserUtil.getUserName());
            MobclickAgent.onEvent(this, CustomEventTypeEnum.APPROVAL_SUBMIT.getTypeName(), map);
        });
    }


    private void showBasicInfo(UrlxcgdGetInstBOModule model) {
        UrlxcgdGetInstBOModule.DataBean.WorkorderAuditModelBean workorder_audit_model = model.getData().getWorkorder_audit_model();
        if (workorder_audit_model.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_PENDING)) {//?????????
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);

            binding.listview.setVisibility(View.GONE);//
            if (userAudioState == null) {
                binding.rlApprovalSug.setVisibility(View.VISIBLE);
                binding.limitInput.setVisibility(View.VISIBLE);
            } else {
                binding.llPass.setVisibility(View.GONE);
            }
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.repair_detail_evaluate_color));
            binding.tvApprovalState.setText(getString(R.string.tv_wait_approval));
            if (tabId == 2) {
                binding.cdLimit.setVisibility(View.GONE);
            }
        } else if (workorder_audit_model.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_HAD_PASS)) {//??????

            binding.listview.setVisibility(View.VISIBLE);//????????????????????????
            binding.rlApprovalSug.setVisibility(View.GONE);
            binding.limitInput.setVisibility(View.GONE);
            binding.llPass.setVisibility(View.GONE);
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
            binding.tvApprovalState.setText(getString(R.string.tv_had_pass));
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.greenTextColor));
        } else if (workorder_audit_model.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_HAD_UNPASS)) {//??????

            binding.listview.setVisibility(View.VISIBLE);//
            binding.rlApprovalSug.setVisibility(View.GONE);
            binding.limitInput.setVisibility(View.GONE);
            binding.llPass.setVisibility(View.GONE);

//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
            binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
            binding.tvApprovalState.setTextColor(getResources().getColor(R.color.redTextColor));
        } else if (workorder_audit_model.getStatus().equals(ApprovalDataKey.APPROVAL_STATE_IN_APPROVAL)) {//?????????
//                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_wait_approval);
            if (userAudioState == null) {//?????????????????????

                binding.listview.setVisibility(View.GONE);//
                binding.rlApprovalSug.setVisibility(View.VISIBLE);
                binding.limitInput.setVisibility(View.VISIBLE);

                binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                binding.tvApprovalState.setTextColor(getResources().getColor(R.color.repair_detail_send_color));
            } else {//??????????????? ?????????????????? UserAuditStatus ?????????
                binding.listview.setVisibility(View.VISIBLE);//????????????????????????
                binding.rlApprovalSug.setVisibility(View.GONE);
                binding.limitInput.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
                binding.tvApprovalState.setText(getString(R.string.tv_approvaling));
                binding.tvApprovalState.setTextColor(getResources().getColor(R.color.repair_detail_send_color));
//                if ("approve".equals(approvalItemmodule.getUserAuditStatus())) {
////                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_pass);
////                    binding.tvApprovalState.setText(getString(R.string.tv_had_approval));
////                    binding.tvApprovalState.setTextColor(getResources().getColor(R.color.greenTextColor));
//                }else if ("reject".equals(approvalItemmodule.getUserAuditStatus())){
//
////                binding.tvApprovalState.setBackgroundResource(R.drawable.iv_approval_unpass);
////                    binding.tvApprovalState.setText(getString(R.string.tv_had_not_approval));
////                    binding.tvApprovalState.setTextColor(getResources().getColor(R.color.redTextColor));
//                }

            }
        }
        /**
         * ????????????
         * */
        binding.tvApprovalNum.setText(workorder_audit_model.getAudit_code());
        binding.tvIntallment.setText(workorder_audit_model.getDivide_name());
        binding.tvApplyPerson.setText(workorder_audit_model.getApply_user());
        binding.tvApplyTime.setText(workorder_audit_model.getApply_date());
        binding.tvApprovalType.setText(viewModel.getTypeValue(workorder_audit_model.getAudit_type(), workorder_audit_model.getAudit_sub_type()));
        /**
         * ????????????
         * */
        String type = workorder_audit_model.getAudit_type();
        String subType = workorder_audit_model.getAudit_sub_type();

        if ("INNER_AUDIT_CREATE_PLAN".equals(type) || "INNER_AUDIT_UPDATE_PLAN".equals(type)) {//???????????????????????????
            binding.llCreatChangeInfo.setVisibility(View.VISIBLE);
            binding.tvCreateLine.setText(approvalFormdata.getLine());//??????
            binding.tvCreatePlanName.setText(approvalFormdata.getWorkPlanName());//????????????
            binding.tvCreateWorkInstruction.setText(approvalFormdata.getWorkGuidanceName());//????????????
            switch (subType) {//???????????? ????????????????????????????????????????????????????????? ??????????????????????????????pc????????????
                case ApprovalDataKey.UPDATE_PATROL_PLAN:
                case ApprovalDataKey.CREATE_PATROL_PLAN:
                    binding.llOrder.setVisibility(View.GONE);
                    binding.tvType.setText("??????");
                    break;
            }
            binding.tvCreateResourceClassification.setText(approvalFormdata.getResourceClassificationName());//????????????
            binding.tvCreateWorkOrderRespone.setText(approvalFormdata.getPrincipal());//??????????????????
            String effectivePeriod = approvalFormdata.getEffectivePeriod();
            if (effectivePeriod != null) {
                String[] split = effectivePeriod.split("----");
                binding.tvCreateApplyStartTime.setText(split[0]);//??????????????????
                binding.tvCreateApplyEndTime.setText(split[1]);//??????????????????
            }
            if (approvalFormdata.getFrequency() != null && approvalFormdata.getFrequency().size() > 0) {
                binding.tvCreateExecutionFrequency.setText(approvalFormdata.getFrequency().get(0));//????????????
            }


        } else if ("INNER_AUDIT_POSTPONED".equals(type) || "INNER_AUDIT_FORCE_CLOSE".equals(type)) {//???????????? ????????????
            if ("INNER_AUDIT_FORCE_CLOSE".equals(type)) {
//                binding.llGoneTime.setVisibility(View.GONE);//??????????????????????????????
                binding.rlCreateTime.setVisibility(View.GONE);
                binding.rlFinishTime.setVisibility(View.GONE);
                binding.rlDelayDay.setVisibility(View.GONE);
            }
            //????????????????????????????????????
            PicUrlModelConvert convert = new PicUrlModelConvert();
            List<PicUrlModel> modelList = convert.stringToSomeObjectList(approvalFormdata.getAttachment());
            photoOrderInfoAdapter.updateList(modelList);

            switch (subType) {//???????????? ???????????? ???????????? ???????????? ??????????????? ??????????????? ??????????????????????????????
                case ApprovalDataKey.FORCE_CLOSE_REPAIR:
                case ApprovalDataKey.FORCE_CLOSE_COMPLAIN:
                case ApprovalDataKey.FORCE_CLOSE_ENQUIRY:
                case ApprovalDataKey.POSTPONED_COMPLAIN:
                case ApprovalDataKey.POSTPONED_REPAIR:
                    binding.rlCreateTime.setVisibility(View.GONE);
                    binding.rlFinishTime.setVisibility(View.GONE);
                    binding.rlHeader.setVisibility(View.GONE);//???????????????
                    binding.rlDispatchType.setVisibility(View.GONE);//???????????????
                    binding.rlLine.setVisibility(View.GONE);//??????
                    if (subType.equals(ApprovalDataKey.FORCE_CLOSE_COMPLAIN)) {
                        binding.rlReason1.setVisibility(View.VISIBLE);
                        binding.rlReason2.setVisibility(View.VISIBLE);
                        binding.tvApplyNoWork.setText(approvalFormdata.getSetToInvalid());
                        binding.tvApplyNoWorkReason.setText(approvalFormdata.getInvalidReasonCategory());
                    } else {
                        binding.rlReason1.setVisibility(View.GONE);
                        binding.rlReason2.setVisibility(View.GONE);
                    }
                    break;
                case ApprovalDataKey.POSTPONED_PLAN://???????????? ???????????????
                case ApprovalDataKey.FORCE_CLOSE_PLAN:
//                    binding.rlHeader.setVisibility(View.GONE);//???????????????
//                    binding.rlDispatchType.setVisibility(View.GONE);//???????????????
////                    binding.rlLine.setVisibility(View.GONE);//??????
//                    break;
                case ApprovalDataKey.POSTPONED_PATROL://?????? ???????????? ???????????? ??????????????? ??????????????? ?????? ??????
                case ApprovalDataKey.FORCE_CLOSE_PATROL:
                    binding.rlHeader.setVisibility(View.GONE);//???????????????
                    binding.rlDispatchType.setVisibility(View.GONE);//???????????????
                    break;
            }
            binding.llDelayCloseInfo.setVisibility(View.VISIBLE);
            binding.tvCloseOrderType.setText(approvalFormdata.getFlowType());//????????????
            binding.tvCloseOrderCode.setText(approvalFormdata.getCode());//????????????
            binding.tvCloseLine.setText(approvalFormdata.getLine());//??????
            binding.tvCloseDispatchOrderType.setText(approvalFormdata.getDispatchFlowType());//???????????????
            binding.tvCloseOrderResponer.setText(approvalFormdata.getProcName());//???????????????
            binding.tvCloseCreateTime.setText(approvalFormdata.getCreationTime());//??????????????????
            binding.tvCloseEndTime.setText(approvalFormdata.getDeadlineTime());//??????????????????
            binding.tvCloseDelayDay.setText(approvalFormdata.getExtensionDays() + "???");//????????????
            binding.tvCloseApplyReason.setText(approvalFormdata.getApplicationDescription());//????????????
            binding.tvWorkAsc.setText(approvalFormdata.getWork_ascription());//????????????

        }

        if (tabId == 2) {//????????????tab ????????????
            binding.llPass.setVisibility(View.GONE);
//            binding.cdLimit.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
