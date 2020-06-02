package com.einyun.app.pms.orderlist.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.manager.BasicDataTypeEnum;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.RecyclerViewNoBugLinearLayoutManager;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.LiveDataBusUtils;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.library.resource.workorder.model.OrderListModel;
import com.einyun.app.library.resource.workorder.model.OrderState;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.library.resource.workorder.net.request.GetNodeIdRequest;
import com.einyun.app.library.resource.workorder.net.request.OrderListPageRequest;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.orderlist.R;
import com.einyun.app.pms.orderlist.databinding.ActivityOrderListAllBinding;
import com.einyun.app.pms.orderlist.databinding.ItemOrderListBinding;
import com.einyun.app.pms.orderlist.viewmodel.OrderListViewModel;
import com.einyun.app.pms.orderlist.viewmodel.ViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;
import java.util.Map;

import static com.einyun.app.common.manager.BasicDataTypeEnum.LINE_TYPES;
import static com.einyun.app.common.manager.BasicDataTypeEnum.REPAIR_AREA;
import static com.einyun.app.common.manager.BasicDataTypeEnum.RESOURCE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_FIR;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_AREA_SEC;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_BUILDING;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_PROPERTYS;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_COMPLAIN_TYPES;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_DATE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_GRID;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_IS_OVERDUE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_LINE_TYPES;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE2;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_ORDER_TYPE3;
import static com.einyun.app.common.ui.widget.SelectPopUpView.SELECT_UNIT;

@Route(path = RouterUtils.ACTIVITY_ORDER_LIST_ALL)
public class OrderListAllActivity extends BaseHeadViewModelActivity<ActivityOrderListAllBinding, OrderListViewModel> implements ItemClickListener<OrderListModel>, PeriodizationView.OnPeriodSelectListener {
    RVPageListAdapter<ItemOrderListBinding, OrderListModel> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    public String tag;
    OrderListPageRequest request;
    protected SelectPopUpView selectPopUpView;
    private GetNodeIdRequest getNodeIdRequest;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list_all;
    }

    private String nodeId;

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
        switch (tag) {
            case RouteKey.ORDER_LIST_DISTRIBUTE:
                setHeadTitle(R.string.text_send_order);
                break;
            case RouteKey.ORDER_LIST_PLAN:
                setHeadTitle(R.string.work_plan);
                break;
            case RouteKey.ORDER_LIST_PATRO:
                setHeadTitle(R.string.title_patrol);
                break;
            case RouteKey.ORDER_LIST_REPAIR:
                setHeadTitle(R.string.text_work_repair);
                break;
            case RouteKey.ORDER_LIST_ASK:
                setHeadTitle(R.string.title_ask);
                break;
            case RouteKey.ORDER_LIST_COMPLAIN:
                setHeadTitle(R.string.text_work_complain);
                break;
            case RouteKey.ORDER_LIST_UNWELL:
                setHeadTitle(R.string.title_unwell);
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(OrderListAllActivity.this::onPeriodSelectListener);
                periodizationView.show(OrderListAllActivity.this.getSupportFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出筛选view

                switch (tag) {
                    case RouteKey.ORDER_LIST_REPAIR:
                        BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                            @Override
                            public void call(BasicData data) {
                                if (selectPopUpView == null) {
                                    ConditionBuilder builder = new ConditionBuilder();
                                    builder.addRepairArea(data.getRepairArea());
                                    List<SelectModel> condition = builder.build();
                                    selectPopUpView = new SelectPopUpView(OrderListAllActivity.this, condition)
                                            .setOnSelectedListener(selected -> handleRepairSelect(selected));
                                }
                                selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                            }

                            @Override
                            public void onFaild(Throwable throwable) {

                            }
                        }, REPAIR_AREA);
                        break;
                    case RouteKey.ORDER_LIST_PATRO:
                        showPatroConditionView();
                        break;
                    case RouteKey.ORDER_LIST_DISTRIBUTE:
                        showDistributeConditionView();
                        break;
                    case RouteKey.ORDER_LIST_PLAN:
                        BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                            @Override
                            public void call(BasicData data) {
                                //弹出筛选view
                                showPlanConditionView(data);
                            }

                            @Override
                            public void onFaild(Throwable throwable) {

                            }
                        }, BasicDataTypeEnum.LINE);
                        break;
                    case RouteKey.ORDER_LIST_COMPLAIN:
                        BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                            @Override
                            public void call(BasicData data) {
                                if (selectPopUpView == null) {
                                    //弹出筛选view
                                    ConditionBuilder builder = new ConditionBuilder();
                                    List<SelectModel> conditions = builder
                                            .addComplainPropertys(data.getComplainPropertys())
                                            .addComplainTypes(data.getComplainTypes())
                                            .build();
                                    selectPopUpView = new SelectPopUpView(OrderListAllActivity.this, conditions);
                                    selectPopUpView.setOnSelectedListener(selected -> showComplainSelect(selected));
                                }
                                selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                            }

                            @Override
                            public void onFaild(Throwable throwable) {

                            }
                        }, BasicDataTypeEnum.COMPLAIN_PROPERTYS, BasicDataTypeEnum.COMPLAIN_TYPES);
                        break;
                    case RouteKey.ORDER_LIST_ASK:
                        break;

                }
