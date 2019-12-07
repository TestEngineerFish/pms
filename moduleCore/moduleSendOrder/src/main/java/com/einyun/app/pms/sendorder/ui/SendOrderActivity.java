package com.einyun.app.pms.sendorder.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        setTxtColor(R.color.blackTextColor);
        setRightOption(R.drawable.scan);
        setBackIcon(R.drawable.back);
        final ArrayList<SendWorkOrderFragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("tabId", i+"");
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
        binding.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView=new PeriodizationView();
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
