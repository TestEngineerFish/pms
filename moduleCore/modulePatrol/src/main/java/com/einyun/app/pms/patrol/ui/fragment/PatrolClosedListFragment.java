package com.einyun.app.pms.patrol.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.ListType;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;

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
    }

    protected void loadData() {
        viewModel.loadCloseData().observe(getActivity(), patrols -> {
            adapter.submitList(patrols);
            adapter.notifyDataSetChanged();
        });
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
