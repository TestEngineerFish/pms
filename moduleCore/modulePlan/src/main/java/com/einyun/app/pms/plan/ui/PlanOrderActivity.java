package com.einyun.app.pms.plan.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.ActivityPlanOrderBinding;
import com.einyun.app.pms.plan.ui.fragment.PlanWorkOrderFragment;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendOrderActivity
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/25 16:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/25 16:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_PLAN_ORDER)
public class PlanOrderActivity extends BaseHeadViewModelActivity<ActivityPlanOrderBinding, PlanOrderViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private String[] mTitles;//tab标题



    @Override
    protected PlanOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_order;
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles=new String[]{getResources().getString(R.string.text_wait_handle),getResources().getString(R.string.text_already_handle)};
        setHeadTitle(R.string.text_plan_order);
        setRightOption(R.drawable.scan);
        final ArrayList<PlanWorkOrderFragment> fragments = new ArrayList<>();
        String fragmentTags[]=new String[]{FRAGMENT_PLAN_OWRKORDER_PENDING,FRAGMENT_PLAN_OWRKORDER_DONE};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(PlanWorkOrderFragment.newInstance(bundle));
        }

        binding.vpSendWork.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public PlanWorkOrderFragment getItem(int i) {
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
        binding.tabSendOrder.setupWithViewPager(binding.vpSendWork);
        binding.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView=new PeriodizationView();
                periodizationView.setPeriodListener(PlanOrderActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(),"");
            }
        });
        binding.sendWorkOrerTabSelectLn.setOnClickListener(v -> {
            //弹出筛选view
            ConditionBuilder builder=new ConditionBuilder();
            List<SelectModel> conditions= builder.addItem(SelectPopUpView.SELECT_LINE,viewModel.listAll)//条线
                    .addItem(SelectPopUpView.SELECT_DATE)
                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                    .mergeLineRes(viewModel.resourceTypeBeans)
                    .build();
            new SelectPopUpView(PlanOrderActivity.this,conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                @Override
                public void onSelected(Map selected) {
                    handleSelect(selected);
                }
            }).showAsDropDown(binding.sendWorkTabLn);

        });
    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.getTiaoXian();
        viewModel.getOrderType();
    }

    @Override
    protected int getColorPrimary() {
        return Color.WHITE;
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.periodSelected.setText(orgModel.getName());
        viewModel.setOrgModel(orgModel);
        viewModel.refreshUI();
    }



    /**
     * 处理筛选返回数据
     * */
    private void handleSelect(Map selected) {
        viewModel.onConditionSelected(selected);
    }
}
