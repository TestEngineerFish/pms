package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityPaymentAdvanceBinding;
import com.einyun.app.pms.toll.databinding.ActivityTollViewModelBinding;
import com.einyun.app.pms.toll.databinding.ItemLackInListBinding;
import com.einyun.app.pms.toll.databinding.ItemLackOutListBinding;
import com.einyun.app.pms.toll.databinding.ItemPaymentAdvanceListBinding;
import com.einyun.app.pms.toll.model.CreateOrderModel;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.JumpAdvanceModel;
import com.einyun.app.pms.toll.model.JumpAdvanceRequset;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.PaymentAdvanceModel;
import com.einyun.app.pms.toll.model.PaymentAdvanceModel2;
import com.einyun.app.pms.toll.model.TollModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.einyun.app.pms.toll.widget.InputMoneyPopWindow;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Route(path = RouterUtils.ACTIVITY_PAYMENT_ADVANCE)
public class PaymentAdvanceActivity extends BaseHeadViewModelActivity<ActivityPaymentAdvanceBinding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_TITLE)
    String title;
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String divideName;
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.HOUSE_FEE_ID)
    String houseFeeId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.NAME)
    String houseName;
    RVBindingAdapter<ItemPaymentAdvanceListBinding, PaymentAdvanceModel2.DataBean.DataListBean> adapter;
    private String mAdvanceMoney;
    private int mPosition;
    //    private List<PaymentAdvanceModel.DataBean> mData;
    private List<PaymentAdvanceModel2.DataBean.DataListBean> mData = new ArrayList<>();
    private ArrayList<CreateOrderRequest.PaymentDetailsBean> paymentDetailsBeans = new ArrayList<>();
    ;
    private String clientName = "";

    //    public  static PaymentAdvanceActivity instance;
    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_advance;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle("预存收费");
        binding.setCallBack(this);

