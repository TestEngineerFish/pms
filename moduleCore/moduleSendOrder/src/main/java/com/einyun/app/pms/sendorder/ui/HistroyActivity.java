package com.einyun.app.pms.sendorder.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
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
                    if (map.get(model.getTaskKey()) == null) {
                        binding.taskName.setText(model.getTaskName());
                    } else {
                        binding.taskName.setText(map.get(model.getTaskKey()));
                    }
                    binding.opinonTxt.setText(model.getOpinion());
                    if (model.status.equals("deliverto")) {
                        binding.taskName.setText("??????");
                    } else if (model.status.equals("start_commu")) {
                        binding.taskName.setText("??????");
                    } else if (model.status.equals("feedback")) {
                        binding.taskName.setText("????????????");
                    }else if (model.status.equals("awaiting_feedback")){
                        binding.taskName.setText("????????????");
                    }
                    if (model.status.equals("timeout")) {
                        binding.name.setText("??????");
                        binding.opinonTxt.setText("??????");
                    }
                    if (binding.name.getText().toString().contains("admin")){
                        binding.name.setText(binding.name.getText().toString().replace("admin","??????"));
                    }
                    if (binding.waitName.getText().toString().contains("admin")){
                        binding.waitName.setText(binding.waitName.getText().toString().replace("admin","??????"));
                    }
                    if (model.getOpinion()!=null) {
                        if (model.getOpinion().equals("????????????")) {
                            binding.opinonTxt.setText("????????????");
                        } else {
                            if (model.getOpinion().equals("???????????????????????????")) {
                                binding.opinonTxt.setText("");
                            } else {
                                if (model.getStatusVal().equals("??????") && model.getOpinion().equals("??????")){
                                    binding.opinonTxt.setText("");
                                }else {
                                }
                            }
                        }
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
     * ????????????????????????
     */
    private void deleteNewStep(List<HistoryModel> historyModels) {
        if (historyModels != null && historyModels.size() > 0) {
            int length = historyModels.size();
            for (int i = 0; i < length; i++) {
                if (historyModels.get(i).getTaskKey().contains("StartEvent")||historyModels.get(i).getTaskKey().contains("Timeout_")) {
                    historyModels.remove(i);
                    i--;
                    length--;
                }else {
                    if (historyModels.get(i).getStatus().equals("skip")) {
                        historyModels.get(i).setOpinion("");
                    }
                    if (i == 0) {
                        if (historyModels.get(0).getProcDefId().contains("zyxcgd") || historyModels.get(0).getProcDefId().contains("zyjhgd")) {
                            historyModels.get(0).setOpinion("??????????????????");
                        } else {
                            historyModels.get(0).setOpinion("");
                        }
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????map
     */
    private Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();
        map.put("NewComplain", "????????????");
        map.put("DispatchOrder", "????????????");
        map.put("NewEnquiry", "????????????");
        map.put("NewRepair", "????????????");
        map.put("Confirm", "??????");
        map.put("Response", "??????");
        map.put("RaiseLv21", "??????????????????");
        map.put("Handle", "??????");
        map.put("ProcessOrder", "??????");
        map.put("CheckOrder", "??????");
        map.put("ReturnVisit", "??????");
        map.put("Timeout_In", "??????????????????");
        map.put("RaiseLv1", "????????????(?????????)");
        map.put("RaiseLv2", "????????????(?????????)");
        map.put("RaiseLv3", "????????????(?????????)");
        map.put("Timeout_Out", "??????????????????");
        map.put("ConfirmCateAndAssignOrBrab", "??????");
        map.put("WorkOrderPoolGrab", "??????");
        map.put("OvertimeMandatoryAssign", "????????????");
        map.put("EndEvent", "??????");
        map.put("UserTask6", "??????");
        map.put("UserTask7", "??????");
        map.put("UserTask3", "????????????");
        map.put("waiting", "??????");
        map.put("Waiting", "??????");
        map.put("UserTask4", "??????");
        map.put("UserTask5", "????????????");
        map.put("UserTask1", "??????");
        map.put("EndEvent1", "??????");
        map.put("StartEvent1", "????????????");
        return map;
    }
}
