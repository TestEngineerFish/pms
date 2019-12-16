package com.einyun.app.pms.patrol.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.BasicDataManager;
import com.einyun.app.common.model.BasicData;
import com.einyun.app.common.model.SelectModel;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.widget.ConditionBuilder;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.common.ui.widget.SelectPopUpView;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.model.LineType;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.model.WorkOrderTypeModel;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.patrol.databinding.FragmentPatrolPendingBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.ui.PatrolListActivity;
import com.einyun.app.pms.patrol.viewmodel.PatrolListViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

import java.util.List;
import java.util.Map;

/**
 * 巡查待办
 */
public class PatrolPendingFragment extends BaseViewModelFragment<FragmentPatrolPendingBinding, PatrolListViewModel> implements ItemClickListener<Patrol>, PeriodizationView.OnPeriodSelectListener {
    protected RVPageListAdapter<ItemPatrolListBinding, Patrol> adapter;

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
    }

    @Override
    protected void setUpData() {
        initAdapter();
        binding.swiperefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(binding.patrolList);
        binding.patrolList.setAdapter(adapter);
        loadData();
    }

    protected void initAdapter() {
        if (adapter == null) {
            adapter = new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), com.einyun.app.pms.patrol.BR.patrol, mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {

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
        viewModel.loadPendingData().observe(getActivity(), patrols -> {
            adapter.submitList(patrols);
            adapter.notifyDataSetChanged();
        });
    }


    //DiffUtil.ItemCallback,标准写法
    protected DiffUtil.ItemCallback<Patrol> mDiffCallback = new DiffUtil.ItemCallback<Patrol>() {

        @Override
        public boolean areItemsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem == newItem;
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem.getID_().equals(newItem.getID_());
        }
    };

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.panelCondition.sendWorkOrerTabPeroidLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出分期view
                PeriodizationView periodizationView = new PeriodizationView();
                periodizationView.setPeriodListener(PatrolPendingFragment.this::onPeriodSelectListener);
                periodizationView.show(getParentFragmentManager(), "");
            }
        });
        binding.panelCondition.sendWorkOrerTabSelectLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<LineType> lineTypes = BasicDataManager.getInstance().loadCache().getListLineTypes();
                List<WorkOrderTypeModel> lines = BasicDataManager.getInstance().loadCache().getLines();
                List<ResourceTypeBean> resources = BasicDataManager.getInstance().loadCache().getResources();
                ConditionBuilder builder = new ConditionBuilder();
                List<SelectModel> conditions = builder.addLines(lines)//条线
                        .addItem(SelectPopUpView.SELECT_IS_OVERDUE)//是否超期
                        .mergeLineRes(resources)
                        .addLineTypesItem(lineTypes)
                        .build();
                new SelectPopUpView(getActivity(), conditions).setOnSelectedListener(new SelectPopUpView.OnSelectedListener() {
                    @Override
                    public void onSelected(Map selected) {
                        handleSelect(selected);
                    }
                }).showAsDropDown(binding.panelCondition.sendWorkOrerTabPeroidLn);
            }
        });
    }

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.panelCondition.periodSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        viewModel.request.setDivideId(orgModel.getId());
        binding.panelCondition.periodSelected.setText(orgModel.getName());
        viewModel.requestDone.setDivideId(orgModel.getId());
        viewModel.onCondition();
    }

    /**
     * 处理筛选返回数据
     */
    private void handleSelect(Map selected) {
        if (selected.size() > 0) {
            binding.panelCondition.selectSelected.setTextColor(getResources().getColor(R.color.blueTextColor));
        }
//        viewModel.onConditionSelected(selected);
    }

    @Override
    protected PatrolListViewModel initViewModel() {
        return new ViewModelProvider(getActivity(), new ViewModelFactory()).get(PatrolListViewModel.class);
    }

    @Override
    public void onItemClicked(View veiw, Patrol data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PATROL_HANDLE)
                .withString(RouteKey.KEY_TASK_ID, data.getTaskId())
                .withString(RouteKey.KEY_ORDER_ID, data.getID_())
                .withString(RouteKey.KEY_TASK_NODE_ID, data.getTaskNodeId())
                .withString(RouteKey.KEY_PRO_INS_ID, data.getProInsId())
                .navigation();
    }
}
