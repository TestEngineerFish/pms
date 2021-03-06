package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.mdm.model.GridModel;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityLackListBinding;
import com.einyun.app.pms.toll.databinding.ActivityPaymentAdvanceBinding;
import com.einyun.app.pms.toll.databinding.ItemLackInListBinding;
import com.einyun.app.pms.toll.databinding.ItemLackOutListBinding;
import com.einyun.app.pms.toll.databinding.ItemTollInListBinding;
import com.einyun.app.pms.toll.databinding.ItemTollOutListBinding;
import com.einyun.app.pms.toll.model.BuildModel;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.CreateOrderRequest.PaymentDetailsBean;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeModel;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.LackListModel;
//import com.einyun.app.pms.toll.model.LackListModel.Value.PaymentList;
import com.einyun.app.pms.toll.model.TollModel;
import com.einyun.app.pms.toll.model.TollModel.DataBean.PaymentList;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Route(path = RouterUtils.ACTIVITY_LACK_LIST)
public class LackListActivity extends BaseHeadViewModelActivity<ActivityLackListBinding, TollViewModel> {
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String  divideName;
    @Autowired(name = RouteKey.HOUSE_ID)
    String  houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String  divideId;
    @Autowired(name = RouteKey.NAME)
    String  houseName;
    @Autowired(name = RouteKey.HOUSE_TITLE)
    String  title;
    @Autowired(name = RouteKey.KEY_ALL_NAME)
    String  allName;
    @Autowired(name = RouteKey.KEY_CLIENT_NAME)
    String  clientName;
    RVBindingAdapter<ItemLackOutListBinding, PaymentList> adapter;
    RVBindingAdapter<ItemLackInListBinding, PaymentList.ListBean> inAdapter;
    private List<PaymentList> paymentLists=new ArrayList<>();
    private ArrayList<JumpRequest.FeeListBean>  feeLists = new ArrayList<>();;
    private ArrayList<PaymentDetailsBean>  paymentDetailsBeans = new ArrayList<>();;
    private List<PaymentList.ListBean> inListDatas=new ArrayList<>();
    private List<PaymentList.ListBean> inListThreeDatas=new ArrayList<>();
    private AlertDialog alertAddDialog;

    //    public  static LackListActivity instance;
    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lack_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle("????????????");

//        instance=this;

        FeeSucFinish();
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
 * ??????
 */

    public  void onSubmitClick(){
        if (StringUtil.isEmpty(clientName)) {

            if (alertAddDialog == null) {
                alertAddDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                        .setMsg("???????????????????????????????????????????????????")
                        .setNegativeButton("????????????", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                feeLists.clear();
                                for (PaymentList paymentList : paymentLists) {
                                    for (PaymentList.ListBean listBean : paymentList.getList()) {
                                        if (listBean.isCheckChirld()) {
                                            feeLists.add(new JumpRequest.FeeListBean(listBean.getReceivableId()));
                                        }

                                    }
                                }
                                checkJumpVerity();
                            }
                        })
                        .setPositiveButton("????????????", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ARouter.getInstance().build(RouterUtils.ACTIVITY_ADD_HOUSER)
                                        .withString(RouteKey.HOUSE_ID, houseId)
                                        .withString(RouteKey.KEY_DIVIDE_ID, divideId)
                                        .navigation();
                            }
                        });
                alertAddDialog.show();
            } else {
                if (!alertAddDialog.isShowing()) {
                    alertAddDialog.show();
                }
            }
        } else {
            feeLists.clear();
            for (PaymentList paymentList : paymentLists) {
                for (PaymentList.ListBean listBean : paymentList.getList()) {
                    if (listBean.isCheckChirld()) {
                        feeLists.add(new JumpRequest.FeeListBean(listBean.getReceivableId()));
                    }

                }
            }
            checkJumpVerity();
        }

