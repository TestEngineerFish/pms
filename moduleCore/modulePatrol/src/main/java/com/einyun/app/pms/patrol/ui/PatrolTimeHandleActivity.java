package com.einyun.app.pms.patrol.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolTimeWorkNodeBinding;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.model.SignInType;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

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

    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType = ListType.PENDING.getType();

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    protected void initRequest() {
        setListType(listType);
        setOrderId(orderId);
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);
    }

    @Override
    protected void switchStateUI() {
        super.switchStateUI();
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.panelHandleInfo.getRoot().setVisibility(View.GONE);
    }

    @Override
    protected void setUpWorkNodes() {
        //工作节点适配
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolTimeWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolTimeWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        WorkNode front = null;
                        if (position > 1) {
                            front = (WorkNode) nodesAdapter.getDataList().get(position - 1);
                        }
                        //处理节点
                        tableItem(binding,model, position);
                        //设置签到
                        setUpSignIn(binding, front, model);
                        //设置拍照
                        setUpCapture(binding, front, model);
                    }
                }

                //处理节点
                protected void tableItem(ItemPatrolTimeWorkNodeBinding binding,WorkNode node, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                    binding.tvWorkNode.setOnClickListener(v -> navigatSignInDetial(node));
                    binding.tvWorkThings.setOnClickListener(v -> navigatSignInDetial(node));
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
                }

                /**
                 * 设置拍照
                 * @param binding
                 * @param model
                 */
                protected void setUpCapture(ItemPatrolTimeWorkNodeBinding binding, WorkNode front, WorkNode model) {
                    //是否需要拍照
                    if (model.is_photo > 0) {//需要拍照
                        if (!TextUtils.isEmpty(model.pic_url)) {//是否已有拍照记录，有拍照记录，显示已拍照
                            binding.llPhotoComplete.setVisibility(View.VISIBLE);
                            binding.llCapture.setVisibility(View.GONE);
                        } else {//无拍照记录，显示拍照按钮，进行拍照
                            binding.llPhotoComplete.setVisibility(View.GONE);
                            binding.llCapture.setVisibility(View.VISIBLE);
                            binding.llCapture.setOnClickListener(v -> {
                                navigatSignIn(front, model);
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

    /**
     * 拍照
     * @param frontWorkNode
     * @param node
     */
    private void navigatSignIn(WorkNode frontWorkNode, WorkNode node) {
        if (patrolInfo == null) {
            return;
        }
        saveLocalUserData();
        int ordered = patrolInfo.getData().getZyxcgd().getIs_sort();//是否需要按照顺序
        if (ordered > 0) {
            if (frontWorkNode != null) {//第一个忽略
                if (frontWorkNode.is_photo > 0 && !viewModel.hasCapture(frontWorkNode)) {
                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_capture);
                    return;
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouteKey.KEY_PATROL_TIME_WORKNODE, node);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_HANDLE)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withBundle(RouteKey.KEY_PARAMS, bundle)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SIGN_IN);
    }

    /**
     * 扫码签到
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
                    ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_order_sign_in);
                    return;
                }
            }
        }
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SCANNER)
                .withString(RouteKey.KEY_QR_ID, qrId)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SCANNER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RouterUtils.ACTIVITY_REQUEST_SIGN_IN) {

        } else if (requestCode == RouterUtils.ACTIVITY_REQUEST_SCANNER && resultCode == RESULT_OK) {
            String pointId = data.getStringExtra(DataConstants.KEY_SCANNER_CONTENT);
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
}
