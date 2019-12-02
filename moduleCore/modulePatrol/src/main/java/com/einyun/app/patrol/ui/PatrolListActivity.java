package com.einyun.app.patrol.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadActivity;
import com.einyun.app.patrol.R;
import com.einyun.app.patrol.ui.fragment.PatrolPendingFragment;

@Route(path = RouterUtils.ACTIVITY_PATROL_LIST)
public class PatrolListActivity extends BaseHeadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PatrolPendingFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_list;
    }
}