//        feeLists.clear();
//        for (PaymentList paymentList : paymentLists) {
//            for (PaymentList.ListBean listBean : paymentList.getList()) {
//                if (listBean.isCheckChirld()) {
//                    feeLists.add(new JumpRequest.FeeListBean(listBean.getReceivableId()));
//                }
//
//            }
//        }
//        checkJumpVerity();

    }
    private void checkJumpVerity() {
        JumpRequest jumpRequest = new JumpRequest();
        jumpRequest.setDivideId(divideId);
        jumpRequest.setHouseId(houseId);
        jumpRequest.setFeeList(feeLists);

        if (feeLists.size()==0) {
            ToastUtil.show(this,"??????????????????");
            return;
        }
        viewModel.jumpVerify(jumpRequest).observe(this,model->{


//            ToastUtil.show(LackListActivity.this,""+model.isData());
            if (!model.isData()) {//??????????????????
                paymentDetailsBeans.clear();
                CreateOrderRequest createOrderRequest = new CreateOrderRequest();
                createOrderRequest.setDivideId(divideId);
                createOrderRequest.setHouseId(houseId);
                createOrderRequest.setHouseName(houseName);
                createOrderRequest.setUserId(viewModel.getUserId());
                createOrderRequest.setPayOrderType("pty-101");
                createOrderRequest.setPayAmount(Double.valueOf(binding.tvToallMoney.getText().toString()));
//                createOrderRequest.setPayAmount(0.01);
                for (PaymentList paymentList : paymentLists) {
                    for (PaymentList.ListBean listBean : paymentList.getList()) {
                        if (listBean.isCheckChirld()) {
                            PaymentDetailsBean paymentDetailsBean = new PaymentDetailsBean();
                            paymentDetailsBean.setChargeAmount(listBean.getTotalReceivableAmount());
//                            paymentDetailsBean.setChargeAmount(new BigDecimal("0.01"));
                            paymentDetailsBean.setChargeReceivableId(listBean.getReceivableId());
                            paymentDetailsBean.setChargeTypeCode(paymentList.getChargeTypeCode());
                            paymentDetailsBeans.add(paymentDetailsBean);
                        }

                    }
                }
                createOrderRequest.setPaymentDetails(paymentDetailsBeans);




                viewModel.createOrder(createOrderRequest).observe(this,createModel->{

                    if (createModel.getData()!=0) {//??????????????????  ????????????????????????????????????

                        Log.e(TAG, "checkJumpVerity: "+createModel.getData());
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE)
                                .withInt(RouteKey.KEY_ORDER_ID,createModel.getData())
                                .withString("MONEY",binding.tvToallMoney.getText().toString())
                                .withString(RouteKey.KEY_DIVIDE_NAME,divideName)
                                .withString(RouteKey.KEY_ALL_NAME,allName)
                                .withString(RouteKey.KEY_CLIENT_NAME,clientName)
                                .withString(RouteKey.HOUSE_TITLE,title)
                                .navigation();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("user_name", UserUtil.getUserName());
                        MobclickAgent.onEvent(this, CustomEventTypeEnum.FEE_DETAIL_LIST.getTypeName(),map);
                        finish();
                    }

                });
            }else {
                ToastUtil.show(LackListActivity.this,"??????????????????????????????");
            }

        });
    }

    private static final String TAG = "LackListActivity";
    private void toallMoney() {
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (PaymentList paymentList : paymentLists) {
            for (PaymentList.ListBean listBean : paymentList.getList()) {
                if (listBean.isCheckChirld()) {
                    bigDecimal=bigDecimal.add(listBean.getTotalReceivableAmount());
                    feeLists.add(new JumpRequest.FeeListBean(listBean.getReceivableId()));
                }

            }
        }
        binding.tvToallMoney.setText(bigDecimal+"");
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        binding.outList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        FeeDetailRequset feeDetailRequset = new FeeDetailRequset();
//        feeDetailRequset.setDivideId(divideId);
        feeDetailRequset.setHouseId(houseId);
        viewModel.getLackDetailList(feeDetailRequset).observe(this,model->{


            if (model.getData()!=null) {
                if (model.getData().getPaymentList()!=null) {
                     paymentLists = model.getData().getPaymentList();
                    adapter.setDataList(paymentLists);
                    toallMoney();
                }
            }

        });

        binding.cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isPressed()) {
                    return;
                }
                if (b) {
                    for (PaymentList paymentList : paymentLists) {
                        paymentList.setCheckParent(true);
                        for (PaymentList.ListBean listBean : paymentList.getList()) {
                            listBean.setCheckChirld(true);
                        }
                    }
                }else {
                    for (PaymentList paymentList : paymentLists) {
                        paymentList.setCheckParent(false);
                        for (PaymentList.ListBean listBean : paymentList.getList()) {
                            listBean.setCheckChirld(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                toallMoney();
            }

        });
        initAdapter();
    }

    private void initAdapter() {
        if(adapter==null){
            adapter=new RVBindingAdapter<ItemLackOutListBinding, PaymentList>(this, com.einyun.app.pms.toll.BR.outList){


                /**
                 * ??????????????????FeeAttribute
                 * 1:??????,
                 * 2:???????????????,
                 * 3:????????????,
                 * 4:???????????????,
                 * 5:??????,
                 * 6:??????,
                 **/
                @Override
                public void onBindItem(ItemLackOutListBinding outListBinding, PaymentList itemParentModel,int outPosition) {
                    outListBinding.tvItemName.setText(itemParentModel.getChargeTypeName());
                    outListBinding.tvToallFee.setText(itemParentModel.getFeeTotal()+"");
                    if (itemParentModel.getChargeTypeCode().equals("2")) {//?????????
                        outListBinding.ivItem.setImageResource(R.drawable.iv_park_fee);
                        outListBinding.tvPark.setVisibility(View.VISIBLE);
                    }else if (itemParentModel.getChargeTypeCode().equals("4")){//?????????
                        outListBinding.tvPark.setVisibility(View.GONE);
                        outListBinding.ivItem.setImageResource(R.drawable.iv_property_fee);
                    }else if (itemParentModel.getChargeTypeCode().equals("1")){//??????
                        outListBinding.tvPark.setVisibility(View.GONE);
                        outListBinding.ivItem.setImageResource(R.drawable.iv_water_fee);
                    }else if (itemParentModel.getChargeTypeCode().equals("5")){//??????
                        outListBinding.tvPark.setVisibility(View.GONE);
                        outListBinding.ivItem.setImageResource(R.drawable.iv_electricity_fee);
                    }else {
                        outListBinding.ivItem.setImageResource(R.drawable.iv_other_fee);
                        outListBinding.tvPark.setVisibility(View.GONE);
                    }
                    outListBinding.tvPark.setText(itemParentModel.getParkingNum());
                    outListBinding.cbOutCheck.setChecked(itemParentModel.isCheckParent());
                    outListBinding.cbOutCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (!compoundButton.isPressed()) {

                                return;
                            }
                            if (b) {
                                itemParentModel.setCheckParent(true);
                                for (PaymentList.ListBean listBean : paymentLists.get(outPosition).getList()) {
                                    listBean.setCheckChirld(true);
                                }
                            }else {
                                itemParentModel.setCheckParent(false);
                                for (PaymentList.ListBean listBean : paymentLists.get(outPosition).getList()) {
                                    listBean.setCheckChirld(false);
                                }

                            }
                            binding.cbCheckAll.setChecked(true);
                            for (PaymentList paymentList : paymentLists) {
                                if (!paymentList.isCheckParent()) {
                                    binding.cbCheckAll.setChecked(false);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            toallMoney();
                        }
                    });

                    inAdapter=new RVBindingAdapter<ItemLackInListBinding, PaymentList.ListBean>(LackListActivity.this, com.einyun.app.pms.toll.BR.inList){

                        @Override
                        public void onBindItem(ItemLackInListBinding inListBinding, PaymentList.ListBean itemChirldModel, int inPosition) {
                            inListBinding.tvMonth.setText(itemChirldModel.getMonth());
                            inListBinding.tvFee.setText(itemChirldModel.getTotalReceivableAmount()+"");
                            inListBinding.tvCycleTime.setText("???????????????"+ TimeUtil.getYMdTime(itemChirldModel.getCycleStartDate()) +"???"+TimeUtil.getYMdTime(itemChirldModel.getCycleEndDate()));


                            inListBinding.cbInCheck.setChecked(itemChirldModel.isCheckChirld());

                            inListBinding.cbInCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                    if (b) {
                                        itemChirldModel.setCheckChirld(true);//??????????????????

                                        outListBinding.cbOutCheck.setChecked(true);
                                        itemParentModel.setCheckParent(true);
                                        for (PaymentList.ListBean listBean : itemParentModel.getList()) {
                                            Log.e("", "onCheckedChanged: "+listBean.isCheckChirld() );
                                            if (!listBean.isCheckChirld()) {
                                                outListBinding.cbOutCheck.setChecked(false);
                                                itemParentModel.setCheckParent(false);
                                            }
                                        }

                                    }else {
                                        itemChirldModel.setCheckChirld(false);//??????????????????
                                        itemParentModel.setCheckParent(false);

                                        outListBinding.cbOutCheck.setChecked(false);

                                    }
                                    binding.cbCheckAll.setChecked(true);
                                    for (PaymentList paymentList : paymentLists) {
                                        if (!paymentList.isCheckParent()) {
                                            binding.cbCheckAll.setChecked(false);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                    toallMoney();

                                }

                            });


                        }
                        @Override
                        public int getLayoutId() {
                            return R.layout.item_lack_in_list;
                        }
                    };
                    outListBinding.inList.setLayoutManager(new LinearLayoutManager(LackListActivity.this, LinearLayoutManager.VERTICAL, false));

                    outListBinding.inList.setAdapter(inAdapter);
                    outListBinding.inList.setFocusable(false);
                    inListThreeDatas.clear();
                    if (itemParentModel.getList()!=null) {

                        if (itemParentModel.getList().size()>3) {//?????????6???
                            if (itemParentModel.isLoreMore) {//?????????????????????
                                outListBinding.tvMore.setVisibility(View.VISIBLE);
                                for (int i = 0; i < 3; i++) {
                                    inListThreeDatas.add(itemParentModel.getList().get(i));
                                }
                                inAdapter.setDataList(inListThreeDatas);
                            }else {
                                if (outListBinding.tvMore.getText().toString().equals("?????? >")) {

                                    inAdapter.setDataList(itemParentModel.getList().subList(0,3));
                                }else if (outListBinding.tvMore.getText().toString().equals("?????? >")){
                                    inAdapter.setDataList(itemParentModel.getList());
                                }
//                                outListBinding.tvMore.setVisibility(View.GONE);
//                                inAdapter.setDataList(itemParentModel.getList());
                            }
                        }else {
                            outListBinding.tvMore.setVisibility(View.GONE);
                            inAdapter.setDataList(itemParentModel.getList());
                        }
                        outListBinding.tvMore.setOnClickListener(view -> {
//                            inAdapter.setDataList(itemParentModel.getList());
                            itemParentModel.setLoreMore(false);
                            if (outListBinding.tvMore.getText().toString().equals("?????? >")) {
                                outListBinding.tvMore.setText("?????? >");
//                            inAdapter.setDataList(mFeeBuildListSixData);
                            }else if (outListBinding.tvMore.getText().toString().equals("?????? >")){
//                            inAdapter.setDataList(mFeeBuildList2);
                                outListBinding.tvMore.setText("?????? >");
                            }
                            adapter.notifyDataSetChanged();
//                            outListBinding.tvMore.setVisibility(View.GONE);
                        });
                    }



                }
                @Override
                public int getLayoutId() {
                    return R.layout.item_lack_out_list;
                }
            };
        }
        binding.outList.setAdapter(adapter);
