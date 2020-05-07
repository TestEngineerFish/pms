package com.einyun.app.pms.approval.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.approval.R;


import com.einyun.app.pms.approval.databinding.ActivityApprovalViewModuleBinding;
import com.einyun.app.pms.approval.ui.fragment.ApprovalViewModelFragment;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModel;
import com.einyun.app.pms.approval.viewmodule.ApprovalViewModelFactory;


import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_APPROVAL)
public class ApprovalViewModuleActivity extends BaseHeadViewModelActivity<ActivityApprovalViewModuleBinding, ApprovalViewModel> {
    @Autowired(name= RouteKey.APPROVAL_LIST_FROM)
            String from;
//    ApprovalPagerAdapter approvalPagerAdapter;//适配器
    private String[] mTitles;
    private ArrayList<ApprovalViewModelFragment> fragments;

    @Override
    protected ApprovalViewModel initViewModel() {
        return new ViewModelProvider(this, new ApprovalViewModelFactory()).get(ApprovalViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_approval_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        if (from==null) {//显示两个待审批 已审批
//            binding.rbtnMeStarted.setVisibility(View.GONE);
            setHeadTitle(getString(R.string.tv_approval));
            mTitles=getResources().getStringArray(R.array.approval_list_two);
            fragments =new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Bundle bundle = new Bundle();
                bundle.putInt("tabId", i);
                fragments.add(ApprovalViewModelFragment.newInstance(bundle));
            }
        }else {// from 不为空  从 mine 来 显示三个fragment
            setHeadTitle(getString(R.string.tv_mine_approval));
            mTitles=getResources().getStringArray(R.array.approval_list_three);
            fragments =new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Bundle bundle = new Bundle();
                bundle.putInt("tabId", i);
                fragments.add(ApprovalViewModelFragment.newInstance(bundle));
            }
        }
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public ApprovalViewModelFragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });
        binding.tabSendOrder.setupWithViewPager(binding.viewPager);
        binding.tabSendOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        approvalPagerAdapter=new ApprovalPagerAdapter(getSupportFragmentManager(),fragmentList);
//        binding.viewpagerApproval.setAdapter(approvalPagerAdapter);
//        binding.viewpagerApproval.addOnPageChangeListener(pageChangeListener);
//        binding.viewpagerApproval.setOffscreenPageLimit(3);
//        binding.rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                if (checkedId== R.id.rbtn_wait_approval) {
//                    binding.viewpagerApproval.setCurrentItem(0);
//                }
//                if (checkedId== R.id.rbtn_had_approval) {
//                    binding.viewpagerApproval.setCurrentItem(1);
//                }
//                if (checkedId== R.id.rbtn_me_started) {
//                    binding.viewpagerApproval.setCurrentItem(2);
//                }
//
//        }
//        });
    }

//    /**
//     * pageItem change event
//     */
//    private ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//        /**
//         * onPageSelected,change item states
//         * @param position
//         */
//        @Override
//        public void onPageSelected(int position) {
//            Logger.i("onPageSelected:"+position);
//            if (position==0) {
//                binding.rgApproval.check(R.id.rbtn_wait_approval);
//            }else if (position==1){
//                binding.rgApproval.check(R.id.rbtn_had_approval);
//            }else if (position==2){
//                binding.rgApproval.check(R.id.rbtn_me_started);
//
//            }
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };
//    class ApprovalPagerAdapter extends FragmentPagerAdapter {
//        List<BaseViewModelFragment> mList;
//        public ApprovalPagerAdapter(@NonNull FragmentManager fm, List<BaseViewModelFragment> mList) {
//            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//            this.mList = mList;
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return this.mList == null ? null : this.mList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return this.mList == null ? 0 : this.mList.size();
//        }
//    }
    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
