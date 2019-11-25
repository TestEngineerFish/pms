package com.einyun.app.pms.user.core.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityLoginBinding;


@Route(path = RouterUtils.ACTIVITY_USER_LOGIN)
public class LoginViewModelActivity extends BaseSkinViewModelActivity<ActivityLoginBinding, UserViewModel> {

    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        //本地用户信息展示
        viewModel.getLastUser().observe(this,
                user -> binding.setUserModel(user));
        viewModel.getTenantId("ccpg").observe(this,
                tenantModel -> ToastUtil.show(this, "tentantId:" + tenantModel.getId()));
        binding.setCallBack(this);

    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }

    /**
     * 登陆事件
     */
    public void onLoginClick() {
        UserModel model = binding.getUserModel();
        viewModel.login(model.getUsername(), model.getPassword(), true)
                .observe(LoginViewModelActivity.this,
                        user -> {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_MAIN_HOME)
                                    .navigation();
                            finish();
                        });
    }
}
