package com.einyun.app.pms.patrol.ui.fragment;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.pms.patrol.databinding.FragmentPatrolPendingBinding;
import com.einyun.app.pms.patrol.databinding.ItemPatrolListBinding;
import com.einyun.app.pms.patrol.R;
import com.einyun.app.pms.patrol.viewmodel.PatrolListViewModel;
import com.einyun.app.pms.patrol.viewmodel.ViewModelFactory;

/**
 * 巡查待办
 */
public class PatrolPendingFragment extends BaseViewModelFragment<FragmentPatrolPendingBinding, PatrolListViewModel> {
    protected RVPageListAdapter<ItemPatrolListBinding,Patrol> adapter;

    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    protected PatrolPageRequest pageRequest;
    protected String period ;
    protected String status ;
    protected String gridId ;
    protected String buildId ;
    protected String unitId;
    protected String divideId;

    public static PatrolPendingFragment newInstance() {
        return new PatrolPendingFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_patrol_pending;
    }

    @Override
    protected void setUpView() {
        binding.swiperefresh.setOnRefreshListener(() -> {
            binding.swiperefresh.setRefreshing(false);
            adapter.submitList(null);
            viewModel.refresh();
        });
    }

    @Override
    protected void setUpData() {
        createPageRequest();
        initAdapter();
        binding.patrolList.setAdapter(adapter);

    }

    protected void initAdapter() {
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), com.einyun.app.pms.patrol.BR.patrol,mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_list;
                }
            };
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    protected void loadData() {
        viewModel.loadPendingData(pageRequest).observe(getActivity(), patrols -> adapter.submitList(patrols));
    }

    protected void createPageRequest(){
        if(pageRequest==null){
            pageRequest=new PatrolPageRequest();
            pageRequest.setPageSize(PageBean.MAX_PAGE_SIZE);
            pageRequest.setUserId(userModuleService.getUserId());
            pageRequest.setPeriod(period);
            pageRequest.setGridId(gridId);
            pageRequest.setUnitId(unitId);
            pageRequest.setBuildingId(buildId);
            pageRequest.setTimeout(status);
            pageRequest.setF_massif_id(divideId);
        }
    }

    //DiffUtil.ItemCallback,标准写法
    protected DiffUtil.ItemCallback<Patrol> mDiffCallback = new DiffUtil.ItemCallback<Patrol>() {

        @Override
        public boolean areItemsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem.getTaskId() == newItem.getTaskId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Patrol oldItem, @NonNull Patrol newItem) {
            return oldItem==newItem;
        }
    };

    @Override
    protected PatrolListViewModel initViewModel() {
        return new ViewModelProvider(getActivity(),new ViewModelFactory()).get(PatrolListViewModel.class);
    }
}
