package com.einyun.app.pms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.R;
import com.einyun.app.pms.databinding.ActivityApprovalViewModuleBinding;
import com.einyun.app.pms.ui.fragment.ApprovalViewModelFragment;
import com.einyun.app.pms.viewmodule.ApprovalViewModel;
import com.einyun.app.pms.viewmodule.ApprovalViewModelFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_APPROVAL)
public class ApprovalViewModuleActivity extends BaseHeadViewModelActivity<ActivityApprovalViewModuleBinding, ApprovalViewModel> {

    List<BaseViewModelFragment> fragmentList;
    ApprovalPagerAdapter approvalPagerAdapter;//适配器
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
        setHeadTitle(getString(R.string.tv_approval));
        fragmentList=new ArrayList<>();
        fragmentList.add(ApprovalViewModelFragment.newInstance());
        fragmentList.add(ApprovalViewModelFragment.newInstance());
        fragmentList.add(ApprovalViewModelFragment.newInstance());
        approvalPagerAdapter=new ApprovalPagerAdapter(getSupportFragmentManager(),fragmentList);
        binding.viewpagerApproval.setAdapter(approvalPagerAdapter);
        binding.viewpagerApproval.addOnPageChangeListener(pageChangeListener);
        binding.rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId==R.id.rbtn_wait_approval) {
                    binding.viewpagerApproval.setCurrentItem(0);
                }
                if (checkedId==R.id.rbtn_had_approval) {
                    binding.viewpagerApproval.setCurrentItem(1);
                }
                if (checkedId==R.id.rbtn_me_started) {
                    binding.viewpagerApproval.setCurrentItem(2);
                }

        }
        });
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
            if (position==0) {
                binding.rgApproval.check(R.id.rbtn_wait_approval);
            }else if (position==1){
                binding.rgApproval.check(R.id.rbtn_had_approval);
            }else if (position==2){
                binding.rgApproval.check(R.id.rbtn_me_started);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    class ApprovalPagerAdapter extends FragmentPagerAdapter {
        List<BaseViewModelFragment> mList;
        public ApprovalPagerAdapter(@NonNull FragmentManager fm, List<BaseViewModelFragment> mList) {
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
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
