package com.einyun.app.pms.sendorder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.LiveDataBusKey;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.manager.CustomEventTypeEnum;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.service.user.IUserModuleService;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.UserUtil;
import com.einyun.app.library.resource.workorder.net.request.ResendOrderRequest;
import com.einyun.app.library.workorder.net.response.GetMappingByUserIdsResponse;
import com.einyun.app.pms.sendorder.R;
import com.einyun.app.pms.sendorder.databinding.ActivityResendOrderBinding;
import com.einyun.app.pms.sendorder.viewmodel.SendOdViewModelFactory;
import com.einyun.app.pms.sendorder.viewmodel.SendOrderViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

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
    @Autowired(name = RouteKey.KEY_CUSTOMER_RESEND_ORDER)
    String reSendKey;
    @Autowired(name = RouteKey.KEY_CUSTOM_TYPE)
    String customType;

    @Override
    protected SendOrderViewModel initViewModel() {
        return new ViewModelProvider(this, new SendOdViewModelFactory()).get(SendOrderViewModel.class);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        ARouter.getInstance().inject(this);
        if (!StringUtil.isNullStr(reSendKey)) {
            setHeadTitle(R.string.text_resend_order);
        } else {
            setHeadTitle(R.string.text_resend_cus_order);
        }
        resendOrderRequest = new ResendOrderRequest();
        LiveEventBus.get(LiveDataBusKey.POST_RESEND_ORDER_USER, GetMappingByUserIdsResponse.class).observe(this, model -> {
            binding.resendName.setText(model.getFullname());
            resendOrderRequest.setId(orderId);
            resendOrderRequest.setOpinion(binding.resendOrderReason.getString());
            resendOrderRequest.setTaskId(taskId);
            resendOrderRequest.setUserId(model.getId());
            resendOrderRequest.setUserName(model.getFullname());
            if (!reSendKey.isEmpty()) {
                resendOrderRequest.setMessageType("inner,app_push");//?????????
            }
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

        if (v.getId() == R.id.resend_submit_btn) {
            if (TextUtils.isEmpty(resendOrderRequest.getUserName())) {
                ToastUtil.show(ResendOrderActivity.this, R.string.txt_plese_select_people);
            } else if (TextUtils.isEmpty(binding.resendOrderReason.getString())) {
                ToastUtil.show(ResendOrderActivity.this, R.string.txt_plese_enter_reason);
            } else {
                resendOrderRequest.setOpinion(binding.resendOrderReason.getString());
                if (!StringUtil.isNullStr(reSendKey)) {
                    resendOrderRequest.setMessageType("inner,app_push");
                    viewModel.resendOrder(resendOrderRequest).observe(this, model -> {
                        if (model.getCode().equals("0")) {
                            ToastUtil.show(ResendOrderActivity.this, R.string.resend_success);
                            this.finish();
                        } else {
                            ToastUtil.show(ResendOrderActivity.this, model.getMsg());
                        }
                    });
                } else {//???????????????????????????
                    viewModel.resendCusOrder(resendOrderRequest).observe(this, model -> {
                        if (model.getCode().equals("0")) {
                            ToastUtil.show(ResendOrderActivity.this, R.string.resend_success);
                            LiveEventBus.get(LiveDataBusKey.CUSTOMER_FRAGMENT_REFRESH, Boolean.class).post(true);
                            this.finish();
                        } else {
                            ToastUtil.show(ResendOrderActivity.this, model.getMsg());
                        }
                        if (customType != null) {
                            if (CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName().equals(customType)) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(ResendOrderActivity.this, CustomEventTypeEnum.COMPLAIN_TURN_ORDER.getTypeName(), map);
                            } else if (CustomEventTypeEnum.INQUIRIES_TURN_ORDER.getTypeName().equals(customType)) {

                                HashMap<String, String> map = new HashMap<>();
                                map.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(ResendOrderActivity.this, CustomEventTypeEnum.INQUIRIES_TURN_ORDER.getTypeName(), map);

                            } else if (CustomEventTypeEnum.REPAIR_TURN_ORDER.getTypeName().equals(customType)) {

                                HashMap<String, String> map = new HashMap<>();
                                map.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(ResendOrderActivity.this, CustomEventTypeEnum.REPAIR_TURN_ORDER.getTypeName(), map);
                            } else if (CustomEventTypeEnum.SEND_ORDER_TURN_ORDER.getTypeName().equals(customType)) {

                                HashMap<String, String> map = new HashMap<>();
                                map.put("user_name", UserUtil.getUserName());
                                MobclickAgent.onEvent(ResendOrderActivity.this, CustomEventTypeEnum.SEND_ORDER_TURN_ORDER.getTypeName(), map);
                            }

                        }
                    });
                }
            }
        } else {
            ARouter.getInstance()
                    .build(RouterUtils.ACTIVITY_SELECT_PEOPLE)
                    .withString(RouteKey.KEY_DIVIDE_ID, divideID)
                    .withString(RouteKey.KEY_PROJECT_ID, projectID)
                    .navigation();
        }
    }

}
