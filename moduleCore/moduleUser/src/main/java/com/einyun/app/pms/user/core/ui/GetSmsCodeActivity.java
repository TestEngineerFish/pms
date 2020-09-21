package com.einyun.app.pms.user.core.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.http.BaseResponse;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.CheckUtil;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityGetSmsCodeBinding;

@Route(path = RouterUtils.ACTIVITY_GET_SMS)
public class GetSmsCodeActivity extends BaseHeadViewModelActivity<ActivityGetSmsCodeBinding, UserViewModel> {
    @Autowired(name = "phone")
    String phone;
    @Autowired(name = "account")
    String account;
    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_sms_code;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("密码修改");
        binding.cpPhone.setText(phone.substring(0,3)+"****"+phone.substring(7,11));
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.getCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckNum();
            }
        });

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNum();
            }
        });
    }

    /**
     * 校验验证码
     */
    public void checkNum() {
        if (TextUtils.isEmpty(binding.checkNum.getText().toString())) {
            ToastUtil.show(this, "请输入验证码");
        } else {
            viewModel.checkNum(phone, binding.checkNum.getText().toString()).observe(this, data -> {
                if (data!=null&&data.isState()){
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_CHANGE_PASS).withString("phone",phone).withString("code",binding.checkNum.getText().toString()).withString("account",account).navigation();
                }else {
                    ToastUtil.show(this,data.getMsg());
                }
            });
        }
    }

    /**
     * 获取验证码
     */
    public void getCheckNum() {
            viewModel.getCheckNum(phone).observe(this, data -> {
                if (data != null && data.isState()) {
                    countDownTime(binding.getCheck);
                } else {
                    ToastUtil.show(this, data.getMsg());
                }
            });
    }

    /**
     * 倒计时
     */
    private void countDownTime(Button view) {
        //用安卓自带的CountDownTimer实现
        CountDownTimer mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setText(millisUntilFinished / 1000 + getResources().getString(R.string.txt_check_count));
            }

            @Override
            public void onFinish() {
                view.setEnabled(true);
                view.setText("重新获取");
                view.setTextColor(getResources().getColor(R.color.user_privacy_bottom_btn_color));
                cancel();
            }
        };
        mTimer.start();
        view.setEnabled(false);
        view.setTextColor(getResources().getColor(R.color.grey_c7));
    }
}