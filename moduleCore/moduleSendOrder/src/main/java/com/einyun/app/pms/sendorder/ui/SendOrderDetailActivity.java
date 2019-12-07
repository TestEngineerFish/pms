package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.TipDialog;
import com.einyun.app.library.resource.workorder.model.DisttributeDetialModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderDetailBinding;
import com.einyun.app.pms.sendorder.databinding.ItemSendOrderDetailImgBinding;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;

@Route(path = RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
public class SendOrderDetailActivity extends BaseHeadViewModelActivity<ActivitySendOrderDetailBinding, SendOrderDetialViewModel> implements View.OnClickListener {
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    RVPageListAdapter<ItemSendOrderDetailImgBinding, SendOrderModel> adapter;
    private TipDialog tipDialog;

    @Override
    protected SendOrderDetialViewModel initViewModel() {
        return new ViewModelProvider(this,new SendOdViewModelFactory()).get(SendOrderDetialViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_order_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_send_order);
        tipDialog = new TipDialog(this, "接单成功");
        setRightOption(R.drawable.histroy);
        setRightTxt(R.string.text_histroy);
    }

    @Override
    protected void initData() {
        viewModel.detial(taskId).observe(this, distributeWorkOrder -> updateUI(distributeWorkOrder));
        super.initData();
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemSendOrderDetailImgBinding, SendOrderModel>(this, com.einyun.app.pms.sendorder.BR.sendOrderModel, mDiffCallback) {


                @Override
                public void onBindItem(ItemSendOrderDetailImgBinding binding, SendOrderModel model) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_send_order_detail_img;
                }

            };
            binding.sendOrderDetailList.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false));
            binding.sendOrderDetailList.setAdapter(adapter);
        }
    }

    private void updateUI(DisttributeDetialModel distributeWorkOrder) {
        binding.setWorkOrder(distributeWorkOrder);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<SendOrderModel> mDiffCallback = new DiffUtil.ItemCallback<SendOrderModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull SendOrderModel oldItem, @NonNull SendOrderModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SendOrderModel oldItem, @NonNull SendOrderModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    protected void initListener() {
        super.initListener();
        binding.sendOrderDetailSubmit.setOnClickListener(this);
        binding.sendOrderApplyLate.setOnClickListener(this);
        binding.applyForceClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_order_detail_submit) {
            tipDialog.show();
        }
        if (v.getId()==R.id.send_order_apply_late){
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_LATE)
                    .navigation();        }
        if (v.getId()==R.id.apply_force_close){
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_CLOSE)
                    .navigation();        }
    }
}
