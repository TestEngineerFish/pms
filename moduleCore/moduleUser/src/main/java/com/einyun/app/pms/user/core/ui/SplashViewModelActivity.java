package com.einyun.app.pms.user.core.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.internal.util.LogUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseActivity;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.base.db.entity.User;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.library.uc.user.model.UserModel;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.Constants;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivitySplashBinding;
import com.googlecode.eyesfree.utils.LogUtils;


import java.util.logging.Logger;


@Route(path = RouterUtils.ACTIVITY_USER_SPLASH)
public class SplashViewModelActivity extends BaseSkinViewModelActivity<ActivitySplashBinding, UserViewModel> {

    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        viewModel.getLastUser().observe(this, userModel -> {
            Log.e("usrModel", "" + userModel);
            if (userModel == null || !StringUtil.isNullStr(userModel.getUsername())) {
                ARouter.getInstance().build(RouterUtils.ACTIVITY_USER_LOGIN).navigation();
                finish();
                return;
            }
            //企业编码校验
            viewModel.getTenantId(SPUtils.get(this, Constants.SP_KEY_TENANT_CODE, "").toString()).observe(this,
                    tenantModel -> {
//                        ToastUtil.show(this,"tenantid->"+tenantModel.getCode());
                        //拿取最后一个user登陆
                        viewModel.login(userModel.getUsername(), userModel.getPassword(), false)
                                .observe(this,
                                        currentUserModel -> {
                                            ARouter.getInstance()
                                                    .build(RouterUtils.ACTIVITY_MAIN_HOME)
                                                    .navigation();
                                            finish();
                                        });
                    });
        });

    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected boolean fullWindowFlag() {
        return true;
    }

}
