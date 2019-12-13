package com.einyun.app.pms.orderpreview.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.FtsOptions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.pms.orderpreview.R;
import com.einyun.app.pms.orderpreview.databinding.ActivityOrderPreviewBinding;
import com.einyun.app.pms.orderpreview.ui.fragment.OrderPreviewFragment;
import com.einyun.app.pms.orderpreview.viewmodel.OrderPreviewModelFactory;
import com.einyun.app.pms.orderpreview.viewmodel.OrderPreviewViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_WORK_PREVIEW_PATRO;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_WORK_PREVIEW_PLAN;

@Route(path = RouterUtils.ACTIVITY_ORDER_PREVIEW)
public class OrderPreviewActivity extends BaseHeadViewModelActivity<ActivityOrderPreviewBinding, OrderPreviewViewModel> {
    private String[] mTitles;//tab标题

    @Override
    protected OrderPreviewViewModel initViewModel() {
        return new ViewModelProvider(this, new OrderPreviewModelFactory()).get(OrderPreviewViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_preview;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles = new String[]{getResources().getString(R.string.plan_order), getResources().getString(R.string.patro_order)};
        setHeadTitle(R.string.work_preview);
        final ArrayList<OrderPreviewFragment> fragments = new ArrayList<>();
        String fragmentTags[] = new String[]{FRAGMENT_WORK_PREVIEW_PLAN, FRAGMENT_WORK_PREVIEW_PATRO};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(OrderPreviewFragment.newInstance(bundle));
        }

        binding.vpOrderPreview.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public OrderPreviewFragment getItem(int i) {
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
        binding.tabOrderPreview.setupWithViewPager(binding.vpOrderPreview);
        binding.orderPreviewTabSelectLn.setOnClickListener(v -> {
            //弹出筛选view
            ConditionBuilder builder = new ConditionBuilder();
            List<SelectModel> conditions = builder
                    .addItem(SelectPopUpView.SELECT_TIME_CIRCLE)//周期
                    .build();
            new SelectPopUpView(OrderPreviewActivity.this, conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                @Override
                public void onSelected(Map selected) {
                    handleSelected(selected);
                }
            }).showAsDropDown(binding.orderPreviewTabLn);
        });
    }

    private void handleSelected(Map selected) {
        viewModel.onConditionSelected(selected);
    }
}
