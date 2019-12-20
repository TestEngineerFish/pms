package com.einyun.app.pms.repairs.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PicUrlModel;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.SwipeItemLayout;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;
import com.einyun.app.library.workorder.net.request.SaveHandleRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.repairs.BR;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ActivityRepairsDetailBinding;
import com.einyun.app.pms.repairs.databinding.ItemHandleBinding;
import com.einyun.app.pms.repairs.model.MaterialModel;
import com.einyun.app.pms.repairs.viewmodel.RepairDetailViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
public class RepairsDetailActivity extends BaseHeadViewModelActivity<ActivityRepairsDetailBinding, RepairDetailViewModel> implements View.OnClickListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String nodeId;
    RepairsDetailModel detialModel;
    PhotoListAdapter photoListInfoAdapter;
    List<MaterialModel> list = new ArrayList<>();
    RVBindingAdapter<ItemHandleBinding, MaterialModel> materialAdapter;
    SaveHandleRequest saveHandleRequest;

    @Override
    protected RepairDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repairs_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_repair);
        setView(nodeId);//根据状态值显示相应布局
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user_id(model.getId());
        });
        materialAdapter = new RVBindingAdapter<ItemHandleBinding, MaterialModel>(this, com.einyun.app.pms.repairs.BR.material) {
            @Override
            public void onBindItem(ItemHandleBinding binding, MaterialModel model, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.item_handle;
            }
        };
        list.add(new MaterialModel());
        list.add(new MaterialModel());
        binding.repairHandle.repairHandleRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        materialAdapter.setDataList(list);
        binding.repairHandle.repairHandleRec.setAdapter(materialAdapter);
        binding.repairHandle.repairHandleRec.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    @Override
    protected void initData() {
        super.initData();
        photoListInfoAdapter = new PhotoListAdapter(this);
        binding.repairsInfo.repairOrderDetailList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.repairsInfo.repairOrderDetailList.addItemDecoration(new SpacesItemDecoration(18, 0, 0, 0));
        binding.repairsInfo.repairOrderDetailList.setAdapter(photoListInfoAdapter);
        viewModel.getRepairDetail(proInsId).observe(this, repairsDetail -> {
            updateUI(repairsDetail);
            saveHandleRequest = new SaveHandleRequest(orderId, detialModel.getData().getCustomer_repair_model());
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.repairDetailSubmit.setOnClickListener(this);
        binding.sendOrder.repairSelectPeople.setOnClickListener(this);
        binding.repairHandle.handleAddMaterial.setOnClickListener(this);
        binding.handlerDetailSave.setOnClickListener(this);
        binding.handlerDetailSubmit.setOnClickListener(this);
        binding.repairHandle.handleAddMaterial.setOnClickListener(this);
    }

    /**
     * 详情数据获取后进行UI展示
     *
     * @param
     */
    private void updateUI(RepairsDetailModel repairsOrderDetail) {
        detialModel = repairsOrderDetail;
        if (detialModel == null) {
            return;
        }
        binding.setRepairs(repairsOrderDetail);
        binding.orderInfo.setRepairs(repairsOrderDetail);
        binding.repairsInfo.setRepairs(repairsOrderDetail);
        binding.sendOrder.setRepairs(repairsOrderDetail);
        binding.repairResponseInfo.setRepairs(repairsOrderDetail);
        binding.sendOrderInfo.setRepairs(repairsOrderDetail);
        binding.repairResponseInfo.setRepairs(repairsOrderDetail);
        if (detialModel.getData().getCustomer_repair_model().getHandle_time() != null) {
            binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detialModel.getData().getCustomer_repair_model().getHandle_time().toString()));
        }
        updateImagesUI(repairsOrderDetail);
        detialModel.setTaskNodeId(nodeId);
    }

    private void updateImagesUI(RepairsDetailModel repairsDetailModel) {
        if (detialModel == null) {
            return;
        }
        PicUrlModelConvert convert = new PicUrlModelConvert();
//        List<PicUrlModel> modelList = convert.stringToSomeObjectList(repairsDetailModel.getData().getCustomer_repair_model().get);
//        photoListInfoAdapter.updateList(modelList);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.repair_detail_submit) {
            handleSubmit();
        }
        if (v.getId() == R.id.repair_select_people) {
            selectPeple();
        }
        if (v.getId() == R.id.repair_handle_rec) {

        }
        if (v.getId() == R.id.handler_detail_save) {
            saveHandler();
        }
        if (v.getId() == R.id.handler_detail_submit) {
            sendOrder();
        }
        if (v.getId()==R.id.handle_add_material){
            addMaterial();
        }

    }

    /**
     * 处理表单
     */
    private void handleSubmit() {
        if (detialModel == null) {
            return;
        }
        sendOrder();
    }

    /**
     * 选择指派人
     */
    private void selectPeple() {
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detialModel.getData().getCustomer_repair_model().getBx_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detialModel.getData().getCustomer_repair_model().getU_project_id())
                .navigation();
    }

    /**
     * 派单，响应,处理
     */
    private void sendOrder() {
//        if (TextUtils.isEmpty(binding.repariResponse.repairResponseReason.getString())) {
//            ToastUtil.show(this, R.string.text_please_enter_reason);
//        } else {
        detialModel.getData().getCustomer_repair_model().setResponse_result(binding.repariResponse.repairResponseReason.getString());
        viewModel.repairSend(new RepairSendOrderRequest(detialModel.getData().getCustomer_repair_model(), new RepairSendOrderRequest.DoNextParamBean(taskId))).observe(this, status -> {
            if (status) {
                new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                        .setMsg(getResources().getString(R.string.text_response_success)).
                        setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.refreshUI();
                                RepairsDetailActivity.this.finish();
                            }
                        }).show();
            }
        });
//        }
    }

    /**
     * 保存处理
     */
    private void saveHandler() {
        if (TextUtils.isEmpty(binding.repairHandleResult.repairResponseReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
        } else {
            saveHandleRequest.getBizData().setHandle_result(binding.repairHandleResult.repairResponseReason.getString());
            viewModel.saveHandler(saveHandleRequest).observe(this, status -> {
                if (status) {
                    new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                            .setMsg(getResources().getString(R.string.text_response_success)).
                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewModel.refreshUI();
                                    RepairsDetailActivity.this.finish();
                                }
                            }).show();
                }
            });
        }
    }

    /**
     * 根据状态显示布局
     */

    private void setView(String status) {
        //响应状态
        if (status.equals(RouteKey.REPAIR_STATUS_RESPONSE)) {
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repariResponse.getRoot().setVisibility(View.VISIBLE);
            binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
            binding.repairDetailSubmit.setVisibility(View.VISIBLE);
        }
        //处理状态
        if (status.equals(RouteKey.REPAIR_STATUS_HANDLE)) {
            binding.sendOrderInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairClosePostpone.getRoot().setVisibility(View.VISIBLE);
            binding.repairResponseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandle.getRoot().setVisibility(View.VISIBLE);
            binding.repairHandleResult.getRoot().setVisibility(View.VISIBLE);
            binding.handleSaveSubmit.setVisibility(View.VISIBLE);
        }

    }
    /**
     * 选择材料
     * */
    private void addMaterial(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_ADD_MATERIAL).navigation();
    }
}
