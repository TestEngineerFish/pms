package com.einyun.app.pms.plan.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;

import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.FragmentPlanWorkOrderBinding;
import com.einyun.app.pms.plan.databinding.ItemWorkPlanBinding;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE;
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
public class PlanWorkOrderFragment extends BaseViewModelFragment<FragmentPlanWorkOrderBinding, PlanOrderViewModel> implements ItemClickListener<PlanWorkOrder> {
    //    private SendOrderAdapter adapter;//适配器
    RVPageListAdapter<ItemWorkPlanBinding, PlanWorkOrder> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

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
        binding.sendOrderRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPagingData();
            }
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
            adapter = new RVPageListAdapter<ItemWorkPlanBinding, PlanWorkOrder>(getActivity(), BR.planWorkOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemWorkPlanBinding binding, PlanWorkOrder distributeWorkOrder) {
                    binding.selectOrderTime.setText(FormatUtil.formatDate(distributeWorkOrder.getCreateTime()));
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
        binding.sendOrderRef.setRefreshing(true);
        String fragmentTag = getFragmentTag();
        if (viewModel.getOrgModel() != null) {
            viewModel.getRequest().setDivideId(viewModel.getOrgModel().getId());
        }
        viewModel.getRequest().setUserId(userModuleService.getUserId());
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
            viewModel.loadPadingData(viewModel.getRequest(), fragmentTag).observe(this, dataBeans -> adapter.submitList(dataBeans));
        } else {
            viewModel.loadDonePagingData(viewModel.getRequest(), fragmentTag).observe(this, dataBeans -> adapter.submitList(dataBeans));
        }
    }

    @Override
    protected PlanOrderViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new PlanOdViewModelFactory()).get(PlanOrderViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<PlanWorkOrder> mDiffCallback = new DiffUtil.ItemCallback<PlanWorkOrder>() {

        @Override
        public boolean areItemsTheSame(@NonNull PlanWorkOrder oldItem, @NonNull PlanWorkOrder newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull PlanWorkOrder oldItem, @NonNull PlanWorkOrder newItem) {
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
    public void onItemClicked(View veiw, PlanWorkOrder data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                .withString(RouteKey.KEY_FRAGEMNT_TAG,getFragmentTag())
                .navigation();
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
