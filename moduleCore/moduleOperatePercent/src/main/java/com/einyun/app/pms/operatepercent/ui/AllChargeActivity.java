package com.einyun.app.pms.operatepercent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.operatepercent.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.ActivityOperatePercentBinding;

public class AllChargeActivity extends BaseHeadViewModelActivity<ActivityOperatePercentBinding, OperatePercentViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_charge);
    }

    @Override
    protected OperatePercentViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_charge;
    }
}
