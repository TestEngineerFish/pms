package com.einyun.app.pms.operatepercent.ui.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.operatepercent.BR;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.FragmentPercentRandBinding;
import com.einyun.app.pms.operatepercent.databinding.ItemPercentRankBinding;
import com.einyun.app.pms.operatepercent.viewmodel.OperatePercentModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

public class PercentRandFragment extends BaseViewModelFragment<FragmentPercentRandBinding, OperatePercentViewModel>  implements View.OnClickListener {

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    private RVBindingAdapter<ItemPercentRankBinding, SelectModel> adapter;//排名adapter
    private List<SelectModel> list=new ArrayList<>();
    public static PercentRandFragment newInstance(Bundle bundle) {
        PercentRandFragment fragment = new PercentRandFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_percent_rand;
    }


    @Override
    protected void init() {
        super.init();

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
        binding.rankRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        binding.rankRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPagingData();
            }
        });
        for (int i=0;i<8;i++){
            list.add(new SelectModel());
        }
        adapter=new RVBindingAdapter<ItemPercentRankBinding, SelectModel>(getActivity(),BR.select) {
            @Override
            public void onBindItem(ItemPercentRankBinding binding, SelectModel model, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.item_percent_rank;
            }
        };
        binding.percentRankList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setDataList(list);
        binding.percentRankList.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(getActivity(), shown -> {
            if (!shown) {
                binding.rankRefresh.setRefreshing(false);
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
    }
}
