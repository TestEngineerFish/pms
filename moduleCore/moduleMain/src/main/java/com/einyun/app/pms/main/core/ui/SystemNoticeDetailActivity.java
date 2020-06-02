package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;
import android.text.Html;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.ActivitySystemNoticeDetailBinding;

@Route(path = RouterUtils.ACTIVITY_SYSTEM_NOTICE_DETAIL)
public class SystemNoticeDetailActivity extends BaseHeadViewModelActivity<ActivitySystemNoticeDetailBinding, WorkBenchViewModel> {
    @Autowired(name = RouteKey.KEY_ID)
    String id;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("消息详情");
        viewModel.getSystemNoticeDetail(id).observe(this, systemNoticeModel -> {
            binding.title.setText(systemNoticeModel.getTitle());
            binding.time.setText(systemNoticeModel.getReleaseTime());
            binding.content.setText(Html.fromHtml(systemNoticeModel.getContent()));
        });
    }

    @Override
    protected WorkBenchViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_notice_detail;
    }

}