//        instance=this;
        binding.tvName.setText(divideName + title);
        /**
         * 付款成功关闭页面
         */
        FeeSucFinish();
        viewModel.repository.getFeeDivideId(houseFeeId,"3/", new CallBack<String>() {
            @Override
            public void call(String data) {
                hideLoading();
                houseFeeId=data;
            }

            @Override
            public void onFaild(Throwable throwable) {
                hideLoading();
            }
        });

    }

    private void FeeSucFinish() {
        LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                finish();
            }
        });
    }

    /**
     * 收费
     */
    ArrayList<String> feeItemIds = new ArrayList<>();

    public void onFeeClick() {
        feeItemIds.clear();
        JumpAdvanceRequset jumpAdvanceRequset = new JumpAdvanceRequset();
        jumpAdvanceRequset.setDivideId(divideId);
        jumpAdvanceRequset.setHouseId(houseFeeId);
//        jumpAdvanceRequset.setDivideId("7");
//        jumpAdvanceRequset.setHouseId("0713ae7f-c17a-42f4-b65a-81919a018ef7");
        for (PaymentAdvanceModel2.DataBean.DataListBean datum : mData) {
            if (datum.isCheck()) {
                feeItemIds.add(datum.getFeeItemId());
            }
        }
        jumpAdvanceRequset.setFeeItemIds(feeItemIds);
        if (feeItemIds.size() == 0) {
            ToastUtil.show(this, "请选择收费项");
            return;
        }
        viewModel.jumpAdvanceVerify(jumpAdvanceRequset).observe(this, model -> {

            String respone = new Gson().toJson(model);
            JSONObject jsonObject = JSON.parseObject(respone);
            String data = jsonObject.getString("data");
            if (data == null) {
                return;
            }
            if (data.equals("{}")) {
                return;
            }
            JSONObject jsondata = JSON.parseObject(data);

            try {
                for (String feeItemId : feeItemIds) {
                    if (jsondata.containsKey(feeItemId)) {
                        boolean token = jsondata.getBoolean(feeItemId);
                        if (!token) {
                            ToastUtil.show(PaymentAdvanceActivity.this, "你有欠费");

                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            paymentDetailsBeans.clear();
            CreateOrderRequest createOrderRequest = new CreateOrderRequest();
            createOrderRequest.setDivideId(divideId);
            createOrderRequest.setHouseId(houseFeeId);
            createOrderRequest.setHouseName(houseName);
            createOrderRequest.setUserId(viewModel.getUserId());
            createOrderRequest.setPayOrderType("pty-102");
            createOrderRequest.setPayAmount(Double.valueOf(binding.tvToallMoney.getText().toString()));
//                createOrderRequest.setPayAmount(0.01);
            for (PaymentAdvanceModel2.DataBean.DataListBean paymentList : mData) {
                if (paymentList.isCheck()) {
                    CreateOrderRequest.PaymentDetailsBean paymentDetailsBean = new CreateOrderRequest.PaymentDetailsBean();
                    paymentDetailsBean.setChargeAmount(paymentList.getChargeAmount());
//                            paymentDetailsBean.setChargeAmount(new BigDecimal("0.01"));
                    paymentDetailsBean.setChargeReceivableId(paymentList.getFeeItemId());
                    paymentDetailsBean.setChargeTypeCode(paymentList.getFeeIAttribute());
                    paymentDetailsBeans.add(paymentDetailsBean);
                }


            }
            createOrderRequest.setPaymentDetails(paymentDetailsBeans);
//            ToastUtil.show(PaymentAdvanceActivity.this,"没有欠费");

            viewModel.repository.createOrder(createOrderRequest, new CallBack<CreateOrderModel>() {
                @Override
                public void call(CreateOrderModel data) {
                    hideLoading();
                    if (data.getData() != 0) {//创建订单成功  跳转二维码界面生成二维码

                        Log.e(TAG, "checkJumpVerity: " + data.getData());
                        Log.e(TAG, "checkJumpVerity:money====  " + binding.tvToallMoney.getText().toString());
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE)
                                .withInt(RouteKey.KEY_ORDER_ID, data.getData())
                                .withString("MONEY", binding.tvToallMoney.getText().toString())
                                .withString(RouteKey.KEY_DIVIDE_NAME, divideName)
                                .withString(RouteKey.KEY_ALL_NAME, binding.tvName.getText().toString())
                                .withString(RouteKey.KEY_CLIENT_NAME, allName)
                                .withString(RouteKey.HOUSE_TITLE, title)
                                .navigation();
//                    finish();
                    }
                }

                @Override
                public void onFaild(Throwable throwable) {
                    hideLoading();
                }
            });
//            viewModel.createOrder(createOrderRequest).observe(this, orderModel -> {
//                if (orderModel.getData() != 0) {//创建订单成功  跳转二维码界面生成二维码
//
//                    Log.e(TAG, "checkJumpVerity: " + orderModel.getData());
//                    Log.e(TAG, "checkJumpVerity:money====  " + binding.tvToallMoney.getText().toString());
//                    ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE)
//                            .withInt(RouteKey.KEY_ORDER_ID, orderModel.getData())
//                            .withString("MONEY", binding.tvToallMoney.getText().toString())
//                            .withString(RouteKey.KEY_DIVIDE_NAME, divideName)
//                            .withString(RouteKey.KEY_ALL_NAME, binding.tvName.getText().toString())
//                            .withString(RouteKey.KEY_CLIENT_NAME, allName)
//                            .withString(RouteKey.HOUSE_TITLE, title)
//                            .navigation();
////                    finish();
//                }
//            });


        });


    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "initData: houseId" + houseId);
        Log.e(TAG, "initData: divideId" + divideId);
        refreData();

        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreData();
    }

    private void refreData() {
        FeeDetailRequset feeDetailRequset = new FeeDetailRequset();
        feeDetailRequset.setDivideId(divideId);
        feeDetailRequset.setHouseId(houseId);
//        feeDetailRequset.setDivideId("7");
//        feeDetailRequset.setHouseId("67605181830795270");
        viewModel.getPaymentAdvanceList(feeDetailRequset).observe(this, model -> {

            if (model.getData() == null) {
                return;
            }
            mData = model.getData().getDataList();

            if (mData != null) {

                if (mData.size() == 0) {
                    binding.rlEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.rlEmpty.setVisibility(View.GONE);
                }
                adapter.setDataList(mData);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initToallMoney2(mData);
                    }
                },1000);
            }

        });
    }

    private String allName = "";

    private void initAdapter() {
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemPaymentAdvanceListBinding, PaymentAdvanceModel2.DataBean.DataListBean>(this, com.einyun.app.pms.toll.BR.payadvance) {


                @Override
                public void onBindItem(ItemPaymentAdvanceListBinding listBinding, PaymentAdvanceModel2.DataBean.DataListBean item, int position) {

                    listBinding.tvParkNum.setText(item.getParkingName() + item.getParkingNum());
                    listBinding.tvMonthTimes.setText(item.getMonths() + "");
                    clientName = item.getClientName();
                    if (clientName.length() >= 2) {
                        String firstName = clientName.substring(0, 1);
                        if (clientName.length() == 2) {
                            allName = firstName + "*";
                        } else if (clientName.length() == 3) {
                            allName = firstName + "**";
                        } else if (clientName.length() == 4) {
                            allName = firstName + "***";
                        }

                    }
                    String s = allName + "(" + divideName + title + ")";
                    binding.tvName.setText(s);
                    listBinding.ischeck.setChecked(item.isCheck());
                    listBinding.tvItemName.setText(item.getFeeItemName());
                    item.setChargeAmount(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString())));
//                    listBinding.tvAdvanceMoney.setText(item.getUnitPrice().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
                    listBinding.tvAdvanceMoney.setText(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString())).setScale(2, BigDecimal.ROUND_HALF_UP) + "");

                    listBinding.tvResidueAmount.setText("可用余额:" + item.getResidueAmount().setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
                    /**
                     * 预缴项目类型FeeAttribute
                     * 1:水费,
                     * 2:车位服务费,
                     * 3:临时收费,
                     * 4:物业服务费,
                     * 5:电费,
                     * 6:押金,
                     **/
                    if (item.getFeeIAttribute().equals("2")) {
                        listBinding.tvParkNum.setVisibility(View.VISIBLE);
                    } else {
                        listBinding.tvParkNum.setVisibility(View.GONE);
                        listBinding.tvParkNum.setText(item.getParkingName() + item.getParkingNum());
                    }


                    listBinding.tvAdd.setOnClickListener(view -> {
                        int integer = Integer.valueOf(listBinding.tvMonthTimes.getText().toString());
                        integer = integer + 1;

                        item.setMonths(integer);
                        listBinding.tvMonthTimes.setText(integer + "");
                        item.setChargeAmount(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString())));
//                        listBinding.tvAdvanceMoney.setText(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString()))+"");
                        adapter.notifyDataSetChanged();
                        initToallMoney();
                    });
                    listBinding.tvReduce.setOnClickListener(view -> {
                        int integer = Integer.valueOf(listBinding.tvMonthTimes.getText().toString());
                        if (integer == 1) {
                            ToastUtil.show(PaymentAdvanceActivity.this, "月份不能小于零");
                            return;
                        }
                        integer = integer - 1;
                        item.setMonths(integer);
                        listBinding.tvMonthTimes.setText(integer + "");
                        item.setChargeAmount(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString())));
