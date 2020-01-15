package com.einyun.app.pms.operatepercent.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.dashboard.net.request.OperateInRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.ReportFormLayoutBinding;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PERCENT_GET;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java类作用描述
 * @Author: zhulufeng
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class ReportFormFragment extends BaseViewModelFragment<ReportFormLayoutBinding, OperatePercentViewModel> implements View.OnClickListener, PeriodizationView.OnPeriodSelectListener {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    private String tag;
    OperateInRequest request;
    private TimePickerView pvTime;

    public static ReportFormFragment newInstance(String tag) {
        ReportFormFragment fragment = new ReportFormFragment(tag);
        return fragment;
    }

    public ReportFormFragment(String tag) {
        this.tag = tag;
    }

    @Override
    public int getLayoutId() {
        return R.layout.report_form_layout;
    }


    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.operatePercentTabPeroidLn.setOnClickListener(this);
        binding.operatePercentTabSelectLn.setOnClickListener(this);
        binding.operatePercentAllGet.getRoot().setOnClickListener(this);
        binding.operatePercentCarGet.carFee.setOnClickListener(this);
        binding.operatePercentPropertyGet.thingFee.setOnClickListener(this);
    }

    @Override
    protected OperatePercentViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new OperatePercentModelFactory()).get(OperatePercentViewModel.class);
    }

    protected String getFragmentTag() {
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPagingData();
    }

    @Override
    protected void setUpView() {
        Log.d("Test",tag);
        binding.operatePercentAllGet.setTag(tag);
        binding.operatePercentCarGet.setTag(tag);
        binding.operatePercentPropertyGet.setTag(tag);
        binding.reportFormRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        binding.reportFormRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPagingData();
            }
        });
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
                String format = dft.format(date);
                request.setGetYearOrMonth(format);
                binding.operatePercentSelectSelected.setText(format);
                loadPagingData();
            }
        }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .build();

    }

    @Override
    protected void setUpData() {
        request = new OperateInRequest();
        if (tag.equals(FRAGMENT_PERCENT_GET)) {
            request.setIncomeType("1");
        } else {
            request.setIncomeType("2");
        }

        //切换筛选条件
        viewModel.getLiveEvent().observe(getActivity(), status -> {
            if (status.isRefresShown()) {
                loadPagingData();
            }
        });

    }


    private void loadPagingData() {
//        //初始化数据，LiveData自动感知，刷新页面
        binding.reportFormRefresh.setRefreshing(false);
        request.setGetYearOrMonth("");
        request.setOrgCode("ops-xm01");
        viewModel.getOpertate(request).observe(this, model -> {
            binding.operatePercentAllGet.allGetAmountTxt.setText(model.getTotalBaseAmount() + "");
            binding.operatePercentAllGet.allYearRate.setText(model.getTotalYestAmountRate()+"%");
            binding.operatePercentAllGet.allGetAmountPro.setProgress(new Double(model.getTotalYestAmountRate()).intValue());
            binding.operatePercentCarGet.carAmountTxt.setText(model.getCwBaseAmount() + "");
            binding.operatePercentCarGet.carBasePro.setProgress(new Double(model.getCwYestAmountRate()).intValue());
            binding.operatePercentCarGet.carBastTxt.setText(model.getCwYestAmountRate() + "%");
            binding.operatePercentPropertyGet.propertyBaseTxt.setText(model.getWyBaseAmount() + "");
            binding.operatePercentPropertyGet.propertyRatePro.setProgress(new Double(model.getWyYestAmountRate()).intValue());
            binding.operatePercentPropertyGet.propertyRateTxt.setText(model.getWyYestAmountRate() + "%");
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.operate_percent_tab_peroid_ln) {
            //弹出分期view
            PeriodizationView periodizationView = new PeriodizationView();
            periodizationView.setPeriodListener(ReportFormFragment.this::onPeriodSelectListener);
            periodizationView.show(getParentFragmentManager(), "");
        } else if (v.getId() == R.id.operate_percent_tab_select_ln) {
            pvTime.show();
        }
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        Log.d("Test", orgModel.getCode());

    }
}
