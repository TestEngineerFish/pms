package com.einyun.app.pms.patrol.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.SubInspectionWorkOrderFlowNode;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.util.Base64Util;
import com.einyun.app.base.util.JsonUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.WorkOrder;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.model.IsClosedState;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.ResultState;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.dialog.CreateNewOrderDialog;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.common.utils.NetWorkUtils;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.IsClosedRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolSubmitRequest;
import com.einyun.app.library.upload.model.PicUrl;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_WORK_PREVIEW_PATRO;

/**
 * 巡查处理
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_HANDLE)
public class PatrolHandleActivity extends PatrolDetialActivity {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;

    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType = ListType.PENDING.getType();

    protected void setListType(int listType) {
        super.setListType(listType);
    }

    protected void setOrderId(String orderId) {
        super.setOrderId(orderId);
    }

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     *转单
     */
    public void resendOrder() {
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {

            ToastUtil.show(CommonApplication.getInstance(), "请连接网络后，进行处理");
            return;
        }
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
    protected void switchStateUI(int f_plan_work_order_state) {//TODO 根据f_plan_work_order_state判断当前状态 显示隐藏处理布局，显示隐藏接单跟派单
        super.switchStateUI(f_plan_work_order_state);
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.panelHandleForm.setVisibility(View.VISIBLE);
        binding.panelHandleInfo.getRoot().setVisibility(View.GONE);
        binding.panelApplyForceCloseAndPostpone.setVisibility(View.VISIBLE);
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

    protected void initRequest() {
        super.setListType(listType);
        super.setOrderId(orderId);
        super.setProInsId(proInsId);
        super.setTaskId(taskId);
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);
    }

    protected void setUpWorkNodes() {
        //工作节点适配
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //处理节点
                        tableItem(binding, position);
                        //选中通过
                        agree(binding, model);
                        //选中不通过
                        reject(binding, model);

                        if (!TextUtils.isEmpty(model.result)) {
                            if (ResultState.RESULT_FAILD.equals(model.result)) {
                                onReject(binding);
                            } else if (ResultState.RESULT_SUCCESS.equals(model.result)) {
                                onAgree(binding);
                            }
                        }
                        if (f_plan_work_order_state==5||f_plan_work_order_state==6) {
                            binding.btnReject.setVisibility(View.VISIBLE);
                            binding.btnAgree.setVisibility(View.VISIBLE);
                            binding.btnAgree.setEnabled(false);
                            binding.btnReject.setEnabled(false);
                            binding.btnAgree.setBackgroundResource(R.drawable.shape_button_corners_grey);
                            binding.btnReject.setBackgroundResource(R.drawable.shape_button_corners_grey);
                            binding.btnAgree.setTextColor(getResources().getColor(R.color.white));
                            binding.btnReject.setTextColor(getResources().getColor(R.color.white));
//                            binding.tvResult.setVisibility(View.GONE);
                        }else {
                            binding.btnReject.setVisibility(View.VISIBLE);
                            binding.btnAgree.setVisibility(View.VISIBLE);
                            binding.btnAgree.setEnabled(true);
                            binding.btnReject.setEnabled(true);
//                            binding.tvResult.setVisibility(View.GONE);
                        }
                    }
                }

                protected void onNoneHandle(ItemPatrolWorkNodeBinding binding) {
                    binding.btnReject.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.GONE);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvResult.setText(R.string.text_un_need_handle);
                    binding.tvResult.setTypeface(null, Typeface.NORMAL);
                    binding.tvResult.setTextSize(12);
                    binding.tvResult.setTextColor(CommonApplication.getInstance().getResources().getColor(R.color.normal_main_text_icon_color));
                }

                protected void onReject(ItemPatrolWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                    binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                }

                protected void onAgree(ItemPatrolWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setBackgroundResource(R.drawable.shape_frame_corners_gray);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.normal_main_text_icon_color));
                }

                //不通过
                protected void reject(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnReject.setOnClickListener(v -> {
                        onReject(binding);
                        model.setResult(ResultState.RESULT_FAILD);
                        saveLocalUserData();
                    });
                }

                //通过
                protected void agree(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(ResultState.RESULT_SUCCESS);
                        saveLocalUserData();
                    });
                }

                //处理节点
                protected void tableItem(ItemPatrolWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //处理表头
                protected void tableHead(ItemPatrolWorkNodeBinding binding) {
                    binding.tvNumber.setText(R.string.text_no);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.btnAgree.setVisibility(View.GONE);
                    binding.btnReject.setVisibility(View.GONE);
                    binding.tvWorkNode.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.CENTER);
                    binding.tvWorkThings.setText(R.string.text_work_items);
                    binding.tvWorkThings.setTextSize(14);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_work_node;
                }
            };
        }
        binding.rvNodes.setAdapter(nodesAdapter);
    }

    /**
     * 校验必填参数
     *
     * @return
     */
    private boolean validateFormData() {
        List<WorkNode> data = getWorkNodes();
        for (int i = 0; i < data.size(); i++) {
            if (TextUtils.isEmpty(data.get(i).result)) {
                ToastUtil.show(this, String.format(getResources().getString(R.string.text_alert_handle_node), data.get(i).number));
                return false;
            }
        }
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }

        if (photoSelectAdapter.getSelectedPhotos().size() <= 0) {
            ToastUtil.show(this, R.string.text_alert_photo_empty);
            return false;
        }

        return true;
    }

    /**
     * 获取节点处理信息
     *
     * @return
     */
    @NotNull
    protected List<WorkNode> getWorkNodes() {
        List<WorkNode> all = nodesAdapter.getDataList();
        return all.subList(1, all.size());
    }

    /**
     * 上传图片
     *
     * @param patrol
     */
    private void uploadImages(PatrolInfo patrol) {
        if (patrol == null) {
            return;
        }
        if (!NetWorkUtils.isNetworkConnected(CommonApplication.getInstance())) {

            ToastUtil.show(CommonApplication.getInstance(), "请连接网络后，进行处理");
            return;
        }
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            if (picUrls == null) {
                ToastUtil.show(CommonApplication.getInstance(), R.string.text_alert_local_cached);
                return;
            }
            wrapFormData(patrol, picUrls);
            acceptForm(patrol);
        });
    }

    /**
     * 包装表单数据
     *
     * @param patrol
     * @param picUrls
     * @return
     */
    private void wrapFormData(PatrolInfo patrol, List<PicUrl> picUrls) {
        GetUploadJson getUploadJsonStr = new GetUploadJson(picUrls).invoke();
        Gson gson = getUploadJsonStr.getGson();
        List<PicUrlModel> picUrlModels = getUploadJsonStr.getPicUrlModels();
        patrol.getData().getZyxcgd().setF_files(gson.toJson(picUrlModels));//包装上传图片信息
        patrol.getData().getZyxcgd().setF_processing_instructions(binding.limitInput.getString());//包装节点选择信息
    }

    private boolean hasException(PatrolInfo patrol) {
        int index = 0; //异常节点选项
        for (SubInspectionWorkOrderFlowNode node : patrol.getData().getZyxcgd().getSub_inspection_work_order_flow_node()) {
            for (WorkNode workNode : getWorkNodes()) {
                if (node.getF_WK_ID().equals(workNode.number)) {
                    node.setF_WK_RESULT(workNode.getResult());
                    if (workNode.result.equals("0")) index++;
                }
            }
        }
        return index > 0;
    }

    private void acceptForm(PatrolInfo patrol) {
        hasException(patrol);
        patrol.getData().getZyxcgd().setF_actual_completion_time(TimeUtil.Now());
        patrol.getData().getZyxcgd().setF_plan_work_order_state(OrderState.CLOSED.getState());
        patrol.getData().getZyxcgd().setF_principal_id(viewModel.getUserService().getUserId());
        patrol.getData().getZyxcgd().setF_principal_name(viewModel.getUserService().getRealName());
//        Logger.d("data->"+new Gson().toJson(patrol));
        String base64 = Base64Util.encodeBase64(new Gson().toJson(patrol.getData()));
        PatrolSubmitRequest request = new PatrolSubmitRequest(taskId, PatrolSubmitRequest.ACTION_AGREE, base64, patrol.getData().getZyxcgd().getId_());
        request.setRemark(binding.limitInput.getString());
        viewModel.submit(request).observe(this, aBoolean -> {
            if (aBoolean) {
                viewModel.finishTask(orderId);
                tipDialog = new TipDialog(this, getString(R.string.text_handle_success));
                tipDialog.setTipDialogListener(dialog -> {
                    if (hasException(patrol)) {
                        tipDialog.dismiss();
                        createSendOrder();
                    } else {
                        finish();
                    }
                });
                tipDialog.showNoCancle();
            }
        });
    }

