package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.ui.fragment.SendWorkOrderFragment;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;

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
@Route(path = RouterUtils.ACTIVITY_SEND_ORDER)
public class SendOrderActivity extends BaseHeadViewModelActivity<ActivitySendOrderBinding, SendOrderViewModel> implements PeriodizationView.OnPeriodSelectListener {
    private String[] mTitles;//tab标题
    PeriodizationView periodizationView;
    private String blockName;

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_order;
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mTitles=new String[]{getResources().getString(R.string.text_wait_handle),getResources().getString(R.string.text_already_handle)};
        setHeadTitle(R.string.text_send_order);
        setRightOption(R.drawable.scan);
        blockName=SPUtils.get(this,"block_name","").toString();
        if (!TextUtils.isEmpty(blockName)){
            binding.periodSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
            binding.periodSelected.setText(blockName);
        }
        final ArrayList<SendWorkOrderFragment> fragments = new ArrayList<>();
        String fragmentTags[]=new String[]{FRAGMENT_SEND_OWRKORDER_PENDING,FRAGMENT_SEND_OWRKORDER_DONE};
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(RouteKey.KEY_FRAGEMNT_TAG, fragmentTags[i]);
            fragments.add(SendWorkOrderFragment.newInstance(bundle));
        }

        binding.vpSendWork.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public SendWorkOrderFragment getItem(int i) {
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
        binding.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                periodizationView=new PeriodizationView();
                periodizationView.setPeriodListener(SendOrderActivity.this::onPeriodSelectListener);
                periodizationView.show(getSupportFragmentManager(),"");
            }
        });
        binding.sendWorkOrerTabSelectLn.setOnClickListener(v -> {
            //弹出筛选view
            ConditionBuilder builder=new ConditionBuilder();
            List<SelectModel> conditions= builder.addItem(SelectPopUpView.SELECT_LINE,viewModel.listAll)//条线
                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                    .mergeLineRes(viewModel.resourceTypeBeans)
                    .build();
            new SelectPopUpView(SendOrderActivity.this,conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
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
        binding.periodSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        viewModel.setOrgModel(orgModel);
    }



    /**
     * 处理筛选返回数据
     * */
    private void handleSelect(Map selected) {
        if (selected.size()>0){
        binding.selectSelected.setTextColor(getResources().getColor(R.color.blueTextColor));}
        viewModel.onConditionSelected(selected);
    }
}
