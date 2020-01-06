package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.databinding.ActivityOrderListBinding;

@Route(path = RouterUtils.ACTIVITY_ORDER_CONDITION_PANDECT)
public class OrderConditionPandectActivity extends BaseHeadViewModelActivity<ActivityOrderListBinding, HomeTabViewModel> implements View.OnClickListener {

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_order_condition_pandect);
    }

    @Override
    public void onClick(View v) {

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
    }
}
