package com.einyun.app.pms.mine.ui;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityAdviceFeedBackViewModuleBinding;
import com.einyun.app.pms.mine.databinding.ActivityUserHeadShotViewModuleBinding;
import com.einyun.app.pms.mine.viewmodule.AdviceFeedBackViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.einyun.app.pms.mine.viewmodule.UserHeadShotViewModel;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_ADVICE_FEED_BACK)
public class AdviceFeedBackViewModuleActivity extends BaseHeadViewModelActivity<ActivityAdviceFeedBackViewModuleBinding, AdviceFeedBackViewModel> {

    @Override
    protected AdviceFeedBackViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(AdviceFeedBackViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_advice_feed_back_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_advice_feed_back));
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
