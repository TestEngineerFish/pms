package com.einyun.app.pms.repairs.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.matcher.ViewMatchers;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.Query;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.repairs.BR;
import com.einyun.app.pms.repairs.R;
import com.einyun.app.pms.repairs.databinding.ItemOrderRepairBinding;
import com.einyun.app.pms.repairs.databinding.RepairsFragmentBinding;
import com.einyun.app.pms.repairs.ui.RepairsActivity;
import com.einyun.app.pms.repairs.viewmodel.RepairsViewModel;
import com.einyun.app.pms.repairs.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_GRAB;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FEED_BACK;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_FIR;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_SEC;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;

/**
 * Paging Demo
 * Paging Component
 */
public class RepairsViewModelFragment extends BaseViewModelFragment<RepairsFragmentBinding, RepairsViewModel> implements ItemClickListener<RepairsModel>, PeriodizationView.OnPeriodSelectListener, RepairsActivity.GrabListener {
    RVPageListAdapter<ItemOrderRepairBinding, RepairsModel> adapter;
    private SelectPopUpView selectPopUpView = null;
    RepairsPageRequest request;

    public RepairsViewModelFragment() {

    }

    public static RepairsViewModelFragment newInstance(Bundle bundle) {
        RepairsViewModelFragment fragment = new RepairsViewModelFragment();
        fragment.setArguments(bundle);
        Logger.d("setBundle->" + bundle.getString(RouteKey.KEY_FRAGEMNT_TAG));
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.repairs_fragment;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        RepairsActivity activityRepair = (RepairsActivity) activity;
        activityRepair.setLinstenr(this);
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.repairOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(RepairsViewModelFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.repairOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出筛选view

                BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                    @Override
                    public void call(BasicData data) {
                        if (selectPopUpView == null) {
                            ConditionBuilder builder = new ConditionBuilder();
                            builder.addRepairArea(data.getRepairArea());
                            List<SelectModel> condition = builder.build();
                            selectPopUpView = new SelectPopUpView(getActivity(), condition)
                                    .setOnSelectedListener(selected -> handleSelect(selected));
                        }
                        selectPopUpView.showAsDropDown(binding.repairOrderTabLn);
                    }

                    @Override
                    public void onFaild(Throwable throwable) {

                    }
                }, BasicDataTypeEnum.REPAIR_AREA);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPagingData();
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
        binding.repairsList.addItemDecoration(new SpacesItemDecoration(0, 0, 0, 30));
    }

    public void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPagingData(request, getFragmentTag()).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }

    @Override
    protected void setUpData() {
        request = new RepairsPageRequest();
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

                    if (getFragmentTag().equals(FRAGMENT_REPAIR_GRAB)) {
                        binding.itemGrabRe.setVisibility(View.VISIBLE);
                    }
                    if (getFragmentTag().equals(FRAGMENT_REPAIR_WAIT_FOLLOW)) {
                        binding.itemContactOrFeedRe.setVisibility(View.VISIBLE);
                    }
                    if (getFragmentTag().equals(FRAGMENT_REPAIR_WAIT_FEED)) {
                        binding.itemFeedRe.setVisibility(View.VISIBLE);
                    }

                    if (repairsModel.getTaskNodeId() == null) {
                        repairsModel.setTaskNodeId("");
                    }
                    //抢单
                    binding.itemRepairGrab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.grabRepair(repairsModel.getTaskId()).observe(getActivity(), status -> {
                                if (status.booleanValue()) {
                                    new AlertDialog(getActivity()).builder().setTitle(getResources().getString(R.string.tip))
                                            .setMsg(getResources().getString(R.string.text_grab_success)).
                                            setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    viewModel.refreshUI();
                                                }
                                            }).show();
                                    loadPagingData();

                                } else {
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
                    //沟通
                    binding.itemContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance().build(RouterUtils.ACTIVITY_COMMUNICATION)
                                    .withString(RouteKey.KEY_TASK_ID, repairsModel.getTaskId())
                                    .withString(RouteKey.KEY_DIVIDE_ID, repairsModel.getBx_dk_id())
                                    .withString(RouteKey.KEY_PROJECT_ID, repairsModel.getU_project_id())
                                    .navigation();
                        }
                    });
                    //反馈
                    binding.itemFeedRe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                    .withString(RouteKey.KEY_TASK_ID, repairsModel.getTaskId())
                                    .navigation();
                        }
                    });
                    //转单
                    binding.itemResend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                    .withString(RouteKey.KEY_TASK_ID, repairsModel.getTaskId())
                                    .withString(RouteKey.KEY_ORDER_ID, repairsModel.getID_())
                                    .withString(RouteKey.KEY_DIVIDE_ID, repairsModel.getBx_dk_id())
                                    .withString(RouteKey.KEY_PROJECT_ID, repairsModel.getU_project_id())
                                    .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                    .navigation();
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
        if (getFragmentTag().equals(FRAGMENT_REPAIR_GRAB)) {
            //抢单列表无法查看详情
        } else {//跳转详情
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_CUSTOMER_REPAIR_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                    .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                    .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                    .withString(RouteKey.KEY_LIST_TYPE, getFragmentTag())
                    .navigation();
        }
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        request.setBx_dk_id(orgModel.getId());
        binding.repairPeriodSelected.setText(orgModel.getName());
        binding.repairPeriodSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        loadPagingData();
    }

    /**
     * 处理筛选返回数据
     */
    private void handleSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            request.setBx_area_id(selected.get(SELECT_AREA) == null ? null : selected.get(SELECT_AREA).getKey());
            request.setBx_cate_lv1_id(selected.get(SELECT_AREA_FIR) == null ? null : selected.get(SELECT_AREA_FIR).getKey());
            request.setBx_cate_lv2_id(selected.get(SELECT_AREA_SEC) == null ? null : selected.get(SELECT_AREA_SEC).getKey());
        }
        loadPagingData();
    }

    @Override
    public void onGrabed() {
        Log.d("test", "hhh");
        viewModel.loadPagingData(request, FRAGMENT_REPAIR_GRAB).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }


    /**
     * 超出10个字显示省略号
     */
    public String LimitText(TextView textView) {
        if (textView.getText().length() > 10) {
            return textView.getText().toString().substring(0, 10) + "...";
        } else {
            return textView.getText().toString();
        }

    }
}
