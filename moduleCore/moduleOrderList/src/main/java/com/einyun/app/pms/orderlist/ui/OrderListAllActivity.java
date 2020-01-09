package com.einyun.app.pms.orderlist.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Distribute;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.orderlist.BR;
import com.einyun.app.pms.orderlist.R;
import com.einyun.app.pms.orderlist.databinding.ActivityOrderListAllBinding;
import com.einyun.app.pms.orderlist.databinding.ItemOrderListBinding;
import com.einyun.app.pms.orderlist.viewmodel.OrderListViewModel;
import com.einyun.app.pms.orderlist.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import static androidx.test.InstrumentationRegistry.getContext;
@Route(path = RouterUtils.ACTIVITY_ORDER_LIST_ALL)
public class OrderListAllActivity extends BaseHeadViewModelActivity<ActivityOrderListAllBinding, OrderListViewModel> implements ItemClickListener<OrderListModel>, PeriodizationView.OnPeriodSelectListener {
    RVPageListAdapter<ItemOrderListBinding, OrderListModel> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    public String tag;
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list_all;
    }


    @Override
    protected OrderListViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(OrderListViewModel.class);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        binding.sendOrderRef.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        binding.sendOrderRef.setOnRefreshListener(() -> {
            binding.sendOrderRef.setRefreshing(false);
            loadPagingData();
            viewModel.refresh();
        });
        switch (tag){
            case RouteKey.ORDER_LIST_DISTRIBUTE:
                setHeadTitle(R.string.text_send_order);
                break;
            case RouteKey.ORDER_LIST_PLAN:
                setHeadTitle(R.string.text_work_plan);
                break;
            case RouteKey.ORDER_LIST_PATRO:
                setHeadTitle(R.string.title_patrol);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(this, shown -> {
            if (!shown) {
                binding.sendOrderRef.setRefreshing(false);
            }
        });

        RecyclerView mRecyclerView = binding.orderListRec;
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemOrderListBinding, OrderListModel>(this, com.einyun.app.pms.orderlist.BR.workOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderListBinding binding, OrderListModel orderListModel) {
                    binding.selectOrderTime.setText(orderListModel.getF_CREATE_TIME());
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_order_list;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.orderListRec);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 0, 30));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClick(this);
        loadPagingData();
    }

    private void loadPagingData() {
        DistributePageRequest request = new DistributePageRequest();
        request.setDivideId("63872495547056133");
        viewModel.loadPagingData(request, tag).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }




    protected void updatePageUIState(int state){
        binding.pageState.setPageState(state);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<OrderListModel> mDiffCallback = new DiffUtil.ItemCallback<OrderListModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull OrderListModel oldItem, @NonNull OrderListModel newItem) {
            return oldItem.getF_ORDER_NO().equals(newItem.getF_ORDER_NO());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull OrderListModel oldItem, @NonNull OrderListModel newItem) {
            return oldItem.getF_ORDER_NO().equals(newItem.getF_ORDER_NO());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull OrderListModel oldItem, @NonNull OrderListModel newItem) {
            return oldItem.getF_ORDER_NO().equals(newItem.getF_ORDER_NO());
        }
    };

    @Override
    public void onItemClicked(View veiw, OrderListModel data) {
        if (tag.equals(RouteKey.ORDER_LIST_DISTRIBUTE)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_SEND_ORDER_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getREF_ID())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID())
                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                    .navigation();
            return;
        }
        if (tag.equals(RouteKey.ORDER_LIST_PLAN)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getREF_ID())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID())
                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                    .navigation();
            return;
        }
        if (tag.equals(RouteKey.ORDER_LIST_PATRO)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getREF_ID())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID())
                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                    .navigation();
            return;
        }

    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {

    }
}
