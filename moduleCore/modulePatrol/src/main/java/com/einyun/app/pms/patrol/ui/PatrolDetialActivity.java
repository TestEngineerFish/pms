package com.einyun.app.pms.patrol.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.db.bean.WorkNode;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.convert.ExtensionApplicationConvert;
import com.einyun.app.pms.patrol.databinding.ActivityPatrolDetialBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolWorkNodeBinding;
import com.einyun.app.pms.patrol.viewmodel.PatrolViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 巡查详情
 */
@Route(path = RouterUtils.ACTIVITY_PATROL_DETIAL)
public class PatrolDetialActivity extends BaseHeadViewModelActivity<ActivityPatrolDetialBinding, PatrolViewModel> {
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
    int listType= ListType.DONE.getType();

    RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode> nodesAdapter;

    PatrolInfo patrolInfo;

    @Override
    protected PatrolViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PatrolViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_detial;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_patrol);
        binding.setCallBack(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //工作节点适配
        if (nodesAdapter == null) {
            nodesAdapter = new RVBindingAdapter<ItemPatrolWorkNodeBinding, WorkNode>(this, com.einyun.app.pms.patrol.BR.node) {
                @Override
                public void onBindItem(ItemPatrolWorkNodeBinding binding, WorkNode model, int position) {
                    //处理列表头
                    if (position == 0) {
                        tableHead(binding);
                    } else {
                        //处理节点
                        tableItem(binding, position);
                        if (WorkNode.RESULT_PASS.equals(model.getResult())) {
                            onAgree(binding);
                        } else {
                            onReject(binding);
                        }
                    }
                }

                protected void onReject(ItemPatrolWorkNodeBinding binding) {
                    binding.btnReject.setBackgroundResource(R.drawable.corners_red_large);
                    binding.btnReject.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.btnAgree.setVisibility(View.GONE);
                }

                protected void onAgree(ItemPatrolWorkNodeBinding binding) {
                    binding.btnAgree.setBackgroundResource(R.drawable.corners_green_large);
                    binding.btnAgree.setTextColor(binding.btnAgree.getResources().getColor(R.color.white));
                    binding.btnReject.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);

                }

                //处理节点
                protected void tableItem(ItemPatrolWorkNodeBinding binding, int position) {
                    binding.tvNumber.setText(position + "");
                    binding.tvWorkNode.setVisibility(View.VISIBLE);
                    binding.tvResult.setVisibility(View.GONE);
                    binding.btnAgree.setVisibility(View.VISIBLE);
                    binding.btnReject.setVisibility(View.VISIBLE);
                    binding.tvWorkThings.setGravity(Gravity.LEFT);
                }

                //处理表头
                protected void tableHead(ItemPatrolWorkNodeBinding binding) {
                    binding.tvNumber.setText(R.string.text_no);
                    binding.tvResult.setVisibility(View.VISIBLE);
                    binding.btnAgree.setVisibility(View.GONE);
                    binding.btnReject.setVisibility(View.GONE);
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
        viewModel.request.setProInsId(proInsId);
        viewModel.request.setTaskNodeId(taskNodeId);
        viewModel.request.setTaskId(taskId);

        if(listType==ListType.PENDING.getType()){
            viewModel.loadPendingDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
            });
        }else if(listType==ListType.DONE.getType()){
            //加载数据
            viewModel.loadDoneDetial(orderId).observe(this, patrolInfo -> {
                updateUI(patrolInfo);
            });
        }
    }

    private void updateUI(PatrolInfo patrol) {
        if (patrol == null) {
            return;
        }
        this.patrolInfo = patrol;
        binding.setPatrol(patrol);
        updateWorkNodesUI(patrol);//更新节点信息
        updateHandleResultUI(patrol);//更新处理结果信息

        ExtensionApplicationConvert convert = new ExtensionApplicationConvert();
        updateForceCloseUI(patrol,convert);//更新强制关闭信息
        uploadPostponeUI(patrol, convert);//更新申请超时信息
    }

    /**
     * 更新申请超时信息
     * @param patrol
     * @param convert
     */
    private void uploadPostponeUI(PatrolInfo patrol, ExtensionApplicationConvert convert) {
        if (patrol.getDelayExtensionApplication() != null) {
            binding.panelPostponeInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelPostponeInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getDelayExtensionApplication())));
        }
    }

    /**
     * 更新强制关闭信息
     * @param patrol
     * @param convert
     * @return
     */
    @NotNull
    private ExtensionApplicationConvert updateForceCloseUI(PatrolInfo patrol,ExtensionApplicationConvert convert) {
        if (patrol.getExtensionApplication() != null) {
            binding.panelCloseInfo.getRoot().setVisibility(View.VISIBLE);
            binding.panelCloseInfo.setExt(convert.stringToSomeObject(convert.getGson().toJson(patrol.getExtensionApplication())));
        }
        return convert;
    }

    /**
     * 更新处理结果信息
     * @param patrol
     */
    private void updateHandleResultUI(PatrolInfo patrol) {
        binding.panelHandleInfo.setPatrol(patrol.getData().getZyxcgd());
    }

    /**
     * 更新处理巡查节点信息
     * @param patrol
     */
    private void updateWorkNodesUI(PatrolInfo patrol) {
        List<WorkNode> nodes = viewModel.loadNodes(patrol);
        nodes.add(0, new WorkNode());
        nodesAdapter.addAll(nodes);
    }
}
