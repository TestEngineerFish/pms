package com.einyun.app.pms.sendorder.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.adapter.SendOrderAdapter;
import com.einyun.app.pms.sendorder.databinding.FragmentSendWorkOrderBinding;
import com.einyun.app.pms.sendorder.databinding.ItemWorkSendBinding;
import com.einyun.app.pms.sendorder.model.SendOrderModel;
import com.einyun.app.pms.sendorder.ui.SendOrderActivity;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

import java.util.ArrayList;
import java.util.List;

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
    RVPageListAdapter<ItemWorkSendBinding, DistributeWorkOrder> adapter;
    private PageBean pageBean=new PageBean();;
    private DistributePageRequest request= new DistributePageRequest();;
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
                binding.sendOrderRef.setRefreshing(false);
                viewModel.refresh();
            }
        });
        binding.workSendList.addItemDecoration(new SpacesItemDecoration(30));

    }

    @Override
    protected void setUpData() {
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

        binding.workSendList.setAdapter(adapter);

        loadPagingData();
    }

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        request.setTypeRe(getArguments().getString("tabId"));
        request.setDivideId(SendOrderActivity.divideId);
        request.setTxId(SendOrderActivity.tiaoxianId);
        request.setType(SendOrderActivity.typeFir);
        request.setEnvType2(SendOrderActivity.typeSec);
        request.setEnvType3(SendOrderActivity.typeThir);
        request.setPage(pageBean.getPage());
        request.setPageSize(pageBean.getPageSize());
        request.setUserId(userModuleService.getUserId());
        viewModel.loadPadingData(request).observe(this, dataBeans -> adapter.submitList(dataBeans));
    }

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);

    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<DistributeWorkOrder> mDiffCallback = new DiffUtil.ItemCallback<DistributeWorkOrder>() {

        @Override
        public boolean areItemsTheSame(@NonNull DistributeWorkOrder oldItem, @NonNull DistributeWorkOrder newItem) {
            return oldItem.getID() == newItem.getID();
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
