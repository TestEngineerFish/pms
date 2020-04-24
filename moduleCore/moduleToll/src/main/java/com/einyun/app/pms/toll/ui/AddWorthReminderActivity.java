package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.widget.BottomPicker;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityAddHouserBinding;
import com.einyun.app.pms.toll.databinding.ActivityAddWorthReminderBinding;
import com.einyun.app.pms.toll.model.AddHouserRequset;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.GetNameRequset;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_ADD_WORTH_REMINDER)
public class AddWorthReminderActivity extends BaseHeadViewModelActivity<ActivityAddWorthReminderBinding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    private String format = "";
    private String formatDate;


    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_worth_reminder;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_add_worth_reminder);
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        formatDate=FormatUtil.getStringDate();
        binding.tvDate.setText(TimeUtil.getYMdTime(System.currentTimeMillis()).replace("-", "/"));

    }

    /**
     * 提交按钮
     */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {
            if (StringUtil.isEmpty(binding.etLimitInput.getString())) {
                ToastUtil.show(this,"请输入催缴说明");
                return;
            }
            ArrayList<String> builds = new ArrayList<>();
            builds.add(houseId);
            FeeRequset feeRequset = new FeeRequset();
            feeRequset.setDivideId(divideId);
            feeRequset.setHouseIdS(builds);
            feeRequset.setOperateType(1);
            feeRequset.setOperateRemark(binding.etLimitInput.getString());
            feeRequset.setOperateTime(formatDate);
            viewModel.allWorth(feeRequset).observe(this, model -> {



                if (model.getCode()==0) {
                    finish();
                }
            });
        }
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
    /**
     * 选择迁入日期
     */
    public void onChioceDateClick() {

        choosePayDate();
    }

    /**
     * 日期选择
     */
    private void choosePayDate() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy/MM/dd");
                binding.tvDate.setText(dft.format(date));
                formatDate = FormatUtil.dateToStrLong(date);


            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .setRangDate(startDate,endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .build();
        pvTime.show();
    }
}
