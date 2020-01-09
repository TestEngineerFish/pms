package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.main.BR;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.HomeTabViewModel;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.ActivityOrderConditionPandectBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTablePendingNumBinding;

import java.util.ArrayList;

@Route(path = RouterUtils.ACTIVITY_ORDER_CONDITION_PANDECT)
public class OrderConditionPandectActivity extends BaseHeadViewModelActivity<ActivityOrderConditionPandectBinding, WorkBenchViewModel> implements View.OnClickListener {

    private RVBindingAdapter<ItemWorkTablePendingNumBinding, String> adapter;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_order_condition_pandect);
        binding.setCallBack(this);
        fresh();
    }

    @Override
    public void onClick(View v) {

    }

    private void fresh(){

    }

    /**
     * 设置待处理数字
     *
     * @param chars
     */
    private void setWorkTablePendingNum(char[] chars) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemWorkTablePendingNumBinding, String>(this, com.einyun.app.pms.main.BR.num) {

                @Override
                public void onBindItem(ItemWorkTablePendingNumBinding binding, String model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table_pending_num;
                }
            };
            binding.rvWorkTablePendingNum.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            binding.rvWorkTablePendingNum.setLayoutManager(manager);
        }
        ArrayList<String> nums = new ArrayList<>();
        for (char ch : chars) {
            nums.add(String.valueOf(ch));
        }
        adapter.setDataList(nums);
    }


    @Override
    protected WorkBenchViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_condition_pandect;
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
