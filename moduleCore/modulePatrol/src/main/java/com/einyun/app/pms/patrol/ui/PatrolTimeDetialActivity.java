package com.einyun.app.pms.patrol.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolTimeWorkNodeBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.model.SignCheckResult;
import com.einyun.app.pms.patrol.model.SignInType;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;


/**
 * 巡更详情，巡更（更：时间，打更），按时进行巡查
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
public class PatrolTimeDetialActivity extends PatrolHandleActivity{
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;

    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType= ListType.PENDING.getType();

    protected void setListType(int listType){
        super.setListType(listType);
    }

    protected void setOrderId(String orderId){
        super.setOrderId(orderId);
    }

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this,new ViewModelFactory()).get(PatrolViewModel.class);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol_time);
        setRightOption(R.drawable.histroy);
    }


    protected void initRequest() {
        setListType(listType);
        setOrderId(orderId);
        setProInsId(proInsId);
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);
    }

    @Override
    protected void switchStateUI() {
        super.switchStateUI();
        binding.tvWorkNodesTitle.setText("巡更点处理");
        binding.panelHandleForm.setVisibility(View.GONE);
        binding.panelHandleInfo.getRoot().setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        binding.itemOrdered.setVisibility(View.VISIBLE);
        binding.llGrid.setVisibility(View.GONE);
        binding.llPlanName.setVisibility(View.GONE);
        binding.llWorkGuide.setVisibility(View.GONE);
        binding.llTypes.setVisibility(View.GONE);
        binding.itemCaptures.setVisibility(View.GONE);
        binding.panelApplyForceCloseAndPostpone.setVisibility(View.GONE);
        binding.llPatrolRoadName.setVisibility(View.VISIBLE);
        binding.llPatrolRoadDuration.setVisibility(View.VISIBLE);
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
                        //处理节点
                        tableItem(binding,model, position);
                        //设置签到


                        if(model.is_photo<=0&&(SignInType.NONE.equals(model.sign_type)|| TextUtils.isEmpty(model.sign_type))){
                            onNoneHandle(binding);
                        }else{
                            setUpSignIn(binding,model);
                            //设置拍照
                            setUpCapture(binding,model);
                        }

                    }
                }

                protected void onNoneHandle(ItemPatrolTimeWorkNodeBinding binding) {
                    binding.llPhotoComplete.setVisibility(View.GONE);
                    binding.llCapture.setVisibility(View.GONE);
                    binding.llSign.setVisibility(View.GONE);
                    binding.llSignComplete.setVisibility(View.GONE);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvResult.setText(R.string.text_un_need_handle);
                    binding.tvResult.setTypeface(null, Typeface.NORMAL);
                    binding.tvResult.setTextSize(12);
                    binding.tvResult.setTextColor(CommonApplication.getInstance().getResources().getColor(R.color.normal_main_text_icon_color));
                }

                //处理节点
                protected void tableItem(ItemPatrolTimeWorkNodeBinding binding,WorkNode node, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                    binding.llSign.setVisibility(View.GONE);
                    binding.llCapture.setVisibility(View.GONE);
                    binding.tvWorkNode.setOnClickListener(v -> navigatSignIn(node));
                    binding.tvWorkThings.setOnClickListener(v -> navigatSignIn(node));
                }

                /**
                 * 设置签到
                 * @param binding
                 * @param model
                 */
                protected void setUpSignIn(ItemPatrolTimeWorkNodeBinding binding,WorkNode model){
                    //按照签到方式
                    if(SignInType.NONE.equals(model.sign_type)|| TextUtils.isEmpty(model.sign_type)){//不需要签到
                        binding.llSign.setVisibility(View.GONE);
                        binding.llSignComplete.setVisibility(View.GONE);
                    }else if(SignInType.QR.equals(model.sign_type)){//扫码签到
                        if(SignCheckResult.SIGN_IN_SUCCESS==model.sign_result){//是否已签到，已签到
                            binding.llSignComplete.setVisibility(View.VISIBLE);
                        }else{//未签到
                            binding.llSignComplete.setVisibility(View.GONE);
                        }
                    }
                }

                /**
                 * 设置拍照
                 * @param binding
                 * @param model
                 */
                protected void setUpCapture(ItemPatrolTimeWorkNodeBinding binding,WorkNode model){
                    //是否需要拍照
                    if(model.is_photo>0){//需要拍照
                        if(!TextUtils.isEmpty(model.pic_url)||model.getCachedImages()!=null){//是否已有拍照记录，有拍照记录，显示已拍照
                            binding.llPhotoComplete.setVisibility(View.VISIBLE);
                        }else{//无拍照记录
                            binding.llPhotoComplete.setVisibility(View.GONE);
                        }
                    }else{//不需要拍照，不显示任何拍照按钮
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
     * 签到详情
     * @param node
     */
    protected void navigatSignIn(WorkNode node){
        saveLocalUserData();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouteKey.KEY_PATROL_TIME_WORKNODE, node);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_QR_SIGNIN_DETIAL)
                .withString(RouteKey.KEY_ORDER_ID, orderId)
                .withBundle(RouteKey.KEY_PARAMS, bundle)
                .navigation(this, RouterUtils.ACTIVITY_REQUEST_SIGN_IN);
    }

}
