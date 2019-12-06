package com.einyun.app.pms.patrol.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolListBinding;
import com.einyun.app.pms.patrol.ui.fragment.PatrolClosedListFragment;
import com.einyun.app.pms.patrol.ui.fragment.PatrolPendingFragment;
import com.einyun.app.pms.patrol.viewmodel.PatrolListViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡查列表
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_LIST)
public class PatrolListActivity extends BaseHeadViewModelActivity<ActivityPatrolListBinding, PatrolListViewModel> implements PeriodizationView.OnPeriodSelectListener{
    String[] mTitles;
    List<BaseViewModelFragment> fragmentList=new ArrayList<>();
    final int TAB_PENDING=0;
    final int TAB_CLOSED=1;

    @Override
    protected PatrolListViewModel initViewModel() {
        return new ViewModelProvider(this,new ViewModelFactory()).get(PatrolListViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_list;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(getString(R.string.title_patrol));
        mTitles=getResources().getStringArray(R.array.order_list);
        fragmentList.add(TAB_PENDING,PatrolPendingFragment.newInstance());
        fragmentList.add(TAB_CLOSED, PatrolClosedListFragment.newInstance());
        binding.vpSendWork.setAdapter(new PatrolFragmentTabAdapter(getSupportFragmentManager(),fragmentList));
        binding.tabSendOrder.setupWithViewPager(binding.vpSendWork);
        binding.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView=new PeriodizationView();
                periodizationView.setPeriodListener(PatrolListActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(),"");
            }
        });
        binding.sendWorkOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SelectPopUpView(PatrolListActivity.this,null).showAsDropDown(binding.sendWorkOrerTabSelectLn);
            }
        });
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

    }

    class PatrolFragmentTabAdapter extends FragmentPagerAdapter{
        List<BaseViewModelFragment> mList;
        public PatrolFragmentTabAdapter(@NonNull FragmentManager fm, List<BaseViewModelFragment> mList) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.mList = mList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList==null?0:mList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}

