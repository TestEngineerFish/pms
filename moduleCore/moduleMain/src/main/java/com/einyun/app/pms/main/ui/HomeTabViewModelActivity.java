package com.einyun.app.pms.main.ui;


import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModelActivity;
import com.einyun.app.common.skin.SkinUtil;
import com.einyun.app.common.ui.activity.BaseSkinViewModelActivity;
import com.einyun.app.pms.main.databinding.ActivityHomeBinding;
import com.orhanobut.logger.Logger;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.ui.fragment.MineViewModelFragment;
import com.einyun.app.pms.main.ui.fragment.WorkBenchViewModelFragment;
import com.einyun.app.pms.main.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.viewmodel.ViewModelFactory;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(path = RouterUtils.ACTIVITY_MAIN_HOME)
public class HomeTabViewModelActivity extends BaseSkinViewModelActivity<ActivityHomeBinding,HomeTabViewModel> {

    WorkBenchViewModelFragment mWorkBenchFragment;//工作台
    MineViewModelFragment mMineFragment;//我的
    HomeTabPagerAdapter homeTabPagerAdapter;//适配器
    List<BaseViewModelFragment> fragmentList;//工作台+我的Fragment集合

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
        fragmentList=new ArrayList<>();
        mWorkBenchFragment=new WorkBenchViewModelFragment();
        mMineFragment=new MineViewModelFragment();
        fragmentList.add(mWorkBenchFragment);
        fragmentList.add(mMineFragment);
        homeTabPagerAdapter=new HomeTabPagerAdapter(getSupportFragmentManager(),fragmentList);
        binding.vpTab.setAdapter(homeTabPagerAdapter);
        binding.vpTab.addOnPageChangeListener(pageChangeListener);

        HomeTabViewModelActivityPermissionsDispatcher.permissionsWithPermissionCheck(this);
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        onWorkBenchPage(false);
    }



    /**
     * pageItem change event
     */
    private ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * onPageSelected,change item states
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            Logger.i("onPageSelected:"+position);
            if(position==0){
                onWorkBenchPage(false);
            }else if(position==1){
                onMinePage(false);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * mine item click event
     * databinding callback
     */
    public void onMineItemClick(){
        onMinePage(true);
    }

    /**
     * workbench item click event
     * databinding callback
     */
    public void onWorkBenchItemClick(){
        onWorkBenchPage(true);
    }

    /**
     * 工作台Page
     * @param flag 是否切换Page
     */
    private void onWorkBenchPage(boolean flag){
        if (binding.ivWorkBench.isEnabled()) {
            binding.ivWorkBench.setEnabled(false);
            binding.tvWorkBench.setTextColor(getColorPrimary());
            binding.ivMine.setEnabled(true);
            binding.tvMine.setTextColor(getResources().getColor(R.color.blackTextColor));
            if(flag){
                binding.vpTab.setCurrentItem(0);
            }
        }
    }

    /**
     * 我的Page
     */
    private void onMinePage(boolean flag){
        if (binding.ivMine.isEnabled()) {
            binding.ivWorkBench.setEnabled(true);
            binding.tvWorkBench.setTextColor(getResources().getColor(R.color.blackTextColor));
            binding.ivMine.setEnabled(false);
            binding.tvMine.setTextColor(getColorPrimary());
            if(flag){
                binding.vpTab.setCurrentItem(1);
            }
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissions() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeTabViewModelActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    class HomeTabPagerAdapter extends FragmentPagerAdapter {
        List<BaseViewModelFragment> mList;

        public HomeTabPagerAdapter(@NonNull FragmentManager fm, List<BaseViewModelFragment> mList) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.mList = mList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mWorkBenchFragment!=null){
            mWorkBenchFragment.onActivityResult(requestCode,resultCode,data);
        }
    }

}
