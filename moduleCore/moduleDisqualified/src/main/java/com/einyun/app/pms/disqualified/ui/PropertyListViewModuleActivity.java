package com.einyun.app.pms.disqualified.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.disqualified.BR;
import com.einyun.app.pms.disqualified.R;
import com.einyun.app.pms.disqualified.databinding.ActivityDisqualifiedViewModuleBinding;
import com.einyun.app.pms.disqualified.databinding.ActivityPropertyViewModuleBinding;
import com.einyun.app.pms.disqualified.databinding.ItemDisqualifiedListBinding;
import com.einyun.app.pms.disqualified.databinding.ItemPropertyListBinding;
import com.einyun.app.pms.disqualified.model.DisqualifiedItemModel;
import com.einyun.app.pms.disqualified.model.PropertyItemModel;
import com.einyun.app.pms.disqualified.ui.fragment.DisqualifiedViewModuleFragment;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModel;
import com.einyun.app.pms.disqualified.viewmodel.DisqualifiedViewModelFactory;
import com.google.android.material.tabs.TabLayout;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;

import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_HAD_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_DISQUALIFIED_WAIT_FOLLOW;
import static com.einyun.app.common.constants.RouteKey.FRAGMENT_TO_FOLLOW_UP;

@Route(path = RouterUtils.ACTIVITY_PROPERTY)
public class PropertyListViewModuleActivity extends BaseHeadViewModelActivity<ActivityPropertyViewModuleBinding, DisqualifiedViewModel> implements ItemClickListener<PropertyItemModel> {
    RVPageListAdapter<ItemPropertyListBinding, PropertyItemModel> adapter;
    @Override
    protected DisqualifiedViewModel initViewModel() {
        return new ViewModelProvider(this, new DisqualifiedViewModelFactory()).get(DisqualifiedViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_property_view_module;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_disqualified_order);
        setRightOption(R.mipmap.icon_add_blue);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
        });
        binding.list.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        binding.list.setAdapter(adapter);
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("onChanged", "onChanged: "+aBoolean);
            }
        });

    }

    private static final String TAG = "PropertyListViewModuleA";
    @Override
    public void onOptionClick(View view) {
        super.onOptionClick(view);
        Log.e(TAG, "onRightOptionClick: " );
        ARouter.getInstance().build(RouterUtils.ACTIVITY_PROPERTY_CREATE).navigation();
    }
    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        if(adapter==null){
            adapter=new RVPageListAdapter<ItemPropertyListBinding, PropertyItemModel>(this, BR.model,mDiffCallback){
                //                private static final String TAG = "ApprovalViewModelFragme";
                @Override
                public void onBindItem(ItemPropertyListBinding binding, PropertyItemModel itemModule) {

                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_property_list;
                }
            };
        }
        binding.list.setAdapter(adapter);
        adapter.setOnItemClick(this);


    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    @Override
    public void onItemClicked(View veiw, PropertyItemModel data) {

    }
    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<PropertyItemModel> mDiffCallback = new DiffUtil.ItemCallback<PropertyItemModel>() {


        @Override
        public boolean areItemsTheSame(@NonNull PropertyItemModel oldItem, @NonNull PropertyItemModel newItem) {
            return oldItem==newItem;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull PropertyItemModel oldItem, @NonNull PropertyItemModel newItem) {
            return oldItem==newItem;
        }
    };

}
