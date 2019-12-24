package com.einyun.app.pms.plan.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.component.searchhistory.PageSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.PageSearchListener;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.FragmentPlanWorkOrderBinding;
import com.einyun.app.pms.plan.databinding.ItemWorkPlanBinding;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;

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
public class PlanWorkOrderFragment extends BaseViewModelFragment<FragmentPlanWorkOrderBinding, PlanOrderViewModel> implements ItemClickListener<Plan>, PeriodizationView.OnPeriodSelectListener {
    //    private SendOrderAdapter adapter;//适配器
    RVPageListAdapter<ItemWorkPlanBinding, Plan> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    PageSearchFragment<ItemWorkPlanBinding, PlanWorkOrder> searchFragment;
    protected SelectPopUpView selectPopUpView;

    public static PlanWorkOrderFragment newInstance(Bundle bundle) {
        PlanWorkOrderFragment fragment = new PlanWorkOrderFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_plan_work_order;
    }


    @Override
    protected void init() {
        super.init();

        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(PlanWorkOrderFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(v -> {
            BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                @Override
                public void call(BasicData data) {
                    //弹出筛选view
                    showConditionView(data);
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });

        });
        binding.panelCondition.search.setVisibility(View.VISIBLE);
        binding.panelCondition.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    protected void showConditionView(BasicData data) {
        if (selectPopUpView == null) {
            ConditionBuilder builder = new ConditionBuilder();
            List<SelectModel> conditions = builder.addLines(data.getLines())//条线
                    .addItem(SelectPopUpView.SELECT_DATE)//完成截止时间
                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                    .mergeLineRes(data.getResources())
                    .build();
            selectPopUpView = new SelectPopUpView(getActivity(), conditions)
                    .setOnSelectedListener(selected -> handleSelect(selected));
        }
        selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
    }

    private void search() {
        if (searchFragment == null) {
            searchFragment = new PageSearchFragment<ItemWorkPlanBinding, PlanWorkOrder>(getActivity(), BR.planWorkOrder, new PageSearchListener<PlanWorkOrder>() {
                @Override
                public LiveData<PagedList<PlanWorkOrder>> search(String search) {
                    try {
                        DistributePageRequest request = (DistributePageRequest) viewModel.request.clone();
                        request.setSearchValue(search);
                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                            return viewModel.loadPadingNetData(getFragmentTag());
                        } else {
                            return viewModel.loadDonePagingNetData(getFragmentTag());
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void onItemClick(PlanWorkOrder model) {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                            .withString(RouteKey.KEY_ORDER_ID, model.getID_())
                            .withString(RouteKey.KEY_PRO_INS_ID, model.getProInsId())
                            .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
                            .withString(RouteKey.KEY_TASK_NODE_ID, model.getTaskNodeId())
                            .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                            .navigation();
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_plan;
                }
            });
            searchFragment.setHint("请搜索工单编号或计划名称");
        }
        searchFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    private void handleSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            binding.panelCondition.selectSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        }
        viewModel.onConditionSelected(selected);
        viewModel.switchCondition(getFragmentTag());
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
        binding.sendOrderRef.setOnRefreshListener(() -> {
            binding.sendOrderRef.setRefreshing(false);
            viewModel.refresh(getFragmentTag());
        });
        binding.workSendList.addItemDecoration(new SpacesItemDecoration(30));

    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(getActivity(), shown -> {
            if (!shown) {
                binding.sendOrderRef.setRefreshing(false);
            }
        });

        //切换筛选条件
        viewModel.getLiveEvent().observe(getActivity(), status -> {
            if (status.isRefresShown()) {
                loadPagingData();
            }
        });
        RecyclerView mRecyclerView = binding.workSendList;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemWorkPlanBinding, Plan>(getActivity(), BR.planWorkOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemWorkPlanBinding binding, Plan distributeWorkOrder) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_plan;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.workSendList);
        binding.workSendList.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
//        binding.sendOrderRef.setRefreshing(true);
        binding.sendOrderRef.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        String fragmentTag = getFragmentTag();
        if (viewModel.getOrgModel() != null) {
            viewModel.request.setDivideId(viewModel.getOrgModel().getId());
        }
        viewModel.request.setUserId(userModuleService.getUserId());
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
            viewModel.loadPendingInDB().observe(this, dataBeans -> {
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        } else {
            viewModel.loadDoneInDB().observe(this, dataBeans -> {
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    protected PlanOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<Plan> mDiffCallback = new DiffUtil.ItemCallback<Plan>() {

        @Override
        public boolean areItemsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
            return oldItem == newItem;
        }
    };


    /**
     * 列表Item 点击，跳转进入详情
     * 代办详情进入(taskId)，已办详情(taskNodeTd,proInsId)
     *
     * @param veiw
     * @param data
     */
    @Override
    public void onItemClicked(View veiw, Plan data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                .navigation();
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        binding.panelCondition.periodSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        viewModel.setOrgModel(orgModel);
        viewModel.switchCondition(getFragmentTag());
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}
