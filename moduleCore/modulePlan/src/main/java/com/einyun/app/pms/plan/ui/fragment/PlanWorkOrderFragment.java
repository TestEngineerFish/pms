package com.einyun.app.pms.plan.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.db.entity.PlanInfo;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Plan;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.component.searchhistory.PageSearchFragment;
import com.einyun.app.common.ui.component.searchhistory.PageSearchListener;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.plan.BR;
import com.einyun.app.pms.plan.R;
import com.einyun.app.pms.plan.databinding.FragmentPlanWorkOrderBinding;
import com.einyun.app.pms.plan.databinding.ItemSearchWorkPlanBinding;
import com.einyun.app.pms.plan.databinding.ItemWorkPlanBinding;
import com.einyun.app.pms.plan.viewmodel.PlanOdViewModelFactory;
import com.einyun.app.pms.plan.viewmodel.PlanOrderDetailViewModel;
import com.einyun.app.pms.plan.viewmodel.PlanOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_PLAN_OWRKORDER_PENDING;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.sendorder.ui
 * @ClassName: SendWorkOrderFragment
 * @Description: java???????????????
 * @Author: zhulufeng
 * @CreateDate: 2019/11/26 14:37
 * @UpdateUser: ????????????
 * @UpdateDate: 2019/11/26 14:37
 * @UpdateRemark: ???????????????
 * @Version: 1.0
 */
