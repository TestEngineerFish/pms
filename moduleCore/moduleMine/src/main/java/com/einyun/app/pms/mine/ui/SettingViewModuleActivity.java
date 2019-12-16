package com.einyun.app.pms.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;


import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySettingViewModuleBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.HashMap;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_MINE_SETTING)
public class SettingViewModuleActivity extends BaseHeadViewModelActivity<ActivitySettingViewModuleBinding, SettingViewModel> {

    @Override
    protected SettingViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SettingViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(getString(R.string.tv_setting));
        binding.setCallBack(this);
        binding.tvVersion.setText(viewModel.getVerName(this));
    }
    /**
    * 退出登录
    * */
    public void onLoginOutClick(){
        new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                .setMsg(getString(R.string.tv_confirm_logout))
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtils.put(BasicApplication.getInstance(), "SIGN_LOGIN", "");
                        ARouter.getInstance()
                                .build(RouterUtils.ACTIVITY_USER_LOGIN)
                                .navigation();
                        finish();
                    }
                }).show();


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