//                        listBinding.tvAdvanceMoney.setText(item.getUnitPrice().multiply(new BigDecimal(listBinding.tvMonthTimes.getText().toString()))+"");
                        adapter.notifyDataSetChanged();
                        initToallMoney();
                    });
                    listBinding.tvChangeMoney.setOnClickListener(view -> {
                        InputMoneyPopWindow inputMoneyPopWindow = new InputMoneyPopWindow(PaymentAdvanceActivity.this, position);
//                        inputMoneyPopWindow.setOnItemClickListener(PaymentAdvanceActivity.this);
                        if (!inputMoneyPopWindow.isShowing()) {
                            inputMoneyPopWindow.showAsDropDown(binding.view);
                        }
                        inputMoneyPopWindow.setOnItemClickListener(new InputMoneyPopWindow.OnItemClickListener() {
                            @Override
                            public void onData(String cate, int position) {
                                if (cate.isEmpty()) {
                                    ToastUtil.show(PaymentAdvanceActivity.this, "输入不能为空");
                                    return;
                                }
                                if (Double.valueOf(cate) == 0) {
                                    ToastUtil.show(PaymentAdvanceActivity.this, "输入不能为0");
                                    return;
                                }
                                item.setChargeAmount(new BigDecimal(cate));
                                listBinding.tvAdvanceMoney.setText(new BigDecimal(cate).setScale(2, RoundingMode.HALF_UP) + "");
                                initToallMoney();
                                inputMoneyPopWindow.dismiss();
                            }
                        });
                    });


                    listBinding.ischeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                item.setCheck(true);

                            } else {
                                item.setCheck(false);
                            }
                            initToallMoney();

                        }
                    });

                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_payment_advance_list;
                }
            };
        }
        binding.list.setLayoutManager(new LinearLayoutManager(PaymentAdvanceActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.list.setFocusable(false);
        binding.list.setAdapter(adapter);
    }

    private void initToallMoney() {
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (PaymentAdvanceModel2.DataBean.DataListBean datum : mData) {
            if (datum.isCheck()) {
//                                    bigDecimal= bigDecimal.add(new BigDecimal(listBinding.tvAdvanceMoney.getText().toString()));
                bigDecimal = bigDecimal.add(datum.getChargeAmount());
            }
        }
        binding.tvToallMoney.setText(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
    }

    private void initToallMoney2(List<PaymentAdvanceModel2.DataBean.DataListBean> mData) {
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (PaymentAdvanceModel2.DataBean.DataListBean datum : mData) {
//            if (datum.isCheck()) {
//                                    bigDecimal= bigDecimal.add(new BigDecimal(listBinding.tvAdvanceMoney.getText().toString()));
//                bigDecimal= bigDecimal.add(datum.getUnitPrice());
                bigDecimal = bigDecimal.add(datum.getUnitPrice().multiply(new BigDecimal(datum.getMonths())));
//            }
        }
        binding.tvToallMoney.setText(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

    private static final String TAG = "PaymentAdvanceActivity";

}
