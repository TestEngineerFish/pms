package com.einyun.app.pms.toll.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityLackDetailViewModelBinding;
import com.einyun.app.pms.toll.databinding.ActivityTollViewModelBinding;
import com.einyun.app.pms.toll.databinding.ItemLackRecordBinding;
import com.einyun.app.pms.toll.model.CreateOrderRequest;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.LackDetailModel;
import com.einyun.app.pms.toll.model.TollModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Route(path = RouterUtils.ACTIVITY_LACK_DETAIL)
public class LackDetailViewModelActivity extends BaseHeadViewModelActivity<ActivityLackDetailViewModelBinding, TollViewModel> {
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String  divideName;
    @Autowired(name = RouteKey.HOUSE_TITLE)
    String  title;
    @Autowired(name = RouteKey.HOUSE_ID)
    String  houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String  divideId;
    @Autowired(name = RouteKey.NAME)
    String  houseName;
    private AlertDialog alertDialog;
    private RVBindingAdapter<ItemLackRecordBinding,LackDetailModel.DataBean.UrgeListBean > adapter;
    private LackDetailModel.DataBean data;
    private String houseIdJump;
    private List<TollModel.DataBean.PaymentList> paymentLists=new ArrayList<>();
    private ArrayList<JumpRequest.FeeListBean>  feeLists = new ArrayList<>();
    private ArrayList<CreateOrderRequest.PaymentDetailsBean>  paymentDetailsBeans = new ArrayList<>();;
    List<LackDetailModel.DataBean.UrgeListBean> urgeThreeList=new ArrayList<>();
    String clientName="";
    private String startName="";
    private String allName;
    private String clientPhone="";
    private String starsPhone="";
//    public  static LackDetailViewModelActivity instance;
    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lack_detail_view_model;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        headBinding.tvHeaderTitle.setText(title);
//        instance=this;
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
//        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        FeeDetailRequset feeDetailRequset = new FeeDetailRequset();
        feeDetailRequset.setDivideId(divideId);
        feeDetailRequset.setHouseId(houseId);
        viewModel.queryFeeDetail(feeDetailRequset).observe(this,model->{


            data = model.getData();
            if (data==null) {
                binding.rlLoadMore.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
                binding.tvToallFee.setText("0元");
                return;
            }

            clientName=data.getClientName();
            clientPhone = data.getClientPhone();
            if (clientName.length()>=2) {
                String firstName = clientName.substring(0, 1);
                if (clientName.length() == 2) {
                    startName = firstName + "*";
                } else if (clientName.length() == 3) {
                    startName = firstName + "**";
                } else {
                    startName = firstName + "***";
                }
            }
            if (clientPhone!=null) {
                String substring = clientPhone.substring(0, 3);
                String substring2 = clientPhone.substring(7, clientPhone.length());
                starsPhone = substring+"****"+substring2;
            }

            binding.tvWorkNum.setText(startName);
            binding.tvPhone.setText(starsPhone);
            binding.tvStartMonth.setText(data.getArrearsMonth());
            binding.tvToallFee.setText(data.getFeeAmount()+"元");
            houseIdJump = data.getHouseId();
            if (data.getFeeAmount()==0.00||data.getFeeAmount()==0.0||data.getFeeAmount()==0) {
                binding.rlLoadMore.setVisibility(View.GONE);
                binding.llPass.setVisibility(View.GONE);
            }


            initHistoryList(data.getUrgeList());
            String name = binding.tvWorkNum.getText().toString();
            allName = name + "(" + divideName + title + ")";
        });
    }

    private void initHistoryList(List<LackDetailModel.DataBean.UrgeListBean> urgeList) {
        //一级列表适配器
        adapter = new RVBindingAdapter<ItemLackRecordBinding, LackDetailModel.DataBean.UrgeListBean>(this, com.einyun.app.pms.toll.BR._all) {
            @Override
            public void onBindItem(ItemLackRecordBinding bind, LackDetailModel.DataBean.UrgeListBean model, int position) {
                if (position == 0) {
                    bind.ivFirst.setVisibility(View.INVISIBLE);
                } else {
                    bind.ivFirst.setVisibility(View.VISIBLE);
                }
                if (position == adapter.getDataList().size() - 1) {
                    bind.itemHistroyImg.setVisibility(View.INVISIBLE);
                } else {
                    bind.itemHistroyImg.setVisibility(View.VISIBLE);
                }
                bind.tvContent.setText(model.getRemark());
                String urgeDate = model.getUrgeDate();
                String[] s = urgeDate.split(" ");
                if (s.length>=2) {
                    String s1 = s[0];
                    String s2 = s[1];
                    String substring = s1.substring(5, 10);
                    String substring2 = s2.substring(0, 5);

                    bind.tvTime.setText(substring+" "+substring2);
                }

            }

            @Override
            public int getLayoutId() {
                return R.layout.item_lack_record;
            }

        };
        urgeThreeList.clear();
        if (urgeList.size()>3) {//只展示6条
            binding.rlLoadMoreRecord.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                urgeThreeList.add(urgeList.get(i));
            }
            adapter.setDataList(urgeThreeList);
        }else {
            binding.rlLoadMoreRecord.setVisibility(View.GONE);
            adapter.setDataList(urgeList);
        }
        binding.rlLoadMoreRecord.setOnClickListener(view -> {
            adapter.setDataList(urgeList);
            binding.rlLoadMoreRecord.setVisibility(View.GONE);
        });
        binding.listHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.listHistory.setAdapter(adapter);

    }
    /**
     * 催缴
     */
    public void onRejectClick(){

        ArrayList<String> builds = new ArrayList<>();
        builds.add(houseId);
        FeeRequset feeRequset = new FeeRequset();
        feeRequset.setDivideId(divideId);
        feeRequset.setHouseIdS(builds);
        viewModel.allWorth(feeRequset).observe(this,model->{
            if (model.getCode()==0) {
                HashMap<String, String> map = new HashMap<>();
                map.put("user_name", UserUtil.getUserName());
                MobclickAgent.onEvent(this,CustomEventTypeEnum.SINGLE_WORTH.getTypeName(),map);
            if (alertDialog == null) {
                alertDialog = new AlertDialog(this).builder().setTitle(getResources().getString(R.string.tip))
                        .setMsg("催缴消息已发送成功！")
                        .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getData();
                            }
                        });
                alertDialog.show();
            } else {
                if (!alertDialog.isShowing()) {
                    alertDialog.show();
                }
            }

            }
        });
    }
    /**
     * 收费
     */
    public void onPassClick(){

        if (data==null) {
            return;
        }
        FeeDetailRequset feeDetailRequset = new FeeDetailRequset();
//        feeDetailRequset.setDivideId(divideId);
        feeDetailRequset.setHouseId(data.getHouseId());
        viewModel.getLackDetailList(feeDetailRequset).observe(this,model->{


            if (model.getData()!=null) {
                if (model.getData().getPaymentList()!=null) {
                    paymentLists = model.getData().getPaymentList();
                }
            }
            checkJumpVerity();

        });


    }
    private void checkJumpVerity() {

        feeLists.clear();
        for (TollModel.DataBean.PaymentList paymentList : paymentLists) {
            for (TollModel.DataBean.PaymentList.ListBean listBean : paymentList.getList()) {
                    feeLists.add(new JumpRequest.FeeListBean(listBean.getReceivableId()));
            }
        }
        JumpRequest jumpRequest = new JumpRequest();
        jumpRequest.setDivideId(divideId);
        jumpRequest.setHouseId(data.getHouseId());
        jumpRequest.setFeeList(feeLists);
        if (feeLists.size()==0) {
            ToastUtil.show(this,"请选择收费项");
            return;
        }
        viewModel.jumpVerify(jumpRequest).observe(this,model->{


//            ToastUtil.show(LackDetailViewModelActivity.this,""+model.isData());
            if (!model.isData()) {//可以生成订单
                paymentDetailsBeans.clear();
                CreateOrderRequest createOrderRequest = new CreateOrderRequest();
                createOrderRequest.setDivideId(divideId);
                createOrderRequest.setHouseId(data.getHouseId());
                createOrderRequest.setHouseName(houseName);
                createOrderRequest.setUserId(viewModel.getUserId());
                createOrderRequest.setPayOrderType("pty-101");
                createOrderRequest.setPayAmount(data.getFeeAmount());
//                createOrderRequest.setPayAmount(0.01);
                for (TollModel.DataBean.PaymentList paymentList : paymentLists) {
                    for (TollModel.DataBean.PaymentList.ListBean listBean : paymentList.getList()) {
//                        if (listBean.isCheckChirld()) {
                            CreateOrderRequest.PaymentDetailsBean paymentDetailsBean = new CreateOrderRequest.PaymentDetailsBean();
                            paymentDetailsBean.setChargeAmount(listBean.getTotalReceivableAmount());
//                            paymentDetailsBean.setChargeAmount(new BigDecimal("0.01"));
                            paymentDetailsBean.setChargeReceivableId(listBean.getReceivableId());
                            paymentDetailsBean.setChargeTypeCode(paymentList.getChargeTypeCode());
                            paymentDetailsBeans.add(paymentDetailsBean);
//                        }

                    }
                }
                createOrderRequest.setPaymentDetails(paymentDetailsBeans);




                viewModel.createOrder(createOrderRequest).observe(this,createModel->{

                    if (createModel.getData()!=0) {//创建订单成功  跳转二维码界面生成二维码
                        ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE)
                                .withInt(RouteKey.KEY_ORDER_ID,createModel.getData())
                                .withString(RouteKey.KEY_DIVIDE_NAME,divideName)
                                .withString(RouteKey.HOUSE_TITLE,title)
                                .withString(RouteKey.KEY_ALL_NAME,allName)
                                .withString(RouteKey.KEY_CLIENT_NAME,data.getClientName())
                                .withString("MONEY",data.getFeeAmount()+"")
                                .navigation();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("user_name",UserUtil.getUserName());
                        MobclickAgent.onEvent(this, CustomEventTypeEnum.FEE_DETAIL.getTypeName(),map);
                    }

                });
            }

        });
    }
    /**
     * 欠费详单
     */
    public void onDetailListClick(){
        ARouter.getInstance().build(RouterUtils.ACTIVITY_LACK_LIST)
                .withString(RouteKey.HOUSE_ID,houseIdJump)
                .withString(RouteKey.KEY_DIVIDE_ID,divideId)
                .withString(RouteKey.NAME,houseName)
                .withString(RouteKey.KEY_DIVIDE_NAME,divideName)
                .withString(RouteKey.KEY_ALL_NAME,allName)
                .withString(RouteKey.KEY_CLIENT_NAME,startName)
                .withString(RouteKey.HOUSE_TITLE,title)
                .navigation();
    }


    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
