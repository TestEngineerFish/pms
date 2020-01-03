package com.einyun.app.pms.complain.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ComplainActivityBinding;
import com.einyun.app.pms.complain.ui.fragment.ComplainViewModelFragment;
import com.einyun.app.pms.complain.viewmodel.ComplainViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_ALREDY_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;

/**
 * demo of paging
 */
@Route(path = RouterUtils.ACTIVITY_COMPLAIN_PAGING)
public class ComplainActivity extends BaseHeadViewModelActivity<ComplainActivityBinding, ComplainViewModel> {
    private String[] mTitles;//tab标题


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_work_complain);
        mTitles=new String[]{getResources().getString(R.string.text_wait_follow),getResources().getString(R.string.text_wait_feedback),getResources().getString(R.string.text_already_follow),getResources().getString(R.string.text_already_done),getResources().getString(R.string.text_copy_me)};
        final ArrayList<ComplainViewModelFragment> fragments = new ArrayList<>();
        String fragmentTags[]=new String[]{FRAGMENT_REPAIR_WAIT_FOLLOW,FRAGMENT_REPAIR_WAIT_FEED,FRAGMENT_REPAIR_ALREADY_FOLLOW,FRAGMENT_REPAIR_ALREDY_DONE,FRAGMENT_REPAIR_COPY_ME};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(ComplainViewModelFragment.newInstance(bundle));
        }

        binding.vpRepair.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public ComplainViewModelFragment getItem(int i) {
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
        binding.tabRepairOrder.setupWithViewPager(binding.vpRepair);
        binding.tabRepairOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                viewModel.currentFragmentTag=fragmentTags[tab.getPosition()];
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected ComplainViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(ComplainViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.complain_activity;
    }
}
