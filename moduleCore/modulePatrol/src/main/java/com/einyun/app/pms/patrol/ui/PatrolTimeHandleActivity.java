package com.einyun.app.pms.patrol.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolTimeHandleBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolTimeWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

@Route(path = RouterUtils.ACTIVITY_PATROL_TIME_HANDLE)
public class PatrolTimeHandleActivity extends PatrolHandleActivity<ActivityPatrolTimeHandleBinding,PatrolViewModel> {
    protected RVBindingAdapter<ItemPatrolTimeWorkNodeBinding, WorkNode> nodesAdapter;
    @Autowired
    IUserModuleService userModuleService;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_TASK_NODE_ID)
    String taskNodeId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;

    @Autowired(name = RouteKey.KEY_LIST_TYPE)
    int listType= ListType.PENDING.getType();
    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this,new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_time_handle;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol_time);
    }

    @Override
    protected void setUpWorkNodes() {
        //工作节点适配
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolTimeWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolTimeWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //处理节点
                        tableItem(binding, position);
                    }
                }



                //处理节点
                protected void tableItem(ItemPatrolTimeWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //处理表头
                protected void tableHead(ItemPatrolTimeWorkNodeBinding binding) {
                    binding.tvNumber.setText(R.string.text_no);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.tvWorkNode.setVisibility(View.GONE);
                    binding.tvWorkThings.setGravity(Gravity.CENTER);
                    binding.tvWorkThings.setText(R.string.text_work_items);
                    binding.tvWorkThings.setTextSize(14);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_work_node;
                }
            };
        }
        binding.rvNodes.setAdapter(nodesAdapter);
    }
}
