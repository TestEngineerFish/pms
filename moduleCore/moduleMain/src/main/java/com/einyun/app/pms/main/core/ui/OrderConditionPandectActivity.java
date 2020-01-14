package com.einyun.app.pms.main.core.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.base.util.ScreenUtils;
import com.einyun.app.common.service.RouterUtils;
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
    private List<OrgModel> orgModels;
    List<String> orgCodes;
    DecimalFormat formatInt = new DecimalFormat("#,###");
    private String year;
    private String month;

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle(R.string.title_order_condition_pandect);
        binding.setCallBack(this);
        orgModels = new Gson().fromJson(SPUtils.get(BasicApplication.getInstance(), Constants.SP_KEY_STAGING, "").toString(),
                new TypeToken<List<OrgModel>>() {
                }.getType());

        Calendar c = Calendar.getInstance();
        year = String.valueOf(c.get(Calendar.YEAR));
        month = String.valueOf(c.get(Calendar.MONTH) + 1);
        orgCodes = handleStagingData(orgModels);
        fresh();
    }

    @Override
    public void onClick(View v) {

    }

    private void fresh() {
        viewModel.workOrderData(orgCodes, year, month).observe(this, workOrderData -> {
            if (workOrderData.getRate() != null) {
                //工单完成率
                String completedRate = workOrderData.getRate().getCompletedRate();
                binding.tvPercentageComplete.setText(completedRate);
                //工单及时率
                String timelyRate = workOrderData.getRate().getTimelyRate();
                binding.tvTimelinessRatio.setText(timelyRate);
            } else {
                binding.tvPercentageComplete.setText("0%");
                binding.tvTimelinessRatio.setText("0%");
            }
            if (workOrderData.getWorkOrder() != null) {
                //总单总数
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
        });
    }

    Float max = 1F;
    Float lineMax = 1F;

    /**
     * 处理分期数据
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
     * 设置待处理数字
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
        tableAdapter.setDataList(workOrders);
    }

    private void setWorkTableLine(List<LineOrder> lineOrders) {
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
        lineAdapter.setDataList(lineOrders);
    }

    TimePickerView pvTime;

    public void selectTime() {
        if (pvTime == null) {
            //时间选择器
            pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    binding.setConditionSelected(true);
                    year = String.valueOf(date.getYear() + 1900);
                    month = String.valueOf(date.getMonth() + 1);
                    fresh();
                }
            }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                    .setRangDate(null, Calendar.getInstance())
                    .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                    .build();
        }

        pvTime.show();
    }

    PeriodizationNoAutoJumpView periodizationView;

    public void selectOrgCodes() {
        if (periodizationView == null) {
            //弹出分期view
            periodizationView = new PeriodizationNoAutoJumpView();
            periodizationView.setPeriodListener(OrderConditionPandectActivity.this::onPeriodSelectListener);
        }
        periodizationView.show(getSupportFragmentManager(), "");
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

    @Override
    public void onPeriodSelectListener(OrgModel orgModel) {
        binding.periodSelected.setText(orgModel.getName());
        binding.setPeriodSelected(true);
        orgCodes = Arrays.asList(orgModel.getId());
        fresh();
    }
}
