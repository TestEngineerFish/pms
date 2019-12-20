package com.einyun.app.pms.operatepercent.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.SpacesItemDecoration;
import com.einyun.app.pms.operatepercent.BR;
import com.einyun.app.pms.operatepercent.OperatePercentViewModel;
import com.einyun.app.pms.operatepercent.R;
import com.einyun.app.pms.operatepercent.databinding.ActivityAllChargeBinding;
import com.einyun.app.pms.operatepercent.databinding.ActivityOperatePercentBinding;
import com.einyun.app.pms.operatepercent.databinding.ItemTodayGetRankBinding;
import com.einyun.app.pms.operatepercent.model.OperatePercentModel;

import java.util.ArrayList;
import java.util.List;
@Route(path = RouterUtils.ACTIVITY_OPERATE_TODAY_ALL_GET)
public class AllChargeActivity extends BaseHeadViewModelActivity<ActivityAllChargeBinding, OperatePercentViewModel> {

    RVBindingAdapter<ItemTodayGetRankBinding, String> adapter;
    private List<String> strings=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected OperatePercentViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_charge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        for (int i=0;i<7;i++){
            strings.add("1");
        }
        adapter=new RVBindingAdapter<ItemTodayGetRankBinding, String>(this, BR.todayGet) {
            @Override
            public void onBindItem(ItemTodayGetRankBinding binding, String model, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.item_today_get_rank;
            }
        };
        adapter.setDataList(strings);
        layoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        binding.rankList.operateAllGetList.setLayoutManager(layoutManager);
        binding.rankList.operateAllGetList.setAdapter(adapter);
        binding.rankList.operateAllGetList.addItemDecoration(new SpacesItemDecoration(30,0,0,0));

    }
}
