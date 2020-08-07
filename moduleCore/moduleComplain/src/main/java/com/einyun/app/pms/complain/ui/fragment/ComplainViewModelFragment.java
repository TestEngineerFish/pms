package com.einyun.app.pms.complain.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.Query;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.searchhistory.PageSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.PageSearchListener;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.workorder.model.ComplainModel;
import com.einyun.app.library.workorder.model.RepairsModel;
import com.einyun.app.library.workorder.net.request.ComplainPageRequest;
import com.einyun.app.library.workorder.net.request.RepairsPageRequest;
import com.einyun.app.pms.complain.BR;
import com.einyun.app.pms.complain.R;
import com.einyun.app.pms.complain.databinding.ComplainFragmentBinding;
import com.einyun.app.pms.complain.databinding.ItemOrderComplainBinding;
import com.einyun.app.pms.complain.databinding.ItemOrderComplainSearchBinding;
import com.einyun.app.pms.complain.viewmodel.ComplainViewModel;
import com.einyun.app.pms.complain.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Route;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FEED;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_REPAIR_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_SEND_OWRKORDER_DONE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_FIR;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_SEC;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_PROPERTYS;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_TYPES;

/**
 * Paging Demo
 * Paging Component
 */
public class ComplainViewModelFragment extends BaseViewModelFragment<ComplainFragmentBinding, ComplainViewModel> implements PeriodizationView.OnPeriodSelectListener, ItemClickListener<ComplainModel> {
    RVPageListAdapter<ItemOrderComplainBinding, ComplainModel> adapter;
    private PageSearchFragment searchFragment;
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

    SelectPopUpView view;

