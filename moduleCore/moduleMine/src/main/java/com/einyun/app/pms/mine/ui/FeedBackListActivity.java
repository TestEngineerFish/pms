package com.einyun.app.pms.mine.ui;


import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.model.PageUIState;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.mdm.model.FeedBackListModel;
import com.einyun.app.pms.mine.BR;
import com.einyun.app.pms.mine.R;
import com.einyun.app.pms.mine.databinding.ActivityFeedBackListBinding;
import com.einyun.app.pms.mine.databinding.ItemFeedBinding;
import com.einyun.app.pms.mine.model.FeedBackModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModel;
import com.einyun.app.pms.mine.viewmodule.SettingViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_FEED_LIST)
public class FeedBackListActivity extends BaseHeadViewModelActivity<ActivityFeedBackListBinding, SettingViewModel> implements ItemClickListener<FeedBackListModel> {
    RVBindingAdapter<ItemFeedBinding, FeedBackListModel> adapter;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_back_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.txt_feed_back);
        setRightTxtColor(R.color.colorPrimary);
//        setRightTxt(R.string.txt_add_feed);
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new RVBindingAdapter<ItemFeedBinding, FeedBackListModel>(this, com.einyun.app.pms.mine.BR.feed) {
            @Override
            public void onBindItem(ItemFeedBinding binding, FeedBackListModel model, int position) {


            }

            @Override
            public int getLayoutId() {
                return R.layout.item_feed;
            }

        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.feedList.setLayoutManager(layoutManager);
        binding.feedList.setAdapter(adapter);
        loadData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClick(this);
        binding.swipeRefresh.setColorSchemeColors(getColorPrimary1());
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefresh.setRefreshing(false);
                loadData();
            }
        });
    }

    @Override
    public void onRightOptionClick(View view) {
        super.onRightOptionClick(view);
        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEED).navigation();
    }

    @Override
    protected SettingViewModel initViewModel() {
        return new ViewModelProvider(this, new SettingViewModelFactory()).get(SettingViewModel.class);
    }

    @Override
    public void onItemClicked(View veiw, FeedBackListModel data) {
        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEED_DETAIL).withString(RouteKey.KEY_FEED_ID,data.getId())
                .navigation();
    }

    private void loadData() {
        viewModel.getFeedBackList(userModuleService.getUserId()).observe(this, data -> {
            if (data == null || data.size() == 0) {
                binding.pageState.setPageState(PageUIState.EMPTY.getState());
                return;
            }
            if (data != null) {
                adapter.setDataList(data);
            }
        });
    }


    /**
     * 获取主题颜色
     * @return
     */
    public int getColorPrimary1(){
        TypedValue typedValue = new  TypedValue();
        this.getTheme().resolveAttribute(com.einyun.app.base.R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
