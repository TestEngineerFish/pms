package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityFeeBinding;
import com.einyun.app.pms.toll.databinding.ActivityFeeSucBinding;
import com.einyun.app.pms.toll.databinding.ItemFeeSucRecordBinding;
import com.einyun.app.pms.toll.databinding.ItemPaymentAdvanceListBinding;
import com.einyun.app.pms.toll.model.FeeSucInfoModel;
import com.einyun.app.pms.toll.model.PaymentAdvanceModel2;
import com.einyun.app.pms.toll.model.QueryFeedDetailsInfoRequest;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.einyun.app.pms.toll.widget.InputMoneyPopWindow;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Route(path = RouterUtils.ACTIVITY_FEE_SUC)
public class FeeSucActivity extends BaseHeadViewModelActivity<ActivityFeeSucBinding, TollViewModel> {
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String  orderId;
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String  divideName;
    @Autowired(name = RouteKey.HOUSE_TITLE)
    String  title;
    @Autowired(name = RouteKey.KEY_ALL_NAME)
    String  allName;
    RVBindingAdapter<ItemFeeSucRecordBinding, FeeSucInfoModel.DataBean.PaymentListBean> adapter;
    private ArrayList<FeeSucInfoModel.DataBean.PaymentListBean> lists;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fee_suc;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle("收费成功");

        binding.tvHouseName.setText(allName);

        QueryFeedDetailsInfoRequest queryFeedDetailsInfoRequest = new QueryFeedDetailsInfoRequest();
//        queryFeedDetailsInfoRequest.setId(orderId+"");
        queryFeedDetailsInfoRequest.setId(orderId);
        viewModel.queryFeedInfDetails(queryFeedDetailsInfoRequest).observe(this,model->{
            adapter.setDataList(model.getData().getPaymentList());
        });


    }

    @Override
    protected void initData() {
        super.initData();
//        lists = new ArrayList<>();
//        lists.add(new FeeSucInfoModel.DataBean.PaymentListBean());
        initAdapter();


    }
    private void initAdapter() {
        if(adapter==null){
            adapter=new RVBindingAdapter<ItemFeeSucRecordBinding, FeeSucInfoModel.DataBean.PaymentListBean>(this, com.einyun.app.pms.toll.BR.payadvance){

                @Override
                public void onBindItem(ItemFeeSucRecordBinding listBinding, FeeSucInfoModel.DataBean.PaymentListBean item, int position) {

                    listBinding.tvFeeItemName.setText(item.getFeeItemName());
                    listBinding.tvMoney.setText(item.getPaidMoney().setScale(2)+"");
                    listBinding.tvTime.setText("已缴费至："+getYMdTimeDot(item.getPayTime()));

                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_fee_suc_record;
                }
            };
        }
        binding.feeList.setLayoutManager(new LinearLayoutManager(FeeSucActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.feeList.setFocusable(false);
        binding.feeList.setAdapter(adapter);

    }
    public static String getYMdTimeDot(long time) {
        if (time == 0L) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            Date now = new Date(time);
            return format.format(now);
        }
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