//    /**
//     * 是否创建派工单
//     */
//    protected void createSendOrder() {
//        if(alertDialog==null){
//            alertDialog=new AlertDialog(PatrolHandleActivity.this).builder()
//                    .setTitle(getString(R.string.text_alert))
//                    .setMsg(getString(R.string.text_request_create_distribute))
//                    .setPositiveButton(getString(R.string.ok), v -> {
//                         //goPaiGongDan(); 跳转至创建派工单
//                        navigatSendWorkOrder();
//                        finish();
//                    }).setNegativeButton(getString(R.string.cancel), v -> {
//                        finish();
//                    });
//            alertDialog.show();
//            alertDialog.setCancelable(false);
//        }
//    }

    /**
     * 是否创建派工单
     */
    private void createSendOrder() {
        if (alertDialog == null) {
            alertDialog = new CreateNewOrderDialog(this).builder()
                    .setCreateSendOrder(v -> {
                        navigatSendWorkOrder();//跳转至创建派工单
                        finish();
                    }).setCreateUnOrder(v -> {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE)
                                .withString(RouteKey.CODE, patrolInfo.getData().getZyxcgd().getF_plan_work_order_code())
                                .withString(RouteKey.KEY_ORDER_ID, patrolInfo.getId())
                                .withString(RouteKey.KEY_ORDER_NO, patrolInfo.getData().getZyxcgd().getF_plan_work_order_code())
                                .withString(RouteKey.KEY_LINE, patrolInfo.getData().getZyxcgd().getF_line_name())
                                .withString(RouteKey.KEY_RESOUSE, patrolInfo.getData().getZyxcgd().getF_type_name())
                                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                                .withString(RouteKey.KEY_TASK_ID, taskId)
                                .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_WORK_PREVIEW_PATRO)
                                .withString(RouteKey.F_ORIGINAL_TYPE, "2")
                                .withString(RouteKey.KEY_LINE_ID, patrolInfo.getData().getZyxcgd().getF_line_id())
                                .withString(RouteKey.KEY_LINE_CODE, patrolInfo.getData().getZyxcgd().getF_line_code())
                                .withString(RouteKey.KEY_PROJECT, patrolInfo.getData().getZyxcgd().getF_project_name())
                                .withString(RouteKey.KEY_DIVIDE_NAME, patrolInfo.getData().getZyxcgd().getF_massif_name())
                                .withString(RouteKey.KEY_DIVIDE_ID, patrolInfo.getData().getZyxcgd().getF_massif_id())
                                .withString(RouteKey.KEY_RESOUSE_ID, patrolInfo.getData().getZyxcgd().getREF_ID_())
                                .navigation();
                        finish();
                    }).setCancel(v -> {
                        finish();
                    });
            alertDialog.show();
        }
    }

    private void navigatSendWorkOrder() {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CREATE_SEND_ORDER)
                .withString(RouteKey.KEY_ORDER_ID, patrolInfo.getId())
                .withString(RouteKey.KEY_ORDER_NO, patrolInfo.getData().getZyxcgd().getF_plan_work_order_code())
                .withString(RouteKey.KEY_LINE, patrolInfo.getData().getZyxcgd().getF_line_name())
                .withString(RouteKey.KEY_RESOUSE, patrolInfo.getData().getZyxcgd().getF_type_name())
                .withString(RouteKey.KEY_PRO_INS_ID, proInsId)
                .withString(RouteKey.KEY_TASK_ID, taskId)
                .withString(RouteKey.F_ORIGINAL_TYPE, "2")
                .withString(RouteKey.KEY_TASK_NODE_ID, taskNodeId)
                .withString(RouteKey.KEY_FRAGEMNT_TAG, FRAGMENT_WORK_PREVIEW_PATRO)
                //带园区过去
                .withString(RouteKey.KEY_LINE_ID, patrolInfo.getData().getZyxcgd().getF_line_id())
                .withString(RouteKey.KEY_LINE_CODE, patrolInfo.getData().getZyxcgd().getF_line_code())
                .withString(RouteKey.KEY_PROJECT, patrolInfo.getData().getZyxcgd().getF_project_name())
                .withString(RouteKey.KEY_DIVIDE_NAME, patrolInfo.getData().getZyxcgd().getF_massif_name())
                .withString(RouteKey.KEY_DIVIDE_ID, patrolInfo.getData().getZyxcgd().getF_massif_id())
                .withString(RouteKey.KEY_RESOUSE_ID, patrolInfo.getData().getZyxcgd().getREF_ID_())
                .navigation();
    }


    /**
     * 提交
     */
    public void onSubmitClick() {//TODO 根据 f_plan_work_order_state 判断是接单 派单 调相应接口

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
                    ToastUtil.show(PatrolHandleActivity.this,model.getMsg());
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
                        ToastUtil.show(PatrolHandleActivity.this,model.getMsg());
                    }
                });
            }else {
                ToastUtil.show(PatrolHandleActivity.this,"请选择指派人");
            }
        } else {

            if (!validateFormData()) {
                return;
            }
            uploadImages(patrolInfo);
        }
    }
    AlertDialog sendDialog;
    public void initSendDialog(String content) {
        if (sendDialog == null) {
            sendDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                    .setMsg(content)
                    .setPositiveButton("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
            sendDialog.show();
        } else {
            if (!sendDialog.isShowing()) {
                sendDialog.show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLocalUserData();
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


}
