package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

@Route(path = RouterUtils.ACTIVITY_RESEND_ORDER)
public class ResendOrderActivity extends BaseHeadViewModelActivity<ActivityResendOrderBinding, SendOrderViewModel> implements View.OnClickListener {

    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_resend_order);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resend_order;
    }



    @Override
    protected void initListener() {
        super.initListener();
        binding.resendOrderTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                .navigation();
    }

}