public class PlanWorkOrderFragment extends BaseViewModelFragment<FragmentPlanWorkOrderBinding, PlanOrderViewModel> implements ItemClickListener<Plan>, PeriodizationView.OnPeriodSelectListener {
    //    private SendOrderAdapter adapter;//?????????
    RVPageListAdapter<ItemWorkPlanBinding, Plan> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    PageSearchFragment<ItemSearchWorkPlanBinding, PlanWorkOrder> searchFragment;
    protected SelectPopUpView selectPopUpView;
    private PlanOrderDetailViewModel planOrderDetailViewModel;

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
        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(PlanWorkOrderFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(v -> {
            BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                @Override
                public void call(BasicData data) {
                    //????????????view
                    showConditionView(data);
                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            }, BasicDataTypeEnum.LINE);

        });
        binding.panelCondition.search.setVisibility(View.VISIBLE);
        binding.panelCondition.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

    }

    protected void showConditionView(BasicData data) {
        if (selectPopUpView == null) {
            ConditionBuilder builder = new ConditionBuilder();
            List<SelectModel> conditions = builder.addOnlyLines(data.getLines())//??????
                    .addItem(SelectPopUpView.SELECT_DATE)//??????????????????
                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//????????????
                    .mergeLineRes(data.getResources())
                    .build();
            selectPopUpView = new SelectPopUpView(getActivity(), conditions)
                    .setOnSelectedListener(selected -> handleSelect(selected));
        }
        selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
    }

    private void search() {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_name", UserUtil.getUserName());
            MobclickAgent.onEvent(getActivity(), CustomEventTypeEnum.PLAN_ORDER_SEARCH.getTypeName(), map);
            DistributePageRequest request = (DistributePageRequest) viewModel.request.clone();
            if (searchFragment == null) {
                searchFragment = new PageSearchFragment<ItemSearchWorkPlanBinding, PlanWorkOrder>(getActivity(), BR.planModel, new PageSearchListener<ItemSearchWorkPlanBinding,PlanWorkOrder>() {
                    @Override
                    public LiveData<PagedList<PlanWorkOrder>> search(String search) {
                        request.setSearchValue(search);
                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                            return viewModel.loadPadingNetData(request, getFragmentTag());
                        } else {
                            return viewModel.loadDonePagingNetData(request, getFragmentTag());
                        }
                    }

                    @Override
                    public void onItemClick(PlanWorkOrder model) {
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                                .withString(RouteKey.KEY_ORDER_ID, model.getID_())
                                .withString(RouteKey.KEY_PRO_INS_ID, model.getProInsId())
                                .withString(RouteKey.KEY_TASK_ID, model.getTaskId())
                                .withString(RouteKey.KEY_TASK_NODE_ID, model.getTaskNodeId())
                                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                                .navigation();
                    }
                    @Override
                    public void onItem(ItemSearchWorkPlanBinding binding,PlanWorkOrder distributeWorkOrder) {

                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                            if (distributeWorkOrder.getF_OT_STATUS() == 1) {
                                binding.itemSendWorkLfImg.setVisibility(View.VISIBLE);
                            } else {
                                binding.itemSendWorkLfImg.setVisibility(View.GONE);
                            }
                        } else {
                            binding.itemSendWorkLfImg.setVisibility(View.GONE);
                        }
                        if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                            binding.waitHandleLayout.setVisibility(View.VISIBLE);
                            if (distributeWorkOrder.getF_OT_STATUS() == 1) {
                                binding.itemSendWorkLfImg.setVisibility(View.VISIBLE);
                            } else {
                                binding.itemSendWorkLfImg.setVisibility(View.GONE);
                            }
                            binding.turnOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ARouter.getInstance()
                                            .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                            .withString(RouteKey.KEY_TASK_ID, distributeWorkOrder.getTaskId())
                                            .withString(RouteKey.KEY_ORDER_ID, distributeWorkOrder.getID_())
                                            .withString(RouteKey.KEY_DIVIDE_ID, distributeWorkOrder.getF_DIVIDE_ID())
                                            .withString(RouteKey.KEY_PROJECT_ID, distributeWorkOrder.getF_project_id())
                                            .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                            .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                            .navigation();
                                }
                            });
                            PlanInfo planInfo = planOrderDetailViewModel.planRepository.loadPlanInfo(distributeWorkOrder.getID_(), planOrderDetailViewModel.getUserID());
                            if (planInfo!=null) {
                                isCached(binding.tvIsCached,binding.ivIsCached,true);
                            }else {
                                isCached(binding.tvIsCached,binding.ivIsCached,false);
                            }
                            if (Integer.parseInt(distributeWorkOrder.getF_STATUS())==(OrderState.PENDING.getState())){
                                binding.turnOrder.setEnabled(false);
                                binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.normal_main_text_icon_color));
                            }else {
                                binding.turnOrder.setEnabled(true);
                                binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
                            }
                        } else {
                            binding.waitHandleLayout.setVisibility(View.GONE);
                            binding.itemSendWorkLfImg.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_search_work_plan;
                    }
                });
                searchFragment.setHint("????????????????????????????????????");
            }
            searchFragment.show(getActivity().getSupportFragmentManager(), "");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void handleSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            binding.panelCondition.setConditionSelected(true);
        } else {
            binding.panelCondition.setConditionSelected(false);
        }
        viewModel.onConditionSelected(selected);
        viewModel.switchCondition(getFragmentTag());
    }

    protected String getFragmentTag() {
        return getArguments().getString(RouteKey.KEY_FRAGEMNT_TAG);
    }

    boolean isfresh = true;

    @Override
    public void onResume() {
        super.onResume();

        if (isfresh) {
            loadPagingData();
            isfresh = false;
        } else {
//            viewModel.refresh();
            viewModel.refresh(getFragmentTag());
//            loadPagingData();
        }
    }

    @Override
    protected void setUpView() {
        binding.sendOrderRef.setOnRefreshListener(() -> {
            binding.sendOrderRef.setRefreshing(false);
            viewModel.refresh(getFragmentTag());
        });
        binding.workSendList.addItemDecoration(new SpacesItemDecoration(30));

    }

    @Override
    protected void setUpData() {
        //????????????
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(getActivity(), shown -> {
            if (!shown) {
                binding.sendOrderRef.setRefreshing(false);
            }
        });
        loadPagingData();
        //??????????????????
        viewModel.getLiveEvent().observe(getActivity(), status -> {
            if (status.isRefresShown()) {
                viewModel.switchCondition(getFragmentTag());
            }
        });
        RecyclerView mRecyclerView = binding.workSendList;
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        planOrderDetailViewModel = new PlanOrderDetailViewModel();
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemWorkPlanBinding, Plan>(getActivity(), BR.planWorkOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemWorkPlanBinding binding, Plan distributeWorkOrder) {

                    if (getFragmentTag().equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
                        binding.waitHandleLayout.setVisibility(View.VISIBLE);
                        if (distributeWorkOrder.getF_OT_STATUS() == 1) {
                            binding.itemSendWorkLfImg.setVisibility(View.VISIBLE);
                        } else {
                            binding.itemSendWorkLfImg.setVisibility(View.GONE);
                        }
                        binding.turnOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance()
                                        .build(RouterUtils.ACTIVITY_RESEND_ORDER)
                                        .withString(RouteKey.KEY_TASK_ID, distributeWorkOrder.getTaskId())
                                        .withString(RouteKey.KEY_ORDER_ID, distributeWorkOrder.getID_())
                                        .withString(RouteKey.KEY_DIVIDE_ID, distributeWorkOrder.getF_DIVIDE_ID())
                                        .withString(RouteKey.KEY_PROJECT_ID, distributeWorkOrder.getF_project_id())
                                        .withString(RouteKey.KEY_CUSTOM_TYPE,CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName())
                                        .withString(RouteKey.KEY_CUSTOMER_RESEND_ORDER, RouteKey.KEY_CUSTOMER_RESEND_ORDER)
                                        .navigation();
                            }
                        });
                        PlanInfo planInfo = planOrderDetailViewModel.planRepository.loadPlanInfo(distributeWorkOrder.getID_(), planOrderDetailViewModel.getUserID());
                        if (planInfo!=null) {
                            isCached(binding.tvIsCached,binding.ivIsCached,true);
                        }else {
                            isCached(binding.tvIsCached,binding.ivIsCached,false);
                        }
                        if (Integer.parseInt(distributeWorkOrder.getF_STATUS())==(OrderState.PENDING.getState())){
                            binding.turnOrder.setEnabled(false);
                            binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.normal_main_text_icon_color));
                        }else {
                            binding.turnOrder.setEnabled(true);
                            binding.turnOrder.setTextColor(getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
                        }
                    } else {
                        binding.waitHandleLayout.setVisibility(View.GONE);
                        binding.itemSendWorkLfImg.setVisibility(View.GONE);
                    }
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
    public static void isCached(TextView textView,ImageView imageView, boolean value){
        if(value){
            imageView.setImageResource(R.drawable.icon_cached);
            textView.setText("?????????");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.stress_text_btn_icon_color));
        }else{
            imageView.setImageResource(R.drawable.icon_no_cache);
            textView.setText("?????????");
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.normal_main_text_icon_color));
        }
    }
    private void loadPagingData() {
        //??????????????????LiveData???????????????????????????
//        binding.sendOrderRef.setRefreshing(true);
        showLoading(getActivity());
        binding.sendOrderRef.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        String fragmentTag = getFragmentTag();
        if (viewModel.getOrgModel() != null) {
            viewModel.request.setDivideId(viewModel.getOrgModel().getId());
        }
        viewModel.request.setUserId(userModuleService.getUserId());
        if (fragmentTag.equals(FRAGMENT_PLAN_OWRKORDER_PENDING)) {
            viewModel.loadPendingInDB().observe(this, dataBeans -> {
                if (dataBeans.size() == 0) {
//                    showLoading(getActivity());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                        }
                    }, 3500);
                    updatePageUIState(PageUIState.EMPTY.getState());
                } else {
                    updatePageUIState(PageUIState.FILLDATA.getState());
                    hideLoading();
                }
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        } else {
            viewModel.loadDoneInDB().observe(this, dataBeans -> {
                if (dataBeans.size() == 0) {
                    updatePageUIState(PageUIState.EMPTY.getState());
                } else {
                    updatePageUIState(PageUIState.FILLDATA.getState());
                }
                hideLoading();
                adapter.submitList(dataBeans);
                adapter.notifyDataSetChanged();
            });
        }
    }

    protected void updatePageUIState(int state) {
        binding.pageState.setPageState(state);
    }

    @Override
    protected PlanOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new PlanOdViewModelFactory()).get(PlanOrderViewModel.class);
    }

    //DiffUtil.ItemCallback,????????????
    private DiffUtil.ItemCallback<Plan> mDiffCallback = new DiffUtil.ItemCallback<Plan>() {

        @Override
        public boolean areItemsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
            return oldItem == newItem;
        }
    };


    /**
     * ??????Item ???????????????????????????
     * ??????????????????(taskId)???????????????(taskNodeTd,proInsId)
     *
     * @param veiw
     * @param data
     */
    @Override
    public void onItemClicked(View veiw, Plan data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PLAN_ORDER_DETAIL)
                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                .withString(RouteKey.KEY_DIVIDE_ID, data.getF_DIVIDE_ID())
                .withString(RouteKey.KEY_PROJECT_ID, data.getF_project_id())
                .withString(RouteKey.KEY_FRAGEMNT_TAG, getFragmentTag())
                .navigation();
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        binding.panelCondition.setPeriodSelected(true);
        viewModel.setOrgModel(orgModel);
        viewModel.switchCondition(getFragmentTag());
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
