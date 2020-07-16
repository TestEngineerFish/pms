package com.einyun.app.pms.patrol.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.SubInspectionWorkOrderFlowNode;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.util.Base64Util;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolTimeWorkNodeBinding;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.model.SignInType;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;

import java.util.List;

/**
 * 巡更详情，巡更（更：时间，打更），按时进行巡查
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
public class PatrolTimeHandleActivity extends PatrolTimeDetialActivity {
    @Autowired
    IUserModuleService userModuleService;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectId;
    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType = ListType.PENDING.getType();
    private AlertDialog dialog;

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    protected void initRequest() {
        setListType(listType);
        setOrderId(orderId);
        setProInsId(proInsId);
        setTaskId(taskId);
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);
        binding.panelHandleInfo.ivDeal.setVisibility(View.GONE);
    }

    /**
     *转单
     */
    public void resendOrder() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withString(RouteKey.KEY_DIVIDE_ID, super.divideId)
                .withString(RouteKey.KEY_PROJECT_ID, super.projectId)
                .withString(RouteKey.KEY_CUSTOM_TYPE, CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                .navigation();
    }
    @Override
    protected void switchStateUI(int plan_work_order_state) {//TODO 根据f_plan_work_order_state判断当前状态 显示隐藏处理布局，显示隐藏接单跟派单
        super.switchStateUI(f_plan_work_order_state);
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.panelHandleForm.setVisibility(View.VISIBLE);
        binding.panelHandleInfo.getRoot().setVisibility(View.GONE);
        binding.panelApplyForceCloseAndPostpone.setVisibility(View.VISIBLE);
        binding.llPatrolRoadName.setVisibility(View.VISIBLE);
        binding.llPatrolRoadDuration.setVisibility(View.VISIBLE);
        if (f_plan_work_order_state==5) {
            binding.btnSubmit.setText("接单");
            binding.panelApplyForceCloseAndPostpone.setVisibility(View.GONE);
            binding.panelHandleForm.setVisibility(View.GONE);
        }else if (f_plan_work_order_state==6){
            binding.btnSubmit.setText("派单");
            binding.panelApplyForceCloseAndPostpone.setVisibility(View.GONE);
            binding.panelHandleForm.setVisibility(View.GONE);
        }else {
            binding.btnSubmit.setText("提交");
        }

    }

    @Override
    protected void setUpWorkNodes() {
        //工作节点适配
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolTimeWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolTimeWorkNodeBinding binding, WorkNode model, int position) {
                    model.setPos(position);
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        WorkNode front = null;
                        if (position > 1) {
                            front = getFrontWorkNode(position);
                        }
                        //处理节点
                        tableItem(binding, model, position);
                        //设置签到
                        setUpSignIn(binding, front, model);
                        //设置拍照
                        setUpCapture(binding, model, position);
                    }
                }

                //处理节点
                protected void tableItem(ItemPatrolTimeWorkNodeBinding binding, WorkNode node, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                    binding.tvWorkNode.setOnClickListener(v -> navigatSignIn(node));
                    binding.tvWorkThings.setOnClickListener(v -> navigatSignIn(node));
                }

                /**
                 * 设置签到
                 * @param binding
                 * @param model
                 */
                protected void setUpSignIn(ItemPatrolTimeWorkNodeBinding binding, WorkNode front, WorkNode model) {
                    //按照签到方式
                    if (SignInType.NONE.equals(model.sign_type) || TextUtils.isEmpty(model.sign_type)) {//不需要签到
                        binding.llSign.setVisibility(View.GONE);
                        binding.llSignComplete.setVisibility(View.GONE);
                    } else if (SignInType.QR.equals(model.sign_type)) {//扫码签到
                        if (SignCheckResult.SIGN_IN_SUCCESS == model.sign_result) {//是否已签到，已签到
                            binding.llSign.setVisibility(View.GONE);
                            binding.llSignComplete.setVisibility(View.VISIBLE);
                        } else {//未签到
                            binding.llSign.setVisibility(View.VISIBLE);
                            binding.llSignComplete.setVisibility(View.GONE);
                            binding.llSign.setOnClickListener(v -> {
                                navigatQRScanner(front, model.getPatrol_point_id());//跳转签到
                            });

                        }
                    }
                    if (f_plan_work_order_state == 5 || f_plan_work_order_state == 6) {
//                        binding.llSign.setVisibility(View.GONE);
//                        binding.llSignComplete.setVisibility(View.VISIBLE);
                        if (SignInType.NONE.equals(model.sign_type) || TextUtils.isEmpty(model.sign_type)) {
                            binding.llSign.setVisibility(View.GONE);
                            binding.llSignComplete.setVisibility(View.GONE);
                        } else {
                            binding.llPhotoComplete.setVisibility(View.GONE);
                            binding.llCapture.setVisibility(View.VISIBLE);
                            binding.llSign.setVisibility(View.VISIBLE);
                            binding.llCapture.setEnabled(false);
                            binding.llSign.setEnabled(false);
                            binding.llSign.setBackgroundResource(R.drawable.shape_button_corners_grey);
                            binding.llCapture.setBackgroundResource(R.drawable.shape_button_corners_grey);
                            binding.ivPic.setColorFilter(getResources().getColor(R.color.white));
                            binding.tvPhoto.setTextColor(getResources().getColor(R.color.white));

                            binding.llSignComplete.setVisibility(View.GONE);
                        }
                    }else {
                        if (SignInType.NONE.equals(model.sign_type) || TextUtils.isEmpty(model.sign_type)) {
                            binding.llSign.setVisibility(View.GONE);
                            binding.llSignComplete.setVisibility(View.GONE);
                        } else {
                            if (SignCheckResult.SIGN_IN_SUCCESS == model.sign_result) {//是否已签到，已签到
                                binding.llSign.setVisibility(View.GONE);
                                binding.llSignComplete.setVisibility(View.VISIBLE);
                            } else {//未签到
                                binding.llPhotoComplete.setVisibility(View.GONE);
                                binding.llCapture.setVisibility(View.VISIBLE);
                                binding.llSign.setVisibility(View.VISIBLE);
                                binding.llCapture.setEnabled(true);
                                binding.llSign.setEnabled(true);
                                binding.llSign.setBackgroundResource(R.drawable.shape_button_corners_blue);
                                binding.llCapture.setBackgroundResource(R.drawable.shape_frame_corners_blue);
                                binding.ivPic.setColorFilter(getResources().getColor(R.color.blueTextColor));
                                binding.tvPhoto.setTextColor(getResources().getColor(R.color.blueTextColor));

                                binding.llSignComplete.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                /**
                 * 设置拍照
                 * @param binding
                 * @param model
                 */
                protected void setUpCapture(ItemPatrolTimeWorkNodeBinding binding, WorkNode model, int position) {
                    //是否需要拍照
                    if (model.is_photo > 0) {//需要拍照
                        if (!TextUtils.isEmpty(model.pic_url) || (model.getCachedImages() != null && model.getCachedImages().size() > 0)) {//是否已有拍照记录，有拍照记录，显示已拍照
                            binding.llPhotoComplete.setVisibility(View.VISIBLE);
                            binding.llCapture.setVisibility(View.GONE);
                            binding.llPhotoComplete.setOnClickListener(v -> navigatSignInPhoto(model, position));//已拍照，点击可以进入修改照片
                        } else {//无拍照记录，显示拍照按钮，进行拍照
                            binding.llPhotoComplete.setVisibility(View.GONE);
                            binding.llCapture.setVisibility(View.VISIBLE);
                            binding.llCapture.setOnClickListener(v -> {
                                navigatSignInPhoto(model, position);
                            });
                        }
                    } else {//不需要拍照，不显示任何拍照按钮
                        binding.llPhotoComplete.setVisibility(View.GONE);
                        binding.llCapture.setVisibility(View.GONE);
                    }
                }

                //处理表头
                protected void tableHead(ItemPatrolTimeWorkNodeBinding binding) {
                    binding.tvNumber.setText(R.string.text_no);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvWorkNode.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.CENTER);
                    binding.tvWorkThings.setText(R.string.text_work_time_items);
                    binding.tvWorkThings.setTextSize(14);
                    binding.llSign.setVisibility(View.GONE);
                    binding.llCapture.setVisibility(View.GONE);
                    binding.llSignComplete.setVisibility(View.GONE);
                    binding.llPhotoComplete.setVisibility(View.GONE);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_time_work_node;
                }
            };
        }
        binding.rvNodes.setAdapter(nodesAdapter);
    }


    private WorkNode getFrontWorkNode(int position) {
        if (position > 0) {
            int frontPos = position - 1;
            WorkNode node = (WorkNode) nodesAdapter.getDataList().get(frontPos);
            if ((SignInType.NONE.equals(node.sign_type) || TextUtils.isEmpty(node.sign_type)) && node.is_photo <= 0) {
                return getFrontWorkNode(frontPos);
            }
            return node;
        }
        return null;
    }

    /**
     * 拍照
     *
     * @param node
     */
    private void navigatSignInPhoto(WorkNode node, int position) {
        if (patrolInfo == null) {
            return;
        }
        saveLocalUserData();
        WorkNode frontWorkNode = getFrontWorkNode(position);
        if (frontWorkNode != null) {
            int ordered = patrolInfo.getData().getZyxcgd().getIs_sort();//是否需要按照顺序
            if (ordered > 0) {
                if (frontWorkNode != null) {//前面没有拍照项
                    if (frontWorkNode.is_photo > 0 && !viewModel.hasCapture(frontWorkNode)) {
//                        ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_capture);
                        initDialog();
                        return;
                    }
                }
            }
            if (ordered > 0) {
                if (frontWorkNode != null) {//前面没有签到项
                    if (SignInType.QR.equals(frontWorkNode.sign_type) && !viewModel.hasSignIn(frontWorkNode)) {
//                        ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_sign_in);
                        initDialog();
                        return;
                    }
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouteKey.KEY_PATROL_TIME_WORKNODE, node);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_PHOTO)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withBundle(RouteKey.KEY_PARAMS, bundle)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SIGN_IN);
    }

    private void initDialog() {
        if (dialog == null) {
            dialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg("请按顺序巡更！")
                    .setPositiveButton("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            dialog.show();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    /**
     * 扫码签到
     *
     * @param frontWorkNode
     * @param qrId
     */
    private void navigatQRScanner(WorkNode frontWorkNode, String qrId) {
        if (patrolInfo == null) {
            return;
        }
        saveLocalUserData();
        int ordered = patrolInfo.getData().getZyxcgd().getIs_sort();
        if (ordered > 0) {
            if (frontWorkNode != null) {//第一个忽略
                if (SignInType.QR.equals(frontWorkNode.sign_type) && !viewModel.hasSignIn(frontWorkNode)) {
//                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_sign_in);
                    initDialog();
                    return;

                }
            }
        }
        if (ordered > 0) {
            if (frontWorkNode != null) {//第一个忽略
                if (frontWorkNode.is_photo > 0 && !viewModel.hasCapture(frontWorkNode)) {
//                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_capture);
                    initDialog();
                    return;
                }
            }
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SCANNER)
                .withString(RouteKey.KEY_QR_ID, qrId)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SCANNER);
    }


    @Override
    protected void initData() {
        super.initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.loadLocalUserData(orderId);
            }
        }, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_SIGN_IN) {

        } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER && resultCode == RESULT_OK) {
            String pointId = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);
            if (pointId.length() < 3) {
                return;
            }
            String subCode = pointId.substring(2, pointId.length());
            boolean sacnResult = data.getBooleanExtra(DataConstants.KEY_QR_SCAN_RESULT, false);
            if (sacnResult) {
                signInSuccess(pointId);
            }
        }
        viewModel.loadLocalUserData(orderId);
    }

    /**
     * 巡更点签到成功
     *
     * @param pointId
     */
    private void signInSuccess(String pointId) {
        viewModel.signInSuccess(orderId, pointId);
    }

    private boolean hasNodeHandled(WorkNode workNode) {
        if (SignInType.QR.equals(workNode.sign_type)) {
            if (workNode.getSign_result() != SignCheckResult.SIGN_IN_SUCCESS) {
                String msg = String.format(getString(R.string.text_lose_node_signin), workNode.getPos());
                ToastUtil.show(this, msg);
                return false;
            }
        }
        if (workNode.is_photo > 0) {
            if (workNode.getCachedImages() == null || workNode.getCachedImages().size() == 0) {
                String msg = String.format(getString(R.string.text_lose_node_pic), workNode.getPos());
                ToastUtil.show(this, msg);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验巡根点
     *
     * @return
     */
    private boolean validateWorkNodes() {
        List<WorkNode> nodes = nodesAdapter.getDataList();
        for (WorkNode workNode : nodes) {
            if (!hasNodeHandled(workNode)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 表单校验
     *
     * @return
     */
    private boolean validateForm() {
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }
        return validateWorkNodes();
    }

    /**
     * 上传所有巡更点图片
     *
     * @param nodes
     */
    private void uploadImage(List<WorkNode> nodes) {
        if (nodes == null) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_local_cached);
            return;
        }
        viewModel.uploadWorkNodesImages(nodes).observe(this, nodes1 -> submitForm(nodes1));
    }

    private void submitForm(List<WorkNode> nodes) {
        createRequest();
        List<SubInspectionWorkOrderFlowNode> flowNodes = viewModel.wrapWorkOrderFlowNodes(patrolInfo.getData().getZyxcgd().getSub_inspection_work_order_flow_node(), nodes);
        patrolInfo.getData().getZyxcgd().setSub_inspection_work_order_flow_node(flowNodes);
        String base64 = Base64Util.encodeBase64(new Gson().toJson(patrolInfo.getData()));
        PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, patrolInfo.getData().getZyxcgd().getId_());
        viewModel.submit(request).observe(this, aBoolean -> {
            if (aBoolean) {
                viewModel.finishTask(orderId).observe(this, aBoolean1 -> {
                    if (aBoolean1) {
                        tipDialog = new TipDialog(this, getString(R.string.text_handle_success));
                        tipDialog.setTipDialogListener(dialog -> {
                            finish();
                        });
                        tipDialog.show();
                    }
                });

            }
        });
    }

    private void createRequest() {
        patrolInfo.getData().getZyxcgd().setF_actual_completion_time(TimeUtil.Now());//处理时间
        patrolInfo.getData().getZyxcgd().setF_plan_work_order_state(OrderState.CLOSED.getState());//关闭状态
        patrolInfo.getData().getZyxcgd().setF_principal_id(viewModel.getUserService().getUserId());//处理人
        patrolInfo.getData().getZyxcgd().setF_principal_name(viewModel.getUserService().getRealName());//处理人姓名
        patrolInfo.getData().getZyxcgd().setF_processing_instructions(binding.limitInput.getString());//处理意见
    }

    @Override
    public void onSubmitClick() {

        if (f_plan_work_order_state == 5) {
            patrolInfo.getData().getZyxcgd().setF_plan_work_order_state(OrderState.HANDING.getState());
            patrolInfo.getData().getZyxcgd().setF_principal_id(viewModel.getUserService().getUserId());
            patrolInfo.getData().getZyxcgd().setF_principal_name(viewModel.getUserService().getRealName());
            Log.e("传参  patrol  为", JsonUtil.toJson(patrolInfo));
            String base64 = Base64Util.encodeBase64(new Gson().toJson(patrolInfo.getData()));
            PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, patrolInfo.getData().getZyxcgd().getId_());
            viewModel.receiceOrder(request).observe(this,model->{

                if (model.isState()) {
                    initSendDialog("接单成功");
                }else {
                    patrolInfo.getData().getZyxcgd().setF_plan_work_order_state(OrderState.PENDING.getState());
                    ToastUtil.show(PatrolTimeHandleActivity.this,model.getMsg());
                }
            });

        } else if (f_plan_work_order_state == 6) {
            Log.e("传参  patrol  为", JsonUtil.toJson(patrolInfo));
            String s = binding.sendOrder.repairSelectedPepple.getText().toString();
            if (!"请选择".equals(s)) {
                patrolInfo.getData().getZyxcgd().setF_SEND_REMARK(binding.sendOrder.repairSendReason.getString());
                String base64 = Base64Util.encodeBase64(new Gson().toJson(patrolInfo.getData()));
                String f_send_remark = binding.sendOrder.repairSendReason.getString();
                PatrolSubmitRequest request = new PatrolSubmitRequest(taskId,f_send_remark, PatrolSubmitRequest.ACTION_AGREE, base64, patrolInfo.getData().getZyxcgd().getId_());
                viewModel.assignOrder(request).observe(this,model->{

                    if (model.isState()) {
                       initSendDialog("派单成功");
                    }else {
                        patrolInfo.getData().getZyxcgd().setF_plan_work_order_state(OrderState.OVER_DUE.getState());
                        ToastUtil.show(PatrolTimeHandleActivity.this,model.getMsg());
                    }
                });
            }else {
                ToastUtil.show(PatrolTimeHandleActivity.this,"请选择指派人");
            }
        }else {

            if (validateForm()) {
                if (nodesAdapter != null) {
                    uploadImage(nodesAdapter.getDataList());
                }
            }
        }
    }

    /**
     * 签到详情
     *
     * @param node
     */
    protected void navigatSignIn(WorkNode node) {
        saveLocalUserData();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouteKey.KEY_PATROL_TIME_WORKNODE, node);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_HANDLE)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withBundle(RouteKey.KEY_PARAMS, bundle)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SIGN_IN);
    }
}
