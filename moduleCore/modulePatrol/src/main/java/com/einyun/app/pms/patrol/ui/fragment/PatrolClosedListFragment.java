package com.einyun.app.pms.patrol.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.library.mdm.model.DivideGrid;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;
import java.util.Map;

/**
 * 巡查工单已办列表
 */
public class PatrolClosedListFragment extends PatrolPendingFragment implements ItemClickListener<Patrol> {
    protected int listType= ListType.DONE.getType();
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    public static PatrolClosedListFragment newInstance() {
        return new PatrolClosedListFragment();
    }


    @Override
    protected void setUpView() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            binding.swiperefresh.setRefreshing(false);
            viewModel.refreshClosedList();
        });
        binding.panelCondition.search.setVisibility(View.VISIBLE);
        binding.panelCondition.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    protected void loadData() {
        showLoading(getActivity());
        viewModel.loadCloseData().observe(getActivity(), patrols -> {
            if(patrols.size()==0){
                updatePageUIState(PageUIState.EMPTY.getState());
            }else{
                updatePageUIState(PageUIState.FILLDATA.getState());
            }
            hideLoading();
            adapter.submitList(patrols);
            adapter.notifyDataSetChanged();
        });
        /**
         * 刷新列表
         */
        LiveEventBus.get(LiveDataBusKey.POST_PATROL_CLOSED_REFRESH,Boolean.class).observe(this, flag -> {
            viewModel.refreshClosedList();
        });
    }


    /**
     * 处理筛选返回数据
     */
    protected void handleSelect(Map selected) {
        if (selected.size() > 0) {
            binding.panelCondition.selectSelected.setTextColor(getResources().getColor(R.color.blueConditionColor));
        }
        wrapCondition(selected,viewModel.requestDone);
        viewModel.onCondition();
    }

    protected void initAdapter() {
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), com.einyun.app.pms.patrol.BR.patrol,mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {
                    binding.itemCache.setVisibility(View.GONE);
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
    public void onItemClicked(View veiw, Patrol data) {
        if(!TextUtils.isEmpty(data.getF_patrol_line_id())){
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_TIME_DETIAL)
                    .withString(RouteKey.KEY_TASK_ID,data.getTaskId())
                    .withString(RouteKey.KEY_ORDER_ID,data.getID_())
                    .withInt(RouteKey.KEY_LIST_TYPE,listType)
                    .withString(RouteKey.KEY_TASK_NODE_ID,data.getTaskNodeId())
                    .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                    .navigation();
        }else{
            ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_DETIAL)
                    .withString(RouteKey.KEY_TASK_ID,data.getTaskId())
                    .withString(RouteKey.KEY_ORDER_ID,data.getID_())
                    .withInt(RouteKey.KEY_LIST_TYPE,listType)
                    .withString(RouteKey.KEY_TASK_NODE_ID,data.getTaskNodeId())
                    .withString(RouteKey.KEY_PRO_INS_ID,data.getProInsId())
                    .navigation();
        }

    }
}
