package com.einyun.app.pms.mine.ui;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;

import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityFeedBackSucBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
@Route(path = RouterUtils.ACTIVITY_FEED_SUCCESS)
public class FeedBackSucActivity extends BaseHeadViewModelActivity<ActivityFeedBackSucBinding, SettingViewModel> {


    @Override
    protected SettingViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SettingViewModel.class);    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back_suc;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.txt_sub_feed);
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterUtils.ACTIVITY_FEED_LIST).navigation();
                finish();
            }
        });
    }
}
