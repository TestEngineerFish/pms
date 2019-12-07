package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityApplyLateBinding;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
@Route(path = RouterUtils.ACTIVITY_LATE)
public class ApplyLateActivity extends BaseHeadViewModelActivity<ActivityApplyLateBinding, SendOrderViewModel> {

    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_late;
    }
}