//                selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabSelectLn);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        request = new OrderListPageRequest();
        getNodeIdRequest = new GetNodeIdRequest();
        //停止刷新
        LiveEventBus.get(LiveDataBusKey.STOP_REFRESH, Boolean.class).observe(this, shown -> {
            if (!shown) {
                binding.sendOrderRef.setRefreshing(false);
            }
        });
        LiveDataBusUtils.getLiveBusData(binding.empty.getRoot(),LiveDataBusKey.ORDER_LIST_EMPTY,this);
        RecyclerView mRecyclerView = binding.orderListRec;
        RecyclerViewNoBugLinearLayoutManager mLayoutManager = new RecyclerViewNoBugLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemOrderListBinding, OrderListModel>(this, com.einyun.app.pms.orderlist.BR.workOrder, mDiffCallback) {

                @Override
                public void onBindItem(ItemOrderListBinding binding, OrderListModel orderListModel) {
                    showItemByTag(tag, binding, orderListModel);

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
        viewModel.setTag(tag);
        viewModel.loadPagingData(request, tag).observe(this, dataBeans -> {
            adapter.submitList(dataBeans);
        });
    }


//    protected void updatePageUIState(int state) {
//        binding.pageState.setPageState(state);
//    }

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
                    .withBoolean(RouteKey.KEY_IS_ORDER_LIST, true)
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID())
                    .withString(RouteKey.KEY_FRAGEMNT_TAG, RouteKey.FRAGMENT_PLAN_OWRKORDER_DONE)
                    .navigation();
            return;
        }
        if (tag.equals(RouteKey.ORDER_LIST_PATRO)) {
           /* ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getPROC_INST_ID())
                    .withInt(RouteKey.KEY_LIST_TYPE, ListType.DONE.getType())
                    .navigation();*/
            if((data.getF_patrol_line_id()!=null)){
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
                        .withString(RouteKey.KEY_TASK_ID,"")
                        .withString(RouteKey.KEY_ORDER_ID,data.getID_())
                        .withInt(RouteKey.KEY_LIST_TYPE,ListType.DONE.getType())
                        .withString(RouteKey.KEY_TASK_NODE_ID,"UserTask1")
                        .withString(RouteKey.KEY_PRO_INS_ID,data.getPROC_INST_ID())
                        .navigation();
            }else{
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                        .withString(RouteKey.KEY_TASK_ID,"")
                        .withString(RouteKey.KEY_ORDER_ID,data.getID_())
                        .withInt(RouteKey.KEY_LIST_TYPE,ListType.DONE.getType())
                        .withString(RouteKey.KEY_TASK_NODE_ID,"")
                        .withString(RouteKey.KEY_PRO_INS_ID,data.getPROC_INST_ID())
                        .navigation();
            }
            return;
        }
        if (tag.equals(RouteKey.ORDER_LIST_REPAIR)) {
            getNodeIdRequest.setDefkey("customer_repair_flow");
            getNodeIdRequest.setId(data.getID_());
            getNodeId(data);
        }
        if (tag.equals(RouteKey.ORDER_LIST_COMPLAIN)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_CUSTOMER_COMPLAIN_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getInstance_id())
                    .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                    .navigation();
            return;
        }
        if (tag.equals(RouteKey.ORDER_LIST_ASK)) {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_INQUIRIES_DETAIL)
                    .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                    .withString(RouteKey.KEY_TASK_NODE_ID, "")
                    .withString(RouteKey.KEY_TASK_ID, "")
                    .withString(RouteKey.KEY_PRO_INS_ID, data.getInstance_id())
                    .withString(RouteKey.KEY_LIST_TYPE, RouteKey.FRAGMENT_REPAIR_ALREADY_FOLLOW)
                    .navigation();
            return;
        }

    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        binding.panelCondition.setPeriodSelected(true);
        switch (tag) {
            case RouteKey.ORDER_LIST_REPAIR:
                request.setBx_dk_id(orgModel.getId());
                break;
            case RouteKey.ORDER_LIST_PATRO:
                request.setDividePatroId(orgModel.getId());
                break;
            case RouteKey.ORDER_LIST_COMPLAIN:
                request.setTs_dk_id(orgModel.getId());
                break;
            case RouteKey.ORDER_LIST_ASK:
                break;
        }
        request.setDivideId(orgModel.getId());
        loadPagingData();
    }

    /**
     * 客户工单展示状态
     */
    private void setStatusCustom(TextView textView, ImageView view, String value) {
        if (value.equals(RouteKey.LIST_STATUS_CLOSED)) {
            textView.setText(com.einyun.app.common.R.string.text_state_closed);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_closed);
        } else if (value.equals(RouteKey.LIST_STATUS_RESPONSE)) {
            textView.setText(com.einyun.app.common.R.string.text_wait_response);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_wait_response);
        } else if (value.equals(RouteKey.LIST_STATUS_HANDLE)) {
            textView.setText(com.einyun.app.common.R.string.text_handling);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_handling);
        } else if (value.equals(RouteKey.LIST_STATUS_EVALUATE)) {
            textView.setText(com.einyun.app.common.R.string.text_wait_evaluate);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_wait_evaluate);
        } else if (value.equals(RouteKey.LIST_STATUS_SEND_ORDER)) {
            textView.setText(com.einyun.app.common.R.string.text_wait_send);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_wait_send);
        } else if (value.equals(RouteKey.LIST_STATUS_WAIT_GRAB)) {
            textView.setText(com.einyun.app.common.R.string.text_wait_grab);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_wait_grab);
        }
    }

    /**
     * 展示状态
     */
    private void setStatus(TextView textView, ImageView view, String value) {
        int valueInt = Integer.parseInt(value);
        if (valueInt == OrderState.NEW.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_state_new);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_new);
        } else if (valueInt == OrderState.HANDING.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_handling);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_processing);
        } else if (valueInt == OrderState.APPLY.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_apply);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_work_order_apply);
        } else if (valueInt == OrderState.CLOSED.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_state_closed);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_closed);
        }
    }

    /**
     * 巡查工单显示状态
     */
    public static void patroStatus(TextView textView, ImageView view, int value) {
        if (value == OrderState.NEW.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_state_new);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_new);
        } else if (value == OrderState.HANDING.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_handling);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_processing);
        } else if (value == OrderState.APPLY.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_apply);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_work_order_apply);
        } else if (value == OrderState.CLOSED.getState()) {
            textView.setText(com.einyun.app.common.R.string.text_state_closed);
            view.setImageResource(com.einyun.app.common.R.mipmap.icon_state_closed);
        }
    }

    /**
     * 根据tag显示item
     */
    private void showItemByTag(String tag, ItemOrderListBinding binding, OrderListModel orderListModel) {
        switch (tag) {
            case RouteKey.ORDER_LIST_COMPLAIN:
                setStatusCustom(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getF_state());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getF_ts_content()));
                binding.itemCreateTime.setText(FormatUtil.formatDate(orderListModel.getF_ts_time()));
                binding.itemOrderNum.setText(orderListModel.getF_ts_code());
                binding.itemComplain.setVisibility(View.VISIBLE);
                binding.itemComplainUser.setText(orderListModel.getF_ts_user());
                binding.itemHouse.setVisibility(View.VISIBLE);
                binding.itemHouseTxt.setText(orderListModel.getF_ts_house());
                break;
            case RouteKey.ORDER_LIST_ASK:
                setStatusCustom(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getState());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getWx_content()));
                binding.itemCreateTime.setText(FormatUtil.formatDate(orderListModel.getWx_time()));
                binding.itemOrderNum.setText(orderListModel.getWx_code());
                break;
            case RouteKey.ORDER_LIST_REPAIR:
                setStatusCustom(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getState());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getBx_area()+ orderListModel.getBx_cate_lv1() + "-" + orderListModel.getBx_cate_lv2() + "-" + orderListModel.getBx_cate_lv3()));
                binding.itemCreateTime.setText(FormatUtil.formatDate(orderListModel.getBx_time()));
                binding.itemOrderNum.setText(orderListModel.getBx_code());
                binding.itemRepair.setVisibility(View.VISIBLE);
                binding.itemRepairUser.setText(orderListModel.getBx_user());
                binding.itemHouse.setVisibility(View.VISIBLE);
                binding.itemHouseTxt.setText(orderListModel.getBx_house());
                break;
            case RouteKey.ORDER_LIST_DISTRIBUTE:
                setStatus(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getF_STATUS());
                binding.itemCreateTime.setText(orderListModel.getF_CREATE_TIME());
                binding.itemOrderNum.setText(orderListModel.getF_ORDER_NO());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getF_DESC()));
                binding.itemLocationLn.setVisibility(View.VISIBLE);
                binding.itemLocation.setText(orderListModel.getF_LOCATION());
                break;
            case RouteKey.ORDER_LIST_PATRO:
                patroStatus(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getF_plan_work_order_state());
                binding.itemCreateTime.setText(FormatUtil.formatDate(orderListModel.getF_creation_date()));
                binding.itemOrderNum.setText(orderListModel.getF_plan_work_order_code());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getF_inspection_work_plan_name()));
                break;
            case RouteKey.ORDER_LIST_PLAN:
                setStatus(binding.itemStatusTxt, binding.itemStatusImg, orderListModel.getF_STATUS());
                binding.itemCreateTime.setText(FormatUtil.formatDate(Long.parseLong(orderListModel.getF_CREATE_TIME())));
                binding.itemOrderNum.setText(orderListModel.getF_ORDER_NO());
                binding.itemSendWorkSubject.setText(LimitText(orderListModel.getF_WP_NAME()));
                break;

        }

    }

    /**
     * 处理筛选返回数据
     */
    private void handleSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            switch (tag) {
                case RouteKey.ORDER_LIST_DISTRIBUTE:
                    request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
                    request.setType(selected.get(SELECT_ORDER_TYPE) == null ? null : selected.get(SELECT_ORDER_TYPE).getKey());
                    request.setEnvType2(selected.get(SELECT_ORDER_TYPE2) == null ? null : selected.get(SELECT_ORDER_TYPE2).getKey());
                    request.setEnvType3(selected.get(SELECT_ORDER_TYPE3) == null ? null : selected.get(SELECT_ORDER_TYPE3).getKey());
                    request.setFState(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
                    break;
                case RouteKey.ORDER_LIST_PATRO:
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
                    break;
            }
        }else {
            if (tag.equals(RouteKey.ORDER_LIST_DISTRIBUTE)) {
                request.setTxId(null);
                request.setType(null);
                request.setEnvType2(null);
                request.setEnvType3(null);
                request.setFState(null);
            }
        }
        binding.panelCondition.setConditionSelected(true);
        loadPagingData();
    }

    /**
     * 计划工单显示筛选条件
     */
    protected void showPlanConditionView(BasicData data) {
        if (selectPopUpView == null) {
            ConditionBuilder builder = new ConditionBuilder();
            List<SelectModel> conditions = builder.addOnlyLines(data.getLines())//条线
                    .addItem(SelectPopUpView.SELECT_DATE)//完成截止时间
                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                    .mergeLineRes(data.getResources())
                    .build();
            selectPopUpView = new SelectPopUpView(OrderListAllActivity.this, conditions)
                    .setOnSelectedListener(selected -> showPlanSelect(selected));
        }
        selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
    }


    /**
     * 巡查工单显示筛选条件
     */
    protected void showPatroConditionView() {
        if (TextUtils.isEmpty(request.getDivideId())) {
            ToastUtil.show(CommonApplication.getInstance(), R.string.text_need_divide_selected);
            return;
        }
        if (selectPopUpView == null) {
            BasicDataManager.getInstance().loadDivideGrid(request.getDivideId(), new CallBack<DivideGrid>() {
                @Override
                public void call(DivideGrid divideGrid) {
                    BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
                        @Override
                        public void call(BasicData data) {
                            ConditionBuilder builder = new ConditionBuilder();
                            List<SelectModel> conditions = builder
                                    .addDivideGrid(divideGrid) //网格-楼栋-单元
                                    .addLineTypesItem(data.getListLineTypes())
                                    .addItem(SELECT_DATE)
                                    .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                                    .mergeLineRes(data.getResources())
                                    .build();
                            selectPopUpView = new SelectPopUpView(OrderListAllActivity.this, conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                                @Override
                                public void onSelected(Map selected) {
                                    showPatroSelect(selected);
                                }
                            });
                            selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
                        }

                        @Override
                        public void onFaild(Throwable throwable) {

                        }
                    }, BasicDataTypeEnum.LINE_TYPES, BasicDataTypeEnum.RESOURCE);

                }

                @Override
                public void onFaild(Throwable throwable) {

                }
            });
        } else {
            selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
        }

    }

    private void showDistributeConditionView() {
        BasicDataManager.getInstance().loadBasicData(new CallBack<BasicData>() {
            @Override
            public void call(BasicData data) {
                //弹出筛选view
                if (selectPopUpView == null) {
                    ConditionBuilder builder = new ConditionBuilder();
                    List<SelectModel> conditions = builder.addLines(data.getLines())//条线
                            .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                            .mergeLineRes(data.getResources())
                            .build();
                    selectPopUpView = new SelectPopUpView(OrderListAllActivity.this, conditions)
                            .setOnSelectedListener(selected -> handleSelect(selected));
                }
                selectPopUpView.showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
            }

            @Override
            public void onFaild(Throwable throwable) {

            }
        }, BasicDataTypeEnum.LINE, BasicDataTypeEnum.RESOURCE);
    }

    /**
     * 报修处理筛选返回数据
     */
    private void handleRepairSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            request.setBx_area_id(selected.get(SELECT_AREA) == null ? null : selected.get(SELECT_AREA).getKey());
            request.setBx_cate_lv1_id(selected.get(SELECT_AREA_FIR) == null ? null : selected.get(SELECT_AREA_FIR).getKey());
            request.setBx_cate_lv2_id(selected.get(SELECT_AREA_SEC) == null ? null : selected.get(SELECT_AREA_SEC).getKey());
        }else {
            request.setBx_area_id(null);
            request.setBx_cate_lv1_id(null);
            request.setBx_cate_lv2_id(null);
        }
        binding.panelCondition.setConditionSelected(true);
        loadPagingData();
    }

    /**
     * 派工单处理筛选返回数据
     */
    public void handleDistributeSelected(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
            request.setType(selected.get(SELECT_ORDER_TYPE) == null ? null : selected.get(SELECT_ORDER_TYPE).getKey());
            request.setEnvType2(selected.get(SELECT_ORDER_TYPE2) == null ? null : selected.get(SELECT_ORDER_TYPE2).getKey());
            request.setEnvType3(selected.get(SELECT_ORDER_TYPE3) == null ? null : selected.get(SELECT_ORDER_TYPE3).getKey());
            request.setFState(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        }
        loadPagingData();
    }

    /**
     * 巡查工单处理筛选返回数据
     */

    protected void showPatroSelect(Map<String, SelectModel> selected) {
        String gridId = selected.get(SELECT_GRID) == null ? null : selected.get(SELECT_GRID).getId();
        String budingId = selected.get(SELECT_BUILDING) == null ? null : selected.get(SELECT_BUILDING).getId();
        String unitId = selected.get(SELECT_UNIT) == null ? null : selected.get(SELECT_UNIT).getId();
        request.setTxPatroId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
        request.setTypeId(selected.get(SELECT_LINE_TYPES) == null ? null : selected.get(SELECT_LINE_TYPES).getKey());
        request.setPeriod(selected.get(SELECT_DATE) == null ? null : selected.get(SELECT_DATE).getKey());
        request.setTimeout(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        request.setGridId(gridId);
        request.setBuildingId(budingId);
        request.setUnitId(unitId);
        binding.panelCondition.setConditionSelected(true);
        loadPagingData();
    }

    /**
     * 计划工单处理筛选返回数据
     */
    public void showPlanSelect(Map<String, SelectModel> selected) {
        request.resetConditions();
        request.setPeriod(selected.get(SELECT_DATE) == null ? null : selected.get(SELECT_DATE).getKey());
        request.setTxId(selected.get(SELECT_LINE) == null ? null : selected.get(SELECT_LINE).getKey());
        request.setType(selected.get(SELECT_ORDER_TYPE) == null ? null : selected.get(SELECT_ORDER_TYPE).getKey());
        request.setEnvType2(selected.get(SELECT_ORDER_TYPE2) == null ? null : selected.get(SELECT_ORDER_TYPE2).getKey());
        request.setEnvType3(selected.get(SELECT_ORDER_TYPE3) == null ? null : selected.get(SELECT_ORDER_TYPE3).getKey());
        request.setOtStatus(selected.get(SELECT_IS_OVERDUE) == null ? null : selected.get(SELECT_IS_OVERDUE).getKey());
        binding.panelCondition.setConditionSelected(true);
        loadPagingData();
    }

    /**
     * 投诉工单处理筛选返回数据
     */

    private void showComplainSelect(Map<String, SelectModel> selected) {
        if (selected.size() > 0) {
            request.setF_ts_property_id(selected.get(SELECT_COMPLAIN_PROPERTYS) == null ? null : selected.get(SELECT_COMPLAIN_PROPERTYS).getKey());
            request.setF_ts_cate_id(selected.get(SELECT_COMPLAIN_TYPES) == null ? null : selected.get(SELECT_COMPLAIN_TYPES).getKey());
        }else {
            request.setF_ts_property_id(null);
            request.setF_ts_cate_id(null);
        }
        binding.panelCondition.setConditionSelected(true);
        loadPagingData();
    }

    /**
     * 根据id获取nodeId
     */

    private void getNodeId(OrderListModel data) {
        viewModel.getNodeId(getNodeIdRequest, data);
    }


    /**
     * 超出10个字显示省略号
     */
    public String LimitText(String s) {
        if (s.length() > 10) {
//            return s.substring(0, 10) + "...";
            return s;
        } else {
            return s;
        }

    }

}
