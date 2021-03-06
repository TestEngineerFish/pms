package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ScreenUtils;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.PeriodizationNoAutoJumpView;
import com.einyun.app.common.ui.widget.PeriodizationView;
import com.einyun.app.library.dashboard.model.LineOrder;
import com.einyun.app.library.dashboard.model.WorkOrder;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.pms.main.BR;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.Constants;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.ActivityOrderConditionPandectBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTableBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTableLineBinding;
import com.einyun.app.pms.main.databinding.ItemWorkTableNumBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_ORDER_CONDITION_PANDECT)
public class OrderConditionPandectActivity extends BaseHeadViewModelActivity<ActivityOrderConditionPandectBinding, WorkBenchViewModel> implements View.OnClickListener, PeriodizationView.OnPeriodSelectListener {

    private RVBindingAdapter<ItemWorkTableNumBinding, String> adapter;
    private RVBindingAdapter<ItemWorkTableBinding, WorkOrder> tableAdapter;
    private RVBindingAdapter<ItemWorkTableLineBinding, LineOrder> lineAdapter;
    private RVBindingAdapter<ItemWorkTableLineBinding, LineOrder> attitudeAdapter;
    private List<OrgModel> orgModels;
    String orgCodes;
    DecimalFormat formatInt = new DecimalFormat("#,###");
    private String year;
    private String month;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("??????????????????");
        binding.setCallBack(this);
        orgModels = new Gson().fromJson(SPUtils.get(BasicApplication.getInstance(), Constants.SP_KEY_STAGING, "").toString(),
                new TypeToken<List<OrgModel>>() {
                }.getType());

