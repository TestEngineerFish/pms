package com.einyun.app.pms.patrol.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.SubInspectionWorkOrderFlowNode;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.util.Base64Util;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.GetUploadJson;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.library.resource.workorder.model.OrderState;
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

@Route(path = RouterUtils.ACTIVITY_PATROL_HANDLE)
public class PatrolHandleActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends PatrolDetialActivity {
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
    int listType= ListType.PENDING.getType();

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol);
        binding.setCallBack(this);
    }

    protected void switchStateUI() {
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.panelHandleInfo.getRoot().setVisibility(View.GONE);
    }

    protected void initRequest() {
        super.orderId=orderId;
        super.listType=listType;
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
                            if (model.result.equals(WorkNode.RESULT_REJECT)) {
                                onReject(binding);
                            } else if (model.result.equals(WorkNode.RESULT_PASS)) {
                                onAgree(binding);
                            }
                        }
                    }
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
                        model.setResult(WorkNode.RESULT_REJECT);
                        saveLocalUserData();
                    });
                }

                //通过
                protected void agree(ItemPatrolWorkNodeBinding binding, WorkNode model) {
                    binding.btnAgree.setOnClickListener((View v) -> {
                        onAgree(binding);
                        model.setResult(WorkNode.RESULT_PASS);
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
     * @return
     */
    private boolean validateFormData(){
        List<WorkNode> data = getWorkNodes();
        for (int i=0;i<data.size();i++) {
            if (TextUtils.isEmpty(data.get(i).result)) {
                ToastUtil.show(this,String.format(getResources().getString(R.string.text_alert_handle_node), data.get(i).number));
                return false;
            }
        }
        if (TextUtils.isEmpty(binding.limitInput.getString())) {
            ToastUtil.show(this, R.string.text_alert_handle_content);
            binding.limitInput.requestFocus();
            return false;
        }

        if(photoSelectAdapter.getSelectedPhotos().size()<=0){
            ToastUtil.show(this, R.string.text_alert_photo_empty);
            return false;
        }

        return true;
    }

    /**
     * 获取节点处理信息
     * @return
     */
    @NotNull
    protected List<WorkNode> getWorkNodes() {
        List<WorkNode> all=nodesAdapter.getDataList();
        return all.subList(1,all.size());
    }

    /**
     * 上传图片
     * @param patrol
     */
    private void uploadImages(PatrolInfo patrol){
        if(patrol==null){
            return;
        }
        viewModel.uploadImages(photoSelectAdapter.getSelectedPhotos()).observe(this, picUrls -> {
            wrapFormData(patrol, picUrls);
            acceptForm(patrol);
        });
    }

    /**
     * 包装表单数据
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
        int index=0; //异常节点选项
        for(SubInspectionWorkOrderFlowNode node:patrol.getData().getZyxcgd().getSub_inspection_work_order_flow_node()){
            for(WorkNode workNode:getWorkNodes()){
                if(node.getF_WK_ID().equals(workNode.number)){
                    node.setF_WK_RESULT(workNode.getResult());
                    if(workNode.result.equals("0")) index++;
                }
            }
        }
        return index>0;
    }

    private void acceptForm(PatrolInfo patrol){
        patrol.getData().getZyxcgd().setF_actual_completion_time(TimeUtil.Now());
        patrol.getData().getZyxcgd().setF_plan_work_order_state(OrderState.APPLY.getState());
        patrol.getData().getZyxcgd().setF_principal_id(userModuleService.getUserId());
        patrol.getData().getZyxcgd().setF_principal_name(userModuleService.getUserName());
        Logger.d("data->"+new Gson().toJson(patrol));
        String base64=Base64Util.encodeBase64(new Gson().toJson(patrol));
        PatrolSubmitRequest request=new PatrolSubmitRequest(taskId,PatrolSubmitRequest.ACTION_AGREE,base64,patrol.getData().getZyxcgd().getId_());
        viewModel.submit(request).observe(this, aBoolean -> {
            if(aBoolean){
                viewModel.finishTask(orderId);
                tipDialog=new TipDialog(this,getString(R.string.text_handle_success));
                tipDialog.setTipDialogListener(dialog -> {
                    if(hasException(patrol)){
                        createSendOrder();
                    }else{
                        finish();
                    }
                });
                tipDialog.show();
            }
        });
    }

    /**
     * 是否创建派工单
     */
    private void createSendOrder() {
        if(alertDialog==null){
            alertDialog=new AlertDialog(PatrolHandleActivity.this).builder()
                    .setTitle(getString(R.string.text_alert))
                    .setMsg(getString(R.string.text_request_create_distribute))
                    .setPositiveButton(getString(R.string.ok), v -> {
                         //goPaiGongDan(); 跳转至创建派工单
                    }).setNegativeButton(getString(R.string.cancel), v -> {
                        finish();
                    });
            alertDialog.show();
        }
    }


//    /**
//     * 保存本地数据
//     */
//    protected void saveLocalUserData(){
//        List<Uri> uris = photoSelectAdapter.getSelectedPhotos();
//        List<String> images = new ArrayList<>();
//        for (Uri uri : uris) {
//            images.add(uri.toString());
//        }
//        if(patrolLocal==null){
//            patrolLocal=new PatrolLocal();
//            patrolLocal.setOrderId(orderId);
//            patrolLocal.setUserId(userModuleService.getUserId());
//        }
//        patrolLocal.setImages(images);
//        patrolLocal.setNote(binding.limitInput.getString());
//        patrolLocal.setNodes(nodesAdapter.getDataList());
//        viewModel.saveLocal(patrolLocal);
//    }

    /**
     * 提交
     */
    public void onSubmitClick() {
        if (!validateFormData()) {
            return;
        }
        uploadImages(patrolInfo);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLocalUserData();
        if(tipDialog!=null){
            tipDialog.dismiss();
        }
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }




}
