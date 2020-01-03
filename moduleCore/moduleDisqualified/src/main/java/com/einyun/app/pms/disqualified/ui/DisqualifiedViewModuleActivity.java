package com.einyun.app.pms.disqualified.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;

import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.databinding.ActivityDisqualifiedViewModuleBinding;
import com.einyun.app.pms.disqualified.ui.fragment.DisqualifiedViewModuleFragment;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_DISQUALIFIED)
public class DisqualifiedViewModuleActivity extends BaseHeadViewModelActivity<ActivityDisqualifiedViewModuleBinding, DisqualifiedViewModel> {

    private String[] mTitles;
    @Override
    protected DisqualifiedViewModel initViewModel() {
        return new ViewModelProvider(this, new DisqualifiedViewModelFactory()).get(DisqualifiedViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_disqualified_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_disqualified_order);
        mTitles=getResources().getStringArray(R.array.order_list);
        ArrayList<DisqualifiedViewModuleFragment> fragments = new ArrayList<>();
        String fragmentTags[]=new String[]{FRAGMENT_DISQUALIFIED_WAIT_FOLLOW,FRAGMENT_DISQUALIFIED_HAD_FOLLOW};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(DisqualifiedViewModuleFragment.newInstance(bundle));
        }
        binding.viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public DisqualifiedViewModuleFragment getItem(int i) {
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