        Calendar c = Calendar.getInstance();
        year = String.valueOf(c.get(Calendar.YEAR));
        month = String.valueOf(c.get(Calendar.MONTH) + 1);
        binding.setConditionSelected(true);
        binding.selectSelected.setText(year + "-" + month);
        orgCodes = "";
        fresh();
    }

    @Override
    public void onClick(View v) {

    }

    private void fresh() {
        viewModel.workOrderData(orgCodes, year, month, userModuleService.getUserId()).observe(this, workOrderData -> {
            if (workOrderData.getRate() != null) {
                //???????????????
                String completedRate = workOrderData.getRate().getCompletedRate();
                binding.tvPercentageComplete.setText(completedRate);
                //???????????????
                String timelyRate = workOrderData.getRate().getTimelyRate();
                binding.tvTimelinessRatio.setText(timelyRate);
            } else {
                binding.tvPercentageComplete.setText("0%");
                binding.tvTimelinessRatio.setText("0%");
            }
            if (workOrderData.getWorkOrder() != null) {
                //????????????
                int num = 0;
                List<Integer> integers = new ArrayList<>();
                for (WorkOrder workOrder : workOrderData.getWorkOrder()) {
                    num += workOrder.getCount();
                    integers.add(workOrder.getCount());
                }
                max = (float) Collections.max(integers) / 4 * 5;
                String format = formatInt.format(num);
                setWorkTablePendingNum(format.toCharArray());
                setWorkTable(workOrderData.getWorkOrder());
            }
            if (workOrderData.getLineOrder() != null) {
                List<Integer> integers = new ArrayList<>();
                for (LineOrder workOrder : workOrderData.getLineOrder()) {
                    integers.add(workOrder.getCompleted_num());
                    integers.add(workOrder.getUnfinished_num());
                }
                lineMax = (float) Collections.max(integers) / 4 * 5;
                setWorkTableLine(workOrderData.getLineOrder());
            }
            //???????????????
            if (workOrderData.getLineatitude() != null) {
                List<Integer> integers = new ArrayList<>();
                for (int i = 0; i < workOrderData.getLineatitude().size(); i++)
                    for (LineOrder workOrder : workOrderData.getLineatitude()) {
                        integers.add(workOrder.getCount());
                    }
                for (LineOrder workOrder : workOrderData.getLineQuality()) {
                    integers.add(workOrder.getCount());
                }
                lineMax = (float) Collections.max(integers) / 4 * 5;
                setAttitudeTableLine(workOrderData.getLineQuality(), workOrderData.getLineatitude());
            }
        });
    }

    Float max = 1F;
    Float lineMax = 1F;

    /**
     * ??????????????????
     *
     * @param orgModels
     * @return
     */
    private List<String> handleStagingData(List<OrgModel> orgModels) {
        List<String> divideCode = new ArrayList<>();
        for (OrgModel orgModel : orgModels) {
            String disabked = orgModel.getDisabled() + "";
            String grade = orgModel.getGrade() + "";
            switch (grade) {
                case "organization_type_divide":
                    if (disabked.equals("1")) {
                        divideCode.add(orgModel.getId());
                    }
                    break;
            }
        }
        return divideCode;
    }


    /**
     * ?????????????????????
     *
     * @param chars
     */
    private void setWorkTablePendingNum(char[] chars) {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemWorkTableNumBinding, String>(this, BR.num) {

                @Override
                public void onBindItem(ItemWorkTableNumBinding binding, String model, int position) {

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table_num;
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

    Float heightOne = 0F;
    Float heightLine = 0F;

    private void setWorkTable(List<WorkOrder> workOrders) {
        List<WorkOrder> orders = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (WorkOrder workOrder2 : workOrders) {
                if (i == 0 && "5".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 1 && "6".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 2 && "4".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 3 && "1".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 4 && "2".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 5 && "3".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
            }
        }

        int width = binding.cvTable.getWidth();
        int llHeightHeight = binding.llHeight.getHeight();
        heightOne = llHeightHeight / max;
        if (tableAdapter == null) {
            tableAdapter = new RVBindingAdapter<ItemWorkTableBinding, WorkOrder>(this, BR.workOrder) {
                @Override
                public void onBindItem(ItemWorkTableBinding binding, WorkOrder model, int position) {
                    ViewGroup.LayoutParams layoutParams = binding.llOrg.getLayoutParams();
                    layoutParams.width = width / 6;
                    binding.llOrg.setLayoutParams(layoutParams);
                    ViewGroup.LayoutParams lineHeightLayoutParams = binding.lineHeight.getLayoutParams();
                    lineHeightLayoutParams.height = Math.round(heightOne * model.getCount());
                    binding.lineHeight.setLayoutParams(lineHeightLayoutParams);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table;
                }
            };
            binding.cvTable.setAdapter(tableAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            binding.cvTable.setLayoutManager(manager);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
            LayoutAnimationController controller = new LayoutAnimationController
                    (animation);
            controller.setDelay(0.5f);
            controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
            binding.cvTable.setLayoutAnimation(controller);
        }
        tableAdapter.setDataList(orders);
    }

    private void setWorkTableLine(List<LineOrder> lineOrders) {
        List<LineOrder> orders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (LineOrder workOrder2 : lineOrders) {
                if (i == 0 && "1".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 1 && "3".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 2 && "4".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
                if (i == 3 && "2".equals(workOrder2.getType())) {
                    orders.add(workOrder2);
                }
            }
        }
        int width = binding.cvLineTable.getWidth();
        int llHeightHeight = binding.llLineHeight.getHeight();
        heightLine = llHeightHeight / lineMax;
        if (lineAdapter == null) {
            lineAdapter = new RVBindingAdapter<ItemWorkTableLineBinding, LineOrder>(this, BR.lineOrder) {
                @Override
                public void onBindItem(ItemWorkTableLineBinding binding, LineOrder model, int position) {
                    ViewGroup.LayoutParams layoutParams = binding.llOrg.getLayoutParams();
                    layoutParams.width = width / 4;
                    binding.llOrg.setLayoutParams(layoutParams);
                    ViewGroup.LayoutParams lineHeightLayoutParams = binding.lineHeight.getLayoutParams();
                    lineHeightLayoutParams.height = Math.round(heightLine * model.getCompleted_num());
                    binding.lineHeight.setLayoutParams(lineHeightLayoutParams);
                    ViewGroup.LayoutParams lineHeightUnLayoutParams = binding.lineHeightUn.getLayoutParams();
                    lineHeightUnLayoutParams.height = Math.round(heightLine * model.getUnfinished_num());
                    binding.lineHeightUn.setLayoutParams(lineHeightUnLayoutParams);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_work_table_line;
                }
            };
            binding.cvLineTable.setAdapter(lineAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            binding.cvLineTable.setLayoutManager(manager);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
            LayoutAnimationController controller = new LayoutAnimationController
                    (animation);
            controller.setDelay(0.5f);
            controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
            binding.cvLineTable.setLayoutAnimation(controller);
        }
        lineAdapter.setDataList(orders);
    }

    private void setAttitudeTableLine(List<LineOrder> lineOrders, List<LineOrder> lineOrders1) {
        List<LineOrder> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (LineOrder workOrder2 : lineOrders) {
                if (i == 0 && "1.0".equals(workOrder2.getType())) {
                    workOrder2.setCompleted_num(workOrder2.getCount());
                    orders.add(workOrder2);

                }
                if (i == 1 && "2.0".equals(workOrder2.getType())) {
                    workOrder2.setCompleted_num(workOrder2.getCount());
                    orders.add(workOrder2);
                }
                if (i == 2 && "3.0".equals(workOrder2.getType())) {
                    workOrder2.setCompleted_num(workOrder2.getCount());
                    orders.add(workOrder2);
                }
                if (i == 3 && "4.0".equals(workOrder2.getType())) {
                    workOrder2.setCompleted_num(workOrder2.getCount());
                    orders.add(workOrder2);
                }
                if (i == 4 && "5.0".equals(workOrder2.getType())) {
                    workOrder2.setCompleted_num(workOrder2.getCount());
                    orders.add(workOrder2);
                }
            }
            }
        for (int j=0;j<5;j++){
        for (LineOrder workOrder3 : lineOrders1) {
            if (j == 0 && "1.0".equals(workOrder3.getType())) {
                workOrder3.setUnfinished_num(workOrder3.getCount());
                orders.get(j).setUnfinished_num(workOrder3.getCount());
            }
            if (j == 1 && "2.0".equals(workOrder3.getType())) {
                orders.get(j).setUnfinished_num(workOrder3.getCount());
            }
            if (j == 2 && "3.0".equals(workOrder3.getType())) {
                orders.get(j).setUnfinished_num(workOrder3.getCount());
            }
            if (j == 3 && "4.0".equals(workOrder3.getType())) {
                orders.get(j).setUnfinished_num(workOrder3.getCount());
            }
            if (j == 4 && "5.0".equals(workOrder3.getType())) {
                orders.get(j).setUnfinished_num(workOrder3.getCount());
            }
        }
            int width = binding.cvLineTable1.getWidth();
            int llHeightHeight = binding.llLineHeight.getHeight();
            heightLine = llHeightHeight / lineMax;
            if (attitudeAdapter == null) {
                attitudeAdapter = new RVBindingAdapter<ItemWorkTableLineBinding, LineOrder>(this, BR.lineOrder) {
                    @Override
                    public void onBindItem(ItemWorkTableLineBinding binding, LineOrder model, int position) {
                        ViewGroup.LayoutParams layoutParams = binding.llOrg.getLayoutParams();
                        layoutParams.width = width / 4;
                        binding.llOrg.setLayoutParams(layoutParams);
                        ViewGroup.LayoutParams lineHeightLayoutParams = binding.lineHeight.getLayoutParams();
                        lineHeightLayoutParams.height = Math.round(heightLine * model.getCompleted_num());
                        binding.lineHeight.setLayoutParams(lineHeightLayoutParams);
                        ViewGroup.LayoutParams lineHeightUnLayoutParams = binding.lineHeightUn.getLayoutParams();
                        lineHeightUnLayoutParams.height = Math.round(heightLine * model.getUnfinished_num());
                        binding.lineHeightUn.setLayoutParams(lineHeightUnLayoutParams);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_work_table_line;
                    }
                };
                binding.cvLineTable1.setAdapter(attitudeAdapter);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                manager.setOrientation(RecyclerView.HORIZONTAL);
                binding.cvLineTable1.setLayoutManager(manager);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
                LayoutAnimationController controller = new LayoutAnimationController
                        (animation);
                controller.setDelay(0.5f);
                controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
                binding.cvLineTable1.setLayoutAnimation(controller);
            }
            attitudeAdapter.setDataList(orders);
        }
    }
        TimePickerView pvTime;

        public void selectTime () {
            if (pvTime == null) {
                //???????????????
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        binding.setConditionSelected(true);
                        year = String.valueOf(date.getYear() + 1900);
                        month = String.valueOf(date.getMonth() + 1);
                        binding.selectSelected.setText(year + "-" + month);
                        fresh();
                    }
                }).setType(new boolean[]{true, true, false, false, false, false})// ??????????????????
                        .setRangDate(null, Calendar.getInstance())
                        .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                        .build();
            }

            pvTime.show();
        }

        PeriodizationNoAutoJumpView periodizationView;

        public void selectOrgCodes () {

            //????????????view
            periodizationView = new PeriodizationNoAutoJumpView();
            periodizationView.setPeriodListener(OrderConditionPandectActivity.this::onPeriodSelectListener);
            periodizationView.show(getSupportFragmentManager(), "");
        }

        @Override
        protected WorkBenchViewModel initViewModel () {
            return new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);
        }

        @Autowired(name = RouterUtils.SERVICE_USER)
        IUserModuleService userModuleService;

        @Override
        public int getLayoutId () {
            return R.layout.activity_order_condition_pandect;
        }

        @Override
        protected void initListener () {
            super.initListener();
        }

        @Override
        public void onPeriodSelectListener (OrgModel orgModel){
            binding.periodSelected.setText(orgModel.getName());
            binding.setPeriodSelected(true);
            orgCodes = orgModel.getId();
            fresh();
        }
    }
