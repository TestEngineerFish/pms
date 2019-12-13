package com.einyun.app.pms.main.core.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.databinding.ActivityOrderListBinding;

@Route(path = RouterUtils.ACTIVITY_ORDER_LIST)
public class OrderListActivity extends BaseHeadViewModelActivity<ActivityOrderListBinding, HomeTabViewModel> implements View.OnClickListener {

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.order_list);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.og_send_order){
            ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER).navigation();
        }
        if (v.getId()==R.id.og_plan_order){
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER).navigation();
        }
        if (v.getId()==R.id.og_patro_order){
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_LIST).navigation();
        }
        if (v.getId()==R.id.og_unwell_order){
            ARouter.getInstance().build(RouterUtils.ACTIVITY_OPERATE_PERCENT).navigation();
        }

    }

    @Override
    protected HomeTabViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.ogSendOrder.setOnClickListener(this);
        binding.ogPatroOrder.setOnClickListener(this);
        binding.ogPatroOrder.setOnClickListener(this);
        binding.ogUnwellOrder.setOnClickListener(this);
    }
}
