package com.einyun.app.pms.main.core.ui;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.ActivityUtil;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.SPKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.pms.main.core.ui.fragment.MineViewModelFragment;
import com.einyun.app.pms.main.core.ui.fragment.WorkBenchViewModelFragment;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.ActivityHomeBinding;
import com.orhanobut.logger.Logger;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.main.R;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(path = RouterUtils.ACTIVITY_MAIN_HOME)
public class HomeTabViewModelActivity extends BaseSkinViewModelActivity<ActivityHomeBinding, HomeTabViewModel> {

    WorkBenchViewModelFragment mWorkBenchFragment;//工作台
    MineViewModelFragment mMineFragment;//我的
    private FragmentManager fragmentManager;
    private static int currentFragment = 0;

    @Override
    protected HomeTabViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(HomeTabViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ActivityUtil.setDefaultClass(this.getClass());
        fragmentManager = getSupportFragmentManager();
        HomeTabViewModelActivityPermissionsDispatcher.permissionsWithPermissionCheck(this);
        showFragment(0, null);
    }

    /**
     * 检查通知权限
     */
    private void showNotificationView() {
        boolean isNotifity = (boolean)SPUtils.get(this, SPKey.SP_KEY_NOTIFICATION, false);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();
        if (!isNotifity) {//没有提示过权限框 弹出框
            if (!isOpened) {
                //弹框
                new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                        .setMsg("是否打开通知权限?")
                        .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_NOTIFICATION, true);
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SPUtils.put(CommonApplication.getInstance(), SPKey.SP_KEY_NOTIFICATION, true);

                                viewModel.setNotify(HomeTabViewModelActivity.this);
                            }
                        }).show();
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        onWorkBenchPage(false);
    }


    private void showFragment(int index, final Bundle bundle) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        currentFragment = index;
        hideFragments(ft);
        switch (index) {
            case 0:
                if (mWorkBenchFragment != null) {
                    ft.show(mWorkBenchFragment);
                } else {
                    mWorkBenchFragment = new WorkBenchViewModelFragment();
                    ft.add(R.id.vp_tab, mWorkBenchFragment, "home");
                }
                break;
            case 1:
                if (mMineFragment != null) {
                    ft.show(mMineFragment);
                } else {
                    mMineFragment = new MineViewModelFragment();
                    if (bundle != null) {
                        mMineFragment.setArguments(bundle);
                    }
                    ft.add(R.id.vp_tab, mMineFragment, "mine");
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction ft) {
        if (mWorkBenchFragment != null)
            ft.hide(mWorkBenchFragment);
        if (mMineFragment != null)
            ft.hide(mMineFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFragment(currentFragment, null);
    }

    /**
     * mine item click event
     * databinding callback
     */
    public void onMineItemClick() {
        onMinePage(true);
    }

    /**
     * workbench item click event
     * databinding callback
     */
    public void onWorkBenchItemClick() {
        onWorkBenchPage(true);
    }

    /**
     * 工作台Page
     *
     * @param flag 是否切换Page
     */
    private void onWorkBenchPage(boolean flag) {
        if (binding.ivWorkBench.isEnabled()) {
            binding.ivWorkBench.setEnabled(false);
            binding.tvWorkBench.setTextColor(getResources().getColor(R.color.main_bottom_tab_text_select_color));
            binding.ivMine.setEnabled(true);
            binding.tvMine.setTextColor(getResources().getColor(R.color.normal_main_text_icon_color));
            if (flag) {
                showFragment(0, null);
            }
        }
    }

    /**
     * 我的Page
     */
    private void onMinePage(boolean flag) {
        if (binding.ivMine.isEnabled()) {
            binding.ivWorkBench.setEnabled(true);
            binding.tvWorkBench.setTextColor(getResources().getColor(R.color.normal_main_text_icon_color));
            binding.ivMine.setEnabled(false);
            binding.tvMine.setTextColor(getResources().getColor(R.color.main_bottom_tab_text_select_color));
            if (flag) {
                showFragment(1, null);
            }
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissions() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeTabViewModelActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        showNotificationView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWorkBenchFragment != null) {
            mWorkBenchFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected int getColorPrimary() {
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean fullWindowFlag() {
        return true;
    }
}
