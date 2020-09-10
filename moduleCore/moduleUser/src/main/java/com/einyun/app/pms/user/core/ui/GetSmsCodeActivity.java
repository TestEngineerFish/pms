package com.einyun.app.pms.user.core.ui;

import androidx.lifecycle.ViewModelProvider;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityGetSmsCodeBinding;

public class GetSmsCodeActivity extends BaseHeadViewModelActivity<ActivityGetSmsCodeBinding, UserViewModel> {
    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_sms_code;
    }

}