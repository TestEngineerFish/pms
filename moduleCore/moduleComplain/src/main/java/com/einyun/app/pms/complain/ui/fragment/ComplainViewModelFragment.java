package com.einyun.app.pms.complain.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.Query;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ComplainFragmentBinding;
import com.einyun.app.pms.complain.databinding.ItemOrderComplainBinding;
import com.einyun.app.pms.complain.viewmodel.ComplainViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

/**
 * Paging Demo
 * Paging Component
 */
public class ComplainViewModelFragment extends BaseViewModelFragment<ComplainFragmentBinding, ComplainViewModel> implements PeriodizationView.OnPeriodSelectListener, ItemClickListener<RepairsModel> {
    RVPageListAdapter<ItemOrderComplainBinding, RepairsModel> adapter;

    public static ComplainViewModelFragment newInstance(Bundle bundle) {
        ComplainViewModelFragment fragment = new ComplainViewModelFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.complain_fragment;
    }


    @Override
    protected void init() {
        super.init();
        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(ComplainViewModelFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(v -> {
            BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                @Override
                public void call(BasicData data) {
                    //弹出筛选view
                    ConditionBuilder builder = new ConditionBuilder();
                    List<SelectModel> conditions = builder.addLines(data.getLines())//条线
                            .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                            .mergeLineRes(data.getResources())
                            .build();
                    new SelectPopUpView(getActivity(), conditions)
                            .setOnSelectedListener(selected -> handleSelect(selected))
                            .showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });

        });
    }

    private void handleSelect(Map<String, SelectModel> selected) {
    }

    @Override
    protected void setUpView() {
        binding.swipeRefresh.setColorSchemeColors(getColorPrimary());
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                loadPagingData();
            }
        });
    }

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        RepairsPageRequest request = new RepairsPageRequest();
        request.setBx_area_id("");
        request.setBx_cate_lv1_id("");
        request.setBx_cate_lv2_id("");
        request.setBx_dk_id("");
        request.setTs_time(Query.SORT_DESC);
        request.setNode_id_("");
        request.setDESC(Query.SORT_DESC);
        request.setState("");
        viewModel.loadPagingData(request, getFragmentTag()).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(getActivity(), shown -> {
            if (!shown) {
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        RecyclerView mRecyclerView = binding.repairsList;
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemOrderComplainBinding, RepairsModel>(getActivity(), com.einyun.app.pms.complain.BR.repair, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderComplainBinding binding, RepairsModel repairsModel) {
                    if (FRAGMENT_REPAIR_WAIT_FOLLOW.equals(getFragmentTag())) {
                        binding.line.setVisibility(View.VISIBLE);
                        binding.llTalkOrTurnSingle.setVisibility(View.VISIBLE);
                        //转单
                        binding.tvTurnOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                        //沟通
                        binding.tvTalk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                    if (FRAGMENT_REPAIR_WAIT_FEED.equals(getFragmentTag())) {
                        binding.rlFeedBack.setVisibility(View.VISIBLE);
                    }
                    binding.repairCreateTime.setText(FormatUtil.formatDate(repairsModel.getCreateTime()));
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_order_complain;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.repairsList);
        binding.repairsList.setAdapter(adapter);
        adapter.setOnItemClick(this);
        loadPagingData();
    }


    protected String getFragmentTag() {
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    @Override
    protected ComplainViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(ComplainViewModel.class);
    }


    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<RepairsModel> mDiffCallback = new DiffUtil.ItemCallback<RepairsModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull RepairsModel oldItem, @NonNull RepairsModel newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull RepairsModel oldItem, @NonNull RepairsModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    public void onItemClicked(View veiw, RepairsModel data) {

    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

    }
}
