package com.einyun.app.pms.sendorder.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.resource.workorder.model.HistoryModel;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityHistroyBinding;
import com.einyun.app.pms.sendorder.databinding.ItemHistoryLayoutBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderDetialViewModel;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_HISTORY)
public class HistroyActivity extends BaseHeadViewModelActivity<ActivityHistroyBinding, SendOrderDetialViewModel> {
    RVBindingAdapter<ItemHistoryLayoutBinding, HistoryModel> adapter;
    private List<HistoryModel> historyModels = new ArrayList<>();
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;

    @Override
    protected SendOrderDetialViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderDetialViewModel.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_histroy;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_histroy);
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemHistoryLayoutBinding, HistoryModel>(this, BR.history) {

                @Override
                public void onBindItem(ItemHistoryLayoutBinding binding, HistoryModel model, int position) {
                    binding.itemHistroyStep.setText(position + 1 + "");
                    if (position == adapter.getDataList().size() - 1) {
                        binding.itemHistroyImg.setVisibility(View.INVISIBLE);
                    } else {
                        binding.itemHistroyImg.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_history_layout;
                }
            };
        }
        adapter.setDataList(historyModels);
        binding.historyRec.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        binding.historyRec.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        super.initData();
        viewModel.getHistory(proInsId).observe(this, modelList -> {
            adapter.setDataList(modelList);
        });
    }
}
