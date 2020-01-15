package com.einyun.app.pms.orderpreview.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.DistributeWorkOrder;
import com.einyun.app.library.resource.workorder.model.OrderPreviewModel;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.OrderPreviewRequest;
import com.einyun.app.pms.orderpreview.BR;
import com.einyun.app.pms.orderpreview.R;
import com.einyun.app.pms.orderpreview.databinding.FragmentOrderPreviewBinding;
import com.einyun.app.pms.orderpreview.databinding.ItemOrderPreviewBinding;
import com.einyun.app.pms.orderpreview.ui.OrderPreviewActivity;
import com.einyun.app.pms.orderpreview.viewmodel.OrderPreviewModelFactory;
import com.einyun.app.pms.orderpreview.viewmodel.OrderPreviewViewModel;
import com.einyun.app.pms.sendorder.repository.OrderDataSourceFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_PENDING;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_WORK_PREVIEW_PLAN;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_TIME_CIRCLE;

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
public class OrderPreviewFragment extends BaseViewModelFragment<FragmentOrderPreviewBinding, OrderPreviewViewModel> implements ItemClickListener<OrderPreviewModel> {
    RVPageListAdapter<ItemOrderPreviewBinding, OrderPreviewModel> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    SelectPopUpView selectPopUpView;

    public static OrderPreviewFragment newInstance(Bundle bundle) {
        OrderPreviewFragment fragment = new OrderPreviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_preview;
    }


    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected OrderPreviewViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new OrderPreviewModelFactory()).get(OrderPreviewViewModel.class);
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
        binding.orderPreviewTabSelectLn.setOnClickListener(v -> {
            showConditionView();
        });
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
            adapter = new RVPageListAdapter<ItemOrderPreviewBinding, OrderPreviewModel>(getActivity(), BR.orderPreview, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderPreviewBinding binding, OrderPreviewModel orderPreviewModel) {
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_order_preview;
                }
            };
        }
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.workSendList);
        binding.workSendList.setAdapter(adapter);
        adapter.setOnItemClick(this);
    }

    private void loadPagingData() {
        binding.sendOrderRef.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //初始化数据，LiveData自动感知，刷新页面
        binding.sendOrderRef.setRefreshing(true);
        String fragmentTag = getFragmentTag();
        viewModel.getRequest().setTypeRe(fragmentTag);
        viewModel.loadPadingData(viewModel.getRequest(), fragmentTag).observe(this, dataBeans -> adapter.submitList(dataBeans));
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<OrderPreviewModel> mDiffCallback = new DiffUtil.ItemCallback<OrderPreviewModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull OrderPreviewModel oldItem, @NonNull OrderPreviewModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull OrderPreviewModel oldItem, @NonNull OrderPreviewModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.orderPreviewTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConditionView();
            }
        });
    }

    /**
     * 列表Item 点击，跳转进入详情
     * 代办详情进入(taskId)，已办详情(taskNodeTd,proInsId)
     *
     * @param veiw
     * @param data
     */
    @Override
    public void onItemClicked(View veiw, OrderPreviewModel data) {
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
        }
    }

    protected void showConditionView() {

        BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
            @Override
            public void call(BasicData data) {
                //弹出筛选view
                if (selectPopUpView == null) {
                    ConditionBuilder builder = new ConditionBuilder();
                    builder.addPreviewSelect(data.getPreviewSelect()).addItem(SELECT_TIME_CIRCLE);
                    List<SelectModel> conditions = builder.build();
                    selectPopUpView = new SelectPopUpView(getActivity(), conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                        @Override
                        public void onSelected(Map selected) {
                            handleSelected(selected);
                        }
                    });

                }
                selectPopUpView.showAsDropDown(binding.orderPreviewTabLn);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        }, BasicDataTypeEnum.PREVIEW_SELECT);
    }

    private void handleSelected(Map selected) {
        viewModel.onConditionSelected(selected);
    }
}
