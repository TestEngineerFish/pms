package com.einyun.app.pms.repairs.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.matcher.ViewMatchers;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.repairs.BR;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ItemOrderRepairBinding;
import com.einyun.app.pms.repairs.databinding.RepairsFragmentBinding;
import com.einyun.app.pms.repairs.viewmodel.RepairsViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_GRAB;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;

/**
 * Paging Demo
 * Paging Component
 */
public class RepairGrabFragment extends BaseViewModelFragment<RepairsFragmentBinding, RepairsViewModel> implements ItemClickListener<RepairsModel> {
    RVPageListAdapter<ItemOrderRepairBinding, RepairsModel> adapter;

    /*public static RepairGrabFragment newInstance(Bundle bundle) {
        RepairsViewModelFragment fragment = new RepairsViewModelFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }*/

    @Override
    public int getLayoutId() {
        return R.layout.item_order_repair;
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
        if (getFragmentTag().equals(FRAGMENT_REPAIR_GRAB)) {
            binding.repairOrderTabLn.setVisibility(View.GONE);
        } else {
            binding.repairOrderTabLn.setVisibility(View.VISIBLE);
        }
    }

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        RepairsPageRequest request = new RepairsPageRequest();
        /*request.setBx_area_id("1");
        request.setBx_cate_lv1_id("1");
        request.setBx_cate_lv2_id("1");
        request.setBx_dk_id("1");
        request.setBx_time(Query.SORT_DESC);
        request.setNode_id_("1");
        request.setDESC("1");
        request.setState("1");*/
        viewModel.loadPagingData(request,getFragmentTag()).observe(this, dataBeans -> {
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
            adapter = new RVPageListAdapter<ItemOrderRepairBinding, RepairsModel>(getActivity(), BR.repair, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderRepairBinding binding, RepairsModel repairsModel) {
                    binding.itemRepairGrab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.grabRepair(repairsModel.getTaskId()).observe(getActivity(),status->{
                                if (status.booleanValue()){
                                    new AlertDialog(getActivity()).builder().setTitle(getResources().getString(R.string.tip))
                                            .setMsg(getResources().getString(R.string.text_grab_success)).
                                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    viewModel.refreshUI();
                                                }
                                            }).show();

                                }else {
                                    new AlertDialog(getActivity()).builder().setTitle(getResources().getString(R.string.tip))
                                            .setMsg(getResources().getString(R.string.text_grab_faile)).
                                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            }).show();
                                }
                            });
                        }
                    });
                    binding.repairCreateTime.setText(FormatUtil.formatDate(repairsModel.getCreateTime()));
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_order_repair;
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
    protected RepairsViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(RepairsViewModel.class);
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
}
