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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(path = RouterUtils.ACTIVITY_HISTORY)
public class HistroyActivity extends BaseHeadViewModelActivity<ActivityHistroyBinding, SendOrderDetialViewModel> {
    RVBindingAdapter<ItemHistoryLayoutBinding, HistoryModel> adapter;
    private List<HistoryModel> historyModels = new ArrayList<>();
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_PRO_INS_ID)
    String proInsId;
    private Map<String, String> map;

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
                    binding.taskName.setText(map.get(model.getTaskKey()));
                    binding.opinonTxt.setText(model.getOpinion());
                    if (model.status.equals("deliverto")) {
                        binding.taskName.setText("转单");
                    } else if (model.status.equals("start_commu")) {
                        binding.taskName.setText("沟通");
                    } else if (model.status.equals("feedback")) {
                        binding.taskName.setText("沟通反馈");
                    }
                    if (model.status.equals("timeout")){
                        binding.opinonTxt.setText("系统");
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_history_layout;
                }
            };
        }
        binding.historyRec.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));
        binding.historyRec.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        super.initData();
        map = initMap();
        viewModel.getHistory(proInsId).observe(this, modelList -> {
            this.historyModels = modelList;
            deleteNewStep(historyModels);
            adapter.setDataList(historyModels);
        });
    }

    /**
     * 提出新建节点步骤
     */
    private void deleteNewStep(List<HistoryModel> historyModels) {
        if (historyModels != null && historyModels.size() > 0) {
            int length=historyModels.size();
            for (int i=0;i<length;i++){
                if (historyModels.get(i).getTaskKey().contains("StartEvent")) {
                    historyModels.remove(i);
                    i--;
                    length--;
                }
            }
        }
    }

    /**
     * 初始化任务节点map
     */
    private Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();
        map.put("NewComplain", "创建工单");
        map.put("DispatchOrder", "创建工单");
        map.put("NewEnquiry", "创建工单");
        map.put("NewRepair", "创建工单");
        map.put("Confirm", "派单");
        map.put("Response", "响应");
        map.put("RaiseLv21", "响应超时批复");
        map.put("Handle", "处理");
        map.put("ProcessOrder", "处理");
        map.put("CheckOrder", "验收");
        map.put("ReturnVisit", "评价");
        map.put("Timeout_In", "进入超时升级");
        map.put("RaiseLv1", "处理超时(第一次)");
        map.put("RaiseLv2", "处理超时(第二次)");
        map.put("RaiseLv3", "处理超时(第三次)");
        map.put("Timeout_Out", "超时升级返回");
        map.put("ConfirmCateAndAssignOrBrab", "派单");
        map.put("WorkOrderPoolGrab", "待抢单");
        map.put("OvertimeMandatoryAssign", "强制派单");
        map.put("EndEvent", "结束");
        map.put("UserTask6", "批复");
        map.put("UserTask7", "批复");
        map.put("UserTask3", "创建工单");
        map.put("waiting", "等待");
        map.put("Waiting", "等待");
        map.put("UserTask4", "接单");
        map.put("UserTask5", "强制派单");
        map.put("UserTask1", "处理");
        map.put("EndEvent1", "结束");
        map.put("StartEvent1", "新建节点");
        return map;
    }
}
