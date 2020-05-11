package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.databinding.ActivityScannerBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;

import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.ui.fragment.ScanBasicInfoFragment;
import com.einyun.app.pms.main.core.ui.fragment.ScanResourceFragment;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.core.viewmodel.MineViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.databinding.ActivityScanResBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_BASIC;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_HISTORY;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SCAN_WAIT_DEAL;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_SCAN_RES)
public class ScanResourceActivity extends BaseHeadViewModelActivity<ActivityScanResBinding, MineViewModel> {
    @Autowired(name = RouteKey.KEY_RES_ID)
    public String resId;
    @Autowired(name = RouteKey.KEY_PATROL_ID)
    public String patrolId;
    @Autowired(name = RouteKey.KEY_TYPE)
    public String type;
    private String[] mTitles;

    @Override
    protected MineViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(MineViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_res;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        switch (type) {
            case "30":
                setHeadTitle(R.string.resource_info);
                break;
            case "31":
                setHeadTitle(R.string.patrol_info);
                break;
        }
        setHeadTitle(R.string.resource_info);
        mTitles = getResources().getStringArray(R.array.scan_res_list);
        ArrayList<Fragment> fragments = new ArrayList<>();
        String fragmentTags[] = new String[]{FRAGMENT_SCAN_WAIT_DEAL, FRAGMENT_SCAN_HISTORY,
                FRAGMENT_SCAN_BASIC};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            if (i == 2) {

                fragments.add(ScanBasicInfoFragment.newInstance(bundle));
            } else {

                fragments.add(ScanResourceFragment.newInstance(bundle));
            }
        }
//        binding.vpCustomerInquiries.setOffscreenPageLimit(1);
        binding.vpCustomerInquiries.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
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
        binding.tabSendOrder.setupWithViewPager(binding.vpCustomerInquiries);
        binding.tabSendOrder.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
