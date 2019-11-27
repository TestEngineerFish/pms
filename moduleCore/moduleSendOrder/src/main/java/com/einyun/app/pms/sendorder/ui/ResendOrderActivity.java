package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

@Route(path = RouterUtils.ACTIVITY_RESEND_ORDER)
public class ResendOrderActivity extends BaseHeadViewModelActivity<ActivityResendOrderBinding, SendOrderViewModel> {

    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("转派工单");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resend_order;
    }

    @Override
    protected int getColorPrimary() {
        return R.color.white;
    }
}
