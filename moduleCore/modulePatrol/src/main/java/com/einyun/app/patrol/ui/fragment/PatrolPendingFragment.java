package com.einyun.app.patrol.ui.fragment;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.einyun.app.base.BaseViewModelFragment;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.paging.bean.PageBean;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.library.resource.workorder.net.request.PatrolPageRequest;
import com.einyun.app.patrol.BR;
import com.einyun.app.patrol.R;
import com.einyun.app.patrol.databinding.FragmentPatrolPendingBinding;
import com.einyun.app.patrol.databinding.ItemPatrolListBinding;
import com.einyun.app.patrol.viewmodel.PatrolListViewModel;
import com.einyun.app.patrol.viewmodel.ViewModelFactory;

public class PatrolPendingFragment extends BaseViewModelFragment<FragmentPatrolPendingBinding, PatrolListViewModel> {
    RVPageListAdapter<ItemPatrolListBinding,Patrol> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    PatrolPageRequest pageRequest;
    private String period = "";
    private String status = "";
    private String gridId = "";
    private String buildId = "";
    private String unitId = "";
    private String divideId = "";

    public static Fragment newInstance() {
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
            viewModel.refresh();
        });
    }

    @Override
    protected void setUpData() {
        createPageRequest();
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPatrolListBinding, Patrol>(getContext(), BR.patrol,mDiffCallback) {

                @Override
                public void onBindItem(ItemPatrolListBinding binding, Patrol model) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_patrol_list;
                }
            };
        }
        binding.patrolList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadPadingData(pageRequest).observe(getActivity(), patrols -> adapter.submitList(patrols));
    }

    private void createPageRequest(){
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
    private DiffUtil.ItemCallback<Patrol> mDiffCallback = new DiffUtil.ItemCallback<Patrol>() {

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
