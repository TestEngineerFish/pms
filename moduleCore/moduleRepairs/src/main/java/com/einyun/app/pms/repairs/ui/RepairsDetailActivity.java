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
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.convert.PicUrlModelConvert;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.component.photo.PhotoListAdapter;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.SwipeItemLayout;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.workorder.model.RepairsDetailModel;
import com.einyun.app.library.workorder.net.request.RepairSendOrderRequest;
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
    RepairsDetailModel detialModel;
    PhotoListAdapter photoListInfoAdapter;
    List<MaterialModel> list=new ArrayList<>();
    RVBindingAdapter<ItemHandleBinding, MaterialModel> materialAdapter;
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
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.sendOrder.repairSelectedPepple.setText(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user(model.getFullname());
            detialModel.getData().getCustomer_repair_model().setAssign_grab_user_id(model.getId());
        });
        materialAdapter=new RVBindingAdapter<ItemHandleBinding, MaterialModel>(this, com.einyun.app.pms.repairs.BR.material) {
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
        binding.repairHandle.repairHandleRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
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
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.sendOrderDetailSubmit.setOnClickListener(this);
        binding.sendOrder.repairSelectPeople.setOnClickListener(this);
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
        binding.responseOrderInfo.setRepairs(repairsOrderDetail);
        binding.sendOrderInfo.setRepairs(repairsOrderDetail);
//        binding.tvHandleTime.setText(TimeUtil.getTimeExpend(detialModel.getData().getCustomer_repair_model());
//        updateImagesUI(repairsOrderDetail);
//        switchState(distributeWorkOrder.getData().getInfo().getStatus());getStatus
    }

    private void updateImagesUI(RepairsDetailModel repairsDetailModel) {
        if (detialModel == null) {
            return;
        }
        PicUrlModelConvert convert = new PicUrlModelConvert();
//        List<PicUrlModel> modelList = convert.stringToSomeObjectList(repairsDetailModel.getData().getCustomer_repair_model());
//        photoListInfoAdapter.updateList(modelList);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.send_order_detail_submit){
            handleSubmit();
        }
        if (v.getId()==R.id.repair_select_people){
            selectPeple();
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

        /*int state = Integer.parseInt(detialModel.getData().getInfo().getStatus());
        if (state == OrderState.NEW.getState()) {
            takeOrder();//接单
        } else if (detialModel.isReply() > 0) {
            reply();
        } else if (state == OrderState.HANDING.getState()) {
            submit();//处理-提交
        } else if (state == OrderState.APPLY.getState()) {
            checkAccept();//验收
        }*/
    }

    /**
     * 选择指派人
     * */
    private void selectPeple(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .withString(RouteKey.KEY_DIVIDE_ID, detialModel.getData().getCustomer_repair_model().getBx_dk_id())
                .withString(RouteKey.KEY_PROJECT_ID, detialModel.getData().getCustomer_repair_model().getU_project_id())
                .navigation();
    }

    /**
     * 派单，响应
     * */
    private void sendOrder() {
        if (TextUtils.isEmpty(binding.responseOrderInfo.repairResponseReason.getString())) {
            ToastUtil.show(this, R.string.text_please_enter_reason);
        } else {
            detialModel.getData().getCustomer_repair_model().setResponse_result(binding.responseOrderInfo.repairResponseReason.getString());
            viewModel.repairSend(new RepairSendOrderRequest(detialModel.getData().getCustomer_repair_model(), new RepairSendOrderRequest.DoNextParamBean(taskId))).observe(this,status->{
                if (status){
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
}
