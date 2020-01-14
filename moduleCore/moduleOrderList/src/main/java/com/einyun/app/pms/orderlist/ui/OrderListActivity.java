package com.einyun.app.pms.orderlist.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.resource.workorder.net.request.DistributePageRequest;
import com.einyun.app.pms.orderlist.R;
import com.einyun.app.pms.orderlist.databinding.ActivityOrderListBinding;
import com.einyun.app.pms.orderlist.viewmodel.OrderListViewModel;
import com.einyun.app.pms.orderlist.viewmodel.ViewModelFactory;

@Route(path = RouterUtils.ACTIVITY_ORDER_LIST)
public class OrderListActivity extends BaseHeadViewModelActivity<ActivityOrderListBinding, OrderListViewModel> implements View.OnClickListener {

    @Autowired(name = RouteKey.KEY_WORK_ORDER_LIST_TYPE)
    public String type;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.order_list);
        if (DataConstants.WORK_ORDER_LIST_TYPE_CREATE.equals(type)) {
            binding.ogPlanOrder.setVisibility(View.GONE);
            binding.secondLine.setVisibility(View.GONE);
            binding.ogPatroOrder.setVisibility(View.INVISIBLE);
            binding.orderUn.setVisibility(View.VISIBLE);
            setHeadTitle("创建工单");
        }
    }

    @Override
    public void onClick(View v) {
        if (DataConstants.WORK_ORDER_LIST_TYPE_CREATE.equals(type)) {
            //创建工单列表
            if (v.getId() == R.id.og_send_order) {
                //创建派工单
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CREATE_SEND_ORDER).navigation();
            }
            if (v.getId() == R.id.order_un) {
                //不合格单
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CREATE_DISQUALIFIED).navigation();
            }
            if (v.getId() == R.id.client_complain_order) {
                //客户投诉
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CREATE_CLIENT_COMPLAIN_ORDER).navigation();
            }
            if (v.getId() == R.id.client_repairs_order) {
                //客户报修
                ARouter.getInstance().build(RouterUtils.ACTIVITY_CREATE_CLIENT_REPAIRS_ORDER).navigation();
            }
            if (v.getId() == R.id.client_enquiry_order) {
                //客户问询
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE).navigation();
            }
        } else {
            //预览工单列表
            if (v.getId() == R.id.og_send_order) {
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_DISTRIBUTE)
                        .navigation();
            }
            if (v.getId() == R.id.og_plan_order) {
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_PLAN)
                        .navigation();
            }
            if (v.getId() == R.id.og_patro_order) {
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_PATRO).navigation();
            }
            if (v.getId() == R.id.og_unwell_order) {
//                ToastUtil.show(this,"该功能暂未实现");
                ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_UNWELL).navigation();
            }
            if (v.getId() == R.id.client_complain_order) {
                //客户投诉
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_COMPLAIN).navigation();
            }
            if (v.getId() == R.id.client_repairs_order) {
                //客户报修
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_REPAIR).navigation();
            }
            if (v.getId() == R.id.client_enquiry_order) {
                //客户问询
                ARouter.getInstance().build(RouterUtils.ACTIVITY_ORDER_LIST_ALL).withString(RouteKey.KEY_LIST_TYPE,RouteKey.ORDER_LIST_ASK).navigation();
            }
        }

    }

    @Override
    protected OrderListViewModel initViewModel() {
        return new ViewModelProvider(this,new ViewModelFactory()).get(OrderListViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.ogSendOrder.setOnClickListener(this);
        binding.ogPatroOrder.setOnClickListener(this);
        binding.ogPatroOrder.setOnClickListener(this);
        binding.ogUnwellOrder.setOnClickListener(this);
        binding.clientComplainOrder.setOnClickListener(this);
        binding.clientEnquiryOrder.setOnClickListener(this);
        binding.clientRepairsOrder.setOnClickListener(this);
        binding.ogPlanOrder.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
