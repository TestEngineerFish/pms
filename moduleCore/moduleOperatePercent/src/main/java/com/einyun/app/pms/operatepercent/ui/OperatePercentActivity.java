package com.einyun.app.pms.operatepercent.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.ActivityOperatePercentBinding;
import com.einyun.app.pms.operatepercent.ui.fragment.PercentRandFragment;
import com.einyun.app.pms.operatepercent.ui.fragment.ReportFormFragment;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_OPERATE_PERCENT)
public class OperatePercentActivity extends BaseHeadViewModelActivity<ActivityOperatePercentBinding, OperatePercentViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private String[] mTitles;//tab标题


    @Override
    protected OperatePercentViewModel initViewModel() {
        return new ViewModelProvider(this, new OperatePercentModelFactory()).get(OperatePercentViewModel.class);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles = new String[]{getResources().getString(R.string.txt_get_report_form), getResources().getString(R.string.txt_own_report_form),getResources().getString(R.string.txt_get_report_rank),getResources().getString(R.string.txt_own_report_rank)};
        setHeadTitle(R.string.txt_operate_percent);
        final ArrayList<Fragment> fragments = new ArrayList<>();
//        String fragmentTags[] = new String[]{FRAGMENT_WORK_PREVIEW_PLAN, FRAGMENT_WORK_PREVIEW_PATRO};
        Bundle bundle = new Bundle();
        fragments.add(ReportFormFragment.newInstance(bundle));
        fragments.add(ReportFormFragment.newInstance(bundle));
        fragments.add(PercentRandFragment.newInstance(bundle));
        fragments.add(PercentRandFragment.newInstance(bundle));
       /* for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
//            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(ReportFormFragment.newInstance(bundle));
        }
*/
        binding.vpOperatePercent.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        binding.tabOperatePercent.setupWithViewPager(binding.vpOperatePercent);
        binding.tabOperatePercent.setOnClickListener(v -> {
            //弹出筛选view
            ConditionBuilder builder = new ConditionBuilder();
            List<SelectModel> conditions = builder
                    .addItem(SelectPopUpView.SELECT_TIME_CIRCLE)//周期
                    .build();
           /* new SelectPopUpView(OrderPreviewActivity.this, conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                @Override
                public void onSelected(Map selected) {
                    handleSelected(selected);
                }
            }).showAsDropDown(binding.orderPreviewTabLn);*/
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_operate_percent;
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

    }
}
