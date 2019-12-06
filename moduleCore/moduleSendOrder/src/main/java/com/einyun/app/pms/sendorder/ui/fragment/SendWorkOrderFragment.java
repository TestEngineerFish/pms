package com.einyun.app.pms.sendorder.ui.fragment;

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
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.FragmentSendWorkOrderBinding;
import com.einyun.app.pms.sendorder.databinding.ItemWorkSendBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
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
public class SendWorkOrderFragment extends BaseViewModelFragment<FragmentSendWorkOrderBinding, SendOrderViewModel> {
    //    private SendOrderAdapter adapter;//适配器
    RVPageListAdapter<ItemWorkSendBinding, DistributeWorkOrder> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    public static SendWorkOrderFragment newInstance(Bundle bundle) {
        SendWorkOrderFragment fragment = new SendWorkOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_send_work_order;
    }


    @Override
    protected void init() {
        super.init();

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
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH,Boolean.class).observe(getActivity(), shown -> {
            if(!shown){
                binding.sendOrderRef.setRefreshing(false);
            }
        });

        //切换筛选条件
        viewModel.getLiveEvent().observe(getActivity(), status -> {
            if(status.isRefresShown()){
                loadPagingData();
            }
        });
        RecyclerView mRecyclerView = binding.workSendList;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemWorkSendBinding, DistributeWorkOrder>(getActivity(), BR.distributeWorkOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemWorkSendBinding binding, DistributeWorkOrder distributeWorkOrder) {
                 binding.itemResendRe.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         ARouter.getInstance()
                                 .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                 .navigation();
                     }
                 });
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_send;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.workSendList);
        binding.workSendList.setAdapter(adapter);

        loadPagingData();
    }

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.getRequest().setTypeRe(getArguments().getString("tabId"));
        if(viewModel.getOrgModel()!=null){
            viewModel.getRequest().setDivideId(viewModel.getOrgModel().getId());
        }
        viewModel.getRequest().setUserId(userModuleService.getUserId());
        viewModel.loadPadingData(viewModel.getRequest()).observe(this, dataBeans -> adapter.submitList(dataBeans));
    }

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new SendOdViewModelFactory()).get(SendOrderViewModel.class);
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DistributeWorkOrder> mDiffCallback = new DiffUtil.ItemCallback<DistributeWorkOrder>() {

        @Override
        public boolean areItemsTheSame(@NonNull DistributeWorkOrder oldItem, @NonNull DistributeWorkOrder newItem) {
            return oldItem.getID().equals(newItem.getID());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DistributeWorkOrder oldItem, @NonNull DistributeWorkOrder newItem) {
            return oldItem == newItem;
        }
    };

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}
