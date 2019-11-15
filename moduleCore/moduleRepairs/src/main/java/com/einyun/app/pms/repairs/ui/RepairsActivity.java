package com.einyun.app.pms.repairs.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseActivity;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.ui.fragment.RepairsViewModelFragment;

/**
 * demo of paging
 */
@Route(path = RouterUtils.ACTIVITY_REPAIRS_PAGING)
public class RepairsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RepairsViewModelFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.repairs_activity;
    }
}
