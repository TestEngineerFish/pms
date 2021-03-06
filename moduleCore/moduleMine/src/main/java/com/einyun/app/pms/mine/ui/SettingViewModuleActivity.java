package com.einyun.app.pms.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;


import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.constants.Constants;
import com.einyun.app.pms.mine.databinding.ActivitySettingViewModuleBinding;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.HashMap;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;


//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_MINE_SETTING)
public class SettingViewModuleActivity extends BaseHeadViewModelActivity<ActivitySettingViewModuleBinding, SettingViewModel> implements CompoundButton.OnCheckedChangeListener {

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
        binding.checkbox.setOnCheckedChangeListener(this);
        binding.tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrivacy();
            }
        });
        binding.tvUserAgreement.setOnClickListener(view -> {
            goToUserAgreement();
        });
        checkNotifySetting();
        binding.rvNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("user_name", UserUtil.getUserName());
                MobclickAgent.onEvent(SettingViewModuleActivity.this, CustomEventTypeEnum.MSG_SWITCH.getTypeName(),map);
                setNotify();
            }
        });
    }

    public void goToPrivacy(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                .withString(RouteKey.KEY_WEB_URL, Constants.PRIVACY_DETAIL_URL)
                .withString(RouteKey.KEY_WEB_TITLE, "????????????????????????")
                .navigation();
    }
    public void goToUserAgreement(){
        ARouter.getInstance()
                .build(RouterUtils.ACTIVITY_X5_WEBVIEW)
                .withString(RouteKey.KEY_WEB_URL, Constants.USER_AGREEMENT_DETAIL_URL)
                .withString(RouteKey.KEY_WEB_TITLE, "????????????")
                .navigation();
    }
    private void setNotify(){
        try {
            // ??????isOpened?????????????????????????????????????????????AppInfo??????????????????App????????????
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //????????????????????? API 26, ???8.0??????8.0??????????????????
            intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);

            //????????????????????? API21??????25?????? 5.0??????7.1 ???????????????????????????
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);

            // ??????6 -MIUI9.6-8.0.0??????????????????????????????????????????????????????"????????????????????????"??????????????????????????????????????????????????????????????????I'm not ok!!!
            //  if ("MI 6".equals(Build.MODEL)) {
            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
            //      intent.setData(uri);
            //      // intent.setAction("com.android.settings/.SubSettings");
            //  }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // ?????????????????????????????????????????????????????????3??????OC105 API25
            Intent intent = new Intent();

            //??????????????????????????????????????????????????????????????????
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    /**
     * ?????????CnPeng
     * ?????????2018/7/12 ??????9:02
     * ????????????????????????????????????????????????
     * ?????????
     */
    private void checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled??????????????????????????????????????????API 19?????????19??????????????????????????????????????????true?????????????????????????????????????????????
        boolean isOpened = manager.areNotificationsEnabled();

        if (isOpened) {
            binding.cbNotification.setChecked(true);
        } else {
            binding.cbNotification.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNotifySetting();
    }

    /**
     * ????????????
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
                        CommonApplication.getInstance().unbindAccount();
                        SPUtils.put(BasicApplication.getInstance(), "SIGN_LOGIN", "");
                        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_CHOOSE_CACHE, "");
                        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_NAME, "");
                        SPUtils.put(CommonApplication.getInstance(), SPKey.KEY_BLOCK_ID, "");
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Logger.d("sssssss"+b);
    }
}
