package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityAddWorthReminderBinding;
import com.einyun.app.pms.toll.databinding.ActivitySetSignBinding;
import com.einyun.app.pms.toll.databinding.UnitCheckPopwindowItemBinding;
import com.einyun.app.pms.toll.model.BuildModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_SET_SIGN)
public class SetSignActivity extends BaseHeadViewModelActivity<ActivitySetSignBinding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    private RVBindingAdapter<UnitCheckPopwindowItemBinding, BuildModel.GridRangeBean> adapter;//外部适配器
    private List<BuildModel.GridRangeBean> mFeeHouseList;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_sign;
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
        mFeeHouseList = new ArrayList<>();
        //一级列表适配器
        adapter = new RVBindingAdapter<UnitCheckPopwindowItemBinding, BuildModel.GridRangeBean>(this, com.einyun.app.pms.toll.BR.check) {

            @Override
            public void onBindItem(UnitCheckPopwindowItemBinding binding, BuildModel.GridRangeBean model, int position) {

                binding.llItem.setOnClickListener(view1 -> {
                    if (model.getChecked()==0) {
                        model.setChecked(1);
                    }else {
                        model.setChecked(0);
                    }
                    adapter.notifyDataSetChanged();
                });
                if (model.getChecked()==1) {
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius19_blue);
                    binding.ivCheck.setVisibility(View.VISIBLE);
                    model.setChecked(1);
                }else {
                    binding.ivCheck.setVisibility(View.GONE);
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blackTextColor));
                    model.setChecked(0);
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius19_grey);
                }
                binding.tvContent.setText(model.getName());

            }

            @Override
            public int getLayoutId() {
                return R.layout.unit_check_popwindow_item;
            }

        };
        adapter.setDataList(mFeeHouseList);
        binding.listHadAdd.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.listHadAdd.setAdapter(adapter);
    }

    /**
     * 提交按钮
     */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {
        }
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
