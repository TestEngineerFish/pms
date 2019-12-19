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
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.base.BaseViewModelFragment;
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

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

/**
 * Paging Demo
 * Paging Component
 */
public class ComplainViewModelFragment extends BaseViewModelFragment<ComplainFragmentBinding, ComplainViewModel> implements ItemClickListener<RepairsModel> {
    RVPageListAdapter<ItemOrderComplainBinding, RepairsModel> adapter;

    public static ComplainViewModelFragment newInstance(Bundle bundle) {
        ComplainViewModelFragment fragment = new ComplainViewModelFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->"+bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.complain_fragment;
    }


    @Override
    protected void init() {
        super.init();

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

    private void loadPagingData(){
        //初始化数据，LiveData自动感知，刷新页面
        RepairsPageRequest request=new RepairsPageRequest();
        /*request.setBx_area_id("1");
        request.setBx_cate_lv1_id("1");
        request.setBx_cate_lv2_id("1");
        request.setBx_dk_id("1");
        request.setBx_time(Query.SORT_DESC);
        request.setNode_id_("1");
        request.setDESC("1");
        request.setState("1");*/
        viewModel.loadPagingData(request).observe(this,dataBeans->{
            adapter.submitList(dataBeans);
        });
    }

    @Override
    protected void setUpData() {
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH,Boolean.class).observe(getActivity(), shown -> {
            if(!shown){
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
                    if(FRAGMENT_SEND_OWRKORDER_DONE.equals(getFragmentTag())){
                        binding.itemResendRe.setVisibility(View.GONE);
                    }
                    binding.itemResendRe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            ARouter.getInstance()
//                                    .build(RouterUtils.ACTIVITY_RESEND_ORDER)
//                                    .withString(RouteKey.KEY_TASK_ID,repairsModel.getTaskId())
//                                    .withString(RouteKey.KEY_ORDER_ID,repairsModel.getID_())
//                                    .withString(RouteKey.KEY_DIVIDE_ID,distribute.getF_DIVIDE_ID())
//                                    .withString(RouteKey.KEY_PROJECT_ID,distribute.getF_PROJECT_ID())
//                                    .navigation();
                        }
                    });
                    binding.itemRepairGrab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                          viewModel.loadPagingData()
                        }
                    });
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


    protected String getFragmentTag(){
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
            return oldItem==newItem;
        }
    };

    @Override
    public void onItemClicked(View veiw, RepairsModel data) {

    }
}
