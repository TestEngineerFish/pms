package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.library.resource.workorder.net.request.ResendOrderRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

@Route(path = RouterUtils.ACTIVITY_RESEND_ORDER)
public class ResendOrderActivity extends BaseHeadViewModelActivity<ActivityResendOrderBinding, SendOrderViewModel> implements View.OnClickListener {
    ResendOrderRequest resendOrderRequest;
    @Autowired(name = RouterUtils.SERVICE_USER)
    IUserModuleService userModuleService;
    @Autowired(name = RouteKey.KEY_TASK_ID)
    String taskId;
    @Autowired(name = RouteKey.KEY_FRAGEMNT_TAG)
    String fragmentTag;
    @Autowired(name = RouteKey.KEY_ORDER_ID)
    String orderId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideID;
    @Autowired(name = RouteKey.KEY_PROJECT_ID)
    String projectID;
    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        setHeadTitle(R.string.text_resend_order);
        resendOrderRequest=new ResendOrderRequest();
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
         binding.resendName.setText(model.getFullname());
         resendOrderRequest.setId(orderId);
         resendOrderRequest.setOpinion(binding.resendOrderReason.getString());
         resendOrderRequest.setTaskId(taskId);
         resendOrderRequest.setUserId(model.getId());
         resendOrderRequest.setUserName(model.getFullname());
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resend_order;
    }



    @Override
    protected void initListener() {
        super.initListener();
        binding.resendOrderTo.setOnClickListener(this);
        binding.resendSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            if (v.getId()==R.id.resend_submit_btn){
                viewModel.resendOrder(resendOrderRequest).observe(this,model->{
                    if (model.getCode().equals("0")){
                        ToastUtil.show(ResendOrderActivity.this,R.string.resend_success);
                        this.finish();
                    }else {
                        ToastUtil.show(ResendOrderActivity.this,model.getMsg());
                    }
                });
            }
        else {
                ARouter.getInstance()
                        .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                        .withString(RouteKey.KEY_DIVIDE_ID,divideID)
                        .withString(RouteKey.KEY_PROJECT_ID,projectID)
                        .navigation();
            }
    }

}