//        PaymentList paymentList1 = new PaymentList();
//        ArrayList<PaymentList.ListBean> listBeans1 = new ArrayList<>();
//        PaymentList.ListBean listBean1 = new PaymentList.ListBean();
//        PaymentList.ListBean listBean2 = new PaymentList.ListBean();
//        PaymentList.ListBean listBean3 = new PaymentList.ListBean();
//        PaymentList.ListBean listBean4 = new PaymentList.ListBean();
//        PaymentList.ListBean listBean5 = new PaymentList.ListBean();
//        PaymentList.ListBean listBean6 = new PaymentList.ListBean();
//        listBeans1.add(listBean1);
//        listBeans1.add(listBean2);
//        listBeans1.add(listBean3);
//        paymentList1.setList(listBeans1);
//
//        PaymentList paymentList2 = new PaymentList();
//        ArrayList<PaymentList.ListBean> listBeans2 = new ArrayList<>();
//
//        listBeans2.add(listBean4);
//        listBeans2.add(listBean5);
//        listBeans2.add(listBean6);
//        paymentList2.setList(listBeans2);
//
//        paymentLists = new ArrayList<>();
//        paymentLists.add(paymentList1);
//        paymentLists.add(paymentList2);
//        adapter.setDataList(paymentLists);
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
