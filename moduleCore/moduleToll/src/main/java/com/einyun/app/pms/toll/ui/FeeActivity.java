package com.einyun.app.pms.toll.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.StatusBarcompUtils;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityFeeBinding;
import com.einyun.app.pms.toll.databinding.ActivityLackListBinding;
import com.einyun.app.pms.toll.model.HouseModel;
import com.einyun.app.pms.toll.model.JumpRequest;
import com.einyun.app.pms.toll.model.QrCodeRequest;
import com.einyun.app.pms.toll.model.QueryOrderStateRequest;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.einyun.app.pms.toll.constants.URLS.URL_GET_FEE_QR_CODE;


@Route(path = RouterUtils.ACTIVITY_FEE)
public class FeeActivity extends BaseHeadViewModelActivity<ActivityFeeBinding, TollViewModel> {
    @Autowired(name = RouteKey.KEY_DIVIDE_NAME)
    String  divideName;
    @Autowired(name = RouteKey.KEY_ALL_NAME)
    String  allName;
    @Autowired(name = RouteKey.KEY_CLIENT_NAME)
    String  clientName;
    @Autowired(name = RouteKey.HOUSE_TITLE)
    String  title;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    int  orderId;
    @Autowired(name = "MONEY")
    String  money;
    private static Timer timer;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fee;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        StatusBarcompUtils.compatpicture(this);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle("二维码收款");
        binding.setCallBack(this);
        binding.tvTitle.setText("二维码收款");
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        binding.tvHouseName.setText("尊敬的"+clientName+"业主"+"("+title+")");
        binding.tvMoney.setText("￥"+money);
        String baseUrl = BuildConfig.BASE_FEE_URL;
        String key_tenant_id = (String) SPUtils.get(this, "KEY_TENANT_ID", "");
        String key_token = (String) SPUtils.get(this, "KEY_TOKEN", "");
        GlideUrl url = new GlideUrl(baseUrl+URL_GET_FEE_QR_CODE+orderId, new LazyHeaders.Builder()//debug
//        GlideUrl url = new GlideUrl("https://fee.einyun.com/fee-center-api/payInfo/getQRCode?orderId="+orderId, new LazyHeaders.Builder()//relase
                .addHeader("Authorization","Bearer "+key_token )
                .addHeader("tenant-id",key_tenant_id)
                .build());
        Glide.with(this).load(url).into(binding.ivQrCode);
    }


    @Override
    protected void initData() {
        super.initData();

//        binding.tvMoney.setOnClickListener(view -> {
//            ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE_SUC)
//                    .withInt(RouteKey.KEY_ORDER_ID,orderId)
//                    .navigation();
//        });
        timer3();
//        QueryOrderStateRequest queryOrderStateRequest = new QueryOrderStateRequest();
//        queryOrderStateRequest.setId(orderId+"");

    }

    private void queryState() {
        viewModel.queryOrderState(orderId).observe(this,model->{

            if (model.getPayStatus()==2) {//已付款
                ARouter.getInstance().build(RouterUtils.ACTIVITY_FEE_SUC)
                        .withString(RouteKey.KEY_ORDER_ID,model.getOpenId())
                        .withString(RouteKey.KEY_DIVIDE_NAME,divideName)
                        .withString(RouteKey.KEY_ALL_NAME,allName)
                        .withString(RouteKey.HOUSE_TITLE,title)
                        .navigation();
                timer.cancel();
                LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
//                if (PaymentAdvanceActivity.instance!=null) {
//                    PaymentAdvanceActivity.instance.finish();
//                }
//                if (LackListActivity.instance!=null) {
//                    LackListActivity.instance.finish();
//                }
                finish();

            }
        });
    }

    public  void timer3() {

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryState();
                    }
                });

                System.err.println("-------延迟5000毫秒，每1000毫秒执行一次--------");

            }

        }, 500, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }

}
