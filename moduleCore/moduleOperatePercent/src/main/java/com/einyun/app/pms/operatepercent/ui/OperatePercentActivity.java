package com.einyun.app.pms.operatepercent.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
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

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PERCENT_GET;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PERCENT_OWE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_WORK_PREVIEW_PLAN;

@Route(path = RouterUtils.ACTIVITY_OPERATE_PERCENT)
public class OperatePercentActivity extends BaseHeadViewModelActivity<ActivityOperatePercentBinding, OperatePercentViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private String[] mTitles;//tab标题
    @Autowired(name = RouteKey.ORGCODE)
    public List<String> orgCodes;
    public String tag;

    @Override
    protected OperatePercentViewModel initViewModel() {
        return new ViewModelProvider(this, new OperatePercentModelFactory()).get(OperatePercentViewModel.class);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles = new String[]{getResources().getString(R.string.txt_get_report_form), getResources().getString(R.string.txt_own_report_form)};
//        mTitles = new String[]{getResources().getString(R.string.txt_get_report_form), getResources().getString(R.string.txt_own_report_form),getResources().getString(R.string.txt_get_report_rank),getResources().getString(R.string.txt_own_report_rank)};
        setHeadTitle(R.string.txt_operate_percent);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        String fragmentTags[] = new String[]{FRAGMENT_PERCENT_GET, FRAGMENT_PERCENT_OWE};
        for (int i = 0; i < mTitles.length; i++) {
            fragments.add(ReportFormFragment.newInstance(fragmentTags[i],orgCodes));
        }
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
