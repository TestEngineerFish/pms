package com.einyun.app.pms.customerinquiries.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.customerinquiries.R;
import com.einyun.app.pms.customerinquiries.databinding.ActivityCustomerInquiriesViewModuleBinding;
import com.einyun.app.pms.customerinquiries.model.InquiriesTypesBean;
import com.einyun.app.pms.customerinquiries.ui.fragment.CustomerInquiriesViewModuleFragment;
import com.einyun.app.pms.customerinquiries.viewmodule.CusInquiriesFragmentViewModel;
import com.einyun.app.pms.customerinquiries.viewmodule.CustomerInquiriesViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_COPY_ME;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_HAVE_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TRANSFERRED_TO;

//@Route(path = RouterUtils.ACTIVITY_APPROVAL)
@Route(path = RouterUtils.ACTIVITY_CUSTOMER_INQUIRIES)
public class CustomerInquiriesViewModuleActivity extends BaseHeadViewModelActivity<ActivityCustomerInquiriesViewModuleBinding, CusInquiriesFragmentViewModel> {

    private String[] mTitles;
    public List<InquiriesTypesBean> mInquiriesTypesModule;

    @Override
    protected CusInquiriesFragmentViewModel initViewModel() {
        return new ViewModelProvider(this, new CustomerInquiriesViewModelFactory()).get(CusInquiriesFragmentViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_customer_inquiries_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
//        setTitleBarColor(R.color.white);
//        setBackIcon(R.drawable.back);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_customer_inquiries);
        mTitles=getResources().getStringArray(R.array.customer_inquiries_list);
        ArrayList<CustomerInquiriesViewModuleFragment> fragments = new ArrayList<>();
        String fragmentTags[]=new String[]{FRAGMENT_TO_FOLLOW_UP,FRAGMENT_TO_FEED_BACK,
                FRAGMENT_HAVE_TO_FOLLOW_UP,FRAGMENT_TRANSFERRED_TO,FRAGMENT_COPY_ME};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(CustomerInquiriesViewModuleFragment.newInstance(bundle));
        }
        binding.vpCustomerInquiries.setOffscreenPageLimit(5);
        binding.vpCustomerInquiries.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public CustomerInquiriesViewModuleFragment getItem(int i) {
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
                viewModel.currentFragmentTag=fragmentTags[tab.getPosition()];
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
        viewModel.queryAduitType().observe(this, model -> {

            mInquiriesTypesModule = model;
        });
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
