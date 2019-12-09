package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.common.constants.DataConstants;
import com.einyun.app.common.databinding.ItemBlockChooseBinding;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.sendorder.BR;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityHistroyBinding;
import com.einyun.app.pms.sendorder.databinding.ActivitySendOrderBinding;
import com.einyun.app.pms.sendorder.databinding.ItemHistoryLayoutBinding;
import com.einyun.app.pms.sendorder.model.HistoryModel;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_HISTORY)
public class HistroyActivity extends BaseHeadViewModelActivity<ActivityHistroyBinding, SendOrderViewModel> {
    RVBindingAdapter<ItemHistoryLayoutBinding, HistoryModel> adapter;
    private List<HistoryModel> historyModels=new ArrayList<>();
    @Override
    protected SendOrderViewModel initViewModel() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_histroy;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.text_histroy);
        for (int i=0;i<3;i++){
            historyModels.add(new HistoryModel());
        }
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemHistoryLayoutBinding, HistoryModel>(this, BR.history) {

                @Override
                public void onBindItem(ItemHistoryLayoutBinding binding, HistoryModel model, int position) {

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

    }
}
