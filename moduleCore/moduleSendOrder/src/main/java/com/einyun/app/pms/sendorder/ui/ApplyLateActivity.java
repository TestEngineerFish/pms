package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

public class ApplyLateActivity extends BaseHeadViewModelActivity<ActivityResendOrderBinding, SendOrderViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_late);
    }

    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_late;
    }
}
