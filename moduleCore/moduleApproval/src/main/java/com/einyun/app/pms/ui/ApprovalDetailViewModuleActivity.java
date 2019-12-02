package com.einyun.app.pms.ui;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.R;
import com.einyun.app.pms.databinding.ActivityApprovalViewModuleBinding;
import com.einyun.app.pms.viewmodule.ApprovalDetailViewModel;
import com.einyun.app.pms.viewmodule.ApprovalViewModelFactory;
//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_APPROVAL_DETAIL)
public class ApprovalDetailViewModuleActivity extends BaseHeadViewModelActivity<ActivityApprovalViewModuleBinding, ApprovalDetailViewModel> {

    @Override
    protected ApprovalDetailViewModel initViewModel() {
        return new ViewModelProvider(this, new ApprovalViewModelFactory()).get(ApprovalDetailViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_approval_detail_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_approval_detail));

    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