    @Override
    protected void init() {
        super.init();
        request = new ComplainPageRequest();
        request.setTs_dk_id("");
        request.setTs_time(Query.SORT_DESC);
        request.setNode_id_("");
        request.setDESC(Query.SORT_DESC);
        request.setState("");
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
                    if (view == null) {
                        //弹出筛选view
                        ConditionBuilder builder = new ConditionBuilder();
                        List<SelectModel> conditions = builder
                                .addComplainPropertys(data.getComplainPropertys())
                                .addComplainTypes(data.getComplainTypes())
                                .build();
                        view = new SelectPopUpView(getActivity(), conditions);
                        view.setOnSelectedListener(selected -> handleSelect(selected));
                    }
                    view.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            }, BasicDataTypeEnum.COMPLAIN_PROPERTYS,BasicDataTypeEnum.COMPLAIN_TYPES);

        });
        LiveDataBusUtils.getLiveBusData(binding.empty.getRoot(),LiveDataBusKey.COMPLAIN_EMPTY+getFragmentTag(),this);
        /**
         * 搜索
         */
        binding.panelCondition.search.setOnClickListener(view1 -> {
            search();
        });
    }
    private void search() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_name", UserUtil.getUserName());
        MobclickAgent.onEvent(getActivity(), CustomEventTypeEnum.COMPLAIN_SEARCH.getTypeName(),map);
        try {
//            DistributePageRequest request = (DistributePageRequest) viewModel.request.clone();
            if (searchFragment == null) {
                searchFragment = new PageSearchFragment<ItemOrderComplainSearchBinding, ComplainModel>(getActivity(), BR.complainSearch, new PageSearchListener<ItemOrderComplainSearchBinding,ComplainModel>() {
                    @Override
                    public LiveData<PagedList<ComplainModel>> search(String search) {
                        searchRequest = new ComplainPageRequest();
                        searchRequest.setSearchValue(search);

//                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                        return viewModel.loadPagingData(searchRequest, getFragmentTag());
//                        } else {
//                            return viewModel.loadPadingData(requestBean, getFragmentTag());
//                            return viewModel.loadPadingData(request, getFragmentTag());
//                        }
                    }

                    @Override
                    public void onItemClick(ComplainModel model) {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                                .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
                                .withString(RouteKey.KEY_PRO_INS_ID, model.getProInsId())
                                .withString(RouteKey.ID, model.getID_())
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                                .navigation();
                    }

                    @Override
                    public void onItem(ItemOrderComplainSearchBinding binding, ComplainModel complainModel) {
                        if (FRAGMENT_REPAIR_WAIT_FOLLOW.equals(getFragmentTag())) {
                            binding.line.setVisibility(View.VISIBLE);
                            binding.llTalkOrTurnSingle.setVisibility(View.VISIBLE);
                            //转单
                            binding.tvTurnOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ARouter.getInstance()
                                            .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                            .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                            .withString(RouteKey.KEY_ORDER_ID, complainModel.getID_())
                                            .withString(RouteKey.KEY_DIVIDE_ID, complainModel.getF_ts_dk_id())
                                            .withString(RouteKey.KEY_PROJECT_ID, complainModel.getU_project_id())
                                            .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                            .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                            .navigation();
                                }
                            });
                            //沟通
                            binding.tvTalk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ARouter.getInstance().build(RouterUtils.ACTIVITY_COMMUNICATION)
                                            .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                            .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_COMMUN.getTypeName())
                                            .withString(RouteKey.KEY_DIVIDE_ID, complainModel.getF_ts_dk_id())
                                            .withString(RouteKey.KEY_PROJECT_ID, complainModel.getU_project_id())
                                            .navigation();
                                }
                            });
                        }
                        if (FRAGMENT_REPAIR_WAIT_FEED.equals(getFragmentTag())) {
                            binding.line.setVisibility(View.VISIBLE);
                            binding.rlFeedBack.setVisibility(View.VISIBLE);
                            binding.rlFeedBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ARouter.getInstance()
                                            .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                            .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                            .navigation();
                                }
                            });
                        }
                        binding.repairCreateTime.setText(FormatUtil.formatDate(complainModel.getCreateTime()));
                    }
                    @Override
                    public int getLayoutId() {
                        return R.layout.item_order_complain_search;
                    }
                });

                searchFragment.setHint("请输入工单编号、投诉内容");
            }
            searchFragment.show(getActivity().getSupportFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        showLoading(getActivity());
        loadPagingData();
    }

    private void handleSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            binding.panelCondition.setConditionSelected(true);
        } else {
            binding.panelCondition.setConditionSelected(false);
        }
        request.setF_ts_property_id(null);
        request.setF_ts_cate_id(null);
        if (selected.size() > 0) {
            request.setF_ts_property_id(selected.get(SELECT_COMPLAIN_PROPERTYS) == null ? null : selected.get(SELECT_COMPLAIN_PROPERTYS).getKey());
            request.setF_ts_cate_id(selected.get(SELECT_COMPLAIN_TYPES) == null ? null : selected.get(SELECT_COMPLAIN_TYPES).getKey());
        }else {
            request.setF_ts_property_id(null);
            request.setF_ts_cate_id(null);
        }
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
    }

    ComplainPageRequest request;
    ComplainPageRequest searchRequest;

    private void loadPagingData() {
        //初始化数据，LiveData自动感知，刷新页面

        viewModel.loadPagingData(request, getFragmentTag()).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
//            hideLoading();
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
            adapter = new RVPageListAdapter<ItemOrderComplainBinding, ComplainModel>(getActivity(), BR.complain, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderComplainBinding binding, ComplainModel complainModel) {
                    if (FRAGMENT_REPAIR_WAIT_FOLLOW.equals(getFragmentTag())) {
                        binding.line.setVisibility(View.VISIBLE);
                        binding.llTalkOrTurnSingle.setVisibility(View.VISIBLE);
                        //转单
                        binding.tvTurnOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                        .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                        .withString(RouteKey.KEY_ORDER_ID, complainModel.getID_())
                                        .withString(RouteKey.KEY_DIVIDE_ID, complainModel.getF_ts_dk_id())
                                        .withString(RouteKey.KEY_PROJECT_ID, complainModel.getU_project_id())
                                        .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                        .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                        .navigation();
                            }
                        });
                        //沟通
                        binding.tvTalk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance().build(RouterUtils.ACTIVITY_COMMUNICATION)
                                        .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                        .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_COMMUN.getTypeName())
                                        .withString(RouteKey.KEY_DIVIDE_ID, complainModel.getF_ts_dk_id())
                                        .withString(RouteKey.KEY_PROJECT_ID, complainModel.getU_project_id())
                                        .navigation();
                            }
                        });
                    }
                    if (FRAGMENT_REPAIR_WAIT_FEED.equals(getFragmentTag())) {
                        binding.line.setVisibility(View.VISIBLE);
                        binding.rlFeedBack.setVisibility(View.VISIBLE);
                        binding.rlFeedBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_INQUIRIES_FEEDBACK)
                                        .withString(RouteKey.KEY_TASK_ID, complainModel.getTaskId())
                                        .navigation();
                            }
                        });
                    }
                    binding.repairCreateTime.setText(FormatUtil.formatDate(complainModel.getCreateTime()));
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
    private DiffUtil.ItemCallback<ComplainModel> mDiffCallback = new DiffUtil.ItemCallback<ComplainModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull ComplainModel oldItem, @NonNull ComplainModel newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ComplainModel oldItem, @NonNull ComplainModel newItem) {
            return oldItem == newItem;
        }
    };

    @Override
    public void onItemClicked(View veiw, ComplainModel data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                .withString(RouteKey.ID, data.getID_())
                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                .navigation();
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        binding.panelCondition.setPeriodSelected(true);
        request.setTs_dk_id(orgModel.getId());
        loadPagingData();
    }
}
