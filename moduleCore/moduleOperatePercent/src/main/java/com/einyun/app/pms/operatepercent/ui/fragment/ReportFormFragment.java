package com.einyun.app.pms.operatepercent.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.ReportFormLayoutBinding;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

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
public class ReportFormFragment extends BaseViewModelFragment<ReportFormLayoutBinding, OperatePercentViewModel>  implements View.OnClickListener, PeriodizationView.OnPeriodSelectListener {
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public static ReportFormFragment newInstance(Bundle bundle) {
        ReportFormFragment fragment = new ReportFormFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        binding.reportFormRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        binding.reportFormRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPagingData();
            }
        });

    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(getActivity(), shown -> {
            if (!shown) {
                binding.reportFormRefresh.setRefreshing(false);
            }
        });

        //切换筛选条件
        viewModel.getLiveEvent().observe(getActivity(), status -> {
            if (status.isRefresShown()) {
                loadPagingData();
            }
        });

    }


    private void loadPagingData() {
//        //初始化数据，LiveData自动感知，刷新页面
//        binding.reportFormRefresh.setRefreshing(true);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.operate_percent_tab_peroid_ln){
            //弹出分期view
            PeriodizationView periodizationView = new PeriodizationView();
            periodizationView.setPeriodListener(ReportFormFragment.this::onPeriodSelectListener);
            periodizationView.show(getParentFragmentManager(), "");
        }else {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_OPERATE_TODAY_ALL_GET).navigation();
        }
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        Log.d("Test",orgModel.getCode());

    }
}
