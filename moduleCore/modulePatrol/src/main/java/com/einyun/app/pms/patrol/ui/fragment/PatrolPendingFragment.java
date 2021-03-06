package com.einyun.app.pms.patrol.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.component.searchhistory.PageSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.PageSearchListener;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.model.PatrolWorkOrder;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.patrol.BR;
import com.einyun.app.pms.patrol.databinding.FragmentPatrolPendingBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemWorkPatrolBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolListViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_BUILDING;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_GRID;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE_TYPES;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_UNIT;

/**
 * ????????????
 */
public class PatrolPendingFragment extends BaseViewModelFragment<FragmentPatrolPendingBinding, PatrolListViewModel> implements ItemClickListener<Patrol>, PeriodizationView.OnPeriodSelectListener {
    protected SelectPopUpView selectPopUpView;
    public int listType = ListType.PENDING.getType();
    protected RVPageListAdapter<ItemPatrolListBinding, Patrol> adapter;
    protected PageSearchFragment<ItemWorkPatrolBinding, Patrol> searchFragment;

    public static PatrolPendingFragment newInstance() {
        return new PatrolPendingFragment();
    }

    boolean hasInit;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_patrol_pending;
    }

    @Override
    protected void setUpView() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            binding.swiperefresh.setRefreshing(false);
            viewModel.refresh();
        });

        binding.panelCondition.search.setVisibility(View.VISIBLE);
        binding.panelCondition.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    @Override
    protected void setUpData() {
        initAdapter();
        binding.swiperefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.patrolList);
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getContext());
        binding.patrolList.setLayoutManager(mLayoutManager);
        binding.patrolList.setAdapter(adapter);
        loadData();
    }

    protected void initAdapter() {
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), com.einyun.app.pms.patrol.BR.patrol, mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {

                    binding.turnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                    .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
                                    .withString(RouteKey.KEY_ORDER_ID, model.getID_())
                                    .withString(RouteKey.KEY_DIVIDE_ID, model.getF_massif_id())
                                    .withString(RouteKey.KEY_PROJECT_ID, model.getF_project_id())
                                    .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                    .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                    .navigation();
                        }
                    });
                    if (model.getF_plan_work_order_state()==(OrderState.PENDING.getState())){
                        binding.turnOrder.setEnabled(false);
                        binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.normal_main_text_icon_color));
                    }else {
                        binding.turnOrder.setEnabled(true);
                        binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_list;
                }
            };
        }
        adapter.setOnItemClick(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasInit) {
            viewModel.refresh();
        }
        hasInit = true;
    }

    protected void loadData() {
        showLoading(getActivity());
        viewModel.loadPendingData().observe(getActivity(), patrols -> {
            if (patrols.size() == 0) {
                updatePageUIState(PageUIState.EMPTY.getState());
//                showLoading(getActivity());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                },3500);
            } else {
                updatePageUIState(PageUIState.FILLDATA.getState());
                hideLoading();
            }

            adapter.submitList(patrols);
            adapter.notifyDataSetChanged();
        });
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }


    //DiffUtil.ItemCallback,????????????
    protected DiffUtil.ItemCallback<Patrol> mDiffCallback = new DiffUtil.ItemCallback<Patrol>() {

        @Override
        public boolean areItemsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem == newItem;
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(PatrolPendingFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConditionView();
            }
        });
    }

    /**
     * ??????????????????
     */
    protected void showConditionView() {
        if (TextUtils.isEmpty(viewModel.request.getDivideId())) {
            ToastUtil.show(getContext(), R.string.text_need_divide_selected);
            return;
        }
        if (selectPopUpView == null) {
            BasicDataManager.getInstance().loadDivideGrid(viewModel.request.getDivideId(), new CallBack<DivideGrid>() {
                @Override
                public void call(DivideGrid divideGrid) {
                    BasicDataManager.getInstance().loadBasicData( new CallBack<BasicData>() {
                        @Override
                        public void call(BasicData data) {
                            ConditionBuilder builder = new ConditionBuilder();
                            List<SelectModel> conditions = builder
                                    .addDivideGrid(divideGrid) //??????-??????-??????
                                    .addLineTypesItem(data.getListLineTypes())
                                    .addItem(SELECT_DATE)
                                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//????????????
                                    .mergeLineRes(data.getResources())
                                    .build();
                            selectPopUpView = new SelectPopUpView(getActivity(), conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                                @Override
                                public void onSelected(Map selected) {
                                    handleSelect(selected);
                                }
                            });
                            selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                        }

                        @Override
                        public void onFaild(Throwable throwable) {

                        }
                    }, BasicDataTypeEnum.LINE_TYPES,BasicDataTypeEnum.RESOURCE);

                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });
        } else {
            selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
        }

    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.setPeriodSelected(true);
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        wrapDivideId(orgModel.getId(), viewModel.request);
        viewModel.onCondition();
        BasicDataManager.getInstance().loadDivideGrid(viewModel.request.getDivideId(), null);//???????????????????????????????????????
    }

    protected void search() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_name", UserUtil.getUserName());
        MobclickAgent.onEvent(getActivity(), CustomEventTypeEnum.PATOL_ORDER_SEARCH.getTypeName(),map);
        if (searchFragment == null) {
            searchFragment = new PageSearchFragment<>(getActivity(), com.einyun.app.pms.patrol.BR.patrolWorkOrder, new PageSearchListener<ItemWorkPatrolBinding,Patrol>() {
                @Override
                public LiveData<PagedList<Patrol>> search(String search) {
                    return viewModel.search(search);
                }

                @Override
                public void onItemClick(Patrol model) {
                    onItemClicked(null, model);
                }
                @Override
                public void onItem(ItemWorkPatrolBinding binding,Patrol model) {
                    binding.turnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance()
                                    .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                    .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
                                    .withString(RouteKey.KEY_ORDER_ID, model.getID_())
                                    .withString(RouteKey.KEY_DIVIDE_ID, model.getF_massif_id())
                                    .withString(RouteKey.KEY_PROJECT_ID, model.getF_project_id())
                                    .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                    .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                    .navigation();
                        }
                    });
                    if (model.getF_plan_work_order_state()==(OrderState.PENDING.getState())){
                        binding.turnOrder.setEnabled(false);
                        binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.normal_main_text_icon_color));
                    }else {
                        binding.turnOrder.setEnabled(true);
                        binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
                    }
                    if (listType== ListType.PENDING.getType()) {
                        binding.handleLayout.setVisibility(View.VISIBLE);
                    }else {//??????????????????????????????
                        binding.handleLayout.setVisibility(View.GONE);

                    }
                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_work_patrol;
                }
            });
            searchFragment.setHint("????????????????????????????????????");
        }
        searchFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    /**
     * ????????????????????????
     */
    protected void handleSelect(Map selected) {
        if (selected.size() > 0) {
            binding.panelCondition.setConditionSelected(true);
        } else {
            binding.panelCondition.setConditionSelected(false);
        }
        wrapCondition(selected, viewModel.request);
        viewModel.onCondition();
    }

    protected void wrapDivideId(String divideId, PatrolPageRequest request) {
        request.setDivideId(divideId);
    }

    protected void wrapCondition(Map<String, SelectModel> selected, PatrolPageRequest request) {
        String gridId = selected.get(SELECT_GRID) == null ? null : selected.get(SELECT_GRID).getId();
        String budingId = selected.get(SELECT_BUILDING) == null ? null : selected.get(SELECT_BUILDING).getId();
        String unitId = selected.get(SELECT_UNIT) == null ? null : selected.get(SELECT_UNIT).getId();
        request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
        request.setTypeId(selected.get(SELECT_LINE_TYPES) == null ? null : selected.get(SELECT_LINE_TYPES).getKey());
        request.setPeriod(selected.get(SELECT_DATE) == null ? null : selected.get(SELECT_DATE).getKey());
        request.setTimeout(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        request.setGridId(gridId);
        request.setBuildingId(budingId);
        request.setUnitId(unitId);

    }

    @Override
    protected PatrolListViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new ViewModelFactory()).get(PatrolListViewModel.class);
    }

    @Override
    public void onItemClicked(View veiw, Patrol data) {
        if (!TextUtils.isEmpty(data.getF_patrol_line_id())) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
                    .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withInt(RouteKey.KEY_LIST_TYPE, listType)
                    .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                    .withString(RouteKey.KEY_DIVIDE_ID, data.getF_massif_id())
                    .withString(RouteKey.KEY_PROJECT_ID, data.getF_project_id())
                    .navigation();
        } else {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                    .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                    .withString(RouteKey.KEY_DIVIDE_ID, data.getF_massif_id())
                    .withString(RouteKey.KEY_PROJECT_ID, data.getF_project_id())
                    .navigation();
        }
    }
}
