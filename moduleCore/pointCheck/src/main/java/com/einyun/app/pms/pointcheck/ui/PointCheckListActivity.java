package com.einyun.app.pms.pointcheck.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.pointcheck.BR;
import com.einyun.app.pms.pointcheck.R;
import com.einyun.app.pms.pointcheck.databinding.ActivityPointCheckListBinding;
import com.einyun.app.pms.pointcheck.databinding.ItemPointCheckBriefBinding;
import com.einyun.app.pms.pointcheck.model.CheckPointModel;
import com.einyun.app.pms.pointcheck.viewmodel.PointCheckListViewModel;
import com.einyun.app.pms.pointcheck.viewmodel.ViewModelFactory;

/**
 * @ProjectName: pms_old
 * @Package: com.einyun.app.pms.pointcheck.ui
 * @ClassName: PointCheckListActivity
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/09 11:41
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/09 11:41
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
@Route(path = RouterUtils.ACTIVITY_POINT_CHECK_ACTIVITY)
public class PointCheckListActivity extends BaseHeadViewModelActivity<ActivityPointCheckListBinding,PointCheckListViewModel> implements ItemClickListener<CheckPointModel> {

    boolean hasInit=false;
    RVPageListAdapter<ItemPointCheckBriefBinding,CheckPointModel> adapter;
    @Override
    protected PointCheckListViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(PointCheckListViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_point_check_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hasInit){
            viewModel.refresh();
        }
        hasInit=true;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_point_check_list);
        binding.headBar.ivRightOption.setVisibility(View.VISIBLE);
        binding.headBar.ivRightOption.setImageResource(R.mipmap.icon_add);
        binding.swipeRefresh.setColorSchemeColors(getColorPrimary());
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            viewModel.refresh();
        });
        binding.checkPointList.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        binding.checkPointList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPointCheckBriefBinding, CheckPointModel>(this, BR.checkpoint,mDiffCallback){

                @Override
                public void onBindItem(ItemPointCheckBriefBinding binding, CheckPointModel checkPointModel) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_point_check_brief;
                }
            };
        }
        binding.checkPointList.setAdapter(adapter);
        adapter.setOnItemClick(this);
        loadPagingData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        //加号->新增点检调整
        binding.headBar.ivRightOption.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterUtils.ACTIVITY_POINT_CHECK_CREATE)
                    .navigation();
        });
    }

    private void loadPagingData(){
        //初始化数据，LiveData自动感知，刷新页面
        viewModel.loadPadingData().observe(this, dataBeans -> adapter.submitList(dataBeans));
    }



    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<CheckPointModel> mDiffCallback = new DiffUtil.ItemCallback<CheckPointModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull CheckPointModel oldItem, @NonNull CheckPointModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CheckPointModel oldItem, @NonNull CheckPointModel newItem) {
            return oldItem==newItem;
        }
    };

    /**
     * 列表Item点击事件，跳转详细
     * @param veiw
     * @param data
     */
    @Override
    public void onItemClicked(View veiw, CheckPointModel data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_POINT_CHECK_DETIAL)
                .withString(RouteKey.KEY_TASK_ID,data.getId())
                .navigation();
    }
}
