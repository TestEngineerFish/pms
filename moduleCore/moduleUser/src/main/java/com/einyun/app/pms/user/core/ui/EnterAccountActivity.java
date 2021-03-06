package com.einyun.app.pms.user.core.ui;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.utils.ToastUtil;
import com.einyun.app.pms.user.R;
import com.einyun.app.pms.user.core.viewmodel.UserViewModel;
import com.einyun.app.pms.user.core.viewmodel.UserViewModelFactory;
import com.einyun.app.pms.user.databinding.ActivityEnterAccountBinding;

@Route(path = RouterUtils.ACTIVITY_ENTER_ACCOUNT)
public class EnterAccountActivity extends BaseHeadViewModelActivity<ActivityEnterAccountBinding, UserViewModel> {
    @Override
    protected UserViewModel initViewModel() {
        return new ViewModelProvider(this, new UserViewModelFactory()).get(UserViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_enter_account;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setHeadTitle("密码修改");
    }

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
    }

    /**
     * 跳转到修改密码界面
     */
    public void changePass() {
        if (TextUtils.isEmpty(binding.etAccount.getText().toString())) {
            ToastUtil.show(this, "请输入账号");
        } else {
            viewModel.getPhone(binding.etAccount.getText().toString()).observe(this, phone -> {
                if (TextUtils.isEmpty(phone)){
                    ToastUtil.show(this,"请联系物业专员维护手机号！");
                }else {
                    ARouter.getInstance().build(RouterUtils.ACTIVITY_GET_SMS).withString("phone",phone)
                            .withString("account",binding.etAccount.getText().toString()).navigation();
                }
            });
        }
    }
}